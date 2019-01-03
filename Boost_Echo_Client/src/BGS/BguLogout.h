#ifndef BGU_LOGOUT_H
#define BGU_LOGOUT_H

#include "ProtocolDataStructs.h"

class bguLogout :  public bguHeader
{
public:

        virtual inline int getSize()
        {
            int size = sizeof(bguProtocolStruct);

            std::cout << size << std::endl;
            return size;
        }
        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::REGISTER);
        }

        virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
        {
            return new bguLogout();
        }
};

#endif
