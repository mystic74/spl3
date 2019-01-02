#ifndef __BGUACK_H_INCL__
#define __BGUACK_H_INCL__
#include "ProtocolDataStructs.h"
#include <stdint.h>
#include <string>
#include <vector>
#include <assert.h>

#pragma pack(1)

struct bguAckHeader
{
    uint16_t    m_MsgOpcode;
};

class bguAck :public  bguHeader
{
    uint16_t    m_MsgOpcode;

    virtual bool Serialize(int8_t* out_buff)
    {
        if (!bguHeader::Serialize(out_buff))
            return false;

        bguAckHeader* tempPtr = (bguAckHeader*)&out_buff[sizeof(bguProtocolStruct)];
        tempPtr->m_MsgOpcode = htons(this->m_MsgOpcode);

        return true;

    }
    virtual bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer)
    {
        if (size_of_buffer < sizeof(bguProtocolStruct)) {
            return false;
        }

        if (!bguHeader::Deserialize(in_buff, size_of_buffer))
            return false;

        bguAckHeader* tempPtr = (bguAckHeader*)&in_buff[sizeof(bguProtocolStruct)];
        this->m_MsgOpcode = ntohs(tempPtr->m_MsgOpcode);

        return true;
    }

    virtual inline uint16_t getMyOPCode()
    {
        return static_cast<uint16_t>(OPCODE::ACK);
    }
    virtual inline bguHeader* Builder()
    {
        return new bguAck();
    }

};
#endif // __BGUACK_H_INCL__

