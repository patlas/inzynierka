#include <stdio.h>
#include <stdint.h>

#include "commands.h"

char *get_command(file_t type, command_t command){
	char str_command[20];
	
	switch(type){
		case PPT:
			return ppt_key[command];
		break;
		
		case PDF:
		break;
		
		case MOVIE:
		break;
	}
}
