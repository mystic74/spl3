#ifndef BGU_FOLLOW_H
#define BGU_FOLLOW_H
#include "ProtocolDataStructs.h"
#include <stdint.h>
#include <string>
#include <vector>
#include <assert.h>

#pragma pack(1)

class bguFollow :public  bguHeader
{
    uint8_t     m_follow;
    uint16_t    m_num_of_users;
    std::string m_userNameList;


    std::vector<std::string> m_nameVector;

private:
typedef struct{
    uint16_t    opcode;
    uint8_t     follow;
    uint16_t    num_of_users;
}dummyStruct;

    bool inline setNameList()
    {
        this->m_userNameList = "";

        for(auto name : this->m_nameVector)
        {
            this->m_userNameList += name;
            this->m_userNameList += '\0';
        }
        return true;
    }


    virtual inline int getSize()
    {
        int size = sizeof(dummyStruct);

        for(auto name : this->m_nameVector)
        {
            size += (name.size() + 1);
        }

        std::cout << size << std::endl;
        return size;
    }
    
public:
    bguFollow() : m_follow(0),
                  m_num_of_users(0),
                  m_nameVector()
    {};

    bguFollow(std::vector<std::string> lineParams)
    {
        if (lineParams.size() > 1) {
            this->m_follow       = std::stoi(lineParams[1]);
            this->m_num_of_users = std::stoi(lineParams[2]);
            
            for (unsigned int i = 3; i < lineParams.size(); i++) {
                this->addNameToList(lineParams[i]);
            }
        }
    }

    /*  This might be cool, try and use this.
    template <typename... Ts>
    bguFollow(int8_t follow, Ts const& ... ts ) : bguFollow()
    {

    } 
        If this works, move addNameToList to private 
    */ 


    bool inline addNameToList(std::string name)
    {
        this->m_nameVector.push_back(name);
    }

    
	virtual inline bool Serialize(int8_t* out_buff)
		{
//            out_buff = new int8_t[this->m_userNameList.size() + sizeof(this->m_num_of_users) + sizeof(this->opcode) + sizeof(this->m_follow)];
			if (out_buff == nullptr)
				return false;

            assert(this->setNameList());


            dummyStruct* tempPtr    = reinterpret_cast<dummyStruct*>(out_buff);
            this->opcode            = this->getMyOPCode() ;
            tempPtr->opcode         = htons(this->opcode);
            // Does nothing on a byte though
            tempPtr->follow         = this->m_follow;
           tempPtr->num_of_users   = htons(this->m_num_of_users);


			memcpy(&out_buff[sizeof(dummyStruct)], this->m_userNameList.data(), this->m_userNameList.size());
		
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

            const dummyStruct* tempPtr = reinterpret_cast<const dummyStruct*>(in_buff);

            this->opcode        = tempPtr->opcode;
            this->m_follow        = tempPtr->follow;
            this->m_num_of_users  = tempPtr->num_of_users;

            std::string tempString(&in_buff[sizeof(dummyStruct)],&in_buff[(size_of_buffer - sizeof(dummyStruct))]);

			return true;

		}

        virtual inline uint16_t getMyOPCode()
        {
            return static_cast<uint16_t>(OPCODE::FOLLOW);
        }

        virtual inline bguHeader* Builder(std::vector<std::string> lineParams)
        {
            return new bguFollow(lineParams);
        }
};


#endif
