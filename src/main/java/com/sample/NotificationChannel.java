package com.sample;


import org.kie.api.runtime.Channel;
import com.sample.CallREST;

public class NotificationChannel implements Channel {
public static final String CHANNEL_ID = "outcomes"; 
	

    public void send(Object object) {
    	//System.out.println("priting whats in sysoutchannel");
        //System.out.println(object.toString());
        //System.out.println("printout from SysoutChannel");
        
    	System.out.println("NotificationChannel called");
    	/*
        String[] parts = object.toString().split("&");
        String part1 = parts[0]; 
        String part2 = parts[1]; 
        System.out.println ("parts 1 and 2"+part1+part2);
        */
    	String message =object.toString();
        System.out.println("calling Your Fav Notification Service");
        System.out.println(message);
      //  CallBPM cb =new CallBPM();
		//cb.callbpm(part2,part1,processid);
        CallREST cr= new CallREST();
        cr.posthisrest(message);
    }

    
}
