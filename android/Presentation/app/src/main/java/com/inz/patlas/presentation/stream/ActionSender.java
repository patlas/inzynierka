package com.inz.patlas.presentation.stream;

public class ActionSender {
	TCPCommunication tcpcomm = null;
	
	public ActionSender(TCPCommunication tcp){
		tcpcomm = tcp;
		
	}

	public boolean sendKey(ActionKey key){
		if(tcpcomm.sendCommand(key.getKey()))
			return true;
		else 
			return false;
		
	}
	
	
}
