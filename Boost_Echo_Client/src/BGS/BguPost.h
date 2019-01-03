
#ifndef BGU_POST_H
#define BGU_POST_H
#include "ProtocolDataStructs.h"
#include <stdint.h>
#include <string>
#include <vector>
#include <assert.h>

class bguPost : bguHeader
{
    std::string content;
    uint8_t     reserved;

    std::vector<std::string> nameVector;

private:

public:

	virtual inline bool Serialize(int8_t* out_buff)
		{
			if (out_buff == nullptr)
				return false;

            if(bguHeader::Serialize(out_buff) == false)
            {
                return false;
            }

			memcpy(&out_buff[sizeof(bguProtocolStruct)], this->content.data(), this->content.size());
            memset(&out_buff[sizeof(bguProtocolStruct) + this->content.size()], 0, 1);
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
            this->content = tempString;

            this->reserved = 0;

			return true;

		}

        virtual inline bguHeader* Builder(){
            return new bguPost();
        }
        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::POST);
        }
        virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
        {
            return new bguPost();
        }
};


#endif
