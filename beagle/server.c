#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>

#include <string.h>
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>

#include "commands.h"


#define IN_ADDR		INADDR_ANY
#define IN_PORT		12345

#define SOFFICE_PATH	"/usr/bin/soffice"
#define XDOTOOL_PATH	"/usr/bin/xdotool"
//przed PID dac # ewentualnie
#define SHELL2(PID,key) "PID=`pidof soffice.bin`\n windowID=`xdotool search --pid " PID " | tail -1`\n \
						xdotool windowfocus --sync $windowID key " key " \n"
						
#define SHELL(key) sprintf(xdo_buff,"PID=`pidof soffice.bin`\n windowID=`xdotool search --pid $PID | tail -1`\n xdotool windowfocus --sync $windowID key %s \n",key)


int socket_desc=0;
struct sockaddr_in addr_struct;
int socket_cli=0;
struct sockaddr_in addr_cli;
socklen_t addrlen;
int sockoptval = 1;
pid_t PID = 0;
char file_name[20] = {"git.ppt"};

char xdo_buff[500];

file_t received_file_type = PPT;
char *current_cmd;
//char mesg[1000];

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
	
	char mesg[5] ;//= "Testowy napis\n";
	int n;
	socklen_t clilen;
	while ( (socket_cli = accept(socket_desc, (struct sockaddr *)&addr_cli, &addrlen)) < 0) 
	{
	
	
		if ((errno != ECHILD) && (errno != ERESTART) && (errno != EINTR))
		{
		    perror("accept failed");
		    exit(1);
		}
        
        //sendto(socket_cli,mesg,14,0,(struct sockaddr *)&addr_cli,sizeof(addr_cli));
        	printf("Connected");
         
	}
	
	printf("Connected2");

rcv_cmd:
	n = recv(socket_cli, mesg, 10, MSG_WAITALL);
	
	switch(n){
		case 0: 
			goto start;
			break;
		case -1:
			goto rcv_cmd;
			break;		
	}
	
	
	
	
	

start:	
	//if( memcmp(
	// dopisywac porownywanie komendy i reakcja. -> napisac tablice z komenndami!!!
	
	PID = vfork();
	//wątek obsługujący programy otwierające pliki
	if( PID == 0 ){
		
		switch(received_file_type){
			case PPT:
				execl(SOFFICE_PATH, "soffice", "--show", PPT_FILE, NULL);
				break;
			case PDF:
			break;
			
			case MOVIE:
			break;
		}
	}
	
	//wątek podstawowoy odpowiedzialny za komunikacje i utrzymanie serwera
	for(;;){
	
	n = recvfrom(socket_cli,mesg,5,0,(struct sockaddr *)&addr_cli,&clilen);
        sendto(socket_cli,"OKI\n",4,0,(struct sockaddr *)&addr_cli,sizeof(addr_cli));
	printf("%.*s\n",n,mesg);
	
	current_cmd=get_command(received_file_type,(command_t)atoi(mesg));
	//printf("%s",current_cmd);
	//wątek dodatkowy do obsługi xdotool
	if(vfork()==0){
	//ewentualnie zrobic kolejke  skoro dane system zeby nie tworzyc nowego procesu za kazdym razem
		SHELL(current_cmd);
		printf("%s",current_cmd);
		printf(xdo_buff);
		system(xdo_buff);
		
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
