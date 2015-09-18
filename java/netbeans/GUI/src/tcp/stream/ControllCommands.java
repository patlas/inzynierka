package tcp.stream;

import java.util.ArrayList;

public class ControllCommands {
	public static String testcommand = "TEST__TEST";
	public static String TRANSFER_SUCCESFULL = "success";
	public static String START_FILE_STREAM = "stream";
	public static String SUCCESS_QUERY = "squery";
	public static String TRANSFER_ERROR = "terror";
	
	public static byte PPT = 0;
	public static byte PPTX = 1;
	public static byte PDF = 2;
	public static byte TXT = 3;
	public static byte MOVIE = 30;
        
        
        public static byte ppt_nextp = 1;
	public static byte ppt_prevp = 2;
	public static byte ppt_next = 3;
	public static byte ppt_prev = 4;
	public static byte ppt_exit = 5;
        
        
	
	public static String[] FILE_TYPES = {
		"ppt",
		"pptx",
		"pdf",
		"txt"
	};
	
	public static String[] KEY_TAB = {
			
	};
	
}
