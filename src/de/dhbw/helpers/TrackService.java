package de.dhbw.helpers;

import java.text.DecimalFormat;
import java.util.List;

import de.dhbw.database.Coordinates;

public class TrackService {

	public TrackService() {

	}

	
	//calculates the distance of the tracked route
	public static double calcDistance(List<Coordinates> listContents) {
		int R = 6371;
		double distance = 0;
		int i;
		if (listContents.size()>1){
			for (i = 0; i < (listContents.size() - 1); i++) {
				
				double dLat = Math.toRadians(listContents.get(i + 1).get_latitude()
						- listContents.get(i).get_latitude());
				double dLon = Math.toRadians(listContents.get(i + 1)
						.get_longitude() - listContents.get(i).get_longitude());
				double a = Math.sin(dLat / 2)
						* Math.sin(dLat / 2)
						+ Math.cos(Math.toRadians(listContents.get(i)
								.get_latitude()))
								* Math.cos(Math.toRadians(listContents.get(i + 1)
										.get_latitude())) * Math.sin(dLon / 2)
										* Math.sin(dLon / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
				double d = R * c;
				distance = distance + d;
			}
		}
		return distance;
	}
	
	
	//calculates the sum of total elevations
	
	public static double calcElevation(List<Coordinates> listContents){
		double ascend = 0;
		int i;
		for (i = 0; i < (listContents.size()-1); i++) {
			if (listContents.get(i+1).get_altitude() > listContents.get(i).get_altitude()){
				ascend = ascend + (listContents.get(i+1).get_altitude() - listContents.get(i).get_altitude());
			}
		}
		return ascend;
	}
	
	//calculates the sum of total descent
	
	public static double calcDescent(List<Coordinates> listContents){
		double descent = 0;
		int i;
		for (i = 0; i < (listContents.size()-1); i++) {
			if (listContents.get(i+1).get_altitude() < listContents.get(i).get_altitude()){
				descent = descent + (listContents.get(i).get_altitude() - listContents.get(i+1).get_altitude());
			}
		}
		return descent;
	}
	
	//calculates duration
	
	public static String calcDuration(List<Coordinates> listContents){
		if (listContents.size() > 1){
			int numberOfSamples = listContents.size();
			long firstTimeStamp = listContents.get(0).get_timestamp();
			long lastTimeStamp = listContents.get(numberOfSamples-1).get_timestamp();
			long duration = lastTimeStamp - firstTimeStamp;
			long secondInMillis = 1000;
			long minuteInMillis = secondInMillis * 60;
			long hourInMillis = minuteInMillis * 60;
			long dayInMillis = hourInMillis * 24;
			long yearInMillis = dayInMillis * 365;
			
			long elapsedYears = duration/yearInMillis;
			duration = duration % yearInMillis;
			long elapsedDays = duration/dayInMillis;
			duration = duration % dayInMillis;
			long elapsedHours = duration/hourInMillis;
			duration = duration % hourInMillis;
			long elapsedMinutes = duration/minuteInMillis;
			duration = duration % minuteInMillis;
			long elapsedSeconds = duration/secondInMillis;
			
			DecimalFormat df = new DecimalFormat("00"); //Zweistellige Ausgabe
			
			return df.format(elapsedHours) + ":" + df.format(elapsedMinutes) + ":" + df.format(elapsedSeconds);
		}else {
			return "00:00:00";
		}
	}
	
	//TODO calculates space
	//TODO calculates calories burned	
	
	
}
