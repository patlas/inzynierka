/*
 * TCPCommunication.h
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */

#ifndef TCP_TCPCOMMUNICATION_H_
#define TCP_TCPCOMMUNICATION_H_

#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

#include <string.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#include <fcntl.h>
#include <signal.h>

#define TLV_STRUCT_SIZE	20
#define TLV_DATA_SIZE	11

typedef enum {
	NO_ERROR = 0,
	SOCKET_ERROR = -1,
	BIND_ERROR = -2,
	LISTEN_ERROR = -3,
	ACCEPT_ERROR = -4,
	SERVER_ERROR = -8
} TCPCommunicationError_t;

#pragma pack(push)
#pragma pack(1)
typedef struct {
	uint8_t type;
	uint64_t length;
	uint8_t value[11];
}TLVStruct;
#pragma pack(pop)

class TCPCommunication {

private:
	bool blockingMode = false;
	int socket_desc=0;
	struct sockaddr_in addr_struct;
	int socket_cli=0;
	struct sockaddr_in addr_cli;
	socklen_t addrlen=0;
	struct timeval tv;
	int sockoptval = 1;


	char mesg[1000];




public:
	TCPCommunication(uint32_t, uint16_t);
	TCPCommunication();
	virtual ~TCPCommunication();

	void setBlockingSocketOption(bool, int);
	bool listenSocket();
	bool acceptConnection();
	TCPCommunicationError_t startServer();
	TCPCommunicationError_t catchNewConnection();
	int sendData(uint8_t*);
	int receiveData(uint8_t*);

private:
	bool createSocket();
	bool bindToSocket();
//TODO - write error check function


};

#endif /* TCP_TCPCOMMUNICATION_H_ */