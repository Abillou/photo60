package util;

import java.io.*;
import java.util.*;


/**
 * Class that holds all the attributes of a phots
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class Photo implements Serializable {
	//private HashMap<String, String> tags;
	private ArrayList<Tag> tags;
	private String title;
	private String caption;
	private Date date;
	private String filePath;

	/**
	 * Method that initializes a photo.
	 * @param for the title, a String value.
	 * @param for the caption, a string value.
	 * @param for the date, a String value.                 //SUBJECT TO CHANGE
	 * @param for the filePath of where the image is located, a String Value
	 * @author AbidAzad aa2177
	 * @param tags 
	 */
	public Photo(String title, String caption, Date date, ArrayList<Tag> tags, String filePath) {
		this.title = title;
		this.caption = caption;
		this.date = date;
		this.filePath = filePath;
		this.tags = tags;
		
	}


	/**
	 * Method that adds to the tags of a photo
	 * @param for the type of tag, a String value.
	 * @param for the value of the tag, a string value.
	 * @author AbidAzad aa2177
	 */
	public void addTag(String type, String value) {
		tags.add(new Tag(type, value));
	}
	/**
	 * Method that gets the caption of a photo
	 * @author AbidAzad aa2177
	 */	
	public String getCaption() {
		return caption;
	}
	/**
	 * Method that set the caption of a photo
	 * @param String value of the new caption.
	 * @author AbidAzad aa2177
	 */		
	public void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * Method that gets the date of a photo    //SUBJECT TO CHANGE
	 * @author AbidAzad aa2177
	 */	
	public Date getDate() {
		return date;
	}
	/**
	 * Method that gets the filePath of a photo    
	 * @author AbidAzad aa2177
	 */	
	public String getFilePath() {
		return filePath;
	}
	
	/**
	 * Method that sets the translation of a Photo class to string to be its caption.
	 * @author AbidAzad aa2177
	 */	
	public String toString() {
		return caption;
	}
	/**
	 * Method that returns the tags associated with a Photo.
	 * @author AbidAzad aa2177
	 */		
	public ArrayList<Tag> getTags() {
		return tags;
	}
	/**
	 * Method that sets the Tags of a Photo.
	 * @param ArrayList<Tag> tags of the new tags to be associated with the photo.
	 * @author AbidAzad aa2177
	 */			
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	/**
	 * Returns a string meant to be used as text for a label. 
	 * @author Diego Castellanos dac392
	 */
/*	public String getStringTags() {

		Set<String> keys = this.tags.keySet();
		ArrayList<String> builder = new ArrayList<>();
		if(keys.size() == 0)
			return "*no tags set*";
		for(String id : keys) {
			String value = this.tags.get(id);
			if(value.contains(";")) {
				String[] sublist = value.split(";");
				for(String val : sublist) {
					builder.add("["+val+"]\t");
				}
			}else {
				builder.add("["+value+"]\t");
			}
		}
//		System.out.println(builder.toString());
//		System.out.println(String.join("", builder));
		return String.join("", builder);
	}*/
	
	/**
	 * Returns a string meant to be used as text for a label. 
	 * @author Diego Castellanos dac392
	 * @author Abid Azad aa2177
	 */
	public String getStringTags() {
		
			if(!tags.isEmpty()){
			String builder = "";
			for(Tag x: tags) {
				builder = builder + "["+x.getTagType()+": "+x.getValue()+"] ";
			}
			return builder;
			}
			return "*no tags set*";
	}

	/**
	 * Returns a string meant to be used as text for a label. 
	 * @author Diego Castellanos dac392
	 * @author Abid Azad aa2177
	 */
	public String getTitle() {
		// TODO Auto-generated method stub
		return this.title;
	}
	/**
	 * Changes the title of a photo. 
	 * @param String value of the new title.
	 * @author Abid Azad aa2177
	 */	
	public void setTitle(String title) {
		// TODO Auto-generated method stub
		this.title = title;
	}

}
