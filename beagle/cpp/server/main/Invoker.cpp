#include "Invoker.h"

Invoker::Invoker(){
};

Invoker::~Invoker(){
};




bool Invoker::insert_function( string str, funct func)
{
    inv_map.insert(imap::value_type(str,func));
    //inv_map[str] = func;
    return true;
}

bool Invoker::invoke(string str, void *p)
{
    imap::const_iterator iter = inv_map.find(str);
    
    if(iter == inv_map.end()){
        return false;
    }
    //printf("%d",(int*)p);
    (*iter->second)(p);
    
    return true;
}

bool Invoker::invoke(string str)
{
    imap::const_iterator iter = inv_map.find(str);
    
    if(iter == inv_map.end()){
        return false;
    }
    //printf("%d",(int*)p);
    (*iter->second)(nullptr);
    
    return true;
}
