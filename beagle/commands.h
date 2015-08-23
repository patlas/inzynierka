#ifndef __COMMANDS_H__
#define __COMMANDS_H__

#define PPT_FILE	"git.ppt"
#define PPTX_FILE	"prezentacja.pttx"
#define PDF_FILE	"dokument.pdf"


//static String testcommand = "TEST__TEST";
#define TRANSFER_SUCCESFULL  	"success\n"
#define TRANSFER_ERROR		"terror\n"
#define START_FILE_STREAM  	"stream"
#define SUCCESS_QUERY  		"squery"



typedef enum {
	PPT = 0,
	PPTX = 1,
	PDF = 2,
	TXT = 3,
	MOVIE = 30
} file_t;

static char *file_name[] = {
	"plik.ppt",
	"plik.pptx",
	"plik.pdf",
	"plik.txt"
};


typedef enum {
	next = 0,
	back,
	zoomin,
	zommout,
	exit_prog
} command_t;

static char *ppt_key[] = {
	"Down",
	"Up",
	"Alt+F4"
};


static char *txt_key[] = {
	"Down"
};

static char *serv_cmd[] = {
	"stream",
	"button",
	"disconnect"
};

typedef enum {
	stream = 0,
	button,
	disconnect

} serv_cmd_t;

typedef enum {
	STREAM_ER = -1,
	HASH_ER = -2

} error_t;


char *get_command(file_t type, command_t command);
char *get_serv_cmd(serv_cmd_t command);


#endif
