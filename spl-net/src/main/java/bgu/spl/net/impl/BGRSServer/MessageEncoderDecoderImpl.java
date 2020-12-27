package bgu.spl.net.api;

import bgu.spl.net.api.Message;
import bgu.spl.net.api.MessageEncoderDecoder;
import bgu.spl.net.api.Messages.*;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Message> {
    //todo check if is this byte buffer is the best solution
    //todo check if there is more elegant way than if else..
    private final ByteBuffer opcode = ByteBuffer.allocate(2);
    private final ByteBuffer courseNum = ByteBuffer.allocate(2);
    private final ByteBuffer messageOpcode = ByteBuffer.allocate(2);
    private byte[] bytes = new byte[1 << 10]; //start with 1k//todo acording to message 1
    private int len = 0;
    private int zeroCounter = 0;

    @Override
    public Message decodeNextByte(byte nextByte) {
        if (opcode.hasRemaining()) {
            opcode.put(nextByte);
            if (!opcode.hasRemaining()) { //we read 2 bytes and therefore can take the length
                opcode.flip();
            }
            return null;
        }

        //we are reading AdminReg Message
        if (opcode.getShort() == 1) {
            return decodeNextByteAdminReg(nextByte);
        }

        //we are reading StudentReg Message
        else if (opcode.getShort() == 2) {
            return decodeNextByteStudentReg(nextByte);
        }

        //we are reading LOGIN Message
        else if (opcode.getShort() == 3) {
            return decodeNextByteLOGIN(nextByte);
        }

        //we are reading Logout Message
        else if (opcode.getShort() == 4) {
            return new LogoutMessage();
        }

        //we are reading CourseReg Message
        else if (opcode.getShort() == 5) {
            return decodeNextByteCourseReg(nextByte);
        }

        //we are reading KdamCheck Message
        else if (opcode.getShort() == 6) {
            return decodeNextByteKdamCheck(nextByte);
        }

        //we are reading CourseStat Message
        else if (opcode.getShort() == 7) {
            return decodeNextByteCourseStat(nextByte);
        }

        //we are reading StudentStat Message
        else if (opcode.getShort() == 8) {
            return decodeNextByteStudentStat(nextByte);
        }

        //we are reading IsRegistered Message
        else if (opcode.getShort() == 9) {
            return decodeNextByteIsRegistered(nextByte);
        }

        //we are reading Unregister Message
        else if (opcode.getShort() == 10) {
            return decodeNextByteUnregister(nextByte);
        }

        //we are reading MyCourses Message
        else if (opcode.getShort() == 11) {
            return new MyCoursesMessage();
        }
/*
        //we are reading Ack Message
        else if (opcode.getShort() == 12) {//todo needed?
            return decodeNextByteAckMessage(nextByte);
        }

        //we are reading Error Message
        else if (opcode.getShort() == 13) {//todo needed?
            return decodeNextByteErrorMessage(nextByte);
        }

 */
        return null;
    }

    public Message decodeNextByteAdminReg(byte nextByte) {
        if (nextByte == 0) {
            if (zeroCounter == 0) {
                zeroCounter++;
            } else {
                String decodedString = new String(bytes, 0, len, StandardCharsets.UTF_8);
                String[] splitString = decodedString.split("0");
                String username = splitString[0];
                String password = splitString[1];
                clearAll();
                return new AdminRegMessage(username, password);
            }
        }
        pushByte(nextByte);
        return null;
    }


    public Message decodeNextByteStudentReg(byte nextByte) {
        if (nextByte == 0) {
            if (zeroCounter == 0) {
                zeroCounter++;
            } else {
                String decodedString = new String(bytes, 0, len, StandardCharsets.UTF_8);
                String[] splitString = decodedString.split("0");
                String username = splitString[0];
                String password = splitString[1];
                clearAll();
                return new StudentRegMessage(username, password);
            }
        }
        pushByte(nextByte);
        return null;
    }


    public Message decodeNextByteLOGIN(byte nextByte) {
        if (nextByte == 0) {
            if (zeroCounter == 0) {
                zeroCounter++;
            } else {
                String decodedString = new String(bytes, 0, len, StandardCharsets.UTF_8);
                String[] splitString = decodedString.split("0");
                String username = splitString[0];
                String password = splitString[1];
                clearAll();
                return new LoginMessage(username, password);
            }
        }
        pushByte(nextByte);
        return null;
    }

    public Message decodeNextByteCourseReg(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();
                CourseRegMessage output = new CourseRegMessage(courseNum.getInt());
                clearAll();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteKdamCheck(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();
                KdamCheckMessage output = new KdamCheckMessage(courseNum.getInt());
                clearAll();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteCourseStat(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();
                CourseStatMessage output = new CourseStatMessage(courseNum.getInt());
                clearAll();
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
                courseNum.flip();
                IsRegisteredMessage output = new IsRegisteredMessage(courseNum.getInt());
                clearAll();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteUnregister(byte nextByte) {
        if (courseNum.hasRemaining()) {
            courseNum.put(nextByte);
            if (!courseNum.hasRemaining()) { //we read 2 bytes and therefore can take the length
                courseNum.flip();
                UnregisterMessage output = new UnregisterMessage(courseNum.getInt());
                clearAll();
                return output;
            }
        }
        return null;
    }
/*
    public Message decodeNextByteAckMessage(byte nextByte) {
        if (messageOpcode.hasRemaining()) {
            messageOpcode.put(nextByte);
            if (!messageOpcode.hasRemaining()) { //we read 2 bytes and therefore can take the length
                messageOpcode.flip();
                AckMessage output = new AckMessage(messageOpcode.getShort());
                clearAll();
                return output;
            }
        }
        return null;
    }

    public Message decodeNextByteErrorMessage(byte nextByte) {
        if (messageOpcode.hasRemaining()) {
            messageOpcode.put(nextByte);
            if (!messageOpcode.hasRemaining()) { //we read 2 bytes and therefore can take the length
                messageOpcode.flip();
                ErrorMessage output = new ErrorMessage(messageOpcode.getShort());
                clearAll();
                return output;
            }
        }
        return null;
    }

 */

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

    private void clearAll() {
        len = 0;
        zeroCounter = 0;
        opcode.clear();
        courseNum.clear();
        messageOpcode.clear();
    }

    @Override
    public byte[] encode(Message message) {//todo what if the object is not non of this types?
        //todo check if needed
        /*
        if (message instanceof AdminRegMessage) {
            String userName = ((AdminRegMessage) message).getUsername();
            byte[] userNameBytes = userName.getBytes();
            String password = ((AdminRegMessage) message).getPassword();
            byte[] passwordBytes = password.getBytes();
            //initialize the result with the appropriate length
            // the appropriate length needs to be the sum of userName length, password length, 2 bytes for opcode and 2 bytes for 2 zero "0"
            byte[] result = new byte[userNameBytes.length + passwordBytes.length + 4];
            byte[] opcode = ByteBuffer.allocate(2).putInt(1).array();
            //add opcode to result
            System.arraycopy(opcode, 0, result, 0, opcode.length);
            //add userName to result
            System.arraycopy(userNameBytes, 0, result, opcode.length, userNameBytes.length);
            //add 0 to result
            result[opcode.length + userNameBytes.length] = 0;
            //add password to result
            System.arraycopy(userNameBytes, 0, result, opcode.length, userNameBytes.length);
            //add 0 to result
            result[opcode.length + userNameBytes.length + passwordBytes.length] = 0;
            return result;
        } else if (message instanceof StudentRegMessage) {

        } else if (message instanceof LoginMessage) {

        } else if (message instanceof LogoutMessage) {

        } else if (message instanceof CourseRegMessage) {

        } else if (message instanceof KdamCheckMessage) {

        } else if (message instanceof CourseStatMessage) {

        } else if (message instanceof StudentStatMessage) {

        } else if (message instanceof IsRegisteredMessage) {

        } else if (message instanceof UnregisterMessage) {

        } else if (message instanceof MyCoursesMessage) {

        } else*/


        byte[] opcode = createOpcode(message);
        byte[] MessageOpcode = createMessageOpcode(message);
        byte[] responseBytes = null;
        //OutputSize will be at least 4 bytes, 2 for the opcode and the other 2 for the MessageOpcode
        int outputSize = 4;
        //add the optional part at AckMessage
        if (message instanceof AckMessage) {
            String response = ((AckMessage<String>) message).getResponse();
            responseBytes = response.getBytes();
            outputSize = outputSize + responseBytes.length;
        }
        byte[] output = new byte[outputSize];
        //add opcode and MessageOpcode to result
        System.arraycopy(opcode, 0, output, 0, opcode.length);
        System.arraycopy(MessageOpcode, 0, output, opcode.length, MessageOpcode.length);
        //add the optional part at AckMessage
        if (message instanceof AckMessage) {
            System.arraycopy(responseBytes, 0, output, opcode.length + MessageOpcode.length, responseBytes.length);
        }
        return output;
    }

    private byte[] createOpcode(Message message) {
        if (message instanceof AckMessage) {
            return ByteBuffer.allocate(2).putInt(12).array();//todo needs to be short
        } else if (message instanceof ErrorMessage) {
            return ByteBuffer.allocate(2).putInt(13).array();
        }
        return null;//todo what to return if the message is not ack or error
    }

    private byte[] createMessageOpcode(Message message) {//todo what to return if the message is not ServerToClientMessage
        Short MessageOpcode = ((ServerToClientMessage) message).getMessageOpcode();
        return ByteBuffer.allocate(2).putShort(MessageOpcode).array();
    }
}
