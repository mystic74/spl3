#include "BguMessageFactory.h"
#include "BGS/BguFollow.h"
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


bguHeader* BguMessageFactory::getMessage(std::string strLine)
{
    // Get the opcode from the line, for now, just checking one
    uint16_t opcode = static_cast<short>(OPCODE::REGISTER);
   
    if (opcode == static_cast<short>(OPCODE::REGISTER)) {
        return (new bguFollow());

    }
    return nullptr;
}

