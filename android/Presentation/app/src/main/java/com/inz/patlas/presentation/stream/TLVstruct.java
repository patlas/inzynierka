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
public class TLVstruct 
{
    public static int TLV_DATA_SIZE = 11;
    public static int TLV_STRUCT_SIZE = 20;
    public byte type;
    public long length;
    public byte[] data = new byte[TLV_DATA_SIZE];
   
}
