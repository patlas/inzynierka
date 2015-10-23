package com.inz.patlas.presentation.stream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import java.net.SocketException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FileStreamer {
	
        static final int SEND_BUFF_SIZE = 100;
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
                
                streamSocket.setSendBufferSize(SEND_BUFF_SIZE);
                
		outStream = new PrintWriter(streamSocket.getOutputStream(), true);
	}
	
	public FileStreamer() throws IOException {
		
		if(hostName.isEmpty() || portNumber==0){
			System.out.println("Socket could not be created");
		}
		else{
			streamSocket = new Socket(hostName, portNumber);
			outStream = new PrintWriter(streamSocket.getOutputStream(), true);
		}
                
                streamSocket.setSendBufferSize(SEND_BUFF_SIZE);
		
	}
	
	public FileStreamer(TCPCommunication tcp){
		hostName = tcp.hostName;
		portNumber = tcp.portNumber;
		streamSocket = tcp.streamSocket;
		outStream = tcp.outStream;
		tcpcomm = tcp;
		
                try{
                    streamSocket.setSendBufferSize(SEND_BUFF_SIZE);
                    streamSocket.setTcpNoDelay(false);
                }catch(SocketException se){
                    
                }
                
                
	}

	
	public boolean streamFile(String fileName){
		BufferedOutputStream outBufferedStream = null;
		 
		try {
			outBufferedStream = new BufferedOutputStream(streamSocket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(outBufferedStream != null){
			File fd = new File(fileName);
                        System.out.println((int)fd.length());
			byte[] fileByteArray = new byte[(int) fd.length()];
			
			//send info that file will be streamed
			tcpcomm.sendCommand(ControllCommands.START_FILE_STREAM);
                        delay(300);
			tcpcomm.sendCommand(Integer.toString((int)fd.length()));                    
                        delay(300);  
			tcpcomm.sendCommand(getHash(fileName));
                        delay(300);
			
			int index=0;
			for(String type : ControllCommands.FILE_TYPES){
				if(type.equalsIgnoreCase(fileName.split("\\.")[1])){
					tcpcomm.sendCommand(index);
				}
				index++;
			}

			FileInputStream fileStream = null;
			
			try {
				fileStream = new FileInputStream(fd);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BufferedInputStream fileBufferedStream = new BufferedInputStream(fileStream);
			
			try {
                           
				fileBufferedStream.read(fileByteArray, 0, fileByteArray.length);
				outBufferedStream.write(fileByteArray, 0, fileByteArray.length);
				outBufferedStream.flush();
				//outBufferedStream.close();
                //connectionSocket.close();
            
                            } catch (IOException ex) {
                                // Do exception handling
                                return false;
                            }
		
		}
		
		 

		//outStream.println("TEST");
		/* 
		 * 1) streamować plik
		 * 2) wysłać komende o końcu streamu -> jego crc?
		 * 3) oczekiwać komendy potwierdzającej poprawne odebranie pliku -> zrobione
		 */
		
		//outStream.wr
		System.out.println("Streaming done");
		return isTransferSuccesfull();
		
	}
	
	private boolean isTransferSuccesfull(){
            delay(2000);
		//TODO dodac jakis timeout zeby nie czekal w nieskonczonosc :D

                ExecutorService service = Executors.newSingleThreadExecutor();

                try {
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            while(!tcpcomm.sendCommand(ControllCommands.SUCCESS_QUERY));
                        }
                    };

                    Future<?> f = service.submit(r);

                    f.get(2, TimeUnit.MINUTES);     // attempt the task for two minutes
                }
                catch (final InterruptedException e) {
                    // The thread was interrupted during sleep, wait or join
                }
                catch (final TimeoutException e) {
                    System.out.println("Can not send SUCCESS_QUERY command!");
                    service.shutdown();
                }
                catch (final ExecutionException e) {
                    // An exception from within the Runnable task
                }
                finally {
                    service.shutdown();
                    System.out.println("Command send");
                }
            
            
		
		
		
		try {
                        delay(2000);
			if(tcpcomm.receiveCommand().equals(ControllCommands.TRANSFER_SUCCESFULL))
				return true;
                        else{
                                System.out.println("Transfer not successful - restart connection");
				return false;
                        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
                }
            
	}
	
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
	
	
	public static String getHash(String fileName){
		
		MessageDigest digest = null;
		try  {
			FileInputStream inputStream = new FileInputStream(fileName);
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 
	        byte[] bytesBuffer = new byte[1024];
	        int bytesRead = -1;
	 
	        try {
				while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
				    digest.update(bytesBuffer, 0, bytesRead);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		byte[] hashedBytes = digest.digest();
		return convertByteArrayToHexString(hashedBytes);
	}
	
        
        private void delay(int val)
        {
            try{
                Thread.sleep(val); 
            }catch(InterruptedException ie){}  
        }
        
}
