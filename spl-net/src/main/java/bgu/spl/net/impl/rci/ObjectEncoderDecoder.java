package bgu.spl.net.impl.rci;

import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.bguMessageFactory;
import bgu.spl.net.api.bguProtocol;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;

public class ObjectEncoderDecoder implements MessageEncoderDecoder<Serializable> {

    private final ByteBuffer lengthBuffer = ByteBuffer.allocate(2);
    private bguProtocol objectBytes = null;
    private int objectBytesIndex = 0;

    @Override
    public Serializable decodeNextByte(byte nextByte) {
        if (objectBytes == null) { //indicates that we are still reading the length
            lengthBuffer.put(nextByte);
            if (!lengthBuffer.hasRemaining()) { //we read 4 bytes and therefore can take the length
                lengthBuffer.flip();
                objectBytes = new bguMessageFactory().getMessage(lengthBuffer.getShort());
                objectBytesIndex = 0;
                lengthBuffer.clear();
            }
        } else {
           return objectBytes.decode(nextByte);
        }

        return null;
    }

    @Override
    public byte[] encode(Serializable message) {
        return serializeBguMessage((bguProtocol)message);
    }
    
    private byte[] serializeBguMessage(bguProtocol msg)
    {
    	return msg.encode();
    }

    private byte[] serializeObject(Serializable message) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            //placeholder for the object size
            for (int i = 0; i < 4; i++) {
                bytes.write(0);
            }

            ObjectOutput out = new ObjectOutputStream(bytes);
            out.writeObject(message);
            out.flush();
            byte[] result = bytes.toByteArray();

            //now write the object size
            ByteBuffer.wrap(result).putInt(result.length - 4);
            return result;

        } catch (Exception ex) {
            throw new IllegalArgumentException("cannot serialize object", ex);
        }
    }

}
