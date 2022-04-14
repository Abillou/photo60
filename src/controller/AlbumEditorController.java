package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ArrayList;
import model.serialController;
import util.Album;
import util.User;
/**
 * Class that acts as the controller for the AlbumEditor.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class AlbumEditorController {
	private Stage a;
	boolean editMode;
	private User user;
	private Album x;
	private serialController cereal;
	//Buttons
	@FXML private Button cancel;   
	@FXML private Button done;
	
	//TextFields
    @FXML private TextField text;
	
    /**
     * Initializes the controller.
     * @param for the Main Stage Window
     * @param boolean Edit to see if the album is being edited or added to.
     * @param User for the user that owns the album.
     * @param Album for the album being edited.
     * @author AbidAzad aa2177
     */
	public void start(Stage mainStage, boolean edit, User user, Album x) {
    	a = mainStage;
    	editMode = edit;
    	this.user = user;
    	cereal = new serialController();
    	this.x = x;
    }
    /**
     * Checks to see if the user inputted nothing or a duplicate, in which case, throws an alert. Otherwise, adds to the album list of the user and updates user data.
     * @param for the actionEvent of the button click.
     * @author AbidAzad aa2177
     */	
	@FXML void done(ActionEvent event) {
		if(text.getText().isEmpty())
		{
			showAlert("Error!", "Missing Name!", "Please enter a valid album name.");
		}
		
		else {
			if(!editMode) {
				
				for(Album x : user.getAlbums()) {
					if(x.getName().equals(text.getText())) {
						showAlert("Error!", "Duplicate!", "Album of this name already exists!");
						return;
					}
				}
				user.addAlbum(text.getText());
				
				
			}
			else {
				for(Album x : user.getAlbums()) {
					if(x.getName().equals(this.x.getName())) {
						for(Album r : user.getAlbums()) {
							if(r.getName().equals(text.getText())) {
								showAlert("Error!", "Duplicate!", "Album of this name already exists!");
								return;
							}
						}
						x.setName(text.getText());
						break;
					}
				}
			}
			try {
				ArrayList<User> x = cereal.data();
				for(User a : x) {
					if (a.getUsername().equals(user.getUsername())){
						x.remove(a);
						x.add(user);
						break;
					}
				}
				cereal.update(x);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			text.clear();
			a.hide();
		}
    	
    	
    }  
	 /**
     * Closes the secondary stage.
     * @param for the actionEvent of the button click.
     * @author AbidAzad aa2177
     */		
	@FXML void cancel(ActionEvent event) {
		
		a.close();
    	
    }  
    /**
   	 * Helper Method that throws an alert.
   	 * @param for the title of alert, String.
   	 * @param for the header of alert, String.
   	 * @param for the content of alert, String.
   	 * @author DiegoCastellanos dac392
   	 */	  	
	 private void showAlert(String title, String header, String content) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(content);
			alert.showAndWait();
			
	}
	    /**
	   	 * Helper Method that returns the user of the album
	   	 * @author Abid Azad aa2177
	   	 */	  	 
	public  User getUser() {
		return user;
	}
}
