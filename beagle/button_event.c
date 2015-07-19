#include <stdio.h>
#include <stdlib.h>
#include <string.h>


#define arrowUp "ctrl+Tab"
#define arrowDown
#define fullScreen


#define SHELL(PID,key) "windowID=`xdotool search --pid " #PID " | tail -1`\n \
						xdotool windowfocus --sync $windowID key " key " \n"

void execute_button(char *button)
{

//		windowID = xdotool search --pid PID | tail -1
//		xdotool windowfocus --sync $windowID key ctrl+Tab

}


int main(void){

	system(SHELL(1420,arrowUp));
	//printf(SHELL(1340,ctrl+Tab));
	printf("DONE");

}