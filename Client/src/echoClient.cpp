#include <stdlib.h>
#include <connectionHandler.h>
#include "BGS/ProtocolDataStructs.h"
#include "BGS/BguLogin.h"
#include "BGS/BguFollow.h"
#include "BGS/BguPost.h"
#include "BGS/BguRegister.h"
#include "BGS/BguAck.h"
#include "BGS/BguError.h"
#include "BguNotification.h"
#include "BguMessageFactory.h"
#include "BGS/BguAckMessages/BguAckFollow.h"
#include "BGS/BguAckMessages/BguAckUserList.h"
#include "BGS/BguAckMessages/BguAckStat.h"
#include <thread>
#include <unistd.h>

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/

// Ethernet seems like a valid max?
#define MAX_MSG_SIZE 1514

struct opcodeTras
{
    short opcode;
};

static bool keepOnRunning = true;
static bool sentLogout    = false;
void getNewMessages(ConnectionHandler* ch)
{
        char opcodeArr[2] = {};
        bguHeader* curResponse = nullptr;
        while (::keepOnRunning)
        {
            // Trying to get the response opcode
            if (!ch->getBytes(opcodeArr,2)){
                std::cout << "Disconnected. Exiting...\n" << std::endl;
                break;
            }
            else
            {
                // Cast the op
                opcodeTras* tempCast = (opcodeTras*)opcodeArr;
                curResponse = BguMessageFactory::getInstance()->getMessage(ntohs(tempCast->opcode));

                if (curResponse->getMyOPCode() == 9) {
                    bguNotification* bgNot = new bguNotification();
                    delete(curResponse);
                    curResponse = bgNot;
                    char readAck[1];
                    do {
                        ch->getBytes(readAck,1);
                    } 
                    while (!bgNot->decode(readAck[0])); 
                }
                else{
                    char ackErrArr[2] = {};
                     if (!ch->getBytes(ackErrArr,2)){
                         std::cout << "Disconnected. Exiting...\n" << std::endl;
                         break;
                     }

                    if (curResponse->decode(ackErrArr))
                    {
                        // Check if ACKS
                        if (curResponse->getMyOPCode() == 10) {

                        	 // Ack Follow
                        	if (((bguAck*)curResponse)->m_MsgOpcode == 4) {
                                bguAck* tempCast = ((bguAck*) curResponse);
                                bguAckFollow* bgack = new bguAckFollow((*tempCast));
                                delete(curResponse);
                                curResponse = bgack;
                                char readAck[1];
                                do {
                                    ch->getBytes(readAck,1);
                                } 
                                while (!bgack->decode(readAck[0])); 
                            }
                        	else if (((bguAck*)curResponse)->m_MsgOpcode == 7) {
                        		bguAck* tempCast = ((bguAck*) curResponse);
                        		bguAckUserList* bgack = new bguAckUserList((*tempCast));
								delete(curResponse);
								curResponse = bgack;
								char readAck[1];
								do {
									ch->getBytes(readAck,1);
								}
								while (!bgack->decode(readAck[0]));
                        	}
                            else if (((bguAck*)curResponse)->m_MsgOpcode == 8) {
                                bguAck* tempCast = ((bguAck*) curResponse);
                                bguAckStat* bgack = new bguAckStat((*tempCast));
                                delete(curResponse);
                                curResponse = bgack;
                                char readAck[1];
                                do {
                                    ch->getBytes(readAck,1);
                                }
                                while (!bgack->decode(readAck[0]));
                            }
                            else if (((bguAck*)curResponse)->m_MsgOpcode == 3)
                            {
                                ::keepOnRunning = false;
                            }

                        }
                        else if (curResponse->getMyOPCode() == 11)
                        {
                            if (((bguError*)curResponse)->m_MsgOpcode == 4)
                            {
                                ::sentLogout = false;
                            }
                        }
                        
                    }
                }
            }
            std::cout << "\rCLIENT > "  << curResponse->toString() << std::endl;

            if (curResponse != nullptr) {
               delete(curResponse);
               curResponse = nullptr;
            }

        }

        BguMessageFactory*  bf = BguMessageFactory::getInstance();
        
        delete(bf);
}

void getTextAndSend(ConnectionHandler* ch)
{
    while (::keepOnRunning) {
        const short bufsize = 1024;
        std::string tempString(1024, '~');
        char buf[1024]{};
        strcpy(buf,tempString.data()) ;

        std::cout << "\rCLIENT < ";
        std::cin.getline(buf, bufsize);
		std::string line(buf);

        bguHeader* tempHeader = BguMessageFactory::getInstance()->generateMessage(line);
        //tempHeader.Serialize((int8_t*)buf);
        tempHeader->Serialize((int8_t*)buf);
        if (!ch->sendBytes(buf, tempHeader->getSize())) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

         if (tempHeader->getMyOPCode() == 3)
         {
            ::sentLogout = true;
         }
        delete(tempHeader);

        do
        {
            usleep(30);
        }
        while(::sentLogout && ::keepOnRunning);

    }
}
int main (int argc, char *argv[]) {
    std::setlocale(LC_ALL, "he.UTF-8");

    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }

    std::vector<std::thread> threads;
   
    std::string host = argv[1];
    short port = atoi(argv[2]);
    

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
    
    
    threads.push_back(std::thread(getNewMessages, &connectionHandler));
    threads.push_back(std::thread(getTextAndSend, &connectionHandler));

    // synchronize threads:
    for(auto& thread : threads){
        thread.join();
    }

    connectionHandler.close();

    //From here we will see the rest of the ehco client implementation:
    
    return 0;
}
