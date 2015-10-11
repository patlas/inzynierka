#ifndef INVOKER_H_
#define INVOKER_H_

#include <string>
#include <stdint.h>
#include <sys/sendfile.h>
#include <string.h>
#include <iostream>
#include <map>


using namespace std;

typedef void (*funct)(void*);
typedef map<string,funct> imap;

class Invoker {

private:
    imap inv_map;

public:
    
    bool insert_function(string, funct);
    bool invoke(string, void*);
    bool invoke(string);

	virtual ~Invoker();
	Invoker();

};

#endif 
