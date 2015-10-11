/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import tcp.stream.ControllCommands;
import tcp.stream.FileStreamer;
import tcp.stream.Messanger;
import tcp.stream.QueueStruct;
import tcp.stream.TCPCommunication;

/**
 *
 * @author PatLas
 */
public class MessangerTest {
    
    
    public static void main(String [ ] args){
        
        
       
     LinkedBlockingQueue receiver = new LinkedBlockingQueue();
     LinkedBlockingQueue<QueueStruct> transmitter = new LinkedBlockingQueue<>();
     
//     try{
         QueueStruct x = new QueueStruct();
         x.setStream(false);
         x.setCommand("TEST");
         //transmitter.add(x);
         
        //TCPCommunication atcp = new TCPCommunication("192.168.1.3", 12345);
        //Messanger m = new Messanger(atcp,receiver,transmitter);
        //(new Thread(m)).start();
        
        byte[] xxx = new byte[11];
        /*for(byte i=0; i<10;i++)
            xxx[i] = (byte)(i+0x30);
        xxx[10] = 'a';
        
        x.setStream(true);
        x.setFileSize(87);
        x.setData(xxx);
        for(int t=0;t<8;t++)
            transmitter.add(x);*/
        
//        tcpcomm.sendCommand(ControllCommands.START_FILE_STREAM);
//                        delay(300);
//			tcpcomm.sendCommand(Integer.toString((int)fd.length()));                    
//                        delay(300);  
//			tcpcomm.sendCommand(getHash(fileName));
//                        delay(300);
//        
                     
        
        //m.sendCommand("F_STREAM");
        File a = new File("pat.txt"); 

        String ext1 = (a.getAbsolutePath());
        String[] ext = ext1.split("\\.");
        for(String e : ext)
        System.out.println(e);

           String extention = ext[ext.length-1]; 
//        
//        if(m.recvCommand().equalsIgnoreCase(ControllCommands.GET_SIZE)){
//            System.out.println("Ask for size");
//                     
//            m.sendCommand(Integer.toString((int)a.length()));
//            
//        }
//        
//        if(m.recvCommand().equalsIgnoreCase(ControllCommands.GET_HASH)){
//           System.out.println("Ask for hash");        
//            m.sendCommand(FileStreamer.getHash("pat.txt"));      
//        }
//        
//        if(m.recvCommand().equalsIgnoreCase(ControllCommands.GET_TYPE)){
//           System.out.println("Ask for type");        
//            m.sendCommand("txt");      
//        }
//                        
//        m.streamFile(a);
//        while(m.streamDone != true){};
//        m.streamDone = false;
//        m.sendCommand(ControllCommands.F_DONE);
//        System.out.println("Stram done with: "+m.recvCommand());
//        
//        for(int a=0;a<20;a++){
//            m.sendCommand("Komenda testowa"+a);
//        }
//         
//         Thread.sleep(1000);
//        }
//        
//        while(true){
//            System.out.println(m.recvCommand());
//        }
                
                
                
                
//     }catch(IOException io){}
     //catch(InterruptedException ie){}
 

}
        
        
       
        
        
}

    
