project(Boost_Echo_Client)

set(Boost_USE_STATIC_LIBS           ON)
set(Boost_USE_MULTITHREADING        ON)
set(Boost_USE_STATIC_RUNTIME       OFF)

find_package(Boost REQUIRED COMPONENTS system)
cmake_minimum_required(VERSION 3.17)
set(CMAKE_CXX_STANDARD 11)

SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -pthread")
include_directories(include)

add_executable(Boost_Echo_Client
        include/connectionHandler.h
        src/Client.cpp
        src/connectionHandler.cpp
        src/SocketReader.cpp include/SocketReader.h src/KeyboardReader.cpp include/KeyboardReader.h)

target_link_libraries(Boost_Echo_Client Boost::system)
