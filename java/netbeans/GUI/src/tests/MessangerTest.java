/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import tcp.stream.Messanger;
import tcp.stream.TLVstruct;

/**
 *
 * @author PatLas
 */
public class MessangerTest {
    
    
    public static void main(String [ ] args){
        
        Messanger m = new Messanger(null, null, null);
        
        byte a[] = "patryk".getBytes();
        byte app[] = m.buildTLVdataHeader(true,a,a.length);
//        m.buildTLVdataHeader(true,a,a.length);
        
//        for(byte ax : app)
//        System.out.println(ax);
        
        String str = "jakisDziwnyNapis123456789"; //25
        int index =  (int) Math.ceil((double)str.length() / TLVstruct.TLV_DATA_SIZE);
                    
                   for(int i=0; i<index;i++)
                   {
                       int end = (i+1)*TLVstruct.TLV_DATA_SIZE;
                       if(end>str.length())
                           end = str.length();
                       
                      String substr =  str.substring(i*TLVstruct.TLV_DATA_SIZE, end);
                       System.out.println(substr);
                   
                   }
        
    }
    
}
