#ifndef __COMMANDS_H__
#define __COMMANDS_H__

#define RESTART_SERV    "RESTART_S"
#define F_STREAM        "F_STREAM"
#define GET_SIZE        "GET_SIZE"
#define GET_HASH        "GET_HASH"
#define GET_TYPE        "GET_TYPE"
#define F_RECEIVED      "F_RECEIVED"
#define F_DONE          "F_DONE"
#define F_ERROR         "F_ERROR"

/*
typedef enum {
	PPT = 0,
	PPTX = 1,
	PDF = 2,
	TXT = 3,
	MOVIE = 30
} file_t;

static char *file_name[] = {
	//"/home/bananapi/Documents/ramdisk/"plik.ppt",
	"plik.pptx",
	"plik.pdf",
	"plik.txt"
};




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


*/

#endif
