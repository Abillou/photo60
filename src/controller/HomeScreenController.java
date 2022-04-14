package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.Event;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;

import model.serialController;
import util.Album;
import util.Manager;
import util.User;
import util.Photo;
import util.albumReader;
import util.Tag;
/**
 * Class that acts as the controller for the HomeScreen.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class HomeScreenController {
	//Textfield
	@FXML private TextField tag1Value;
	@FXML private TextField tag2Value;
	//Buttons
    @FXML private Button New_Album;
    @FXML private Button Remame_Album;
    @FXML private Button Log_Out;
    @FXML private Button delete;
    @FXML private Button search;
    
    //Labels
    @FXML private Label name;
    @FXML private Label firstOptionText;
    @FXML private Label secondOptionText;
    @FXML private Label thirdOptionText;
    
    //Table Stuff
    @FXML private TableView<albumReader> albumList;
    @FXML private TableColumn<albumReader, String> albumName;
    @FXML private TableColumn<albumReader, String> date;
    @FXML private TableColumn<albumReader, Integer> photoCount;
    
    //ChoiceBoxStuff
    @FXML private ChoiceBox searchOptions;
    private ObservableList<String> searchList = FXCollections.observableArrayList();
    
    @FXML private ChoiceBox firstOptions;
    private ObservableList<String> firstOptionList = FXCollections.observableArrayList();
    
    @FXML private ChoiceBox secondOptions;
    private ObservableList<String> secondOptionList = FXCollections.observableArrayList();
    
    @FXML private ChoiceBox thirdOptions;
    private ObservableList<String> thirdOptionList = FXCollections.observableArrayList();
    
	private Stage a;
	private User user;
	private ObservableList<albumReader> obsList = FXCollections.observableArrayList(); 
	/**
	 * Initializes the controller and displays the list of album names the user holds. It also detect if user double clicked on any of the elements, which then prompts to
	 * close the primary stage and open its respective Image View Stage.
	 * @param for the Main Stage Window.
	 * @param for the user that holds the album data.
	 * @author AbidAzad aa2177
	 */	
	public void start(Stage mainStage, User user) {
    	a = mainStage;
    	this.user = user;
    	name.setText(user.getUsername());
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	albumName.setCellValueFactory(new PropertyValueFactory<>("title"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        photoCount.setCellValueFactory(new PropertyValueFactory<>("photoCount"));
        
        searchList.add("        ");
        searchList.add("Date");
        searchList.add("Tags");
        searchOptions.setItems(searchList);
        
        searchOptions.getSelectionModel().selectedItemProperty().addListener(String ->{
    		
    		if(searchOptions.getSelectionModel().isEmpty() || searchOptions.getSelectionModel().getSelectedItem().toString().isBlank()) {
    			firstOptions.setVisible(false);
    			firstOptionText.setVisible(false);
    			
    			secondOptions.setVisible(false);
    			secondOptionText.setVisible(false);
    			
    			search.setVisible(false);
    			tag1Value.setVisible(false);
    			tag2Value.setVisible(false);
    			thirdOptions.setVisible(false);
    			thirdOptionText.setVisible(false);
    		
    			
    		}
    			
    		else {
	    		if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Date")) {
	    			firstOptions.setVisible(true);
	    			firstOptionText.setText("Start");
	    			firstOptionText.setVisible(true);
	    			secondOptions.setVisible(false);
	    			secondOptionText.setVisible(false);	    			
	    			search.setVisible(false);
	    			tag1Value.setVisible(false);
	    			tag2Value.setVisible(false);
	    			firstOptionList.clear();
	    			secondOptionList.clear();
	    			
	    			for(Album x: user.getAlbums()) {
	    				for(Photo y: x.getPhotos()) {
	    					if(!firstOptionList.contains(formatter.format(y.getDate()))) {
	    						firstOptionList.add(formatter.format(y.getDate()));
	    						
	    						
	    					}
	    				}
	    			}
	    			firstOptions.setItems(firstOptionList);
	    		}
	    		
	    		if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Tags")) {
	    			firstOptions.setVisible(true);
	    			firstOptionText.setText("Tag 1");
	    			firstOptionText.setVisible(true);
	    			
	    			firstOptionList.clear();
	    			secondOptionList.clear();
	    			
	    			for(String x: user.getUserCreatedTags()) {
	    				firstOptionList.add(x);
	    			}
	    			firstOptions.setItems(firstOptionList);
	    		}
    		}
    	});
        
        firstOptions.getSelectionModel().selectedItemProperty().addListener(String ->{
        	
        	if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Date")) {
    		
    		if(firstOptions.getSelectionModel().isEmpty() || !firstOptions.isVisible() || firstOptions.getSelectionModel().getSelectedItem().toString().isBlank()) {
    			secondOptions.setVisible(false);
    			secondOptionText.setVisible(false);
    		}
    			
    		else {
	    		if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Date")) {
	    			secondOptions.setVisible(true);
	    			secondOptionText.setText("End");
	    			secondOptionText.setVisible(true);
	    			
	    			for(Album x: user.getAlbums()) {
	    				for(Photo y: x.getPhotos()) {
	    					if(!secondOptionList.contains(formatter.format(y.getDate())) &&
	    							formatter.format(y.getDate()).compareTo(firstOptions.getSelectionModel().getSelectedItem().toString() ) >=0) {
	    						secondOptionList.add(formatter.format(y.getDate()));	
	    					}
	    			
	    					}
	    				}
	    			secondOptions.setItems(secondOptionList);
	    			}
	    			
	    		}
        	}
        	
        	if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Tags")) {
        		if(firstOptions.getSelectionModel().isEmpty() || !firstOptions.isVisible() || firstOptions.getSelectionModel().getSelectedItem().toString().isBlank()) {
        			secondOptions.setVisible(false);
        			secondOptionText.setVisible(false);
        			tag1Value.setVisible(false);
        		}
        		else {
        		secondOptions.setVisible(true);
	    		secondOptionText.setText("");
	    		secondOptionText.setVisible(true);
	    		tag1Value.setVisible(true);
	    		search.setVisible(true);
	    		if(secondOptionList.isEmpty()) {
        		secondOptionList.add("       ");
        		secondOptionList.add("OR");
        		secondOptionList.add("AND");
        		secondOptions.setItems(secondOptionList);
        		}
        		}
        	}
    		
    	});
        
        secondOptions.getSelectionModel().selectedItemProperty().addListener(String ->{
        	
        	if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Date")) {
    		
    		if(secondOptions.getSelectionModel().isEmpty() || !secondOptions.isVisible() || secondOptions.getSelectionModel().getSelectedItem().toString().isBlank()) {
    			search.setVisible(false);
    		}
    			
    		else {
	    		search.setVisible(true);
	    		
    		}
        	}
        	
        	if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Tags")) {
        		
        		
        		if(secondOptions.getSelectionModel().isEmpty() || secondOptions.getSelectionModel().getSelectedItem().toString().isBlank()) {
    	    		search.setVisible(true);
    	    		thirdOptions.setVisible(false);
        			tag2Value.setVisible(false);
        			thirdOptionText.setVisible(false);
        		}
        		else {
        			if(thirdOptionList.isEmpty()) {
        			for(String x: user.getUserCreatedTags()) {
        				thirdOptionList.add(x);
        			}
        			thirdOptions.setItems(thirdOptionList);
        			}
        			thirdOptions.setVisible(true);
        			tag2Value.setVisible(true);
        			thirdOptionText.setVisible(true);
        		}
            }
    	});
 
        for(int i = 0; i < user.getAlbums().size(); i++)
        {
        	obsList.add(new albumReader(user.getAlbums().get(i)));
        }
        
        
        albumList.setItems(obsList);
      
        
        albumList.setRowFactory(R ->{
        	TableRow<albumReader> row = new TableRow<>();
        	row.setOnMouseClicked(event ->{
        		if(event.getClickCount() == 2 && !(row.isEmpty())) {
        			try {		
        				FXMLLoader loader = new FXMLLoader();   
        				loader.setLocation(getClass().getResource("/views/ImageView.fxml"));
        				
        				AnchorPane root = (AnchorPane)loader.load();
        				ImageViewController listController = loader.getController();
        				for(Album x: user.getAlbums()) {
        					if(x.getName().equals(albumList.getSelectionModel().getSelectedItem().getTitle())) {
        						listController.start(a, x, user, false);
        						break;
        					}
        						
        					
        				}
        				a.close();

        				Scene scene = new Scene(root);
        				a.setScene(scene);
        				a.show();
        				
        			} catch(Exception e) {
        				Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to initiate the Homescreen");
        			}
        	}});
        	return row;
        });
	
    }
	/**
	 * Adds a new album for the user, checking for duplicates and updates user data. Done by opening a secondary stage for the album editor, awaits for input, and updates user data.
	 * @param for the ActionEvent of the button being pushed.
	 * @author AbidAzad aa2177
	 */		
	 @FXML void New_Album(ActionEvent event) {
		 try {		
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/views/AlbumEditor.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				AlbumEditorController listController = loader.getController();
				Stage b = new Stage();
				b.initOwner(a.getScene().getWindow());
				b.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(root);
				b.setScene(scene);
				listController.start(b, false, user, null);
				b.showAndWait();
					obsList.clear();
					for(int i = 0; i < user.getAlbums().size(); i++)
		        	{
		        			obsList.add(new albumReader(user.getAlbums().get(i)));
		        	}				
		        	albumList.setItems(obsList);
				
			} catch(Exception e) {
				Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to add a new album");
			}
	 }
	 /**
		 * Renames an existing album for the user, checking for duplicates and updates user data.  Done by opening a secondary stage for the album editor, awaits for input, and updates user data.
		 * @param for the ActionEvent of the button being pushed.
		 * @author AbidAzad aa2177
		 */		
	 @FXML void Rename_Album(ActionEvent event) {
		 if(albumList.getSelectionModel().isEmpty()) {
			 showAlert("Error", "No Album Selected", "Select the one you would like to rename, then try again.");
			 return;
		 }
		 try {		
				FXMLLoader loader = new FXMLLoader();   
				loader.setLocation(getClass().getResource("/views/AlbumEditor.fxml"));
				
				AnchorPane root = (AnchorPane)loader.load();
				AlbumEditorController listController = loader.getController();
				
				
					
				Stage b = new Stage();
				b.initOwner(a.getScene().getWindow());
				b.initModality(Modality.WINDOW_MODAL);
				Scene scene = new Scene(root);
				b.setScene(scene);
				albumReader a = albumList.getSelectionModel().getSelectedItem();
				Album x = new Album(a.getTitle());
				listController.start(b, true, user, x);
				b.showAndWait();
				user = listController.getUser();
				obsList.clear();
				for(int i = 0; i < user.getAlbums().size(); i++)
		        {
		        	obsList.add(new albumReader(user.getAlbums().get(i)));
		        }				
		        albumList.setItems(obsList);			
			} catch(Exception e) {
				Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to rename an album");
			}
	 }
	 /**
		 * Deletes the selected album and updates user data.
		 * @param for the ActionEvent of the button being pushed.
		 * @author AbidAzad aa2177
		 */		
	 @FXML void delete(ActionEvent event) {
	    	if(!albumList.getSelectionModel().isEmpty()) {
	    		user.getAlbums().remove(albumList.getSelectionModel().getSelectedIndex());
	    		serialController cereal = new serialController();
	    		try {
					ArrayList<User> data = cereal.data();
					for(User x : data) {
						if(x.getUsername().equals(user.getUsername())) {
							x.getAlbums().remove(albumList.getSelectionModel().getSelectedIndex());
							break;
						}
					}
					cereal.update(data);
				} catch(Exception e) {
					Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to delete an album");
				}
	    		
	    		obsList.clear();
				for(int i = 0; i < user.getAlbums().size(); i++)
		        {
		        	obsList.add(new albumReader(user.getAlbums().get(i)));
		        }				
		        albumList.setItems(obsList);	
		        	        
	    		
	    	}
	    
	    }
	 /**
		 * Returns user back to log in screen.
		 * @param for the ActionEvent of the button being pushed.
		 * @author AbidAzad aa2177
		 */			 
	 @FXML void Log_Out(ActionEvent event) {
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
				Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to log out");
			}
	 }	
	 

	 /**
		 * Takes the parameters user inputs in the search fields, determines if they are valid search parameters, then sends user to a ImageView of photos that fill their search parameters.
		 * @param for the ActionEvent of the button being pushed.
		 * @author AbidAzad aa2177
		 */		

	 @FXML void search(ActionEvent event) {
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 
		 if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Date")) {
			 if(firstOptions.isVisible() && !firstOptions.getSelectionModel().isEmpty() && !firstOptions.getSelectionModel().getSelectedItem().toString().isBlank() 
			    && secondOptions.isVisible() && !secondOptions.getSelectionModel().isEmpty() && !secondOptions.getSelectionModel().getSelectedItem().toString().isBlank() ) {
				 Album searchResults = new Album("Search Results");
				 for(Album x : user.getAlbums()) {
					 for(Photo y : x.getPhotos()) {
						 String time = formatter.format(y.getDate());
						 System.out.println(time);
						 System.out.println(firstOptions.getSelectionModel().toString());
						 if(time.compareTo(firstOptions.getSelectionModel().getSelectedItem().toString()) >= 0 && time.compareTo(secondOptions.getSelectionModel().getSelectedItem().toString()) <= 0 && !searchResults.getPhotos().contains(y)) {
							 searchResults.getPhotos().add(y);
						 }
						 
					 }
				 }
				 
				 try {		
     				FXMLLoader loader = new FXMLLoader();   
     				loader.setLocation(getClass().getResource("/views/ImageView.fxml"));
     				
     				AnchorPane root = (AnchorPane)loader.load();
     				ImageViewController listController = loader.getController();
     				listController.start(a, searchResults, user, true);  				
     				a.close();
     				Scene scene = new Scene(root);
     				a.setScene(scene);
     				a.show();
     				
     			} catch(Exception e) {
					Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to search for a match of your parameters");
				}
			 }
		 }
		 
		 if(searchOptions.getSelectionModel().getSelectedItem().toString().equals("Tags")) {
			 Album searchResults = new Album("Search Results");
			 if(firstOptions.isVisible() && !firstOptions.getSelectionModel().isEmpty() && !firstOptions.getSelectionModel().getSelectedItem().toString().isBlank() && !tag1Value.getText().isEmpty() && !tag1Value.getText().isBlank()) {
				 
				 if(secondOptions.isVisible() && (secondOptions.getSelectionModel().isEmpty() || secondOptions.getSelectionModel().getSelectedItem().toString().isBlank())){
					 
					 for(Album x : user.getAlbums()) {
						 for(Photo y : x.getPhotos()) {
							 for(Tag z : y.getTags()) {

								 if(z.getValue().equals(tag1Value.getText()) && z.getTagType().equals(firstOptions.getSelectionModel().getSelectedItem().toString()) && !searchResults.getPhotos().contains(y) ) {

									 searchResults.getPhotos().add(y);
									 break;
								 }
							 }
							 
							 
						 }
					 }
				 }
				 
				 if(secondOptions.isVisible() && !secondOptions.getSelectionModel().isEmpty()){
					 
					 if(thirdOptions.isVisible() && !thirdOptions.getSelectionModel().isEmpty() && !thirdOptions.getSelectionModel().getSelectedItem().toString().isBlank() && !tag2Value.getText().isEmpty() && !tag2Value.getText().isBlank()) {
						 if(secondOptions.getSelectionModel().getSelectedItem().toString().equals("OR")) {
							 searchResults = new Album("Search Results");
							 for(Album x : user.getAlbums()) {
								 for(Photo y : x.getPhotos()) {
									 for(Tag z : y.getTags()) {

										 if( ( (z.getValue().equals(tag1Value.getText()) && z.getTagType().equals(firstOptions.getSelectionModel().getSelectedItem().toString())) || (z.getValue().equals(tag2Value.getText()) && z.getTagType().equals(secondOptions.getSelectionModel().getSelectedItem().toString())))&& !searchResults.getPhotos().contains(y) ) {

											 searchResults.getPhotos().add(y);
											 break;
										 }
									 }
									 
									 
								 }
							 }
						 }
						 
						 if(secondOptions.getSelectionModel().getSelectedItem().toString().equals("AND")) {
							 searchResults = new Album("Search Results");
							 
							 for(Album x : user.getAlbums()) {
								 for(Photo y : x.getPhotos()) {
									 Boolean a = false;
									 Boolean b = false;
									 for(Tag z : y.getTags()) {
										 if(z.getValue().equals(tag1Value.getText()) && z.getTagType().equals(firstOptions.getSelectionModel().getSelectedItem().toString())) 
											 a = true;
										 if(z.getValue().equals(tag2Value.getText()) && z.getTagType().equals(secondOptions.getSelectionModel().getSelectedItem().toString()))

											 b = true;
										 
										 if(a && b && !searchResults.getPhotos().contains(y)) {
											 searchResults.getPhotos().add(y);
											 break;
										 }
										 
									 }
									 
									 
								 }
							 }
							 
						 }
					 }
				 }
			 }
			 if(searchResults.getPhotos().isEmpty()) {
				 Manager.showAlert("Error", "No resluts were found", "There were no results found with these parameters");
				 return;
			 }
			 try {		
  				FXMLLoader loader = new FXMLLoader();   
  				loader.setLocation(getClass().getResource("/views/ImageView.fxml"));
  				
  				AnchorPane root = (AnchorPane)loader.load();
  				ImageViewController listController = loader.getController();
  				listController.start(a, searchResults, user, true);  				
  				a.close();
  				Scene scene = new Scene(root);
  				a.setScene(scene);
  				a.show();
  				
  			} catch(Exception e) {
				Manager.showAlert("Error", "Unexpected error", "An unexpected error occured while trying to display the results of your search");
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
