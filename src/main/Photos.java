package main;


import controller.LoginController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Manager;

import java.util.Optional;

/**
 * Main Photo Library class that will initiate and run the program.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 * 
 */


public class Photos extends Application{

	public static void main(String[] args) {
		
		launch(args);
	}
	/**
	 * void method that loads the Login FXML. 
	 * @param for the main stage window.
	 * @author Abid Azad
	 */	
	public void start(Stage primaryStage){
		try {		
			FXMLLoader loader = new FXMLLoader();   
			loader.setLocation(getClass().getResource("/views/Login.fxml"));
			
			// load the fxml
			AnchorPane root = (AnchorPane)loader.load();

			// get the controller (Do NOT create a new Controller()!!)
			// instead, get it through the loader
			Manager.start();
			LoginController listController = loader.getController();
			listController.start(primaryStage);
			
		
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			Manager.showAlert("FATAL", "Fatal error has been encountered", "The program encountered an error that it cannot recover from. Now attempting to safely exit the program.");
		}
	}


}
