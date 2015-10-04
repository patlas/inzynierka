CCXX=g++
CFLAGS=-g
LFLAGS=-std=c++11 -pthread -v
TCP_LOC=tcp/
TEST_LOC=main/

OBJS=$(TEST_LOC)main.cpp $(TCP_LOC)TCPCommunication.cpp $(TCP_LOC)Messanger.cpp

test:
	$(CCXX) $(LFLAGS) $(OBJS) -o test
	
all:
	$(CCXX) $(LFLAGS) $(OBJS) -o serv
	