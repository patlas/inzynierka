/*
 * Messanger.h
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */

#ifndef TCP_MESSANGER_H_
#define TCP_MESSANGER_H_

#include <thread>
#include <mutex>
#include <queue>
#include <string>
#include <stdint.h>
#include <sys/sendfile.h>
#include <math.h>
#include <string.h>
#include <fstream>
#include <iostream>

#include "TCPCommunication.h"

#define TEMP_NAME "tempfile.raw"

using namespace std;

#pragma pack(push)
#pragma pack(1)
typedef struct {
	bool stream;
    string command;
    uint8_t data[20];
    uint64_t fileSize;
}QueueStruct_t;
#pragma pack(pop)


class Messanger {

private:
	TCPCommunication *tcpcomm1;
	queue<QueueStruct_t> *tQueue1;
	queue<string> *rQueue1;
	mutex *tMutex1, *rMutex1;

    static uint8_t divCeil(uint8_t, uint8_t);

public:
	//static string tempName = "tempfile.raw";

public:
	static void TLVtoArray(TLVStruct *, uint8_t *);
	static void ArrayToTLV(TLVStruct *, uint8_t *);
	static void buildTLVheader(TLVStruct *, string, uint64_t); //only support command mode
	void startMessanger();

public:
	Messanger(TCPCommunication *, mutex *, mutex *, queue<QueueStruct_t> *, queue<string> *); // jako argumenty przyjmować 2x mutex i 2x kolejka
	virtual ~Messanger();
	Messanger();

	static void run(TCPCommunication*, mutex*, mutex*, queue<QueueStruct_t>*, queue<string>*);
};

#endif /* TCP_MESSANGER_H_ */
