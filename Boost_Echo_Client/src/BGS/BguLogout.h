#ifndef BGU_LOGOUT_H
#define BGU_LOGOUT_H

#include "ProtocolDataStructs.h"

class bguLogout : bguHeader
{
        virtual inline uint16_t getMyOPCode()
        {
            return OPCODE::REGISTER;
        }
}
#endif
