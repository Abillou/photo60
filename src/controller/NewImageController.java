package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import model.serialController;
import util.Album;
import util.Manager;
import util.Parser;
import util.Photo;
import util.Tag;
import util.User;
import util.albumReader;
import util.tagReader;

/**
 * Class that acts as the controller for the NewImage.fxml file.
 * @author DiegoCastellanos dac392
 * @author AbidAzad aa2177
 */
public class NewImageController {
	//Labels
    @FXML private ImageView dragged;
    
    //Buttons
    @FXML private Button OK;
    @FXML private Button CANCEL;
    @FXML private Button add;
    @FXML private Button delete;
    
    //Textfield
    @FXML private TextField name;
    @FXML private TextField Caption; //havent done anything with tags yet.
    @FXML private TextField Tags;
    @FXML private TextField newTagType;
    @FXML private TextField newTagValue;
    
    //ComboBox
    @FXML private ChoiceBox options;
    
    //TableView
    @FXML private TableView<tagReader> tagsList;
    @FXML private TableColumn<tagReader, String> tagType;
    @FXML private TableColumn<tagReader, String> value;
    private ObservableList<tagReader> obsList1 = FXCollections.observableArrayList(); 


    
    private ObservableList<String> obsList = FXCollections.observableArrayList(); 
    
	private Stage a;
	private File newPhoto;
	private Album album;
	private User user;
	private boolean nothingDragged = false;
	boolean canceled = false;
	/**
	 * Method that initializes the controller.
	 * @param for the Main Stage window.
	 * @param for the Album that contains all the images.
	 * @author AbidAzad aa2177
	 */	
    public void start(Stage mainStage, Album album, User user) {
    	a = mainStage;
    	this.album = album;
    	this.user = user;
        tagType.setCellValueFactory(new PropertyValueFactory<>("tagType"));
        value.setCellValueFactory(new PropertyValueFactory<>("value"));
        
       
        
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
    	
    	
    	dragged.setOnDragOver(event -> {
            event.acceptTransferModes(TransferMode.MOVE);
        });
    	
        dragged.setOnDragDropped(event -> {
            Dragboard r = event.getDragboard();
            if(event.getDragboard().hasFiles() && !nothingDragged){
            	
                File draggedFile = r.getFiles().get(0);
                String path = draggedFile.toURI().toString();
                boolean isAccepted = draggedFile.getName().toLowerCase().endsWith(".png")
                		||draggedFile.getName().toLowerCase().endsWith(".bmp")
                		||draggedFile.getName().toLowerCase().endsWith(".gif")
                		||draggedFile.getName().toLowerCase().endsWith(".jpeg")
                		||draggedFile.getName().toLowerCase().endsWith(".jpg");
                
                if(isAccepted && !album.containsImage(path)) {
                	Image a = new Image(path);
                	dragged.setImage(a);
                	nothingDragged = true;
                	newPhoto = draggedFile;
                	
                }else {
                	showAlert("ERROR", "Duplicate picture", "Cannot have more than one picture in the same album." );
                }
                
            }
        });
    	
    }
   
    /**
	 * Method that determines whether an image is added. If there is no photo input or a duplicate detected, it will send an alert. Otherwise, it will add the photo to the album.
	 * @param for the ActionEvent for the button click.
	 * @author AbidAzad aa2177
	 * @author DiegoCastellanos
	 */	    
    @FXML void OK(ActionEvent event) {
		if(newPhoto == null) {
			showAlert("WARNING", "No photo inputted", "Please drag a photo in.");
			return;
		}
		
		for(Photo x : album.getPhotos()) {
			if(x.getFilePath().equals(newPhoto.getAbsolutePath())){
				System.out.println(x.getFilePath());
				System.out.println(newPhoto.getAbsolutePath());
				showAlert("WARNING", "Duplicate Photo", "Cancel Operation and Try Again.");
				return;
			}
		}
		
		String title = "Unnamed";
		String caption = "Unnamed";
		ArrayList<Tag> tags = new ArrayList<Tag>();
		
		if(!name.getText().isBlank())
			title = name.getText();
		
		if(!Caption.getText().isBlank())
			caption = Caption.getText();
		
		if(!obsList1.isEmpty()) {
			
			/*tags = Parser.getTags(Tags.getText().trim());
			if(tags == null) {
				showAlert("WARNING", "Invalid tag format", "While attempting to add tags, please use the format <tag-type> = <tag-name> and use ',' for adding multiple tags at once\n\n(i.e. person=John , location = Rutgers)");
			}*/

			for(tagReader x : obsList1) {
				tags.add(new Tag(x.getTagType(), x.getValue()));
			}
		}
		Date date = Manager.getTime();
		
		
		Photo z = new Photo(title, caption, date, tags, newPhoto.toURI().toString());
		
    	//album.addPhoto(title, caption, date, tags, newPhoto.toURI().toString()); //newPhoto.getAbsolutePath()
    	
    	for(Album x : user.getAlbums()) {
			for(Photo y: x.getPhotos()) {
				if(!x.getName().equals(album.getName()) && y.getFilePath().equals(newPhoto.toURI().toString())) {
					 z = y;
					 z.setCaption(caption);
					 z.setTags(tags);
					 z.setTitle(title);
				}
			}
		}
    	album.getPhotos().add(z);
    	a.hide();
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
   	 * Method that adds a tag to a photo. Also keeps track if a user has created a new user type.
   	 * @param for the ActionEvent for the button click.
   	 * @author AbidAzad aa2177
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
   	 * Method that deletes a selected tag to a added photo.
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
    /**
   	 * Void Method that checks if the operation was canceled.
   	 * 
   	 * @author AbidAzad aa2177
   	 */	      

    public boolean isCanceled() {
    	return canceled;
    }
    /**
   	 * Method that returns the album that is trying to perform an add operation.
   	 * 
   	 * @author AbidAzad aa2177
   	 */	    
    public Album getAlbum() {
    	return album;
    }
}
