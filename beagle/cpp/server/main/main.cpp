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
string exec_command;


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





void execute_command(string cmd, string prog)
{
    if(vfork()==0)
    {
		SHELL(cmd.c_str(),prog.c_str()); // wyrzucić przed forka
		printf("TUTAJ: %s",xdo_buff);
		system(xdo_buff);
		//exit(1);
	}

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
    cout<<endl<<endl<<"receive_stream"<<endl;
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

    cout<<endl<<endl<<"check_file"<<endl;
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
    cout<<endl<<endl<<"start_file"<<endl;
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
                execl(SOFFICE_PATH, "soffice", "--show", file_name_tab[PPT_FILE_INDEX].c_str(), NULL);
			 //exit(1);   
            break;

            case 1:
                execl(SOFFICE_PATH, "soffice", "--show", file_name_tab[PPTX_FILE_INDEX].c_str(), NULL);
			    break;

            case 2:
                execl(PDF_PATH, "qpdfview", file_name_tab[PDF_FILE_INDEX].c_str(), NULL);
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

void pnext_page(void *param)
{
    cout<<endl<<endl<<"pnext_page"<<endl;
    uint16_t actionPage = pptCurrentPage;

	uint8_t tmpCmd[6];
	uint8_t strCommand[20];
	uint8_t pindex=0;


    actionPage+=2;
    exec_command.clear();

    string str_actionPage = to_string(actionPage);
    for(int strIndex=0; strIndex<str_actionPage.length(); strIndex++)
    {
        exec_command+=str_actionPage[strIndex];
        exec_command+="+";
    }
    exec_command += "KP_Enter+Left";
	
	cout<<"Command: "<<exec_command<<endl;
	pptCurrentPage++;

    execute_command(exec_command,PROG_PPT_ALIAS);
   
}


void pprev_page(void *param)
{
    cout<<endl<<endl<<"pprev_page"<<endl;
    uint16_t actionPage = pptCurrentPage;

	uint8_t tmpCmd[6];
	uint8_t strCommand[20];
	uint8_t pindex=0;

    exec_command.clear();
    string str_actionPage = to_string(actionPage);
    for(int strIndex=0; strIndex<str_actionPage.length(); strIndex++)
    {
        exec_command+=str_actionPage[strIndex];
        exec_command+="+";
    }
    exec_command += "KP_Enter+Left";
	
	cout<<"Command: "<<exec_command<<endl;
	pptCurrentPage--;

    execute_command(exec_command,PROG_PPT_ALIAS);
   
}

void pnext_effect(void *param)
{
    cout<<endl<<endl<<"pnext_effect"<<endl;

    exec_command = "Right";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PPT_ALIAS);
   
}

void pprev_effect(void *param)
{
    cout<<endl<<endl<<"pprev_effect"<<endl;

    exec_command = "Left";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PPT_ALIAS);
   
}

void pfirst_page(void *param)
{
    cout<<endl<<endl<<"pfirst_page"<<endl;

    exec_command = "Home";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PPT_ALIAS);
}


void dnext_page(void *param)
{
    cout<<endl<<endl<<"dnext_page"<<endl;

    exec_command = "Right";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);
}

void dprev_page(void *param)
{
    cout<<endl<<endl<<"dprev_page"<<endl;

    exec_command = "Left";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);
}

void dfull_page(void *param)
{
    cout<<endl<<endl<<"dfull_page"<<endl;

    exec_command = "Ctrl+9";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);
}

void drotate_page(void *param)
{
    cout<<endl<<endl<<"drotate_page"<<endl;

    exec_command = "Ctrl+Left";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);
}


void dup_page(void *param)
{
    cout<<endl<<endl<<"dup_page"<<endl;

    exec_command = "Page_Up";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);
}

void ddown_page(void *param)
{
    cout<<endl<<endl<<"ddown_page"<<endl;

    exec_command = "Page_Down";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);
}

void dexit(void *param)
{
    cout<<endl<<endl<<"dexit"<<endl;

    exec_command = "Alt+F4";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PDF_ALIAS);

    //server_restart(nullptr);
}

void pexit(void *param)
{
    cout<<endl<<endl<<"pexit"<<endl;

    exec_command = "Escape";
	
	cout<<"Command: "<<exec_command<<endl;

    execute_command(exec_command,PROG_PPT_ALIAS);

    //server_restart(nullptr);
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

    invoker.insert_function(RESTART_SERV,   &server_restart);
    invoker.insert_function(F_STREAM,       &receive_stream);
    invoker.insert_function(F_DONE,         &check_file);
    invoker.insert_function(F_START,        &start_file);

    invoker.insert_function(F_PNEXTP,       &pnext_page);
    invoker.insert_function(F_PPREVP,       &pprev_page);
    invoker.insert_function(F_PNEXTE,       &pnext_effect);
    invoker.insert_function(F_PPREVE,       &pprev_effect);
    invoker.insert_function(F_PFIRST,       &pfirst_page);
    invoker.insert_function(F_PEXIT,        &pexit);
   
    invoker.insert_function(F_DPUP,         &dup_page);
    invoker.insert_function(F_DPDOWN,       &ddown_page);
    invoker.insert_function(F_DNEXT,        &dnext_page);
    invoker.insert_function(F_DPREV,        &dprev_page);
    invoker.insert_function(F_DROTATE,      &drotate_page);
    invoker.insert_function(F_DFULL,        &dfull_page);
    invoker.insert_function(F_DEXIT,        &dexit);

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



