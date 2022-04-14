package util;
import java.io.*;
import java.util.*;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
/**
 * Helper Class that translates the data from the user Data. This is done as 
 * Serializable data is not readable, thus, changing Album would not work.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class albumReader {
	private SimpleStringProperty title;
    private SimpleStringProperty date;
    private SimpleIntegerProperty photoCount;
    /**
     * Initializes the class. 
     * @param  the Album it is translating.
     * @author AbidAzad aa2177
     */
	public albumReader(Album a) {
		this.title = new SimpleStringProperty( a.getName());
		this.date = new SimpleStringProperty(a.getDateRange());
		this.photoCount = new SimpleIntegerProperty(a.getPhotoCount());
	}
	
	/**
     * Returns the title of the album. 
     * @author AbidAzad aa2177
     */
    public String getTitle() {
        return title.get();
    }
    /**
     * Changes the title of the album. 
     * @param  a String value of the new title.
     * @author AbidAzad aa2177
     */
    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }
    /**
     * Returns the date of the album.  ///SUBJECT TO CHANGE
     * @author AbidAzad aa2177
     */
    public String getDate() {
        return date.get();
    }


	/**
     * Returns the size of the album.
     * @author AbidAzad aa2177
     */
    public int getPhotoCount() {
        return photoCount.get();
    }

    /**
     * Changes the size of the album. 
     * @param  the new Integer Size.
     * @author AbidAzad aa2177
     */
    public void setPhotoCount(int photoCount) {
        this.photoCount = new SimpleIntegerProperty(photoCount);
    }

}
