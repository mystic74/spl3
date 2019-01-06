package bgu.spl.net.impl;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bguMessageFactory;
import bgu.spl.net.api.bguProtocol;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class BGUEncoderDecoder implements MessageEncoderDecoder<Serializable> {

    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(2);
    private bguProtocol objectBytes = null;
    @Override
    public Serializable decodeNextByte(byte nextByte) {
    	bguProtocol rValue = null;
        if (objectBytes == null) { //indicates that we are still reading the length
            lengthBuffer.put(nextByte);
            if (!lengthBuffer.hasRemaining()) { //we read 4 bytes and therefore can take the length
                lengthBuffer.flip();
                objectBytes = new bguMessageFactory().getMessage(lengthBuffer.getShort());
                lengthBuffer.clear();
                rValue  = objectBytes.isDone();
            }
        } else {
           rValue = objectBytes.decode(nextByte);   
        }
        if (rValue != null)
     	   objectBytes = null;
        return rValue;
    }

    @Override
    public byte[] encode(Serializable message) {
        return serializeBguMessage((bguProtocol)message);
    }
    
    private byte[] serializeBguMessage(bguProtocol msg)
    {
    	return msg.encode();
    }
}
