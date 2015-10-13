package tcp.stream;

import java.util.ArrayList;

public class ControllCommands {
	public static String testcommand = "TEST__TEST";
	public static String TRANSFER_SUCCESFULL = "success";
	public static String START_FILE_STREAM = "stream";
	public static String SUCCESS_QUERY = "squery";
	public static String TRANSFER_ERROR = "terror";
        
        
        
        public static String F_STREAM = "F_STREAM";
	public static String GET_SIZE = "GET_SIZE";
	public static String GET_HASH = "GET_HASH";
	public static String GET_TYPE = "GET_TYPE";
	public static String F_DONE = "F_DONE";
        public static String F_RECEIVED = "F_RECEIVED";
	public static String F_ERROR = "F_ERROR";
        public static String RESTART_S = "RESTART_S";
        public static String F_START = "F_START";
        public static String F_PNEXTP = "F_PNEXTP";
	public static String F_PPREVP = "F_PPREVP";
        public static String F_PNEXTE = "F_PNEXTE";
	public static String F_PPREVE = "F_PPREVE";
        public static String F_PFIRST = "F_PFIRST";
        
        public static String F_DPUP = "F_DPUP";
        public static String F_DPDOWN = "F_DPDOWN";
        public static String F_DFULL = "F_DFULL";
        public static String F_DROTATE = "F_DROTATE";
        public static String F_DNEXT = "F_DNEXT";
        public static String F_DPREV = "F_DPREV";
        
        
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
        
        
        public static byte pdf_nextp = 1;
	public static byte pdf_prevp = 2;
	public static byte pdf_next = 3;
	public static byte pdf_prev = 4;
        public static byte pdf_rotate = 5;
	public static byte pdf_exit = 6;
        
	
	public static String[] FILE_TYPES = {
		"ppt",
		"pptx",
		"pdf",
		"txt"
	};
	
	public static String[] KEY_TAB = {
			
	};
	
}
