/*
 * Messanger.cpp
 *
 *  Created on: 30 wrz 2015
 *      Author: patlas
 */

#include "Messanger.h"
#include "commands.h"

Messanger::Messanger(TCPCommunication *tcp, mutex *tm, mutex *rm, queue<QueueStruct_t>  *tq, queue<string> *rq)
{
	tcpcomm1 = tcp;
	tMutex1 = tm;
	rMutex1 = rm;
	tQueue1 = tq;
	rQueue1 = rq;

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
	memcpy((uint8_t*)tlv,rawData,TLV_STRUCT_SIZE);
	
	uint64_t tempLen = 0;
	
	/*
	tempLen |= rawData[1];
	tempLen<<=8;
	tempLen |= rawData[2];
	tempLen<<=8;
	tempLen |= rawData[3];
	tempLen<<=8;	
	tempLen |= rawData[4];
	tempLen<<=8;
	tempLen |= rawData[5];
	tempLen<<=8;
	tempLen |= rawData[6];
	tempLen<<=8;
	tempLen |= rawData[7];
	tempLen<<=8;	
	tempLen |= rawData[8];


	tlv->length = tempLen;*/

}

//cmd string could not be longer than TLV_DATA_SIZE!
void Messanger::buildTLVheader(TLVStruct *tlv, string cmd, uint64_t full_cmd_length)
{
	// TODO - this implementation supports only command header
	tlv->type = 0;
	tlv->length = full_cmd_length;//cmd.length();
	uint8_t dataStr[TLV_DATA_SIZE];
	memcpy(dataStr,cmd.data(), cmd.length());
	memcpy(tlv->value,dataStr,cmd.length());

}

void Messanger::run(TCPCommunication *tcpcomm, mutex *tMutex, mutex *rMutex, queue<QueueStruct_t>  *tQueue, queue<string> *rQueue)
{
	QueueStruct_t QSt;
	uint8_t rData[TLV_STRUCT_SIZE];
	uint64_t commandSize = 0;
	uint64_t compSize = 0;
	uint64_t fsize = 0;
	string command ;
	ofstream outfile;
    TLVStruct tempTLV;
	//cout<<"WAtek ruszyl"<<endl;
    uint8_t count = 0;

	while(1)
	{
		if(tMutex->try_lock())
		{
			if(!tQueue->empty())
			{
                cout<<"Chce wysylac dane"<<endl;
				QSt = tQueue->front();
				tQueue->pop();
				tMutex->unlock();
				//parse command
				if(QSt.stream == false)
				{
					string cmd = QSt.command;
                    cout<<"Wysylam takie dane: "<<cmd<<cmd.length()<<endl;
					uint8_t index =  (uint8_t) ceil(cmd.length() / (float)TLV_DATA_SIZE);
                    //uint8_t index =  (uint8_t) divCeil(cmd.length(),TLV_DATA_SIZE);
                    cout<<"Dane to tyle pakietow: "<<(int)index<<endl;
				   for(uint8_t i=0; i<index;i++)
				   {
					   uint8_t end = (i+1)*TLV_DATA_SIZE;
					   if(end>cmd.length())
					   {
						   end = cmd.length();
					   }

					   string substr =  cmd.substr(i*TLV_DATA_SIZE, end);

					   TLVStruct tempTLV;
					   buildTLVheader(&tempTLV,substr,cmd.length());
					   uint8_t dataToSend[TLV_STRUCT_SIZE];
					   TLVtoArray(&tempTLV,dataToSend);
					   cout<<"Wyslalem tyle: "<<tcpcomm->sendData(dataToSend) <<endl; //sprawdzac czy sie wyslalo - przez returny
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
            //cout<<"T: Biore kolejke"<<endl;
			if( (count = tcpcomm->receiveData(rData)) > 0)
			{
				

				//TLVStruct tempTLV;
				ArrayToTLV(&tempTLV,rData);
                printf("Taki typ: %d\n",tempTLV.type);
				if(tempTLV.type == 0)
				{
					//cout<<"Odebralem komende"<<endl;
					//TODO - if tempTLV.type == stream than ommit below and start saving to file
					if(commandSize == 0)
					{
						compSize = tempTLV.length;
						//printf("Rozmiar komendy to: %x\n",tempTLV.length);
					}

					command.append((char*)tempTLV.value);
					commandSize+=TLV_DATA_SIZE;//sizeof(tempTLV.value);

					if(commandSize >= compSize)
					{
						rQueue->push(command.substr(0, (int)compSize));
                        cout<<"T: Wstawiam do kolejki"<<endl;                    
						//cout<<"To taka komenda:"<<command.substr(0, (int)compSize)<<endl;

						command.clear();
						commandSize = 0;
					}
				}
				else if(tempTLV.type == 1)
				{
					cout<<"Odebralem stream"<<endl;
                    cout<<"Dlugosc streama: "<<tempTLV.length<<endl;
                    //for(int c =0; c<8;c++)
                    //    printf("%d\n",(uint8_t)(tempTLV.length>>(c*8)));
                    for(int c=0; c<20;c++)
                        printf("%d\n",(uint8_t)rData[c]);

					//TODO - save stream to file
					if(fsize == 0)
					{
						compSize = tempTLV.length;
                        cout<<"Rozmiar pliku to: "<<tempTLV.length<<endl;

						if(outfile.is_open()) outfile.close();
						outfile.open("temp.raw", ofstream::binary | ofstream::out);

					}
					if(fsize < compSize)
					{
						fsize+=TLV_DATA_SIZE;
                        printf("fsize: %d\n",fsize);;

						outfile.write((char*)tempTLV.value,TLV_DATA_SIZE);
                        //cout<<tempTLV.value[0]<<"rozmiar:"<<fsize<<endl;
					}
					if(fsize >= compSize)
					{
						//zrobic flush i zapisać do pliku
						outfile.flush();
						outfile.close();
						int source = open("temp.raw", O_RDONLY, 0);
						int dest = open(TEMP_NAME, O_WRONLY | O_CREAT /*| O_TRUNC/**/, 0644);
                        
                        printf("compSize before save: %d\n",compSize);
						sendfile(dest, source, 0, compSize);

						close(source);
						close(dest);
                        fsize = 0;
                        cout<<"Odebralem plik"<<endl;

					}
				}
			}
            else if(count<0)
            {
                //connection lost -> send to queue error message, close conection and goto listen state
                rQueue->push(RESTART_SERV);
            }
			rMutex->unlock();
            //cout<<"T: Zwalniam kolejke"<<endl;
		}


	}

}

uint8_t Messanger::divCeil(uint8_t num, uint8_t denom)
{
    std::div_t res = std::div(num,denom);
    return res.rem ? (res.quot + 1) : res.quot;
}


void Messanger::startMessanger()
{
	//(TCPCommunication *tcpcomm, mutex *tMutex, mutex *rMutex, queue<QueueStruct_t>  *tQueue, queue<string> *rQueue)


// TODO - start messanger thread -> return bool?
	thread mes_thread(run,tcpcomm1,tMutex1,rMutex1,tQueue1,rQueue1);
}
