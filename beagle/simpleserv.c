#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

#include <string.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <errno.h>
#include <fcntl.h>


#define IN_ADDR		INADDR_ANY
#define IN_PORT		12345

#define PPT_FILE_NAME	"a.txt"

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

/* BARDZO WAŻNE USTAWIANIE FLAGI BLOKOWANIA !!!!!!*/
	int opts;
	opts = fcntl(socket_desc,F_GETFL);
	opts = opts & (~O_NONBLOCK);
	fcntl(socket_desc,F_SETFL,opts);

/*************************************************/
	


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


	int n;
	socklen_t clilen;
	char mesg[100];

	

	n = recv(socket_cli,mesg,100,0);
	//printf("%.*s\n",n,mesg);
	printf("%s\n",mesg);
	printf("%d\n",n);

	if((memcmp(mesg, "stream",n)) == 0){
	n = recv(socket_cli,mesg,100,0);
	printf("%d %d",n,mesg[0]);
	unsigned long fsize;
	switch(n){
		case 1:
		fsize = mesg[0];
		break;
		
		case 2:
		fsize = mesg[0]+256*mesg[1];
		break;

		case 3:
		fsize = mesg[0]+256*mesg[1]+mesg[2]*65536;
		break;
	
	}	
	printf("File size is: %d\n",fsize);
	n = recv(socket_cli,mesg,100,0);
	printf("Hash code is: %.*s\n",n,mesg);
	
	
	//odbieranie pliku
	FILE* fd = fopen(PPT_FILE_NAME, "w+");
	int rsize = 0;
	while(rsize<fsize){
	//n = recvfrom(socket_cli,mesg,100,0,(struct sockaddr *)&addr_cli,&clilen);
		n=recv(socket_cli,mesg,100,0);
		if(fwrite(mesg,1,n,fd) != n) printf("Zapisano za malo");
		rsize+=n;
	
	
	}
	fclose(fd);

	}
	//sendto(socket_cli,"success2",8,0,(struct sockaddr *)&addr_cli,sizeof(addr_cli));
	


	// 1) odczytywać odebrane komendy
	// 2) jeżeli komenda dotyczy przycisku to zrobić vfork i odpalić program z button_event.c (execl)


	setsockopt(socket_desc, SOL_SOCKET, SO_REUSEADDR, &sockoptval, sizeof(int));
	printf("Closing");	
	close(socket_desc);
	close(socket_cli);



}
