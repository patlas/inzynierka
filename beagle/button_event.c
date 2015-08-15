#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define arrowUp "Ctrl+n"
#define arrowDown
#define fullScreen


#define SHELL(PID,key) "windowID=`xdotool search --pid " #PID " | tail -1`\n \
						xdotool windowfocus --sync $windowID key " key " \n"


#define SHELL2(PID,key) "windowID=`xdotool search --pid " #PID " | tail -1`\n \
						xdotool windowfocus --sync $windowID key " key " \n"


void execute_button(char *button)
{

//		windowID = xdotool search --pid PID | tail -1
//		xdotool windowfocus --sync $windowID key ctrl+Tab

}


int main(char *argc[], int argv){
int a = 859;
char key[10] = {"Ctrl+n"};

	char buf[500];
sprintf(buf,"PID=`pidof gedit`\n windowID=`xdotool search --pid $PID | tail -1`\n xdotool windowfocus --sync $windowID key %s \n",key);

	system(buf);
	//printf(SHELL(1340,ctrl+Tab));
	printf("DONE");

}
