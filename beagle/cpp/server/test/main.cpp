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

using namespace std;

void trd(){

	for(int a=0; a<5;a++)
	{
		cout<<"Wątek policzyl do: "<<a<<endl;
		sleep(1);
	}
}


int main(void){

	//thread t(trd);
	TCPCommunication *a = new TCPCommunication(INADDR_ANY,12345);
	/*for(int a = 0; a<3;a++)
	{
		cout<<"Glowny program policzyl do:"<<a<<endl;
		sleep(2);
	}*/

	if(a->startServer() == NO_ERROR) cout<<"Connection has been established"<<endl;
	uint8_t buff[] = "PATRYK";
	a->sendData(buff);
	while(1);





	return 0;
}



