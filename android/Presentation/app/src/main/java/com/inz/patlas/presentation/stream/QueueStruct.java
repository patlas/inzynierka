/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inz.patlas.presentation.stream;

/**
 *
 * @author PatLas
 */
public class QueueStruct {
    private boolean stream;
    private String command;
    private byte[] data = new byte[20];
    private long fileSize = 0;
    
    public boolean isStream(){
        return stream;
    }
    
    public String getCommand(){
        return command;
    }
    
    public byte[] getData(){
        return data;
    }
    
    public long getFileSize(){
        return fileSize;
    }
    
    public void setStream(boolean tf){
        stream = tf;
    }
    
    public void setCommand(String cmd){
        command = cmd;
    }
    
    public void setData(byte[] b){
        System.arraycopy(b, 0, data, 0, b.length);
    }
    
    public void setFileSize(long fs){
        fileSize = fs;
    }
    
}
