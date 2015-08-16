#ifndef __COMMANDS_H__
#define __COMMANDS_H__

#define PPT_FILE	"git.ppt"
#define PPTX_FILE	"prezentacja.pttx"
#define PDF_FILE	"dokument.pdf"

typedef enum {
	PPT = 100,
	PDF = 200,
	MOVIE = 300
} file_t;


typedef enum {
	next = 0,
	back,
	exit_prog
} command_t;

static char *ppt_key[] = {
	"Down",
	"Up",
	"Alt+F4"
};




char *get_command(file_t type, command_t command);


#endif
