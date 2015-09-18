package tcp.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPCommunication {
	
	public int portNumber;
	public String hostName = null;
	public Socket streamSocket = null;
	public PrintWriter outStream = null;
	public BufferedReader inStream = null;

	public TCPCommunication(String hn, int pn) throws UnknownHostException, IOException{
		hostName = hn;
		portNumber = pn;
		streamSocket = new Socket(hostName, portNumber);
		outStream = new PrintWriter(streamSocket.getOutputStream(), true);
		inStream = new BufferedReader(new InputStreamReader(streamSocket.getInputStream()));
	}
	
	public boolean sendCommand(String command){
		outStream.write(command);
		return !outStream.checkError();
	}
	
	public boolean sendCommand(Integer value){
		outStream.write(value);
		return !outStream.checkError();
	}
	
	
	public String receiveCommand() throws IOException{
		while(!inStream.ready());
		return inStream.readLine();
		//return inStream.
		
	}
	
}
