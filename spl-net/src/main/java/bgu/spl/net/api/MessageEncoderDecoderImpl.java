package bgu.spl.net.api;

import bgu.spl.net.api.Messages.*;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder{
    //todo check if is this byte buffer is the best solution
    //todo check if there is more elegant way than if else..
    private final ByteBuffer opcode = ByteBuffer.allocate(2);
    private final ByteBuffer courseNum = ByteBuffer.allocate(2);
    private final ByteBuffer messageOpcode = ByteBuffer.allocate(2);
    private byte[] bytes = new byte[1 << 10]; //start with 1k//todo acording to message 1
    private int len = 0;
    private int zeroCounter=0;

    @Override
    public Message decodeNextByte(byte nextByte) {
        if (opcode.hasRemaining()) {
            opcode.put(nextByte);
            if (!opcode.hasRemaining()) { //we read 2 bytes and therefore can take the length
                opcode.flip();

                //we are reading AdminReg Message
                if (opcode.getShort() == 1) {//todo make general
                    return decodeNextByteAdminReg(nextByte);
                }

                //we are reading StudentReg Message
                else if (opcode.getShort() == 2) {//todo make general
                    return decodeNextByteStudentReg(nextByte);
                }

                //we are reading LOGIN Message
                else if (opcode.getShort() == 3) {//todo make general
                    return decodeNextByteLOGIN(nextByte);
                }

                //we are reading Logout Message
                else if (opcode.getShort() == 4) {//todo make general
                    return new LogoutMessage();
                }

                //we are reading CourseReg Message
                else if (opcode.getShort() == 5) {//todo make general
                    return decodeNextByteCouseReg(nextByte);
                }

                //we are reading KdamCheck Message
                else if (opcode.getShort() == 6) {//todo make general
                    return decodeNextByteKdamCheck(nextByte);
                }

                //we are reading CourseStat Message
                else if (opcode.getShort() == 7) {//todo make general
                    return decodeNextByteCourseStat(nextByte);
                }

                //we are reading StudentStat Message
                else if (opcode.getShort() == 8) {//todo make general
                    return decodeNextByteStudentStat(nextByte);
                }

                //we are reading IsRegistered Message
                else if (opcode.getShort() == 9) {//todo make general
                    return decodeNextByteIsRegistered(nextByte);
                }

                //we are reading Unregister Message
                else if (opcode.getShort() == 10) {//todo make general
                    return decodeNextByteUnregister(nextByte);
                }

                //we are reading MyCourses Message
                else if (opcode.getShort() == 11) {//todo make general
                    return new MyCoursesMessage();
                }

                //we are reading Ack Message
                else if (opcode.getShort() == 12) {//todo make general
                    return decodeNextByteAckMessage(nextByte);
                }

                //we are reading Error Message
                else if (opcode.getShort() == 13) {//todo make general
                    return decodeNextByteErrorMessage(nextByte);
                }

            }
        }
        return null;
    }

    public Message decodeNextByteAdminReg(byte nextByte) {
        if (nextByte==0){
            if (zeroCounter==0){
                zeroCounter++;
            }
            else {
                zeroCounter=0;
                String decodedString = new String(bytes, 0, len, StandardCharsets.UTF_8);
                String[] splitString = decodedString.split("0");
                String username = splitString[0];
                String password = splitString[1];
                len=0;
                opcode.clear();
                return new AdminRegMessage(username,password);
            }
        }
        pushByte(nextByte);
        return null;
    }


    public Message decodeNextByteStudentReg(byte nextByte) {
        if (nextByte==0){
            if (zeroCounter==0){
                zeroCounter++;
            }
            else {
                zeroCounter=0;
                opcode.clear();
                return new StudentRegMessage(bytes,len);
            }
        }
        pushByte(nextByte);
        return null;
    }


    public Message decodeNextByteLOGIN(byte nextByte) {
        if (nextByte==0){
            if (zeroCounter==0){
                zeroCounter++;
            }
            else {
                zeroCounter=0;
                opcode.clear();
                return new LoginMessage(bytes,len);
            }
        }
        pushByte(nextByte);
        return null;
    }

    public Message decodeNextByteCouseReg(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                CourseRegMessage output=new CourseRegMessage(courseNum.getInt());
                courseNum.clear();
                opcode.clear();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteKdamCheck(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                KdamCheckMessage output=new KdamCheckMessage(courseNum.getInt());
                courseNum.clear();
                opcode.clear();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteCourseStat(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                CourseStatMessage output=new CourseStatMessage(courseNum.getInt());
                opcode.clear();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteStudentStat(byte nextByte) {
        //notice that the top 128 ascii characters have the same representation as their utf-8 counterparts
        //this allow us to do the following comparison
        if (nextByte == 0) {
            opcode.clear();
            return new StudentStatMessage(popString());
        }
        pushByte(nextByte);
        return null; //not a line yet
    }

    public Message decodeNextByteIsRegistered(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                IsRegisteredMessage output=new IsRegisteredMessage(courseNum.getInt());
                courseNum.clear();
                opcode.clear();
                return output;
            }
        }
        return null;
    }
    public Message decodeNextByteUnregister(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();//todo check if needed
                UnregisterMessage output =new UnregisterMessage(courseNum.getInt());
                courseNum.clear();
                opcode.clear();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteAckMessage(byte nextByte) {
        if (messageOpcode.hasRemaining()) {
            messageOpcode.put(nextByte);
            if (!messageOpcode.hasRemaining()) { //we read 2 bytes and therefore can take the length
                messageOpcode.flip();//todo check if needed
                AckMessage output=new AckMessage(messageOpcode.getShort());
                messageOpcode.clear();
                opcode.clear();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteErrorMessage(byte nextByte) {
        if (messageOpcode.hasRemaining()) {
            messageOpcode.put(nextByte);
            if (!messageOpcode.hasRemaining()) { //we read 2 bytes and therefore can take the length
                messageOpcode.flip();//todo check if needed
                ErrorMessage output=new ErrorMessage(messageOpcode.getShort());
                messageOpcode.clear();
                opcode.clear();
                return output;
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
    public byte[] encode(Object message) {//todo what if the object is not non of this types?
        //todo how utf8 ?
        if (message instanceof AdminRegMessage){
            String userName= ((AdminRegMessage) message).getUsername();
            byte[] userNameBytes= userName.getBytes();
            String password=((AdminRegMessage) message).getPassword();
            byte[] passwordBytes= password.getBytes();
            //initialize the result with the appropriate length
            // the appropriate length needs to be the sum of userName length, password length, 2 bytes for opcode and 2 bytes for 2 zero "0"
            byte[] result=new byte[userNameBytes.length+passwordBytes.length+4];
            byte[] opcode=ByteBuffer.allocate(2).putInt(1).array();
            //add opcode to result
            System.arraycopy(opcode,0,result,0,opcode.length);
            //add userName to result
            System.arraycopy(userNameBytes,0,result,opcode.length,userNameBytes.length);
            //add 0 to result
            result[opcode.length+userNameBytes.length]=0;
            //add password to result
            System.arraycopy(userNameBytes,0,result,opcode.length,userNameBytes.length);
            //add 0 to result
            result[opcode.length+userNameBytes.length+passwordBytes.length]=0;
            return result;
        }
        else if (message instanceof StudentRegMessage){

        }
        else if (message instanceof LoginMessage){

        }
        else if (message instanceof LogoutMessage){

        }
        else if (message instanceof CourseRegMessage){

        }
        else if (message instanceof KdamCheckMessage){

        }
        else if (message instanceof CourseStatMessage){

        }
        else if (message instanceof StudentStatMessage){

        }
        else if (message instanceof IsRegisteredMessage){

        }
        else if (message instanceof UnregisterMessage){

        }
        else if (message instanceof MyCoursesMessage){

        }
        else if (message instanceof AckMessage){

        }
        else if (message instanceof ErrorMessage){

        }
    }
}
