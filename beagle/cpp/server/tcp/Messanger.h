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
#include <fstream>

#include "TCPCommunication.h"

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
	TCPCommunication tcpcomm;
	queue<QueueStruct_t> tQueue;
	queue<string> rQueue;
	mutex *tMutex, *rMutex;

public:
	string tempName = "tempfile.raw";

public:
	void TLVtoArray(TLVStruct *, uint8_t *);
	void ArrayToTLV(TLVStruct *, uint8_t *);
	void buildTLVheader(TLVStruct *, string); //only support command mode

public:
	Messanger(TCPCommunication &, mutex *, mutex *, queue<QueueStruct_t> &, queue<string> &); // jako argumenty przyjmować 2x mutex i 2x kolejka
	virtual ~Messanger();
	Messanger();

	void run();
};

#endif /* TCP_MESSANGER_H_ */
