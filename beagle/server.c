#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

#include <string.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <errno.h>


#define IN_ADDR		INADDR_ANY
#define IN_PORT		55555

int socket_desc=0;
struct sockaddr_in addr_struct;
int socket_cli=0;
struct sockaddr_in addr_cli;
socklen_t addrlen;
int sockoptval = 1;

int main(void)
{

	memset( (char*)&addr_struct, 0, sizeof(int) );

	addr_struct.sin_family = AF_INET;
	addr_struct.sin_addr.s_addr = htonl(IN_ADDR);
	addr_struct.sin_port = htons(IN_PORT);

	if ((socket_desc = socket(AF_INET, SOCK_STREAM, 0)) < 0) 
	{
		perror("Cannot create socket\n"); //spróbować ponownie
		return 0;
	}
	else printf("Socket has been created\n");

	if ( bind(socket_desc, (struct sockaddr *)&addr_struct, sizeof(addr_struct)) < 0 ) 
	{
		perror("Bind failed\n");
		return 0;
	}
	else printf("Binding has ended succesfully\n");

	if( listen(socket_desc,1) < 0)
	{
		perror("Listen failed\n");
	}
	else printf("Listening...\n");

	while ( (socket_cli = accept(socket_desc, (struct sockaddr *)&addr_cli, &addrlen)) < 0) 
	{
        if ((errno != ECHILD) && (errno != ERESTART) && (errno != EINTR))
        {
            perror("accept failed");
            exit(1);
        } 
	}


	// 1) odczytywać odebrane komendy
	// 2) jeżeli komenda dotyczy przycisku to zrobić vfork i odpalić program z button_event.c (execl)


	setsockopt(socket_desc, SOL_SOCKET, SO_REUSEADDR, &sockoptval, sizeof(int));
	printf("Closing");	
	close(socket_desc);
	close(socket_cli);



}