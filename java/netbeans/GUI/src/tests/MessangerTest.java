/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import tcp.stream.Messanger;
import tcp.stream.QueueStruct;
import tcp.stream.TCPCommunication;
import tcp.stream.TLVstruct;

/**
 *
 * @author PatLas
 */
public class MessangerTest {
    
    
    public static void main(String [ ] args){
        
        
       
     LinkedBlockingQueue receiver = new LinkedBlockingQueue();
     LinkedBlockingQueue<QueueStruct> transmitter = new LinkedBlockingQueue<>();
     
     try{
         QueueStruct x = new QueueStruct();
         x.setStream(false);
         x.setCommand("TEST");
         //transmitter.add(x);
         
        TCPCommunication atcp = new TCPCommunication("192.168.1.3", 12345);
        Messanger m = new Messanger(atcp,receiver,transmitter);
        (new Thread(m)).start();
        
        byte[] xxx = new byte[11];
        /*for(byte i=0; i<10;i++)
            xxx[i] = (byte)(i+0x30);
        xxx[10] = 'a';
        
        x.setStream(true);
        x.setFileSize(87);
        x.setData(xxx);
        for(int t=0;t<8;t++)
            transmitter.add(x);*/
        
        m.streamFile("pat.txt");
        
        
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
                
                
                
                
     }catch(IOException io){}
     //catch(InterruptedException ie){}
 

}
        
        
       
        
        
}

    
