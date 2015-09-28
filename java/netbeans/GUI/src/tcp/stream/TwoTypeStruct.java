/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcp.stream;

/**
 *
 * @author PatLas
 */
public class TwoTypeStruct {
    private byte[] data = new byte[500];
    private int size = 0;
    
    public byte[] getData(){
        return data;
    }
    
    public int length(){
        return size;
    }
    
    public TwoTypeStruct(byte[] d, int s){
        data = d;
        size = s;
    }
    
    
}
