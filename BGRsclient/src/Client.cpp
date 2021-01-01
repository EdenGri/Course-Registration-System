//
// Created by spl211 on 21/12/2020.
//
#include <stdlib.h>
#include <connectionHandler.h>
#include "KeyboardReader.h"
#include "SocketReader.h"
#include <thread>
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>
using namespace std;
using namespace boost::algorithm;

/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/

int main (int argc, char *argv[]) {
    /*if (argc < 3) {//todo
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
     */
    bool isTerminated = false; //todo not sure about these
    bool shouldTerminate = false; //todo not sure about these

    std::string host = argv[1];//todo
    short port = atoi(argv[2]);//todo

    //std::string host = "127.0.0.1";//todo
    //short port = 7777;//todo
    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    KeyboardReader task1(&connectionHandler, &isTerminated, &shouldTerminate);
    SocketReader task2(&connectionHandler, &isTerminated, &shouldTerminate);

    std::thread thread1(&KeyboardReader::run, std::ref(task1));
    std::thread thread2(&SocketReader::run, std::ref(task2));
    thread1.join();
    thread2.join(); //todo check if to flip order of 1 and 2 or are they good ??

    return 0;


}
