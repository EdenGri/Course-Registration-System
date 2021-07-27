//
// Created by spl211 on 21/12/2020.
//

#include <boost/algorithm/string/split.hpp>
#include "KeyboardReader.h"
#include <boost/algorithm/string/classification.hpp>

using namespace std;
using namespace boost::algorithm;

KeyboardReader::KeyboardReader(ConnectionHandler *connectionHandler, bool *isTerminated, bool *shouldTerminate) :
        connectionHandler(connectionHandler), isTerminated(isTerminated), shouldTerminate(shouldTerminate) {}


void KeyboardReader::run() {
    //while process is not terminated
    while (!*isTerminated) {
        const short bufsize = 1024;
        char buf[bufsize];
        //while process should not terminate
        while (!*shouldTerminate) {
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            //get input from keyboard as string vector
            std::vector<std::string> stringVector;
            split(stringVector, line, is_any_of(" "));
            char bytesArray[2];

            //if we are sending an Admin Register message
            //encode message properly
            if (stringVector[0] == "ADMINREG") {
                shortToBytes((short) 1, bytesArray); //convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
                connectionHandler->sendFrameAscii(stringVector[1], '\0');
                connectionHandler->sendFrameAscii(stringVector[2], '\0');

                //if we are sending a Student Register message
                //encode message properly
            } else if (stringVector[0] == "STUDENTREG") {
                shortToBytes((short) 2, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
                connectionHandler->sendFrameAscii(stringVector[1], '\0');
                connectionHandler->sendFrameAscii(stringVector[2], '\0');

                //if we are sending a login message
                //encode message properly
            } else if (stringVector[0] == "LOGIN") {
                shortToBytes((short) 3, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);//convert opcode given in short to bytes
                connectionHandler->sendFrameAscii(stringVector[1], '\0');
                connectionHandler->sendFrameAscii(stringVector[2], '\0');

                //if we are sending a logout message
                //encode message properly
            } else if (stringVector[0] == "LOGOUT") {
                shortToBytes((short) 4, bytesArray);//convert opcode given in short to bytes
                *shouldTerminate = true;
                connectionHandler->sendBytes(bytesArray, 2);

                //if we are sending an course register message
                //encode message properly
            } else if (stringVector[0] == "COURSEREG") {
                shortToBytes((short) 5, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

                //if we are sending a kdam check message
                //encode message properly
            } else if (stringVector[0] == "KDAMCHECK") {
                shortToBytes((short) 6, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

                //if we are sending a course status message
                //encode message properly
            } else if (stringVector[0] == "COURSESTAT") {
                shortToBytes((short) 7, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

                //if we are sending an student status message
                //encode message properly
            } else if (stringVector[0] == "STUDENTSTAT") {
                shortToBytes((short) 8, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
                connectionHandler->sendFrameAscii(stringVector[1], '\0');

                //if we are sending an is Registered message
                //encode message properly
            } else if (stringVector[0] == "ISREGISTERED") {
                shortToBytes((short) 9, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

                //if we are sending an unregister message
                //encode message properly
            } else if (stringVector[0] == "UNREGISTER") {
                shortToBytes((short) 10, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                //if we are sending a my courses message
                //encode message properly
            } else if (stringVector[0] == "MYCOURSES") {
                shortToBytes((short) 11, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
            }
        }
    }

}

//converts short to bytes
void KeyboardReader::shortToBytes(short num, char *bytesArray) {
    bytesArray[0] = ((num >> 8) & 0xFF);
    bytesArray[1] = (num & 0xFF);

}

