#include <string.h>

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
} dumbHeader;

class bguHeader
{
	protected :
	uint16_t opcode;

	virtual ~bguHeader() { };

	virtual inline bool Serialize(int8_t* out_buff)
	{
		if (out_buff == nullptr)
			return false;
		
		dumbHeader* tempRef = (dumbHeader*)out_buff;

		// Should switch to this, can't remember the syntax as of this specific moment though
		//dumbHeader* tempRef = reinterpet_cast<dumbHeader*>(out_buff);


		// Set the opcode in the beggining of the buffer, like god intended
		tempRef->opcode = this->opcode;
		return true;
	}

};


