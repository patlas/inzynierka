package tcp.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPCommunication {
	
    public static int TIMEOUT = 10;
    public static int ISTREAM_SIZE = 20;
	public int portNumber;
	public String hostName = null;
	public Socket streamSocket = null;
	public PrintWriter outStream = null;
	public BufferedReader inStream = null;
        public InputStream iStream = null;

	public TCPCommunication(String hn, int pn) throws UnknownHostException, IOException{
		hostName = hn;
		portNumber = pn;
		streamSocket = new Socket(hostName, portNumber);
		outStream = new PrintWriter(streamSocket.getOutputStream(), true);
		inStream = new BufferedReader(new InputStreamReader(streamSocket.getInputStream()));
                iStream = streamSocket.getInputStream();
	}
	
	public boolean sendCommand(String command){
		outStream.write(command);
                System.out.print("String: ");
                System.out.println(command);
		return !outStream.checkError();
	}
	
	public boolean sendCommand(Integer value){
		outStream.write(value);
                System.out.println(value);
		return !outStream.checkError();
	}
	
	
	public String receiveCommand() throws IOException{
		while(!inStream.ready());
		return inStream.readLine();
		//return inStream.
		
	}
        
        public TwoTypeStruct readRawNonBlocking()
        {
            int size = 0;
            byte[] data = new byte[500];
            try {
                streamSocket.setSoTimeout(TIMEOUT);
                size = iStream.read(data,0,10);
            } catch (SocketException se){
                
            } catch (IOException ioe){
                
            }
            return new TwoTypeStruct(data,size);
        }
        
        public boolean disconnect(){
            try{
                streamSocket.close();
            }catch(IOException e){
                
            }
            return streamSocket.isClosed();
        }
        
        public boolean isConnected(){
            return streamSocket.isConnected();
        }
	
}
