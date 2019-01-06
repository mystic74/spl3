#include "BguMessageFactory.h"
#include "BGS/BguFollow.h"
#include "BGS/BguLogin.h"
#include "BGS/BguAck.h"
#include "BGS/BguError.h"
#include "BGS/BguRegister.h"
#include "BGS/BguLogout.h"
#include "BGS/BguPost.h"
#include "BGS/BguPM.h"
#include "BGS/BguUserList.h"
#include "BGS/BguStat.h"
#include "BguNotification.h"
#include <boost/algorithm/string.hpp>
#include <iostream>
using namespace std;
using namespace boost;
BguMessageFactory *BguMessageFactory::_instance = 0;


BguMessageFactory::~BguMessageFactory()
{
}

/**
 * Returns the single instance of the object.
 */
BguMessageFactory* BguMessageFactory::getInstance() {
    // check if the instance has been created yet
    if (0 == _instance) {
        // if not, then create it
        _instance = new BguMessageFactory;
    }
    // return the single instance
    return _instance;
};


bguHeader* BguMessageFactory::generateMessage(std::string strLine)
{
   typedef vector< string > split_vector_type;
    
    split_vector_type SplitVec; // #2: Search for tokens
    split( SplitVec, strLine, is_any_of(" "), token_compress_on ); 
    if (SplitVec.size() == 0) {
        cout << "Invalid input" << std::endl;
        return nullptr;
    }
    
    // 1
    if (SplitVec[0] == "REGISTER") {
        return  bguRegister().Builder(SplitVec);
    }
    // 2 
    else if (SplitVec[0] == "LOGIN"){
        return bguLogin().Builder(SplitVec);
    }
    // 3
    else if (SplitVec[0] == "LOGOUT") {
        return bguLogout().Builder(SplitVec);
    }
    // 4
    else if (SplitVec[0] == "FOLLOW") {
        return bguFollow().Builder(SplitVec);
    }
    // 5
    else if (SplitVec[0] == "POST") {
        return bguPost().Builder(SplitVec);
    }
    // 6
    else if (SplitVec[0] == "PM")
    {
        return bguPM().Builder(SplitVec);
    }
    // 7
    else if (SplitVec[0] == "USERLIST")
    {
        return bguUserlist().Builder(SplitVec);
    }
    // 8
    else if (SplitVec[0] == "STAT")
    {
        return bguStat().Builder(SplitVec);
    }

    return nullptr;
}

bguHeader* BguMessageFactory::getMessage(short opcode)
{
    switch (opcode) {
    case ((int)OPCODE::ACK):
        return new bguAck();
    case((int)OPCODE::ERROR):
        return new bguError();
    case((int)OPCODE::NOTIFICATION):
        return new bguNotification();
    }
    return nullptr;
}
