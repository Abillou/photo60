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
import util.Photo;
/**
 * Class that acts as the controller for the ImageMove.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class ImageMoveController {
	//Buttons
    @FXML private Button OK;
    @FXML private Button CANCEL;
    
    //Textfields
    @FXML private TextField albumName;
    
    //Labels
    @FXML private Label move;
    @FXML private Label move2;
    
	private Stage a;
    public ArrayList<Album> albums;
	private Photo added;
	boolean canceled = true;
	private Album original;
	private boolean copy;
	private boolean addition=false;
	/**
	 * Initializes the controller. Based on parameters, if in copy/move mode, it asks the user to input an existing album. In addition mode, it asks user for a valid name for a new album.
	 * 
	 * @param for the Main Stage Window.
	 * @param for the list of albums that a user has.
	 * @param for the Photo selected that user wants an operation performed on.
	 * @param for the album that the Photo selected originates in.
	 * @param for the boolean that checks if the user prompts for a copy operation.
	 * @param for the boolean that checks if the user prompts for a addition operation.
	 * @author AbidAzad aa2177
	 */	
    public void start(Stage mainStage, ArrayList<Album> albums, Photo added, Album original, boolean copy, boolean addition) {
    	a = mainStage;
    	this.albums = albums;
    	this.added = added;
    	this.original = original;
    	this.copy = copy;
    	this.addition = addition;
    	
    	if(copy) {
    		move.setText("Copy Image");
    		move2.setText("Copy image into album:");
    	}
    	
    	if(addition) {
    		move.setText("Create Album");
    		move2.setText("Create new album:");
    	}
    	
    	
    }
    /**
	 * Method that first checks if the button is in Copy Mode or Adding Mode. In Copy Mode, it copies the image to another album. In addition mode, it creates a new album based on search results. 
	 * Both cases check if the data inputed is valid and also both close the window.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 */	      
    @FXML void OK(ActionEvent event) {
    	System.out.println(addition);
    	if(addition) {
 			for (Album x: albums){
 				if(x.getName().equals(original.getName())) {
 					showAlert("Error", "Duplicate", "Album name exists already.");
 					a.hide();
 					return;
 				}
 			}
 			
 			original.setName(albumName.getText());
 			albums.add(original);
 			canceled = false;
 			a.hide();
 			return;
 		}
    	
 		if(albumName.getText().isBlank()) {
 			showAlert("WARNING", "No input", "Please enter a valid photo.");
 			return;
 		}
 		
 		
 		
 		for(Album x : albums) {
 			System.out.println(x.getName());
 			if(x.getName().equals(albumName.getText())){
 				
 				for(Photo z : x.getPhotos()) {
 					if(z.equals(added) || z.getFilePath().equals(added.getFilePath())) {
 						showAlert("Error", "Duplicate", "Photo exists in album already.");
 					    return;
 					}
 				}
 				x.getPhotos().add(added);
 				System.out.println(x.getPhotoCount());
 				
 				if(!copy) {
 					
	 				for(int i = 0; i < original.getPhotos().size(); i++) {
	 					if(original.getPhotos().get(i).getFilePath().equals(added.getFilePath())) {
	 						original.getPhotos().remove(i);
	 						break;
	 					}
	 				}
	 				for(int i = 0; i< albums.size(); i++) {
	 					if(albums.get(i).getName().equals(original.getName())) {
	 						albums.remove(i);
	 						albums.add(i, original);
	 						break;
	 					}
	 				}
 				
 				}
 				a.hide();
 				return;
 			}
 			
 		}
 		showAlert("WARNING", "Non-existing Album", "Please enter a valid album.");
	    return;
 		
     	
    }  
     /**
    	 * Method that closes the stage. Keeps referenced of whether a cancel operation occured.
    	 * @param for the ActionEvent for the button click.
    	 * @author AbidAzad aa2177
    	 */	      
     @FXML void CANCEL(ActionEvent event) {
 		canceled = true;
 		a.hide();
     	
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
 
}