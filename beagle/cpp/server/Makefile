CCXX=g++
CFLAGS=-g
LFLAGS=-std=c++11 -pthread #-v
TCP_LOC=tcp/
MAIN_LOC=main/

OBJS=$(MAIN_LOC)main.cpp $(TCP_LOC)TCPCommunication.cpp $(TCP_LOC)Messanger.cpp $(MAIN_LOC)Invoker.cpp $(MAIN_LOC)md5hash.c

test:
	$(CCXX) $(LFLAGS) $(OBJS) -o test
	
all:
	$(CCXX) $(LFLAGS) $(OBJS) -o serv
	
