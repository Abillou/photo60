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
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ArrayList;
import model.serialController;
import util.Manager;
import util.User;
import controller.AdminController;

/**
 * Class that acts as the controller for the Login.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class LoginController {
	//Stage
	private Stage a;
	private ArrayList<User> data;
	 // labels
    @FXML private Label Enter_a_Username;
    @FXML private Label Photo_Gallery;
   
    //Buttons
    @FXML private Button Log_in;
    @FXML private Button quit;   
    
  //TextFields
    @FXML private TextField userText;
    
    private User potentialUser;
	/**
	 * Method that initializes the controller.
	 * @param for the Main Stage window.
	 * @author AbidAzad aa2177
	 */	   
    public void start(Stage mainStage) {
    	a = mainStage;
    	serialController a = new serialController();
    	try {
			this.data = a.data();
		} catch (Exception e) {
			Manager.showAlert("Fatal", "Fatal error encountered", "A fata error was encountered while retreaving user data. Data may have been corrupted and will be deleted.");
		}

    }
	/**
	 * Method that compares the text user inputed to the user data. If user inputs admin, opens the admin screen. Else if user inputs a valid name from user data, opens home screen.
	 * Else, shows an alert.
	 * @param for the Main Stage window.
	 * @param for the Album that contains all the images.
	 * @author AbidAzad aa2177
	 */	   
    @FXML void Log_In(ActionEvent event) {
    	// event listener on submit button. 
    	//test
    	if(userText.getText().isEmpty())
    	{
    		showAlert("Error!", "Username Missing", "Please enter a valid username.");
    	}
    	else if(userText.getText().equals("admin")) {
    		try {		
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/views/AdminView.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				AdminController listController = loader.getController();
				listController.start(a);
				a.close();
				
				
			
				Scene scene = new Scene(root);
				a.setScene(scene);
				a.show();
				
			} catch(Exception e) {
				Manager.showAlert("Fatal", "Fatal error encountered", "A fata error was encountered while retreaving user data. Data may have been corrupted and will be deleted.");
			}
    	}
    	
    	else {
    		boolean found = false;
    		
    		if(data != null) {
	    		for(User a : data) {
	    			if(a.getUsername().equals(userText.getText())) {
	    				found = true;
	    				potentialUser = a;
	    			}
	
	    		}
    		}
    		if(found) {
		    	try {		
					FXMLLoader loader = new FXMLLoader();   
					loader.setLocation(getClass().getResource("/views/HomeScreen.fxml"));
					
					AnchorPane root = (AnchorPane)loader.load();
					HomeScreenController listController = loader.getController();
					listController.start(a, potentialUser);
					a.close();
					
					
				
					Scene scene = new Scene(root);
					a.setScene(scene);
					a.show();
					
				} catch(Exception e) {
					e.printStackTrace();
				}
    		}
    		
    		else {
    			showAlert("Error!", "Wrong Username", "Please enter a valid username.");
    		}
    	}
    }
    /**
	 * Button that closes the program.
	 * @author AbidAzad aa2177
	 */	       
    @FXML void quit(ActionEvent event) {
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
    
}
