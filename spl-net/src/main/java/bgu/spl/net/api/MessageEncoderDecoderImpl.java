package bgu.spl.net.api;

import bgu.spl.net.api.Messages.*;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder{
    private final ByteBuffer opcode = ByteBuffer.allocate(2);
    private final ByteBuffer courseNum = ByteBuffer.allocate(2);
    private byte[] objectBytes = null;
    private int objectBytesIndex = 0;

    private byte[] bytes = new byte[1 << 10]; //start with 1k//todo acording to message 1
    private int len = 0;
    private boolean first0=true;

    @Override
    public Message decodeNextByte(byte nextByte) {
        if (objectBytes == null) { //indicates that we are still reading the length
            opcode.put(nextByte);
            if (!opcode.hasRemaining()) { //we read 4 bytes and therefore can take the length
                opcode.flip();
                if (opcode.getShort()==1){//todo make general
                    return decodeNextByteAdminReg(nextByte);
                }
                else if (opcode.getShort()==2){//todo make general
                    return decodeNextByteStudentReg(nextByte);
                }
                else if (opcode.getShort()==3){//todo make general
                    return decodeNextByteLOGIN(nextByte);
                }
                else if (opcode.getShort()==4){//todo make general
                    return new LogoutMessage();
                }
                else if (opcode.getShort()==5){//todo make general
                    return new LogoutMessage();
                }
                else if (opcode.getShort()==6){//todo make general
                    return decodeNextByteKdamCheck(nextByte);
                }
                else if (opcode.getShort()==7){//todo make general
                    return decodeNextByteCourseStat(nextByte);
                }
                else if (opcode.getShort()==8){//todo make general
                    return decodeNextByteStudentStat(nextByte);
                }
                else if (opcode.getShort()==9){//todo make general
                    return decodeNextByteIsRegisered(nextByte);
                }
                else if (opcode.getShort()==10){//todo make general
                    return decodeNextByteUnregister(nextByte);
                }
                else if (opcode.getShort()==11){//todo make general
                    return new MyCoursesMessage();
                }

            }
        }
        return null;
    }

    public Message decodeNextByteAdminReg(byte nextByte) {
        if (nextByte==0){
            if (first0){
                first0=false;
            }
            else {
                first0=true;
                return new AdminRegMessage(bytes,len);
            }
        }
        pushByte(nextByte);
        return null;
    }


    public Message decodeNextByteStudentReg(byte nextByte) {
        if (nextByte==0){
            if (first0){
                first0=false;
            }
            else {
                first0=true;
                return new StudentRegMessage(bytes,len);
            }
        }
        pushByte(nextByte);
        return null;
    }


    public Message decodeNextByteLOGIN(byte nextByte) {
        if (nextByte==0){
            if (first0){
                first0=false;
            }
            else {
                first0=true;
                return new LoginMessage(bytes,len);
            }
        }
        pushByte(nextByte);
        return null;
    }

    public Message decodeNextByteKdamCheck(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                return new KdamCheckMessage(courseNum.getInt());
            }
        }
        return null;
    }

    public Message decodeNextByteCourseStat(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                return new CourseStatMessage(courseNum.getInt());
            }
        }
        return null;
    }

    public Message decodeNextByteStudentStat(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == 0) {
            return new StudentStatMessage(popString());
        }
        pushByte(nextByte);
        return null; //not a line yet
    }

    public Message decodeNextByteIsRegisered(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                return new IsRegisteredMessage(courseNum.getInt());
            }
        }
        return null;
    }
    public Message decodeNextByteUnregister(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                return new UnregisterMessage(courseNum.getInt());
            }
        }
        return null;
    }

    private void pushByte(byte nextByte) {
        if (len >= bytes.length) {
            bytes = Arrays.copyOf(bytes, len * 2);
        }
        bytes[len++] = nextByte;
    }


    private String popString() {
        //notice that we explicitly requesting that the string will be decoded from UTF-8
        //this is not actually required as it is the default encoding in java.
        String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
        len = 0;
        return result;
    }

    @Override
    public byte[] encode(Object message) {
        return new byte[0];
    }
}
