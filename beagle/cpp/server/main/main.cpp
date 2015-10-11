/*
 * main.cpp
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */
#include <iostream>
#include <thread>
#include <unistd.h>
#include "../tcp/TCPCommunication.h"
#include "../tcp/Messanger.h"
#include "../main/Invoker.h"
#include "../main/md5hash.h"
#include <mutex>
#include <queue>
#include <string>
#include <stdint.h>
#include <fstream>

#include <map>


#define SERV_ADDR	INADDR_ANY
#define SERV_PORT	12345

using namespace std;


mutex tMutex, rMutex;
queue<QueueStruct_t> tQueue;
queue<string> rQueue;
uint64_t fSize = 0;
string fHash;
string fType;


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
        cout<<fHash<<endl;
        cout<<md5_hash<<endl;
        sendCommand(F_ERROR);
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



