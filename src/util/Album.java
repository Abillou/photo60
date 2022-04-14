package util;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class that holds all the attributes of a Album.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class Album implements Serializable{
	
	/**
	 * 
	 */
	
	private String title;
//	private String dateRange;
	private ArrayList<Photo> photos;
	//TODO: Make an arraylist of photos

/**
 * Initializes the album.
 * @param String value for the title of album.
 * @author AbidAzad aa2177
 */
	public Album(String title) {
		this.title = title;
//		this.dateRange = "N/A";
		this.photos = new ArrayList<Photo>();
	}
	/**
	 * Returns the title of album
	 * @author AbidAzad aa2177
	 */	
	public String getName() {
		return title;
	}
	/**
	 * Renames the album.
	 * @param String value for the new title of album.
	 * @author AbidAzad aa2177
	 */	
	public void setName(String newName) {
		title = newName;
	}
	/**
	 * Adds to the list of photos the album holds.
	 * @param String value for the title of photo.
	 * @param String value for the caption of photo.
	 * @param String value for the date of photo.
	 * @param String value for the filePath of photo.
	 * @author AbidAzad aa2177
	 * @param tags 
	 */		
	public void addPhoto(String title, String caption, Date date, ArrayList<Tag> tags, String filePath) {
		Photo addition = new Photo(title, caption, date, tags, filePath);
		photos.add(addition);
	}
	
	public ArrayList<Photo> getPhotos(){
		return photos;
	}
	/**
	 * Returns the number of photos an album holds
	 * @author AbidAzad aa2177
	 */	
	public int getPhotoCount(){
		return photos.size();
	}
	
	public String getDateRange() {
		if(photos.isEmpty())
			return "N/A";
		
		Date earliest = null;
		Date latest = null;
		for(Photo photo : photos) {
			if(earliest == null) 
				earliest = photo.getDate();
			if(latest == null)
				latest = photo.getDate();
			
			if(earliest.compareTo(photo.getDate()) > 0)
				earliest = photo.getDate();
			if(latest.compareTo(photo.getDate()) < 0)
				latest = photo.getDate();
		}
		if(earliest.equals(latest))
			return getDisplayDate(earliest);
		String first = getDisplayDate(earliest);
		String last = getDisplayDate(latest);
		String dateRange = first+" - "+last;
		return dateRange;
	}
	
	private String getDisplayDate(Date d) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
		return format1.format(d);
	}
	
	public boolean containsImage(String filepath) {
		for(Photo photo : photos) {
			if(photo.getFilePath().equals(filepath))
				return true;
		}
		return false;
	}
}
