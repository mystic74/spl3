/*
 * BguAckUserList.h
 *
 *  Created on: Jan 2, 2019
 *      Author: tom
 */

#ifndef SRC_BGS_BGUACKMESSAGES_BGUACKUSERLIST_H_
#define SRC_BGS_BGUACKMESSAGES_BGUACKUSERLIST_H_
#include "../BguAck.h"
#include <boost/algorithm/string.hpp>
#include <boost/array.hpp>
#include <iostream>
using namespace std;
using namespace boost;


class bguAckUserList :public  bguAck
{
	int			m_usersByteCounter 		    = 0;
	int			m_nullTerminatorCounter     = 0;
    uint16_t    m_numOfUsers 			    = 0;
    std::string m_nameVector 			    = "";
public:
    bguAckUserList(const bguAck& papa)
    {
        this->m_MsgOpcode = papa.m_MsgOpcode;
    }

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
    	if (this->m_usersByteCounter < 2)
    	{
    		this->m_numOfUsers = this->m_numOfUsers << 8;
    		this->m_numOfUsers |= (short)(nextBytes & 0xff);
    		this->m_usersByteCounter++;
    		return false;
    	}

    	if( this->m_nullTerminatorCounter < this->m_numOfUsers)
    	{
    		this->m_nameVector += nextBytes;

    		if (nextBytes == '\0')
    		{
    			this->m_nullTerminatorCounter++;
    		}

    		return ( this->m_nullTerminatorCounter == this->m_numOfUsers);
    	}

        return true;
    }

    virtual inline std::string toString()
    {
        std::string rstring = "ACK " + std::to_string(this->m_MsgOpcode);

        rstring += " " + std::to_string(this->m_numOfUsers) + " ";

        for (unsigned int index = 0; index < this->m_nameVector.size(); index++)
        {
            char cr = this->m_nameVector.data()[index];
            if (cr == '\0')
            {
                rstring += " ";
            }
            else
            {
                rstring += cr;
            }
        }

        return rstring;
    }

};

#endif /* SRC_BGS_BGUACKMESSAGES_BGUACKUSERLIST_H_ */
