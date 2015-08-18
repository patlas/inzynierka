package tcp.stream;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import exceptions.ErrorException;

public class FileStreamer {
	
	int portNumber;
	String hostName = null;
	Socket streamSocket = null;
	PrintWriter outStream = null;
	TCPCommunication tcpcomm = null;
	
	//deprecated
	public void setPortNumber(int pn){
		portNumber = pn;
		
	}
	//deprecated
	public void setHostName(String hn){
		hostName = hn;
	}
	
	//deprecated
	public FileStreamer(String hn, int pn) throws UnknownHostException, IOException{
		hostName = hn;
		portNumber = pn;
		streamSocket = new Socket(hostName, portNumber);
		outStream = new PrintWriter(streamSocket.getOutputStream(), true);
	}
	
	public FileStreamer() throws ErrorException, UnknownHostException, IOException {
		
		if(hostName.isEmpty() || portNumber==0){
			System.out.println("Socket could not be created");
			throw new ErrorException("Wrong host name of port number");
		}
		else{
			streamSocket = new Socket(hostName, portNumber);
			outStream = new PrintWriter(streamSocket.getOutputStream(), true);
		}
		
	}
	
	public FileStreamer(TCPCommunication tcp){
		hostName = tcp.hostName;
		portNumber = tcp.portNumber;
		streamSocket = tcp.streamSocket;
		outStream = tcp.outStream;
		tcpcomm = tcp;
		
	}

	
	public boolean streamFile(File fd){

		//outStream.println("TEST");
		/* 
		 * 1) streamować plik
		 * 2) wysłać komende o końcu streamu -> jego crc?
		 * 3) oczekiwać komendy potwierdzającej poprawne odebranie pliku -> zrobione
		 */
		System.out.println(isTransferSuccesfull());
		return true;
	}
	
	private boolean isTransferSuccesfull(){
		while(!tcpcomm.sendCommand(ControllCommands.testcommand));
		System.out.println("Command send");
		
		try {
			if(tcpcomm.receiveCommand().equals(ControllCommands.TRANSFER_SUCCESFULL))
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
