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
         transmitter.add(x);
         
        TCPCommunication atcp = new TCPCommunication("192.168.1.3", 12345);
        Messanger m = new Messanger(atcp,receiver,transmitter);
        (new Thread(m)).start();
        
        
        for(int a=0;a<4;a++){
            x.setStream(false);
         x.setCommand("TEST"+a);
         transmitter.add(x);
         
         Thread.sleep(1000);
        }
        
        while(true){
            if(!receiver.isEmpty()){
                System.out.println(receiver.poll());
            }
        }
                
                
                
                
     }catch(IOException io){}
     catch(InterruptedException ie){}
 

}
        
        
       
        
        
}

    
