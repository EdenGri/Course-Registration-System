//
// Created by spl211 on 21/12/2020.
//

#include <boost/algorithm/string/split.hpp>
#include "KeyboardReader.h"
#include <boost/algorithm/string/classification.hpp>

using namespace std;
using namespace boost::algorithm;

KeyboardReader::KeyboardReader(ConnectionHandler *connectionHandler, bool *isTerminated, bool *shouldTerminate) :
connectionHandler(connectionHandler),isTerminated(isTerminated),shouldTerminate(shouldTerminate){}

//TODO there is lots of code repeat here but first make sure its ok before reducing to functions

void KeyboardReader::run() {

    while (!*isTerminated) { //todo not sure about star and bool in general
        const short bufsize = 1024;
        char buf[bufsize];
        while (!*shouldTerminate) { //todo not sure about star and bool in general
            std::cin.getline(buf, bufsize);
            std::string line(buf);
            int len = line.length();
            std::vector<std::string> stringVector;
            split(stringVector, line, is_any_of(" "));
            char bytesArray[2];

            if (stringVector[0] == "ADMINREG") {
                shortToBytes((short) 1, bytesArray); //convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
                connectionHandler->sendFrameAscii(stringVector[1], '\0');
                connectionHandler->sendFrameAscii(stringVector[2], '\0');

            } else if (stringVector[0] == "STUDENTREG") {
                shortToBytes((short) 2, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
                connectionHandler->sendFrameAscii(stringVector[1], '\0');
                connectionHandler->sendFrameAscii(stringVector[2], '\0');

            } else if (stringVector[0] == "LOGIN") {
                shortToBytes((short) 3, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);//convert opcode given in short to bytes
                connectionHandler->sendFrameAscii(stringVector[1], '\0');
                connectionHandler->sendFrameAscii(stringVector[2], '\0');

            } else if (stringVector[0] == "LOGOUT") {
                shortToBytes((short) 4, bytesArray);//convert opcode given in short to bytes
                *shouldTerminate = true; //todo check star
                connectionHandler->sendBytes(bytesArray, 2);

            } else if (stringVector[0] == "COURSEREG") {
                shortToBytes((short) 5, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

            } else if (stringVector[0] == "KDAMCHECK") {
                shortToBytes((short) 6, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);


            } else if (stringVector[0] == "COURSESTAT") {
                shortToBytes((short) 7, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

            } else if (stringVector[0] == "STUDENTSTAT") {
                shortToBytes((short) 8, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
                connectionHandler->sendFrameAscii(stringVector[1], '\0');

            } else if (stringVector[0] == "ISREGISTERED") {
                shortToBytes((short) 9, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);
                connectionHandler->sendBytes(bytesArray, 2);

            } else if (stringVector[0] == "UNREGISTER") {
                shortToBytes((short) 10, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

                int courseNum = stoi(stringVector[1]);
                shortToBytes((short) courseNum, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);

            } else if (stringVector[0] == "MYCOURSES") {
                shortToBytes((short) 11, bytesArray);//convert opcode given in short to bytes
                connectionHandler->sendBytes(bytesArray, 2);
            }
        }
    }

}

//TODO should we add conversion to uppercase ? ron did

void KeyboardReader::shortToBytes(short num, char *bytesArray) {
    bytesArray[0] = ((num >> 8) & 0xFF);
    bytesArray[1] = (num & 0xFF);

}

