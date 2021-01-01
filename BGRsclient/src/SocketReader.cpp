//
// Created by spl211 on 21/12/2020.
//

#include "SocketReader.h"

using namespace std;

SocketReader::SocketReader(ConnectionHandler *connectionHandler, bool *isTerminated, bool *shouldTerminate) :
        connectionHandler(connectionHandler), isTerminated(isTerminated), shouldTerminate(shouldTerminate) {}


//TODO there is lots of code repeat here but first make sure its ok before reducing to functions

void SocketReader::run() {

    while (!*isTerminated) { //todo not sure about star and bool in general
        char bytes[2];
        bool getBytes = connectionHandler->getBytes(bytes, 2);
        if (getBytes) {
            short opcode = bytesToShort(bytes);
            if (opcode == 12) { //ACK message
                getBytes = connectionHandler->getBytes(bytes, 2);
                if (getBytes) {
                    short messageOpcode = bytesToShort(bytes); //tells us which message the ack was sent for
                    //logout message
                    if (messageOpcode == 4) {
                        //terminate connection
                        *isTerminated = true;
                        cout << "ACK " << messageOpcode << endl;
                        connectionHandler->getBytes(bytes, 1);
                    }

                    //TODO add  /n for new line in all!!!!

                    //kdam check message
                    else if (messageOpcode == 6) {
                        string optionalSection = "";
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }

                        //course stat message
                    else if (messageOpcode == 7) {
                        string optionalSection = "";
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }
//                        getBytes = connectionHandler->getBytes(bytes, 2);
//                        short courseNum = bytesToShort(bytes);
//                        string courseName="";
//                        connectionHandler->getFrameAscii(courseName, '\0');
//                        getBytes = connectionHandler->getBytes(bytes, 2);
//                        short numOfSeatsAvailable = bytesToShort(bytes);
//                        getBytes = connectionHandler->getBytes(bytes, 2);
//                        short maxNumOfSeats = bytesToShort(bytes);
//                        string students = "";
//                        for (int i = 0; i < (maxNumOfSeats - numOfSeatsAvailable); i++) { //going through string of registered student
//                            string student="";
//                            connectionHandler->getFrameAscii(student, '\0');
//                            students.append(student);
//                            students.append(" ");
//                        }
//                        cout << "ACK " << messageOpcode << " " << courseNum << " " << courseName << " " << numOfSeatsAvailable << " " << maxNumOfSeats
//                             << students.substr(0,students.size()-2) << endl; //todo check student string size

                        //student stat message
                    else if (messageOpcode == 8) {
                        string optionalSection = "";
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }
                        //is registered message
                    else if (messageOpcode == 9) {
                        string optionalSection = "";
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }

                        //my courses message
                    else if (messageOpcode == 11) {
                        string optionalSection = "";
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }
                    //message opcode = 1/2/3/5/10 (standard ACK)
                    else {
                        cout << "ACK " << messageOpcode << endl;
                        connectionHandler->getBytes(bytes, 1);
                    }

                }
            }
            else if (opcode = 13) { //error message
                getBytes = connectionHandler->getBytes(bytes, 2);
                if (getBytes) {
                    short messageOpcode = bytesToShort(bytes); //tells us which message the error was sent for
                    if (messageOpcode == 4) {
                        *shouldTerminate = false;//if an error message was sent for logout we should not terminate
                    }
                    cout << "ERROR " << messageOpcode << endl;
                }
            }
        }
    }


}

short SocketReader::bytesToShort(char *bytesArray) {
    short output = (short) ((bytesArray[0] & 0xff) << 8);
    output += (short) (bytesArray[1] & 0xff);
    return output;
}

