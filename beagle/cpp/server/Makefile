CCXX=g++
CFLAGS=-g
LFLAGS=-std=c++11 -lpthread -v
TCP_LOC=tcp/
TEST_LOC=test/

OBJS=$(TEST_LOC)main.cpp $(TCP_LOC)TCPCommunication.cpp $(TCP_LOC)Messanger.cpp

test:
	$(CCXX) $(LFLAGS) $(OBJS) -o test
	
all:
	$(CCXX) $(LFLAGS) $(OBJS) -o serv
	