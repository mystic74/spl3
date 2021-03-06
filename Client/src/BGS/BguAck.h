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
public:
    uint16_t    m_MsgOpcode = 0;


    virtual inline int getSize()
        {
            int size = sizeof(bguProtocolStruct) + 2;

            std::cout << "Should not send bgu ack" << std::endl;
            return size;
        }
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

    virtual inline  bool decode(char nextBytes[2])
    {
        short result = (short)((nextBytes[0] & 0xff) << 8);
        result += (short)(nextBytes[1] & 0xff);
        this->m_MsgOpcode  = result;

        return true;
    }


    virtual inline uint16_t getMyOPCode()
    {
        return static_cast<uint16_t>(OPCODE::ACK);
    }

    virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
    {
        // Shouldn't get here
        return new bguAck();
    }

    virtual inline std::string toString()
    {
        return "ACK " + std::to_string(this->m_MsgOpcode);
    }
};
#endif // __BGUACK_H_INCL__

