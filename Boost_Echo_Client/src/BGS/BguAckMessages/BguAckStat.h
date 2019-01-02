/*
 * BguAckStat.h
 *
 *  Created on: Jan 2, 2019
 *      Author: tom
 */

#ifndef SRC_BGS_BGUACKMESSAGES_BGUACKSTAT_H_
#define SRC_BGS_BGUACKMESSAGES_BGUACKSTAT_H_
#include "../BguAck.h"

class bguAckStat :public  bguAck
{
	int			m_usersByteCounter[3] 	= {};
    uint16_t    m_posts 				= 0;
    uint16_t 	m_followers				= 0;

    // Yeah, two name that differ by the ending, that won't be an issue ever right?
    uint16_t 	m_following				= 0;

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
    	if (this->m_usersByteCounter[0] < 2)
    	{
    		this->m_posts 	 = this->m_posts << 8;
    		this->m_posts 	|= (short)(nextBytes & 0xff);
    		this->m_usersByteCounter[0]++;
    		return false;
    	}

    	if (this->m_usersByteCounter[1] < 2)
		{
			this->m_followers 	 = this->m_followers << 8;
			this->m_followers 	|= (short)(nextBytes & 0xff);
			this->m_usersByteCounter[1]++;
			return false;
		}

    	if (this->m_usersByteCounter[2] < 2)
		{
			this->m_following 	 = this->m_following << 8;
			this->m_following 	|= (short)(nextBytes & 0xff);
			this->m_usersByteCounter[2]++;
			return false;
		}

        return true;
    }
};





#endif /* SRC_BGS_BGUACKMESSAGES_BGUACKSTAT_H_ */
