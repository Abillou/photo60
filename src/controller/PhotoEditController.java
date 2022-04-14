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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import util.Tag;
import util.User;
import util.tagReader;

/**
 * Class that acts as the controller for the PhotoEdit.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class PhotoEditController {
	private User user;
	private Stage a;
	public Photo edited;
	private Album album;
	
    @FXML
    private ImageView imageContainer;

    //TextFields
    @FXML
    private TextField newCaptionField;
    @FXML private TextField newTagType;
    @FXML private TextField newTagValue;
    
    //TableView
    @FXML private TableView<tagReader> tagsList;
    @FXML private TableColumn<tagReader, String> tagType;
    @FXML private TableColumn<tagReader, String> value;
    private ObservableList<tagReader> obsList1 = FXCollections.observableArrayList(); 
    private ObservableList<String> obsList = FXCollections.observableArrayList(); 

    
  //ChoiceBox
    @FXML private ChoiceBox options;
    
    //Buttons
    @FXML private Button done;
    @FXML private Button add;
    @FXML private Button delete;

    @FXML
    private Label photoName;
    
    @FXML
    private Label date;

    /**
	 * Method that closes the edit window and updates the information for a photo.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 * @author DiegoCastellanos
	 */	  
    @FXML
    void done(ActionEvent event) {
    	if(newCaptionField.getText().isBlank()) {
    		edited.setCaption("Unnamed");
    	}
    	else {
    		edited.setCaption(newCaptionField.getText());
    	}
    	
    	ArrayList<Tag> tags = edited.getTags();
    	
    	tags.clear();
    	
    	for(tagReader x: obsList1) {
    		tags.add(new Tag(x.getTagType(), x.getValue()));
    	}
    	edited.setTags(tags);
    	
    	for(Photo x: album.getPhotos()) {
    		if(x.getFilePath().equals(edited.getFilePath())) {
    			album.getPhotos().remove(x);
    			album.getPhotos().add(edited);
    			break;
    		}
    	}
    	a.hide();
    

    }
	/**
	 * Method that initializes the controller.
	 * @param for the Main Stage window.
	 * @author AbidAzad aa2177
	 */
    public void start(Stage mainStage, Photo editing, Album album, User user) {
    	a = mainStage;
    	this.user = user;
    	this.edited = editing;
    	this.album = album;
    	imageContainer.setImage(new Image(editing.getFilePath()));
    	photoName.setText(editing.getTitle());
    	newCaptionField.setText(editing.getCaption());
    	date.setText(editing.getDate().toString());
    	tagType.setCellValueFactory(new PropertyValueFactory<>("tagType"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        
    	if(!editing.getTags().isEmpty()) {
    		for(Tag x: editing.getTags()) {
    			obsList1.add(new tagReader(x.getTagType(), x.getValue()));
    		}
    		tagsList.setItems(obsList1);
    	}
    	for(String x : user.getUserCreatedTags()) {
    		obsList.add(x);
    	}
    	
    	obsList.add("custom...");
    	
    	options.setItems(obsList);
    	
    	options.getSelectionModel().selectedItemProperty().addListener(String ->{
    		
    		if(options.getSelectionModel().isEmpty())
    			newTagType.setVisible(false);
    		else {
	    		String x = options.getSelectionModel().getSelectedItem().toString();
	    		if(x.equals("custom...")) {
	    			newTagType.setVisible(true);
	    		}
	    		else {
	    			newTagType.setVisible(false);
	    		}
    		}
    	});    	
    }
    
    /**
	 * Method that gets the album containing the photo being edited.
	 * @param for the Main Stage window.
	 * @author AbidAzad aa2177
	 */
    public Album getAlbum() {
    	return album;
    }
    
    /**
	 * Method that checks if it is valid to add a tag to a photo and then does so. Otherwise, it throws an alert.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 * @author DiegoCastellanos
	 */	  
    @FXML void add(ActionEvent event) {
		if(options.getSelectionModel().isEmpty()) {
			showAlert("Error!", "Tag type not selected.", "Please select a tag type.");
			return;
		}
		
		else {
			if(!options.getSelectionModel().getSelectedItem().toString().equals("custom...")) {
				if(newTagValue.getText().isEmpty()) {
					showAlert("Error!", "No tag Value Inputted", "Please input a valid tag");
					return;
				}
				if(newTagValue.getText().equals("custom...")) {
					showAlert("Error!", "Keyword Inputted", "Please input a valid tag");
					return;
				}
				//REMEMBER TO ADD ONE TO CHECK DUPLICATES IN A BIT
				
				tagReader a = new tagReader(options.getSelectionModel().getSelectedItem().toString(), newTagValue.getText());
				
				for(tagReader x : obsList1) {
					if(x.getTagType().equals(options.getSelectionModel().getSelectedItem().toString()) && x.getValue().equals(newTagValue.getText())){
						showAlert("Error!", "Tag Pair Exists Already!", "Please input a valid tag");
						return;
					}
				}
				obsList1.add(a);
				tagsList.setItems(obsList1);
			}
			else {
				if(newTagValue.getText().isEmpty()|| newTagType.getText().isEmpty()) {
					showAlert("Error!", "No tag Value/Type Inputted", "Please input a valid tag/type");
					return;
				}
				if(newTagValue.getText().equals("custom...") || newTagType.getText().equals("custom...")) {
					showAlert("Error!", "Keyword Inputted", "Please input a valid tag/type");
					return; 
				}
				//REMEMBER TO ADD ONE TO CHECK DUPLICATES IN A BIT
				
				
				tagReader a = new tagReader(newTagType.getText(), newTagValue.getText());
				for(tagReader x : obsList1) {
					if(x.getTagType().equals(newTagType.getText()) && x.getValue().equals(newTagValue.getText())){
						showAlert("Error!", "Tag Pair Exists Already!", "Please input a valid tag");
						return;
					}
				}
				if(!user.getUserCreatedTags().contains(newTagType.getText()))
					user.getUserCreatedTags().add(newTagType.getText());
				
				
				obsList.clear();
				for(String x : user.getUserCreatedTags()) {
		    		obsList.add(x);
		    	}		    	
		    	obsList.add("custom...");		    	
		    	options.setItems(obsList);
		    	
				obsList1.add(a);
				tagsList.setItems(obsList1);
			}
		}
    	
    }
    /**
	 * Method that deletes a tag from a photo.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 */	  
    @FXML void delete(ActionEvent event) {
		if(tagsList.getSelectionModel().isEmpty()) {
			showAlert("Error!", "Tag type not selected.", "Please select a tag type.");
			return;
		}
		
		else {
			obsList1.remove(tagsList.getSelectionModel().getSelectedIndex());
			tagsList.setItems(obsList1);
			
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