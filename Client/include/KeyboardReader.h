//
// Created by spl211 on 21/12/2020.
//

#ifndef BOOST_ECHO_CLIENT_KEYBOARDREADER_H
#define BOOST_ECHO_CLIENT_KEYBOARDREADER_H

#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>
#include "connectionHandler.h"

class KeyboardReader {

public:

    KeyboardReader(ConnectionHandler *connectionHandler, bool *isTerminated, bool *shouldTerminate);
    void run();
    void shortToBytes(short num, char* bytesArray);

private:

    ConnectionHandler *connectionHandler;
    bool *isTerminated;
    bool *shouldTerminate;


};

#endif //BOOST_ECHO_CLIENT_KEYBOARDREADER_H

