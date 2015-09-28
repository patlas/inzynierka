/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.stream;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author PatLas
 */
public class Messanger implements Runnable {
    
    private LinkedBlockingQueue receiver = null, transmitter = null;
    private TCPCommunication tcpcomm = null;
    
    private LinkedList<TwoTypeStruct> dataList = new LinkedList();
    private int index = 0;
    
    @Override 
    public void run(){
        
        TwoTypeStruct tts = null;
        TLVstruct tlv = null;
        
        while(true)
        {
            if(transmitter.poll() != null){
                //TODO -  SEND MESSAGE -> first build frame
            }
            else
            {
                if((tts = tcpcomm.readRawNonBlocking()).length()!=0)
                {
                    dataList.add(tts);
                }
                else
                {
                    tlv = TTStoTLV(dataList.poll());
                    if(tlv.length <= TLVstruct.TLV_DATA_SIZE) // or check type field
                    {
                        //command received -> insert it into queue
                        try{
                            receiver.put(new String(tlv.data,StandardCharsets.UTF_8));
                        }catch(InterruptedException ie){
                            
                        }
                    }
                    else
                    {
                        //stream is being received -> TODO if client will be receiving stream data
                    }
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
    
    // TODO - build tlv header for transmited data
    
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