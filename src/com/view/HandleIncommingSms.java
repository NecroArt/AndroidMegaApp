package com.view;

import java.util.ArrayList;

import smsParsing.Parser;
import database.DbHelper;
import database.SmsRecord;

public class HandleIncommingSms extends Thread /*implements Runnable*/ {
	
	private static int runningThreadAmount = 0;
	
	public void run() {
    	
		runningThreadAmount++;
		
    	int addedRows = 0;
    	int iterationsOccured = 0;
    	boolean isSmsFound = false;
    	
    	try {
    		
			while(!isSmsFound && iterationsOccured < 10) {
    			
				iterationsOccured++;
    			//wait while sms will store in database
	    		Thread.sleep(10000);
	    		
	    		DbHelper dbHelper = new DbHelper(SmsReceiver.getContext(), null, null, DbHelper.getDBVersion());
	    		ArrayList<SMS> smsArrayList = dbHelper.getNewSms(SmsReceiver.getContext());
	    		
	    		if (smsArrayList.size() > 0) {
	    			
	    			isSmsFound = true;
	    			
	    			int smsHandled = 0;
	    			for (SMS currentSms: smsArrayList) {
	    				
	    				smsHandled++;
	    				ArrayList<SmsRecord> recordsArray = Parser.parse(currentSms);
	    				
	    				for (SmsRecord currentRecord : recordsArray) {
	    					
	    					System.out.println("added " + String.valueOf(addedRows));
	    					dbHelper.addRecord(currentRecord);
	    					addedRows++;
	    					
	    				}
	    				
	    				if (smsHandled == 5) {
	    					
	    					break;
	    					
	    				}
	    				
	    			}
	    			
	    		}
	    		
    		}
    		
    		
    	}
    	catch (Exception ex) {
    		
    		System.out.println("exception on sleep of thread");
    		ex.printStackTrace();
    		runningThreadAmount--;
    		
    	}
        
    }
	
	public static int getNumberRunning () {
		
		return runningThreadAmount;
		
	}

}