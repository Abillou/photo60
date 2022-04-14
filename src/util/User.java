package util;

import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.file.Paths;
import util.Tag;

/**
 * Class that holds all the attributes of a User.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class User implements Serializable{
	
	/**
	 * 
	 */
	private String username;
	private ArrayList<Album> albums;
	private ArrayList<String> userCreatedTags; 
	
	
	/**
	 * Creates a new user and provides them with a preset Stock Album
	 * @param for String username to identify user.
	 * @author AbidAzad aa2177
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
		userCreatedTags = new ArrayList<String>();
		userCreatedTags.add("location");
		userCreatedTags.add("person");
		
		//Preset Stock Images
		
		if(username.equals("stock")) {
		Album stock = new Album("Stock");
		stock.addPhoto("Stock 1", "Stock 1", Manager.getTime(), new ArrayList<Tag>() , Paths.get("data/stock1.png").toAbsolutePath().toUri().toString());
		stock.addPhoto("Stock 2", "Stock 2", Manager.getTime(), new ArrayList<Tag>(), Paths.get("data/stock2.png").toAbsolutePath().toUri().toString());
		stock.addPhoto("Stock 3", "Stock 3", Manager.getTime(), new ArrayList<Tag>(), Paths.get("data/stock3.png").toAbsolutePath().toUri().toString());
		stock.addPhoto("Stock 4", "Stock 4", Manager.getTime(), new ArrayList<Tag>(), Paths.get("data/stock4.png").toAbsolutePath().toUri().toString());
		stock.addPhoto("Stock 5", "Stock 5", Manager.getTime(), new ArrayList<Tag>(), Paths.get("data/stock5.png").toAbsolutePath().toUri().toString());
		stock.addPhoto("Stock 6", "Stock 6", Manager.getTime(), new ArrayList<Tag>(), Paths.get("data/stock6.jpeg").toAbsolutePath().toUri().toString());
		stock.addPhoto("Stock 7", "Stock 7", Manager.getTime(), new ArrayList<Tag>(), Paths.get("data/stock7.png").toAbsolutePath().toUri().toString());
		albums.add(stock);
		}
	
	}
	/**
	 * String method that gets the username of a User.
	 * @author AbidAzad aa2177
	 */	
	public String getUsername() {
		return username;
	}
	/**
	 * Method that gets all the albums a User owns.
	 * @author AbidAzad aa2177
	 */	
	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	/**
	 * Method that gets all the tags a User has created.
	 * @author AbidAzad aa2177
	 */	
	
	public ArrayList<String> getUserCreatedTags(){
		return userCreatedTags;
	}
	/**
	 * Adds a new album that a User holds.
	 * @param for Album to be added.
	 * @author AbidAzad aa2177
	 */	
	public void addAlbum(String newName) {
		albums.add(new Album(newName));
	}
	
	/**
	 * Method that sets the album list of a user equal to parameter.
	 * @param ArrayList<Album> to be set as the user's albums.
	 * @author AbidAzad aa2177
	 */	
	
	public void setAlbums(ArrayList<Album> a) {
		albums = a;
	}
}
