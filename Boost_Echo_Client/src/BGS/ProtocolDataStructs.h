#ifndef PROTOCOL_DATA_STRUCTS_H
#define PROTOCOL_DATA_STRUCTS_H
#include <string.h>
#include <arpa/inet.h>
#include <netinet/in.h>
#include <iostream>
#include <vector>
#include <string>

// Change the Lib to -I
#include "../Lib/ISerializable.h"
enum class OPCODE : uint16_t
{
	REGISTER 		= 1,
	LOGIN		 	= 2,
	LOGOUT 			= 3,
	FOLLOW 			= 4,
	POST 			= 5,
	PM 				= 6,
	USERLIST 		= 7,
	STAT 			= 8,
	NOTIFICATION 	= 9,
	ACK 			= 10,
	ERROR 			= 11
};

typedef struct 
{
	public:
		uint16_t opcode;
} bguProtocolStruct;

class bguHeader : public ISerializable
{
protected :
	uint16_t opcode;

public:
	virtual ~bguHeader() { };

	bguHeader() : opcode(0) {};
	virtual inline bool Serialize(int8_t* out_buff)
	{
		if (out_buff == nullptr)
			return false;
		
		bguProtocolStruct* tempRef = (bguProtocolStruct*)out_buff;

		// Should switch to this, can't remember the syntax as of this specific moment though
		//bguProtocolStruct* tempRef = reinterpet_cast<bguProtocolStruct*>(out_buff);

		// Move to network format, short is important here.
		this->opcode = htons(this->getMyOPCode());

		// Set the opcode in the beggining of the buffer, like god intended
		tempRef->opcode = this->opcode;
		
		return true;
	}

	virtual inline bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer)
	{
		if (in_buff == nullptr)
			return false;

		bguProtocolStruct* tempRef = (bguProtocolStruct*)in_buff;

        this->opcode = ntohs(tempRef->opcode);
        //this->opcode = tempRef->opcode;

		return true;

	}

    virtual inline bool decode(char nextByte[2]) 
    {
        std::cout <<  "bguProtocol::decode -> Shouldn't get here, this function is just for inheritance \n" << std::endl;
        return true;
    }

    virtual uint16_t getMyOPCode() = 0;
    virtual bguHeader* Builder(std::vector<std::string> lineParams) = 0;
    virtual int getSize() = 0;

    virtual inline std::string toString()
    {
        return "";
    }

};


#endif // include guard



