/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.stream;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author PatLas
 */
public class Messanger implements Runnable {
    
    private LinkedBlockingQueue receiver = null;
    private LinkedBlockingQueue<QueueStruct> transmitter = null;
    private TCPCommunication tcpcomm = null;
    
    private LinkedList<TwoTypeStruct> dataList = new LinkedList();
    private int index = 0;
    
    @Override 
    public void run(){
        // TODO - add interrupt mechanism to stop task in case of server/client error
        TwoTypeStruct tts = null;
        TLVstruct tlv = null;
        QueueStruct qs = null;
        long commandSize = 0;
        long compSize = 0;
        
        StringBuilder command = new StringBuilder();
        while(true)
        {
            if((qs = transmitter.poll()) != null){
                if(!qs.isStream())
                {
                    //send regular command
                   int index =  (int) Math.ceil((double)qs.getCommand().length() / TLVstruct.TLV_DATA_SIZE);
                    
                   for(int i=0; i<index;i++)
                   {
                       int end = (i+1)*TLVstruct.TLV_DATA_SIZE;
                       if(end>qs.getCommand().length())
                       {
                           end = qs.getCommand().length();
                       }
                       
                       String substr =  qs.getCommand().substring(i*TLVstruct.TLV_DATA_SIZE, end);
                      // System.out.println(substr);                  
                      byte[] dataToSend =  buildTLVdataHeader(true,substr.getBytes(Charset.forName("UTF-8")), qs.getCommand().length());
                      tcpcomm.sendByteArray(dataToSend, dataToSend.length);         
                   }
                   
                   System.out.println("Send command: "+qs.getCommand()+" in interation: "+index+" and length: "+qs.getCommand().length());

                }
                else
                {
                    //send stream data (file)
                    byte[] dataToSend =  buildTLVdataHeader(false,qs.getData(),qs.getFileSize());
                    tcpcomm.sendByteArray(dataToSend, dataToSend.length);
                    
                    System.out.println("Streaming file, size: "+qs.getFileSize()+", packet length: "+dataToSend.length);
                    
                    
                }
                
            }
            else
            {
                if((tts = tcpcomm.readRawNonBlocking()).length()!=0)
                {
                    //TODO - think about dataList -> is it realy necessary?
                    //dataList.add(tts);  

                        //tlv = TTStoTLV(dataList.poll());
                    tlv = TTStoTLV(tts);
                    
                    if(commandSize == 0)
                    {
                        compSize = tlv.length;
                    }
                    
                    command.append(new String(tlv.data,StandardCharsets.UTF_8));
                    commandSize+=tlv.data.length;
                    
                    if(commandSize >= compSize)
                    {
                        try{
                            receiver.put(command.substring(0, (int)compSize));
                        }catch(InterruptedException ie){
                            command.setLength(0);
                            commandSize = 0;
                        }
                        command.setLength(0);
                        commandSize = 0;              
                    }
                }
                else
                {
//                    tlv = TTStoTLV(dataList.poll());
//                    
//                    if(tlv.length <= TLVstruct.TLV_DATA_SIZE) // or check type field
//                    {
//                        //command received -> insert it into queue
//                        try{
//                            receiver.put(new String(tlv.data,StandardCharsets.UTF_8));
//                        }catch(InterruptedException ie){
//                            
//                        }
//                    }
//                    else
//                    {
//                        int commandSize = 0;
//                        StringBuilder command = new StringBuilder();
//                        command.append(new String(tlv.data,StandardCharsets.UTF_8));
//                        commandSize+=tlv.data.length;
//                        while(commandSize < tlv.length)
//                        {
//                            tlv = TTStoTLV(dataList.poll());
//                            command.append(new String(tlv.data,StandardCharsets.UTF_8));
//                            commandSize+=tlv.data.length;
//                        }
//                        command.substring(0, (int)tlv.length);
//                        try{
//                            receiver.put(new String(tlv.data,StandardCharsets.UTF_8));
//                        }catch(InterruptedException ie){
//                            
//                        }
                        //TODO - if necessary -> recognize if stream is being send
//                    }
                }
            }
        }
    }
    
    
    
    public Messanger(TCPCommunication tcp, LinkedBlockingQueue rec, LinkedBlockingQueue trans)
    {
        tcpcomm = tcp;
        receiver = rec;
        transmitter = trans;
    }
    
    private TLVstruct TTStoTLV(TwoTypeStruct tts)
    {
        TLVstruct tlv = new TLVstruct();
        tlv.type = tts.getData()[0];
        tlv.length = ByteUtils.bytesToLong(tts.getData(),1);
        //tlv.data = new ByteBuffer()
        ByteBuffer bb = ByteBuffer.allocate(TLVstruct.TLV_DATA_SIZE);
        tlv.data = (bb.put(tts.getData(), 9, TLVstruct.TLV_DATA_SIZE)).array();
        
        return tlv;
    }
    
    
    //data array <= 11B
    /*private*/ public byte[] buildTLVdataHeader(boolean command, byte[] data, long length)
    {
        byte[] header = new byte[TLVstruct.TLV_STRUCT_SIZE];
        
        if(command == true)
            header[0] = 0;
        else
            header[0] = 1;
        
        byte[] len = ByteUtils.longToBytes(length);
        
        System.arraycopy(len, 0, header, 1, len.length);    
        System.arraycopy(data, 0, header, 9, data.length);
        
        return header;
    }
    
}


class ByteUtils {
    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes, int offset) {
        buffer.put(bytes, offset, 8);
        buffer.flip();//need flip 
        return buffer.getLong();
    }
}