/*
 * TCPCommunication.cpp
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */

#include "TCPCommunication.h"

TCPCommunication::TCPCommunication(){}

TCPCommunication::TCPCommunication(uint32_t in_addr, uint16_t in_port) {
	// TODO Auto-generated constructor stub

	memset( (char*)&addr_struct, 0, sizeof(int) );

	addr_struct.sin_family = AF_INET;
	addr_struct.sin_addr.s_addr = htonl(in_addr);
	addr_struct.sin_port = htons(in_port);

}

TCPCommunication::~TCPCommunication() {
	// TODO Auto-generated destructor stub
}


//Create TCP socket
bool TCPCommunication::createSocket()
{
	if ((socket_desc = socket(AF_INET, SOCK_STREAM, 0)) < 0)
	{
		perror("Cannot create socket\n"); //spróbować ponownie
		return false;
	}
	return true;
}

//BARDZO WAŻNE USTAWIANIE FLAGI BLOKOWANIA !!!!!!
void TCPCommunication::setBlockingSocketOption(bool set) //moze zwraca bool jezeli fcntl sie nie powiedzie?
{
	int opts;
	opts = fcntl(socket_desc,F_GETFL);
	if(set){
		opts = opts & (~O_NONBLOCK);
	}
	else{
		opts = opts | (O_NONBLOCK);
	}

	fcntl(socket_desc,F_SETFL,opts);
}


bool TCPCommunication::bindToSocket()
{
	if ( bind(socket_desc, (struct sockaddr *)&addr_struct, sizeof(addr_struct)) < 0 )
	{
		perror("Bind failed\n");
		return false;
	}

	return true;
}

bool TCPCommunication::listenSocket()
{
	if( listen(socket_desc,1) < 0)
	{
		perror("Listen failed\n");
		return false;
	}

	printf("Listening...\n");
	return true;
}

bool TCPCommunication::acceptConnection()
{
	socklen_t clilen;
	while ( (socket_cli = accept(socket_desc, (struct sockaddr *)&addr_cli, &addrlen)) < 0)
	{


		if ((errno != ECHILD) && (errno != ERESTART) && (errno != EINTR))
		{
			perror("Accept failed");
			//exit(1);
			return false;
		}

		printf("Trying to connect...\n");
	}
	return true;
}


TCPCommunicationError_t TCPCommunication::startServer()
{
	if(!createSocket()) return SOCKET_ERROR;

	setBlockingSocketOption(true);

	if(!bindToSocket()) return BIND_ERROR;
	if(!listenSocket()) return LISTEN_ERROR;
	if(!acceptConnection()) return ACCEPT_ERROR;

	return NO_ERROR;
}

TCPCommunicationError_t TCPCommunication::catchNewConnection()
{
	if(!listenSocket()) return LISTEN_ERROR;
	if(!acceptConnection()) return ACCEPT_ERROR;

	return NO_ERROR;
}

int TCPCommunication::receiveData(uint8_t *buffer)
{
	return recv(socket_cli, buffer, TLV_STRUCT_SIZE, 0);
}

int TCPCommunication::sendData(uint8_t *buffer)
{
	int size = 0;
	if(blockingMode == false)
	{
		while(!(size = send(socket_cli,buffer,TLV_STRUCT_SIZE,0)));
		return size;
	}
	else{
		return send(socket_cli,buffer,TLV_STRUCT_SIZE,0);
	}
}





