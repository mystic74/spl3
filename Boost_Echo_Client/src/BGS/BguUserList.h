#ifndef BGU_USERLIST_H
#define BGU_USERLIST_H

#include "ProtocolDataStructs.h"

class bguUserlist :  public bguHeader
{
public:
        
        virtual inline int getSize()
        {
            int size = sizeof(bguProtocolStruct);

            return size;
        }
        
        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::USERLIST);
        }

        virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
        {
            return new bguUserlist();
        }
};

#endif
