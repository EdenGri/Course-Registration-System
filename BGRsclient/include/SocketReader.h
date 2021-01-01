//
// Created by spl211 on 21/12/2020.
//

#ifndef BOOST_ECHO_CLIENT_SOCKETREADER_H
#define BOOST_ECHO_CLIENT_SOCKETREADER_H

#include "connectionHandler.h"

class SocketReader {

public:
    SocketReader(ConnectionHandler *connectionHandler, bool *isTerminated, bool *shouldFinish);
    void run();
    short bytesToShort(char* bytesArray);

private:

    ConnectionHandler *connectionHandler;
    bool *isTerminated;
    bool *shouldTerminate;
};
#endif //BOOST_ECHO_CLIENT_SOCKETREADER_H
