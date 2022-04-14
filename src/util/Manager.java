package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Manager {
	
	public static void start() {
		
	}

	public static Date getTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		return cal.getTime();
	}
//	public static Date getAlbumTime() {
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, 1);
//		
//	}
	
    public static void showAlert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
		
    }
}
