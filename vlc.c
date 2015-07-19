#include <sys/socket.h>
#include <netinet/in.h>
#include <stdio.h>

//vlc -vvv http://192.168.1.6:55555/test.mp3


int main(int argc, char**argv)
{
   
int status = system("vlc -vvv http://192.168.1.6:55555/test.mp3 --fullscreen");

}