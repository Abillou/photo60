package util;

import javafx.beans.property.SimpleStringProperty;

/**
 *  * Helper Class that translates the data from the user Data. This is done as 
 * Serializable data is not readable, thus, changing Tags would not work.
 * @author AbidAzad aa2177
 */
public class tagReader {
	private SimpleStringProperty tagType;
    private SimpleStringProperty value;
    /**
     * Initializes the class. 
     * @param  the Tag it is translating.
     * @author AbidAzad aa2177
     */
	public tagReader(String tagType, String Value) {
		this.value = new SimpleStringProperty(Value);
		this.tagType = new SimpleStringProperty(tagType);
	}
	
	/**
     * Returns the type of tag. 
     * @author AbidAzad aa2177
     */
    public String getTagType() {
        return tagType.get();
    }
    /**
     * Changes the type of the Tag. 
     * @param  a String value of the new title.
     * @author AbidAzad aa2177
     */
    public void setTagType(String newTag) {
        this.tagType = new SimpleStringProperty(newTag);
    }

    /**
     * Changes the value of a  tag. 
     * @author AbidAzad aa2177
     */
    public String getValue() {
        return value.get();
    }
    /**
     * Changes the title of the album. 
     * @param  a String value of the new title.
     * @author AbidAzad aa2177
     */
    public void setTagValue(String value) {
        this.value = new SimpleStringProperty(value);
    }

}
