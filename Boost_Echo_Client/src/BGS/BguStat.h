#ifndef BGU_STAT_H
#define BGU_STAT_H
#include "ProtocolDataStructs.h"
#include <stdint.h>
#include <string>
#include <vector>
#include <assert.h>

class bguStat : public bguHeader
{
    std::string username;
    uint8_t     reserved;

    std::vector<std::string> nameVector;

private:

public:

    bguStat()
    {

    };

    bguStat(std::vector<std::string> lineParams)
    {
        this->username = lineParams[1];
    }

    virtual inline int getSize()
       {
           int size = 1 + strlen(this->username.data()) + sizeof(bguProtocolStruct);

           return size;
       }


	virtual inline bool Serialize(int8_t* out_buff)
		{
			if (out_buff == nullptr)
				return false;

            if(bguHeader::Serialize(out_buff) == false)
            {
                std::cout << " BGUHEADER Serialize Failed!!! " << std::endl;
                return false;
            }

			memcpy(&out_buff[sizeof(bguProtocolStruct)], this->username.data(), this->username.size());
            memset(&out_buff[sizeof(bguProtocolStruct) + this->username.size()], 0, 1);
			return true;
		}



		virtual inline bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer)
		{
			// Sanity   
			if (in_buff == nullptr)
				return false;

			if (size_of_buffer < sizeof(bguProtocolStruct))
			{
				// Buffer won't fit the opcode, so not chance that the login message fits here.
				return false;
			}

            if (!bguHeader::Deserialize(in_buff, size_of_buffer)) 
            {
                return false;
            }

            std::string tempString(&in_buff[sizeof(bguProtocolStruct)],&in_buff[(size_of_buffer - (sizeof(bguProtocolStruct) + 1))]);
            this->username = tempString;

            this->reserved = 0;

			return true;

		}

        virtual inline bguHeader* Builder(){
            return new bguStat();
        }
        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::STAT);
        }

        virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
        {
            return new bguStat(lineParams);
        }
};


#endif
