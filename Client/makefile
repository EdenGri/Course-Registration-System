CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude
LDFLAGS:=-lboost_system -lboost_thread -pthread


all: bin/BGRSclient


bin/BGRSclient: bin/connectionHandler.o bin/KeyboardReader.o bin/SocketReader.o bin/Client.o
	@echo 'Building target: Client'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/BGRSclient bin/connectionHandler.o bin/KeyboardReader.o bin/SocketReader.o bin/Client.o $(LDFLAGS)
	@echo 'Finished building target: Client'
	@echo ''



bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/connectionHandler.o src/connectionHandler.cpp

bin/KeyboardReader.o: src/KeyboardReader.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/KeyboardReader.o src/KeyboardReader.cpp


bin/SocketReader.o: src/SocketReader.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/SocketReader.o src/SocketReader.cpp

bin/Client.o: src/Client.cpp
	g++ -g -Wall -Weffc++ -std=c++11 -c -Iinclude -o bin/Client.o src/Client.cpp


#Clean the build directory
clean:
	rm -f bin/*

