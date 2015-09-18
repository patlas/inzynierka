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

#include "commands.h"
#include "md5hash.h"


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
char file_name2[20] = {"git.ppt"};

char xdo_buff[500];

file_t received_file_type = -1;
uint8_t current_cmd;
char stream_flag = 0;
char mesg[1000];
char chash[32];
char rhash[32];
unsigned long fsize;
uint32_t ss[5];
uint16_t pptCurrentPage = 1;
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
	
	//char mesg[5] ;//= "Testowy napis\n";
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
        	printf("Connected\n");
         
	}
	
	printf("Connected2\n");

	int counter = 0;
rcv_cmd:
	n = recv(socket_cli, mesg, 100, 0);
	printf("rcv_command: %.*s\n",n,mesg);
	
	
	if((memcmp(mesg, get_serv_cmd(stream),n)) == 0){
		printf("%d %d\n",n,mesg[0]);
		goto rcv_file;
	}
	else if((memcmp(mesg, get_serv_cmd(button),n)) == 0){
		//obsulga buttonu	
	}
	else if((memcmp(mesg, get_serv_cmd(disconnect),n)) == 0){
		//zrobic disconnect servera i ruszyc od poczatku programu
	}
	else {
		//wrocic do rcv_cmd jezeli kilkakrotnie tak sie stanie lub minie timeout to
		//cos sie posypalo zorlaczyc klienta i zaczac wszystko od nowa
		counter++;
		if(counter>10){
			//pozamykac sockety, zwolnic adres, i restartnac server
		}
		goto rcv_cmd;
	}
	


rcv_file:
	
	n = recv(socket_cli,mesg,100,0);
	char *end;
	mesg[n] = '\0';
	fsize = strtol(mesg, &end, 10);
	//n = recv(socket_cli,ss,5,0);
	printf("String size: %.*s\n",n,mesg);
	printf("Long size: %d\n",fsize);
	printf("File size is: %d\n",fsize);
	n = recv(socket_cli,mesg,100,0);
	printf("Hash code is: %.*s\n",n,mesg);
	memcpy(rhash,mesg,32);
	n = recv(socket_cli,mesg,100,0);
	received_file_type = mesg[0];
	printf("%d\n",mesg[0]/*file_name[received_file_type]*/);
			//filename
	
	
	
	//odbieranie pliku
	FILE* fd = fopen(file_name[received_file_type], "w+");
	unsigned long rsize = 0;
	while(rsize<fsize){
	//n = recvfrom(socket_cli,mesg,100,0,(struct sockaddr *)&addr_cli,&clilen);
		n=recv(socket_cli,mesg,10,0);
		
		if(stream_flag != STREAM_ER){
			if(fwrite(mesg,1,n,fd) != n){
				printf("Blad zapisu pliku");
				stream_flag = STREAM_ER;
				fclose(fd);
				//break;	
			}
		}
		rsize+=n;
		printf("rsize = %d", rsize);
	
	//PRZERYWAM ODBIRANIE I CO??? JAVA DALEJ WYSYŁA I WTEDY ODBIERAM ZLE KOMUNIKATY - zakomentowany break
	}
	fclose(fd);
	
	if(stream_flag == STREAM_ER){
		//wyslac blad streamu
		//powrot do odbierania komend -> jezeli klient zadecyduje o retransmisji to tu wrocimy
		printf("stream_flag = -1");
		//goto rcv_cmd;
	}
	
	n=recv(socket_cli,mesg,100,0);
	printf("Query: %.*s\n",n,mesg);
	//sprawdzic hash
	//DONE -sprawdzic typ pliku i ustawic go w received_file_type
	
	while(!md5(file_name[received_file_type],chash));
	
	if(memcmp(chash,rhash,32) != 0){
		//hash niepoprawny!!!
		printf("Hash compare error\n");
		printf("rHash: %.*s\n",32,rhash);
		printf("cHash: %.*s\n",32,chash);
		stream_flag = HASH_ER;
	}
	
	if( (memcmp(mesg,SUCCESS_QUERY,n)) == 0 ){
		printf("SUCCESS_QUERY: %s\n", mesg);
		//DONE-wyslac potwierdzenei poprawnosci odebrania pliku
		if( stream_flag < 0 ){ 
			n = send(socket_cli,TRANSFER_ERROR,sizeof(TRANSFER_ERROR),0);
			//TODO - w javie okienko - opening file error please try againg
		}
		else{
			n = send(socket_cli,TRANSFER_SUCCESFULL,sizeof(TRANSFER_SUCCESFULL),0);
		}
		printf("Wyslano: %d\n",n);
	}
	
	
	
	
	
	

start:	
	//if( memcmp(
	// dopisywac porownywanie komendy i reakcja. -> napisac tablice z komenndami!!!
	
	PID = vfork();
	//wątek obsługujący programy otwierające pliki
	if( PID == 0 ){
		printf("Starting process pid: %d", getpid());
		
		switch(received_file_type){
			case PPT:
				execl(SOFFICE_PATH, "soffice", "--show", file_name[PPT], NULL);
				break;
			case PDF:
			break;
			
			case TXT:
				execl("/usr/bin/mousepad", "mousepad", "plik.txt", NULL);
				break;
			
			case MOVIE:
			break;
			
			default:
				//zamykame proces ktory mial otworzyc plik
				printf("Unknown file type");
				//TODO-wyslać informacje przez kolejke do procesu głównego że sie nie udalo
				//i rozpoczac prace serwera od poczatku
				exit(1);
				//goto rcv_cmd;
				break;
		}
	}
	
	//tymczasowo -- do usunięcia
	//if(PID != 0) return 0;
	
	//wątek podstawowoy odpowiedzialny za komunikacje i utrzymanie serwera
	for(;;){
	//TODO - obierac informacje sterujace z kolejki
	printf("Czekam na komende\n");
	n = recv(socket_cli,mesg,100,0);
	printf("Command: %d",mesg[0]);
	
	if(n==1){
		current_cmd = mesg[0];
	}
	
	if(kill(PID,0) != 0){
		//proces potomny został zakończony więc wracamy do początku programu
		//goto rcv_cmd;
		printf("Zabito program\n");
	}
	
	uint16_t actionPage = pptCurrentPage;
	uint8_t strCommand[16];
	uint8_t pindex=0;
	
	switch(received_file_type){
		
		case PPT:
			
			switch(current_cmd){
				case ppt_nextp:
					actionPage+=2;
					pindex = sprintf(strCommand, "%d", actionPage);
					memcpy(&strCommand[pindex],"+ Enter Left\0",13);
					printf("Str command: %s\n",strCommand);
					pptCurrentPage++;
				break;
			
				case ppt_prevp:
					//actionPage+=2;
					pindex = sprintf(strCommand, "%d", actionPage);
					memcpy(&strCommand[pindex],"+ Enter Left\0",13);
					printf("Str command: %s\n",strCommand);
					pptCurrentPage--;
				break;
				
			}
			
			if(vfork()==0){
				SHELL(strCommand);
				printf("%s",xdo_buff);
				//system(xdo_buff);
				exit(1);
			}	
			
			break;
		
		
		
	}
	//printf("%d\n",mesg[0]);
	
	/* POPRAWNY INTERPRETER KOMEND:
	 * current_cmd=get_command(received_file_type,(command_t)atoi(mesg));
	 * */
	
	//printf("%s",current_cmd);
	//wątek dodatkowy do obsługi xdotool
	
	
	/* DOBRZE NAPISANE STEROWANIE PONIZEJ !!!!!!!!!!!
	 *if(vfork()==0){
	//ewentualnie zrobic kolejke  skoro dane system zeby nie tworzyc nowego procesu za kazdym razem
		SHELL(current_cmd);
		printf("%s",current_cmd);
		printf(xdo_buff);
		system(xdo_buff);
		
		exit(1);
		
	}*/
	
	}


	// 1) odczytywać odebrane komendy
	// 2) jeżeli komenda dotyczy przycisku to zrobić vfork i odpalić program z button_event.c (execl)


	setsockopt(socket_desc, SOL_SOCKET, SO_REUSEADDR, &sockoptval, sizeof(int));
	printf("Closing");	
	close(socket_desc);
	close(socket_cli);



}
