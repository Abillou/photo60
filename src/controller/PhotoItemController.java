package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import util.Photo;

/**
 *	Class Used for setting up a PhotoItem Anchor Pane which will be used as a PhotoListItem in the ImageView screen
 *	@author diegocastellanos 
 */
public class PhotoItemController {
	/**
	 *	Thumbnail container in PhotoItemView.fxml which is displayed far left of the Anchor Pane
	 *	@author diegocastellanos
	 */
    @FXML
    private ImageView img;
    /**
     *	Container used to display the name given to the Photo instance. It is displayed above the caption
     *	@author diegocastellanos 
     */
    @FXML
    private Label title;
	/**
	 *	Container used to display the caption given to the Photo instance. It is displayed bellow the title
	 *	@author diegocastellanos
	 */
    @FXML
    private Label caption;
    
    /**
     *	Holds a reference to the Photo instance for easy access
     *	@author diegocastellanos 
     */
    private Photo photo;
    
    /**
     * 	Given a Photo instance, this method is used for initializing the PhotoItem to be displayed on the ImageView screen.
     *	@param newItem Photo instance used for setting up a PhotoItem
     *	@return void
     *	@author diegocastellanos 
     */
	public void setData(Photo newItem) {
		this.photo = newItem;
		title.setText(photo.getTitle());
		caption.setText(photo.getCaption());
		Image image = new Image(newItem.getFilePath());
		img.setImage(image);
	}

}
