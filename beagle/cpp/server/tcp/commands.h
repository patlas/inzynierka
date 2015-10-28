#ifndef __COMMANDS_H__
#define __COMMANDS_H__

#include <string>

#define SOFFICE_PATH	"/usr/bin/soffice"
#define XDOTOOL_PATH	"/usr/bin/xdotool"
#define MOUSEPAD_PATH   "/usr/bin/mousepad"
#define PDF_PATH        "/usr/bin/qpdfview"

static std::string PROG_PPT_ALIAS="soffice.bin";
static std::string PROG_PDF_ALIAS="qpdfview";     
//przed PID dac # ewentualnie
					
#define SHELL(key,prog) sprintf(xdo_buff,"PID=`pidof %s`\n windowID=`xdotool search --pid $PID | tail -1`\n xdotool windowfocus --sync $windowID key %s \n",prog,key)

#define SHELL2(key) sprintf(xdo_buff,"PID=`pidof qpdfview`\n windowID=`xdotool search --pid $PID | tail -1`\n xdotool windowfocus --sync $windowID key %s \n",key)


//COMMUNICATION COMMANDS
#define RESTART_SERV    "RESTART_S"
#define F_STREAM        "F_STREAM"
#define GET_SIZE        "GET_SIZE"
#define GET_HASH        "GET_HASH"
#define GET_TYPE        "GET_TYPE"

#define U_ERROR         "U_ERROR"

#define F_RECEIVED      "F_RECEIVED"
#define F_DONE          "F_DONE"
#define F_ERROR         "F_ERROR"
#define F_START         "F_START"

#define F_PNEXTP        "F_PNEXTP"
#define F_PPREVP        "F_PPREVP"
#define F_PNEXTE        "F_PNEXTE"
#define F_PPREVE        "F_PPREVE"
#define F_PFIRST        "F_PFIRST"
#define F_PEXIT         "F_PEXIT"

#define F_DPUP          "F_DPUP"
#define F_DPDOWN        "F_DPDOWN"
#define F_DFULL         "F_DFULL"
#define F_DROTATE       "F_DROTATE"
#define F_DNEXT         "F_DNEXT"
#define F_DPREV         "F_DPREV"
#define F_DEXIT         "F_DEXIT"






//RAW FILE TEMP NAME
#define TEMP_NAME       "tempfile.raw"

#define PPT_FILE_INDEX  0
#define PPTX_FILE_INDEX 1
#define PDF_FILE_INDEX  2
#define TXT_FILE_INDEX  3


#define EXTENSION_TAB_SIZE  4
static std::string file_name_tab[EXTENSION_TAB_SIZE] = {
    "plik.ppt",
    "plik.pptx",
    "plik.pdf",
    "plik.txt"
};

static std::string extension_tab[EXTENSION_TAB_SIZE] = {
    "ppt",
    "pptx",
    "pdf",
    "txt"
};

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
