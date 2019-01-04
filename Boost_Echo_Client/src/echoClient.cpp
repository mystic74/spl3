#include <stdlib.h>
#include <connectionHandler.h>
#include "BGS/ProtocolDataStructs.h"
#include "BGS/BguLogin.h"
#include "BGS/BguFollow.h"
#include "BGS/BguPost.h"
#include "BGS/BguRegister.h"
#include "BGS/BguAck.h"
#include "BguNotification.h"
#include "BguMessageFactory.h"
#include "BGS/BguAckMessages/BguAckFollow.h"
#include <thread>
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
void getNewMessages(ConnectionHandler* ch)
{
        char opcodeArr[2] = {};
        bguHeader* curResponse = nullptr;
        while (keepOnRunning)
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
                        if (curResponse->getMyOPCode() == 10) {
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
                        }
                        std::cout << "CLIENT > "  << curResponse->toString() << std::endl;
                    }
                }
            }

            if (curResponse != nullptr) {
               delete(curResponse);
               curResponse = nullptr;
            }

        }
}

void getTextAndSend(ConnectionHandler* ch)
{
    while (1) {
        const short bufsize = 1024;
        std::string tempString(1024, '~');
        char buf[1024]{};
        strcpy(buf,tempString.data()) ;

        std::cout << "CLIENT <";
        std::cin.getline(buf, bufsize);
		std::string line(buf);
		int len=1024;

        bguHeader* tempHeader = BguMessageFactory::getInstance()->generateMessage(line);
        //tempHeader.Serialize((int8_t*)buf);
        tempHeader->Serialize((int8_t*)buf);
        if (!ch->sendBytes(buf, tempHeader->getSize())) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
		// connectionHandler.sendLine(line) appends '\n' to the message. Therefor we send len+1 bytes.
        std::cout << "Sent " << len+1 << " bytes to server" << std::endl;

        delete(tempHeader);

    }
}
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }

    std::vector<std::thread> threads;

   

    std::cout << "foo and bar completed.\n";
    std::string host = argv[1];
    short port = atoi(argv[2]);
    

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }
	

    
    
    threads.push_back(std::thread(getNewMessages, &connectionHandler));
    threads.push_back(std::thread(getTextAndSend, &connectionHandler));

    std::cout << "main, foo and bar now execute concurrently...\n";

    // synchronize threads:
    for(auto& thread : threads){
        thread.join();
    }

    //From here we will see the rest of the ehco client implementation:
    
    return 0;
}
