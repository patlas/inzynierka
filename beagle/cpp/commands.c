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
		
		case TXT:
			return txt_key[command];
		break;
		
		case MOVIE:
		break;
	}
}


char *get_serv_cmd(serv_cmd_t command){
	
	return serv_cmd[command];
}
