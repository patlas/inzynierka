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
#include <mutex>
#include <queue>
#include <string>
#include <stdint.h>
#include <fstream>


#define SERV_ADDR	INADDR_ANY
#define SERV_PORT	12345

using namespace std;


mutex tMutex, rMutex;
queue<QueueStruct_t> tQueue;
queue<string> rQueue;


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
	command_struct.command = cmd; //czy takie kopiowanie dziala?
	
    tMutex.lock();
	tQueue.push(command_struct);
	tMutex.unlock();

}



int main(void){

	TCPCommunication tcpcomm = TCPCommunication(SERV_ADDR, SERV_PORT);
	Messanger messanger = Messanger(&tcpcomm, &tMutex, &rMutex, &tQueue, &rQueue);

	if(tcpcomm.startServer() == NO_ERROR)
	{
		cout<<"Server starts correctly!"<<endl;
		//messanger.startMessanger();
		thread mes_thread(Messanger::run,&tcpcomm,&tMutex,&rMutex,&tQueue,&rQueue);
		//thread t(test);
		mes_thread.detach();
	}
	// TODO - sprawdzac czy nie zerwano połączenia, jeżeli tak to catchNewConnection, jak?
cout << "Ide do maina"<<endl;
while(1){
	cout<<"Odebrano komendex: "<<getCommand()<<endl;
    //sleep(2);
//      if(!rQueue.empty()) cout<<"Kolejka pelna"<<endl;
//      else cout<<"Kolejka pusta:"<<endl;
    //sendCommand("Patryk12345678945612321456");
}

	return 0;
}



