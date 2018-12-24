#ifndef BGU_FOLLOW_H
#define BGU_FOLLOW_H
#include "ProtocolDataStructs.h"
#include <stdint.h>
#include <string>
#include <vector>
#include <assert.h>

class bguFollow : bguHeader
{
    uint8_t     follow;
    uint16_t    num_of_users;
    std::string userNameList;


    std::vector<std::string> nameVector;

private:

typedef struct{
    uint16_t    opcode;
    uint8_t     follow;
    uint16_t    num_of_users;
}dummyStruct;

    bool inline setNameList()
    {
        this->userNameList = "";

        for(auto name : nameVector)
        {
            this->userNameList += name;
            this->userNameList += '\0';
        }
        return true;
    }

public:

	virtual inline bool Serialize(int8_t* out_buff)
		{
			if (out_buff == nullptr)
				return false;

            assert(this->setNameList());


            dummyStruct* tempPtr = reinterpret_cast<dummyStruct*>(out_buff);
            this->opcode        = tempPtr->opcode;
            this->follow        = tempPtr->follow;
            this->num_of_users  = tempPtr->num_of_users;


			memcpy(&out_buff[sizeof(dummyStruct)], this->userNameList.data(), this->userNameList.size());
		
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

            const dummyStruct* tempPtr = reinterpret_cast<const dummyStruct*>(in_buff);

            this->opcode        = tempPtr->opcode;
            this->follow        = tempPtr->follow;
            this->num_of_users  = tempPtr->num_of_users;

            std::string tempString(&in_buff[sizeof(dummyStruct)],&in_buff[(size_of_buffer - sizeof(dummyStruct))]);

			return true;

		}

        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::FOLLOW);
        }

};


#endif
