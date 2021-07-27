//
// Created by spl211 on 21/12/2020.
//

#include "SocketReader.h"

using namespace std;

SocketReader::SocketReader(ConnectionHandler *connectionHandler, bool *isTerminated, bool *shouldTerminate) :
        connectionHandler(connectionHandler), isTerminated(isTerminated), shouldTerminate(shouldTerminate) {}


void SocketReader::run() {
    //while process is not terminated
    while (!*isTerminated) {
        char bytes[2];
        //gets first 2 bytes : opcode
        bool getBytes = connectionHandler->getBytes(bytes, 2);
        if (getBytes) { //if we successfully got the bytes
            short opcode = bytesToShort(bytes); //convert bytes to short
            if (opcode == 12) { //ACK message
                //get second two bytes: message opcode
                getBytes = connectionHandler->getBytes(bytes, 2);
                if (getBytes) { //if we successfully got the bytes
                    short messageOpcode = bytesToShort(bytes); //tells us which message the ack was sent for

                    //logout message
                    if (messageOpcode == 4) {
                        //terminate connection
                        *isTerminated = true;
                        //decodes message properly
                        cout << "ACK " << messageOpcode << endl;
                        connectionHandler->getBytes(bytes, 1);
                    }

                        //kdam check message
                    else if (messageOpcode == 6) {
                        string optionalSection = "";
                        //decodes message properly
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }

                        //course stat message
                    else if (messageOpcode == 7) {
                        string optionalSection = "";
                        //decodes message properly
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }

                        //student stat message
                    else if (messageOpcode == 8) {
                        string optionalSection = "";
                        //decodes message properly
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }
                        //is registered message
                    else if (messageOpcode == 9) {
                        string optionalSection = "";
                        //decodes message properly
                        connectionHandler->getFrameAscii(optionalSection, '\0');
                        cout << "ACK " << messageOpcode << "\n" << optionalSection << endl;
                    }

                        //my courses message
                    else if (messageOpcode == 11) {
                        string optionalSection = "";
                        //decodes message properly
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
                //error message
            else {
                //gets upcoming 2 bytes: message opcode
                getBytes = connectionHandler->getBytes(bytes, 2);
                if (getBytes) { //if successful
                    //decodes message properly
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

//converts bytes to short
short SocketReader::bytesToShort(char *bytesArray) {
    short output = (short) ((bytesArray[0] & 0xff) << 8);
    output += (short) (bytesArray[1] & 0xff);
    return output;
}

