#include "BguMessageFactory.h"
#include "BGS/BguFollow.h"
#include "BGS/BguLogin.h"
#include "BGS/BguAck.h"
#include "BGS/BguRegister.h"
#include <boost/algorithm/string.hpp>
#include <iostream>
using namespace std;
using namespace boost;
BguMessageFactory *BguMessageFactory::_instance = 0;

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
    // Get the opcode from the line, for now, just checking one
    uint16_t opcode = static_cast<short>(OPCODE::REGISTER);
   typedef vector< string > split_vector_type;
    
    split_vector_type SplitVec; // #2: Search for tokens
    split( SplitVec, strLine, is_any_of(" "), token_compress_on ); 
    if (SplitVec.size() == 0) {
        cout << "Invalid input" << std::endl;
        return nullptr;
    }
    
    if (SplitVec[0] == "REGISTER") {
        return new bguRegister();
    }
    else if (SplitVec[0] == "LOGIN"){
        return new bguLogin();
    }


    return nullptr;
}

bguHeader* BguMessageFactory::getMessage(short opcode)
{
    switch (opcode) {
    case ((int)OPCODE::ACK):
        return new bguAck();
    }
    return nullptr;
}
