#ifndef __BGUNOTIFICATION_H_INCL__
#define __BGUNOTIFICATION_H_INCL__

#include "BGS/ProtocolDataStructs.h"
#include <boost/algorithm/string.hpp>
#include <iostream>
using namespace std;
using namespace boost;

class bguNotification : public  bguHeader
{
	int8_t		m_notType        		= -1;
    bool        m_userIsDone            = false;
	std::string m_postingUser 			= "";
    int8_t      m_reserved1  			= -1;
    bool        m_contentIsDone         = false;
    std::string m_content    			= "";
    int8_t      m_reserved2  			= -1;

    
public:

    virtual bool Serialize(int8_t* out_buff)
    {
        /*if (!bguHeader::Serialize(out_buff))
            return false;

        bguAckHeader* tempPtr = (bguAckHeader*)&out_buff[sizeof(bguProtocolStruct)];
        tempPtr->m_MsgOpcode = htons(this->m_MsgOpcode);
*/
        return true;

    }
    virtual bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer)
    {
        /*if (size_of_buffer < sizeof(bguProtocolStruct)) {
            return false;
        }

        if (!bguHeader::Deserialize(in_buff, size_of_buffer))
            return false;

        bguAckHeader* tempPtr = (bguAckHeader*)&in_buff[sizeof(bguProtocolStruct)];
        this->m_MsgOpcode = ntohs(tempPtr->m_MsgOpcode);
*/
        return true;
    }

    virtual inline  bool decode(char nextBytes)
    {
    	if (this->m_notType == -1)
    	{
            this->m_notType = nextBytes;
    		return false;
    	}

    	if(!this->m_userIsDone)
    	{
    		if (nextBytes == '\0')
    		{
    			this->m_userIsDone  = true;
                this->m_reserved1   = nextBytes;
    		}
            else
            {
                this->m_postingUser += nextBytes;
            }

            return false;
    	}

        if (!this->m_contentIsDone) {
            if (nextBytes == '\0')
    		{
    			this->m_contentIsDone   = true;
                this->m_reserved2       = nextBytes;
    		}
            else
            {
                this->m_content        += nextBytes;
            }

            return false;
        }

        return true;
    }

    virtual inline std::string toString()
    {
        std::string rstring = "NOTIFICATION " + std::to_string(this->m_notType);

        rstring += " " + this->m_postingUser;
        rstring += " " + this->m_content;

        return rstring; 
    }

    virtual inline uint16_t getMyOPCode()
    {
        return static_cast<uint16_t>(OPCODE::NOTIFICATION);
    }

    virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
    {
        return nullptr;
    }

    virtual inline int getSize()
    {
        int size = 3;

        size += this->m_content.size();
        size += this->m_postingUser.size();

        return size;
    }
};


#endif // __BGUNOTIFICATION_H_INCL__

