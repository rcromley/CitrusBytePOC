package com.citrusbyte.poc.smartAc.web.dto;

import java.util.Map;
import java.util.TreeMap;

public class Notifications {
	public static Object sync = new Object();
	private long lastSeqNbr = 0;
	private Map<Long, String> notifications = new TreeMap<Long, String>();

	public Map<Long, String> getNotifications() {
		return notifications;
	}	
	
	public void addNotification(String notification) {
		long thisSeqNbr = 0;
		synchronized(sync){
			thisSeqNbr = lastSeqNbr++;
			notifications.put(thisSeqNbr, notification);
		}		
	}
	
	public void removeNotification(long id) {
		synchronized(sync){
			notifications.remove(id);
		}		
	}
}
