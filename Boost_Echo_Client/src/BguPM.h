#ifndef __BGUPM_H_INCL__
#define __BGUPM_H_INCL__
#include "ProtocolDataStructs.h"
#include <stdint.h>
#include <string>
#include <vector>
#include <assert.h>

class bguPM : bguHeader
{
    std::string username;
    uint8_t     reserved1;
    std::string content;
    uint8_t     reserved2;

private:

public:


     virtual inline int getSize()
        {
            int size = stelen(this->username.data()) + 2 + strlen(this->content.data()) + sizeof(bguProtocolStruct);

            return size;
        }


	virtual inline bool Serialize(int8_t* out_buff)
		{

            unsigned int usernameSize   = this->username.size();
            unsigned int contentSize    = this->content.size();


			if (out_buff == nullptr)
				return false;

            if(bguHeader.Serialize(out_buff) == false)
            {
                return false;
            }

			memcpy(&out_buff[sizeof(bguProtocolStruct)], this->username.data(), usernameSize);
            memset(&out_buff[sizeof(bguProtocolStruct) + usernameSize], 0, 1);
            memcpy(&out_buff[sizeof(bguProtocolStruct) + usernameSize + 1], this->content.data(), this->content.size());
            memset(&out_buff[sizeof(bguProtocolStruct) + usernameSize + contentSize + 1], 0, 1);



			return true;
    }



		virtual inline bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer)
		{
            unsigned int index = 0;
			char tempBuffer[size_of_buffer] = { };

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

            // Should find the location of \0, and add that as the end point.
            // Again for the content
            std::string tempString(&in_buff[sizeof(bguProtocolStruct)],&in_buff[(size_of_buffer - (sizeof(bguProtocolStruct) + 1))]);
            this->content = tempString;

            this->reserved = 0;

			return true;

		}

        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::PM);
        }
};

#endif // __BGUPM_H_INCL__

