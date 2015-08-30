package tcp.stream;

import java.util.ArrayList;

public class ControllCommands {
	static String testcommand = "TEST__TEST";
	static String TRANSFER_SUCCESFULL = "success";
	static String START_FILE_STREAM = "stream";
	static String SUCCESS_QUERY = "squery";
	static String TRANSFER_ERROR = "terror";
	
	static byte PPT = 0;
	static byte PPTX = 1;
	static byte PDF = 2;
	static byte TXT = 3;
	static byte MOVIE = 30;
	
	static String[] FILE_TYPES = {
		"ppt",
		"pptx",
		"pdf",
		"txt"
	};
	
	static String[] KEY_TAB = {
			
	};
	
}
