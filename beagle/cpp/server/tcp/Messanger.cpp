/*
 * Messanger.cpp
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */

#include "Messanger.h"

Messanger::Messanger(TCPCommunication &tcp, mutex *tm, mutex *rm, queue<QueueStruct_t>  &tq, queue<string> &rq)
{
	tcpcomm = tcp;
	tMutex = tm;
	rMutex = rm;
	tQueue = tq;
	rQueue = rq;

}

Messanger::Messanger(){

}

Messanger::~Messanger() {
	// TODO Auto-generated destructor stub
}



void Messanger::TLVtoArray(TLVStruct *tlv, uint8_t *rawData)
{
	//ustawia w kolejności najstarszy na najstarszym czyli
	//liczba 0x11170 (70000) byte[0]=0x01; byte[1]=0x11; byte[2]=0x70
	memcpy(rawData, tlv, TLV_STRUCT_SIZE);

}

void Messanger::ArrayToTLV(TLVStruct *tlv, uint8_t *rawData)
{
	memcpy(tlv,rawData,TLV_STRUCT_SIZE);
}

//cmd string could not be longer than TLV_DATA_SIZE!
void Messanger::buildTLVheader(TLVStruct *tlv, string cmd)
{
	// TODO - this implementation supports only command header
	tlv->type = 0;
	tlv->length = cmd.length();
	uint8_t dataStr[TLV_DATA_SIZE];
	memcpy(dataStr,cmd.data(), cmd.length());
	memcpy(tlv->value,dataStr,cmd.length());

}

void Messanger::run()
{
	QueueStruct_t QSt;
	uint8_t rData[TLV_STRUCT_SIZE];
	uint64_t commandSize = 0;
	uint64_t compSize = 0;
	uint64_t fsize = 0;
	string command ;
	ofstream outfile;

	while(1)
	{
		if(tMutex->try_lock())
		{
			if(!tQueue.empty())
			{
				QSt = tQueue.front();
				tQueue.pop();
				tMutex->unlock();
				//parse command
				if(QSt.stream == false)
				{
					string cmd = QSt.command;
					uint8_t index =  (uint8_t) ceil(cmd.length() / TLV_DATA_SIZE);

				   for(uint8_t i=0; i<index;i++)
				   {
					   uint8_t end = (i+1)*TLV_DATA_SIZE;
					   if(end>cmd.length())
					   {
						   end = cmd.length();
					   }

					   string substr =  cmd.substr(i*TLV_DATA_SIZE, end);

					   TLVStruct tempTLV;
					   buildTLVheader(&tempTLV,substr);
					   uint8_t dataToSend[TLV_STRUCT_SIZE];
					   TLVtoArray(&tempTLV,dataToSend);
					   tcpcomm.sendData(dataToSend);
				   }
				}
				else
				{
					// TODO - if server will be steamer than implement that functionality
				}

				continue;
			}
			tMutex->unlock();
		}

		if(rMutex->try_lock())
		{
			if(tcpcomm.receiveData(rData) > 0)
			{

				TLVStruct tempTLV;
				ArrayToTLV(&tempTLV,rData);
				if(tempTLV.type == 0)
				{
					//TODO - if tempTLV.type == stream than ommit below and start saving to file
					if(commandSize == 0)
					{
						compSize = tempTLV.length;
					}

					command.append((char*)tempTLV.value);
					commandSize+=sizeof(tempTLV.value);

					if(commandSize >= compSize)
					{
						rQueue.push(command.substr(0, (int)compSize));

						command.clear();
						commandSize = 0;
					}
				}
				else
				{
					//TODO - save stream to file
					if(fsize == 0)
					{
						compSize = tempTLV.length;

						if(outfile.is_open()) outfile.close();
						outfile.open("temp.raw", ofstream::binary | ofstream::out);

					}
					else if(fsize < compSize)
					{
						fsize+=TLV_DATA_SIZE;

						outfile.write((char*)tempTLV.value,TLV_DATA_SIZE);
					}
					else if(fsize >= compSize)
					{
						//zrobic flush i zapisać do pliku
						outfile.flush();
						outfile.close();
						int source = open("temp.raw", O_RDONLY, 0);
						int dest = open(tempName.c_str(), O_WRONLY | O_CREAT /*| O_TRUNC/**/, 0644);

						sendfile(dest, source, 0, compSize);

						close(source);
						close(dest);

					}
				}
			}

			rMutex->unlock();
		}


	}

}


void Messanger::startMessanger()
{

// TODO - start messanger thread -> return bool?
	thread mes_thread(run);
}
