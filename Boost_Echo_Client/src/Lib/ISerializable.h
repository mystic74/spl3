class ISerializable
{
public:
	virtual ~ISerializable() {};
	virtual bool Serialize(int8_t* out_buff)  = 0;
	virtual bool Deserialize(const int8_t* in_buff, const unsigned int size_of_buffer) = 0; 
};