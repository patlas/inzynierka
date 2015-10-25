/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inz.patlas.presentation.stream;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public boolean streamDone = false;
    
    @Override 
    public void run(){
        // TODO - add interrupt mechanism to stop task in case of server/client error
        try {
            tcpcomm = new TCPCommunication("192.168.1.4", 12345);
        }catch(IOException io){
            Log.d("EXCEPTION",io.getMessage() );
        }
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
                    byte[] dat = new byte[TLVstruct.TLV_DATA_SIZE];
                   // if(qs.getData().length > TLVstruct.TLV_DATA_SIZE)
                    //    System.arraycopy(qs.getData(), 0, dat, 0, TLVstruct.TLV_DATA_SIZE);
                    //else
                        System.arraycopy(qs.getData(), 0, dat, 0, TLVstruct.TLV_DATA_SIZE);
                    
                    for(long ww = 0; ww<TLVstruct.TLV_DATA_SIZE; ww++)
                    System.out.println(qs.getData()[(int)ww]);
                    
                    byte[] dataToSend =  buildTLVdataHeader(false,dat,qs.getFileSize());
                    
                    
                    
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
                    //System.out.println("Sa jakies dane odebrane");
                        //tlv = TTStoTLV(dataList.poll());
                    tlv = TTStoTLV(tts);
                    
                    if(commandSize == 0)
                    {
                        compSize = tlv.length;
                    }
                    command.append(new String(tlv.data,Charset.forName("UTF-8")));
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
//        byte[] x = tts.getData();
//        for(int a =0 ; a<tts.length(); a++)
//            System.out.println(x[a]);
        
        TLVstruct tlv = new TLVstruct();
        tlv.type = tts.getData()[0];
        
        //System.out.println("Type is: "+tlv.type);
        tlv.length = ByteUtils.bytesToLong(tts.getData(),1);
        //System.out.println("Length is: "+tlv.length);
        //tlv.data = new ByteBuffer()
        ByteBuffer bb = ByteBuffer.allocate(TLVstruct.TLV_DATA_SIZE);
        tlv.data = (bb.put(tts.getData(), 9, TLVstruct.TLV_DATA_SIZE)).array();
        //System.out.println("data is: "+tlv.data[0]);
        
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
    
    
    
    public boolean sendCommand(String cmd)
    {
         QueueStruct struct = new QueueStruct();
         struct.setStream(false);
         struct.setCommand(cmd);
         return transmitter.add(struct);
    }
    
    public String recvCommand()
    {
        while(true)
        {
            if(!receiver.isEmpty())
                return (String)receiver.poll();
        }
    }
    
    
    
    public boolean streamFile(File fd)
    {
       // File fd = new File(fileName);



        //send info that file will be streamed
//            tcpcomm.sendCommand(ControllCommands.START_FILE_STREAM);
//            delay(300);
//            tcpcomm.sendCommand(Integer.toString((int)fd.length()));                    
//            delay(300);  
//            tcpcomm.sendCommand(getHash(fileName));
//            delay(300);

        int index=0;
        /*for(String type : ControllCommands.FILE_TYPES){
                if(type.equalsIgnoreCase(fileName.split("\\.")[1])){
                        tcpcomm.sendCommand(index);
                }
                index++;
        }*/
        try{
            //Path path = fd.toPath();
            byte[] fileByteArray = convertFileToByteArray(fd);//Files.readAllBytes(path);
            byte[] tempDat = new byte[TLVstruct.TLV_DATA_SIZE];
            
        
                int sindex =  (int) Math.ceil((double)fileByteArray.length / TLVstruct.TLV_DATA_SIZE);
                for(int i=0; i<sindex;i++)
               {
                    QueueStruct fileQS = new QueueStruct();
                    fileQS.setStream(true);
                    fileQS.setFileSize(fd.length());
                   
                    int end = TLVstruct.TLV_DATA_SIZE;
                    if(((1+i)*TLVstruct.TLV_DATA_SIZE)>fileByteArray.length)
                    {
                        end = fileByteArray.length - (i*TLVstruct.TLV_DATA_SIZE);
                    }

                    
                    for(int pos=0; pos<end; pos++)
                    {
                        tempDat[pos] = fileByteArray[i*TLVstruct.TLV_DATA_SIZE + pos];
                    }
                    
                    fileQS.setData(tempDat);
                    
                    transmitter.put(fileQS);      
               }



            }catch(InterruptedException ie){
                
            }

    System.out.println("Streaming done");
    streamDone = true;
    return true;//isTransferSuccesfull();
		
    }




    private static byte[] convertFileToByteArray(File f)
    {
        byte[] byteArray = null;
        try
        {
            InputStream inputStream = new FileInputStream(f);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024*8];
            int bytesRead =0;

            while ((bytesRead = inputStream.read(b)) != -1)
            {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return byteArray;
    }
    
}


class ByteUtils {
    //private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);    

    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
        buffer.putLong(0, x);
        return myArray(buffer);
    }

    public static long bytesToLong(byte[] bytes, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE/Byte.SIZE);
        byte[] tempB = new byte[Long.SIZE/Byte.SIZE];
        
        for(int index=0; index<(Long.SIZE/Byte.SIZE);index++){
            tempB[index] = bytes[offset+index];
        }
        
        buffer.put(myArray(tempB), 0, 8);
        buffer.flip();//need flip 
        return buffer.getLong();
    }
    
    private static byte[] myArray(ByteBuffer b){
        
        int size = b.capacity();
        byte[] ret = new byte[size];
        
        //System.out.println(size);
        for(int index=0; index<size; index++){
            ret[index]= b.get((size-1)-index) ;
        }
       return ret; 
    }
    
    private static byte[] myArray(byte[] b){
        
        int size = b.length;
        byte[] ret = new byte[size];
        
        //System.out.println(size);
        for(int index=0; index<size; index++){
            ret[index]= b[(size-1)-index] ;
        }
       return ret; 
    }
}