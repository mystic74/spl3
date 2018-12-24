/* BguLogin.h */
#ifndef BGU_LOGIN_H
#define BGU_LOGIN_H
#include "ProtocolDataStructs.h"
#include <string>

#define SIZE_FOR_RESERVED 1
#define NULL_TERMINATOR '\0'
class bguLogin : bguHeader
{

private:
	std::string username;
	uint8_t reserved1;
	std::string password;
	uint8_t reserved2;	

public :
	virtual inline bool Serialize(int8_t* out_buff)
		{
			int unSize 	= strlen(this->username.data());
			int pwdSize = strlen(this->password.data());

			if (out_buff == nullptr)
				return false;
			
			bguHeader::Serialize(out_buff);


			memcpy(&out_buff[sizeof(bguHeader)], this->username.data(), unSize);
			memset(&out_buff[sizeof(bguHeader) + unSize], NULL_TERMINATOR, SIZE_FOR_RESERVED);
			memcpy(&out_buff[sizeof(bguHeader) + unSize + SIZE_FOR_RESERVED], this->password.data(), pwdSize);
			memset(&out_buff[sizeof(bguHeader) + unSize + SIZE_FOR_RESERVED + pwdSize], NULL_TERMINATOR , SIZE_FOR_RESERVED);

			return true;
		}



		virtual inline bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer)
		{

			unsigned int index = 0;
			unsigned int tempIndex = 0;

			bguHeader::Deserialize(in_buff, size_of_buffer);
			char tempBuffer[size_of_buffer] = { };
			// Sanity
			if (in_buff == nullptr)
				return false;

			if (size_of_buffer < sizeof(bguHeader))
			{
				// Buffer won't fit the opcode, so not chance that the login message fits here.
				return false;
			}


			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// Assuming that we WON'T have null terminator in the middle of the username or the password
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

			

			for ( index = 0; index < size_of_buffer; index++)
			{	
				if (in_buff[index + sizeof(bguHeader)] != 0)
					tempBuffer[index] = in_buff[index + sizeof(bguHeader)];
				else
					break;
			}

			if (index == size_of_buffer)
				return false;


			std::string tempName(tempBuffer);

			// nullify this buffer
			memset(tempBuffer, 0, size_of_buffer);


			for (tempIndex = 0; tempIndex < (size_of_buffer - index); tempIndex++)
			{	
				if (in_buff[index + tempIndex] != 0)
					tempBuffer[tempIndex] = in_buff[index + tempIndex];
				else
					break;
			}

			if (tempIndex == size_of_buffer)
				return false;

			std::string tempPwd(tempBuffer);



			this->username = tempName;
			this->password = tempPwd;


			return true;

		}
};

#endif
