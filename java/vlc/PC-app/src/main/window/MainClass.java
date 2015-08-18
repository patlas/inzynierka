package main.window;

import java.io.IOException;
import java.net.UnknownHostException;

import tcp.stream.FileStreamer;
import tcp.stream.TCPCommunication;

public class MainClass {

	public static void main(String[] args) {
		
		try {
			TCPCommunication tcp = new TCPCommunication("127.0.0.1", 55555);
			FileStreamer fs = new FileStreamer(tcp);
			fs.streamFile(null);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
