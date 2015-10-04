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


string getCommand()
{
//	while(1)
//	{
//		if(rMutex.try_lock()){
//			string b = "main";
//			rq.push(b);
//			rMutex.unlock();
//			cout<<"Main wstawił do kolejki "<<index<<endl;
//		}
	string cmd;
	cmd.clear();
	while(!cmd.empty())
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




mutex tMutex, rMutex;
queue<QueueStruct_t> tQueue;
queue<string> rQueue;

int main(void){

	TCPCommunication tcpcomm = TCPCommunication(SERV_ADDR, SERV_PORT);
	Messanger messanger = Messanger(tcpcomm, &tMutex, &rMutex, tQueue, rQueue);

	if(tcpcomm.startServer() == NO_ERROR)
	{
		cout<<"Server starts correctly!"<<endl;
		messanger.startMessanger();
	}
	// TODO - sprawdzac czy nie zerwano połączenia, jeżeli tak to catchNewConnection, jak?

	cout<<"Odebrano komende: "<<getCommand()<<endl;


	return 0;
}



