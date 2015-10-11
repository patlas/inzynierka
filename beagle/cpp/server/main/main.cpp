/*
 * main.cpp
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */
#include <iostream>
#include <thread>
#include <unistd.h>
#include <sys/types.h>
#include <sys/sendfile.h>
#include <sys/stat.h>
#include "../tcp/TCPCommunication.h"
#include "../tcp/Messanger.h"
#include "../main/Invoker.h"
#include "../main/md5hash.h"
#include <mutex>
#include <queue>
#include <string>
#include <string.h>
#include <stdint.h>
#include <fstream>
#include <map>


#define SERV_ADDR	INADDR_ANY
#define SERV_PORT	12345

using namespace std;


mutex tMutex, rMutex;
queue<QueueStruct_t> tQueue;
queue<string> rQueue;
pid_t childPID;
uint64_t fSize = 0;
string fHash;
string fType;
char xdo_buff[500];
uint16_t pptCurrentPage = 1;


string getCommand()
{
	string cmd;
	cmd.clear();
	while(cmd.empty())
	{
		if(rMutex.try_lock()){
			if(!rQueue.empty()){
				cmd = rQueue.front();
				rQueue.pop();
			}
			rMutex.unlock();
		}
	}
	return cmd;
}

void sendCommand(string cmd)
{
	QueueStruct_t command_struct;
    command_struct.stream=false;
	command_struct.command = cmd;
	
    tMutex.lock();
	tQueue.push(command_struct);
	tMutex.unlock();

}





//tutaj funkcje do rejestracji
void test(void *param){

    cout<<"Invoker dziala"<<endl;//(int)(*param)<<endl;
    printf("%d",*((int*)param));
}




void server_restart(void *param)
{
    //TODO - zabić messangera i stworzyć nowy obiekt???
    TCPCommunication *tcpptr = (TCPCommunication *) param;
    TCPCommunicationError_t error_code;
    if( (error_code=tcpptr->catchNewConnection()) == NO_ERROR)
    {
        cout<<"New connection established"<<endl;
    }
    else
    {
        printf("An error occured while trying new connection established. ERROR CODE: %d",error_code);
    }

}


void receive_stream(void *param)
{
    sendCommand(GET_SIZE);
    string size = getCommand();
    fSize = stol(size);
    sendCommand(GET_HASH);
    fHash = getCommand();
    sendCommand(GET_TYPE);
    fType = getCommand();
}

void check_file(void *param)
{
    char md5_tab[50];

    md5(TEMP_NAME, md5_tab); 
    string md5_hash(md5_tab);

    if(md5_hash.compare(fHash)==0)
    {
        cout<<"Received file hash match"<<endl;
        sendCommand(F_RECEIVED);
    }
    else
    {
        //wyslac error code
        cout<<"Hash doesn't match"<<endl;
        cout<<"Receied hash: "<<fHash<<endl;
        cout<<"Compute hash: "<<md5_hash<<endl;
        sendCommand(F_ERROR);
    }

}


void start_file(void *param)
{
    uint8_t index;
    for(index=0; index<EXTENSION_TAB_SIZE; index++)
    {
        if(extension_tab[index].compare(fType) == 0){
            //prepare file with proper extension
            int source = open(TEMP_NAME, O_RDONLY,0);
            int dest = open(file_name_tab[index].c_str(), O_WRONLY | O_CREAT, 0664);

            struct stat stat_source;
            fstat(source, &stat_source);
            sendfile(dest, source, 0, stat_source.st_size);
            
            close(source);
            close(dest);

            break;  
        } 
    }

    childPID = vfork();
	//proces obsługujący programy otwierające pliki
	if( childPID == 0 )
    {
        switch(index)
        {
            case 0:
                //execl(SOFFICE_PATH, "soffice", "--show", file_name_tab[PPT_FILE_INDEX].c_str(), NULL);
			    break;

            case 1:
                execl(SOFFICE_PATH, "soffice", "--show", file_name_tab[PPTX_FILE_INDEX].c_str(), NULL);
			    break;

            case 2:
                //execl(PDF_PATH, "pdf", file_name_tab[PDF_FILE_INDEX].c_str(), NULL);
			    break;

            case 3:
                execl(MOUSEPAD_PATH, "mousepad", file_name_tab[TXT_FILE_INDEX].c_str(), NULL);
			    break;

            default:
                cout<<"Unknown file format: "<<fType<<endl;
                exit(1);
                break;
        }
    }

}

void next_page(void *param)
{
    uint8_t index;
    uint16_t actionPage = pptCurrentPage;

	uint8_t tmpCmd[6];
	uint8_t strCommand[20];
	uint8_t pindex=0;

    string command;

    for(index=0; index<EXTENSION_TAB_SIZE; index++)
    {
        if(extension_tab[index].compare(fType) == 0){
            break;  
        } 
    }

    switch(index)
    {
        case 0:
        case 1:
        {
            actionPage+=2;

            string str_actionPage = to_string(actionPage);
            for(int strIndex=0; strIndex<str_actionPage.length(); strIndex++)
            {
                command+=str_actionPage[strIndex];
                command+="+";
            }
            command += "KP_Enter+Left";
			
			cout<<"Command: "<<command<<endl;
			pptCurrentPage++;
        }
        break;

        default:
            return;
    }

    if(vfork()==0)
    {
		SHELL(command.c_str());
		printf("TUTAJ: %s",xdo_buff);
		//system(xdo_buff);
		exit(1);
	}
}


int main(void){

	TCPCommunication tcpcomm = TCPCommunication(SERV_ADDR, SERV_PORT);
	Messanger messanger = Messanger(&tcpcomm, &tMutex, &rMutex, &tQueue, &rQueue);
    Invoker invoker;
    string command;

//    invoker.insert_function("testowa",&test);
//    int xxx = 2;
//    cout<<"Wynik wywolania invokera: "<<endl;
//    invoker.invoke("testowa",&xxx);//<<endl;

    invoker.insert_function(RESTART_SERV, &server_restart);
    invoker.insert_function(F_STREAM, &receive_stream);
    invoker.insert_function(F_DONE, &check_file);
    invoker.insert_function(F_START, &start_file);
    invoker.insert_function(F_NEXTP, &next_page);


	if(tcpcomm.startServer() == NO_ERROR)
	{
		cout<<"Server starts correctly!"<<endl;
		//messanger.startMessanger();
		thread mes_thread(Messanger::run,&tcpcomm,&tMutex,&rMutex,&tQueue,&rQueue);
		//thread t(test);
		mes_thread.detach();
	}
	// TODO - sprawdzac czy nie zerwano połączenia, jeżeli tak to catchNewConnection, jak?

    cout<<"Ide do maszyny stanu"<<endl;
    while(1)
    {
        //maszyna stanu :)
        command = getCommand();
            cout<<command<<endl;
        if(command.compare(RESTART_SERV)==0)
        {
            cout<<"RESTART SERV???"<<endl;
            invoker.invoke(RESTART_SERV,&tcpcomm);
        }
        else
        {
            invoker.invoke(command);
            //cout<<command<<endl;

        }


    }


//cout << "Ide do maina"<<endl;
//while(1){
//	cout<<"Odebrano komendex: "<<getCommand()<<endl;
    //sleep(2);
//      if(!rQueue.empty()) cout<<"Kolejka pelna"<<endl;
//      else cout<<"Kolejka pusta:"<<endl;
    //sendCommand("Patryk12345678945612321456");
//}

	return 0;
}



