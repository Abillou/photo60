package util;

import java.io.IOException;

import controller.PhotoItemController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import util.Photo;

/**
 *	Class extends ListCell<Photo> and is used only for setting up the ListView item in ImageView
 *	@author diegocastellanos
 */
public class PhotoListItem extends ListCell<Photo>{
	/**
	 *	Overides the ListCell<Photo> method and is used to define the way in which Photo objects are displayed in the ListView 
	 *	@author diegocastellanos
	 */
	@Override public void updateItem(Photo item, boolean empty) {
		try {
			super.updateItem(item, empty);
			if(item != null && !empty) {
				FXMLLoader fxmlLoader = new FXMLLoader(); 
		    	fxmlLoader.setLocation(getClass().getResource("/views/PhotoItemView.fxml"));
		    	AnchorPane root;
		    	root = fxmlLoader.load();
		    	PhotoItemController itemController = fxmlLoader.getController();
		    	itemController.setData(item);
		    	setGraphic(root);
			}else {
				setGraphic(null);
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}
