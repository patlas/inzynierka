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

using namespace std;

mutex tMutex, rMutex;
queue<QueueStruct_t> tQueue, rQueue;
queue<string> tq,rq;

TLVStruct tlv, tlv2;


void trd(){
int index = 0;
	while(1){
		if(tMutex.try_lock()){
			string b = "trd";
			tq.push(b);
			tMutex.unlock();
			cout<<"Wątek wstawił do kolejki "<<index<<endl;
		}

		if(rMutex.try_lock()){
			if(!rq.empty()){
				cout<<index<<": Wątek odczytał z kolejki: "<<rq.front()<<endl;
				rq.pop();
			}
			rMutex.unlock();
		}

		index++;
		sleep(1);
	}
}


int main(void){

	tlv.type = 1;
	tlv.length = 70000;
	tlv.value[0] = 'p';
	tlv.value[10] = 's';


	Messanger *mes = new Messanger();
	uint8_t rav[20];

	mes->TLVtoArray(&tlv,rav);
	mes->ArrayToTLV(&tlv2, rav);

	for(int s = 0; s<20; s++)
		printf("%d\n",rav[s]);

	printf("%d, %d, %c\n",tlv2.type,tlv2.length,tlv2.value[0]);

	string a = "patryk";

	mes->buildTLVheader(&tlv, a);
	printf("%d, %d, %s\n",tlv.type,tlv.length,tlv.value);



//
//	thread t(trd);
//	int index=0;
//	//TCPCommunication *a = new TCPCommunication(INADDR_ANY,12345);
//	while(1)
//	{
//		if(rMutex.try_lock()){
//			string b = "main";
//			rq.push(b);
//			rMutex.unlock();
//			cout<<"Main wstawił do kolejki "<<index<<endl;
//		}
//
//		if(tMutex.try_lock()){
//			if(!tq.empty()){
//				cout<<index<<": Main odczytał z kolejki: "<<tq.front()<<endl;
//				tq.pop();
//			}
//			tMutex.unlock();
//		}
//		index++;
//		sleep(2);
//	}
//	tMutex.lock();
//	if(a->startServer() == NO_ERROR) cout<<"Connection has been established"<<endl;
//	uint8_t buff[] = "PATRYK";
//	a->sendData(buff);
//	while(1);






	return 0;
}



