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
import util.Manager;
import util.User;
/**
 * Class that acts as the controller for the AdminView.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class AdminController {
	//Buttons
    @FXML private Button add;
    @FXML private Button exit;
    @FXML private Button delete;
    //TextFields
    @FXML private TextField adminText;
    
    @FXML private ListView<String> userList;
    
    private ObservableList<String> obsList = FXCollections.observableArrayList(); 
    private Stage a;
    
    private ArrayList<User> data = new ArrayList<User>();
    serialController cereal;
    /**
	 * Initializes the controller and displays the list of user.
	 * @param for the Main Stage Window.
	 * @author AbidAzad aa2177
	 */	    
    public void start(Stage mainStage) {
    	a = mainStage;
    	
		cereal = new serialController();
    	try {
			this.data = cereal.data();
			for(User x : this.data) {
	    		obsList.add(x.getUsername());
	    	}
	    	userList.setItems(obsList);
		} catch (Exception e) {
			Manager.showAlert("Fatal", "Fatal error encountered", "A fata error was encountered while retreaving user data. Data may have been corrupted and will be deleted.");
		}
    	

    }
    /**
	 * Adds a user based on admin input, provided there is input and not a duplicate. Updates data afterwards.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 */	       
    @FXML void add(ActionEvent event) {
    	boolean repeat = false;
    	if(adminText.getText().isEmpty()) {
    		showAlert("Error", "Missing Info", "Box is empty");
    	}
    	
    	else {
    		for(User x: data) {
    			if(x.getUsername().equals(adminText.getText())) {
    				repeat = true;
    				break;
    			}
    		}
    		
    		if(repeat) {
    			showAlert("Error", "Duplicate", "User of that name already exists!");
    		}
    		else {
    		data.add(new User(adminText.getText()));
    		try {
    			cereal = new serialController();
				cereal.update(data);
				obsList.clear();
				for(User x : this.data) {
		    		obsList.add(x.getUsername());
		    	}
		    	userList.setItems(obsList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		}
    	}
    }
    /**
	 * Returns back to login screen.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 */	      
    @FXML void exit(ActionEvent event) {
    	 try {		
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/views/Login.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				LoginController listController = loader.getController();
				listController.start(a);
				a.close();

				Scene scene = new Scene(root);
				a.setScene(scene);
				a.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
    }
    /**
 	 * Deletes a user and update user data.
 	 * @param for the ActionEvent for the button click.
 	 * @author AbidAzad aa2177
 	 */	     
    @FXML void delete(ActionEvent event) {
    	if(!userList.getSelectionModel().isEmpty()) {
    		data.remove(userList.getSelectionModel().getSelectedIndex());
    		try {
				cereal.update(data);
				obsList.clear();
				for(User x : this.data) {
		    		obsList.add(x.getUsername());
		    	}
		    	userList.setItems(obsList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    
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
