package model;

import java.util.*;

import util.User;

import java.io.*;

/**
 * Class used to save and update user information inputed on the program.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 * 
 */
public class serialController implements Serializable{
	/**
	 * Method that updates the file meant to contain the data inputted from user session.
	 * @param for ArrayList of Users.
	 * @author Abid Azad
	 */	
	public void update(ArrayList<User> user) throws IOException{
		ObjectOutputStream a = new ObjectOutputStream(new FileOutputStream("users.ser"));
		a.writeObject(user);
		a.close();
	}
	/**
	 * void method that returns currently saved user data.
	 * @author Abid Azad
	 */		
	public ArrayList<User> data() throws IOException, ClassNotFoundException{
		ObjectInputStream a = new ObjectInputStream(new FileInputStream("users.ser"));
		ArrayList<User> data = (ArrayList<User>) a.readObject();
		a.close();
		return data;
	}
	
}
