/**
 * Tag is a special string that will be associated with a Photo Object.
 * The format of a Tag is "name:value".
 * 
 * @author Rohan Deshpande
 * @author Saman Sathenjeri
 * @version 1.0
 */
package main.java.photos.models;

import java.io.Serializable;

public class Tag implements Serializable {

    /**
     * name of tag
     */
    String tagName;

    /**
     * data of tag
     */
    String tagData;

    /**
     * Constructor
     * 
     * @param totalTag
     */
    public Tag(String totalTag) {
        String[] arrOfStr = totalTag.split(":", 2);
        this.tagName = arrOfStr[0];
        this.tagData = arrOfStr[1];
    }

    /**
     * Constuctor
     * 
     * @param tagName
     * @param tagData
     */
    public Tag(String tagName, String tagData) {
        this.tagName = tagName;
        this.tagData = tagData;
    }

    /**
     * Getter for Tag Name
     * 
     * @return
     */
    public String getTagName() {
        return this.tagName;
    }

    /**
     * Setter for Tag Name
     * 
     * @param tagName
     * @return
     */
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Getter for Tag Data
     * 
     * @return
     */
    public String getTagData() {
        return this.tagData;
    }

    /**
     * Setter for Tag Data
     * 
     * @param tagData
     * @return
     */
    public void setTagData(String tagData) {
        this.tagData = tagData;
    }

    /**
     * Helper to return tag as string
     * 
     * @return
     */
    public String tagAsString() {
        return this.getTagName() + ":" + this.getTagData();
    }

    /**
     * Tag equality function
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Tag)) {
            return false;
        }
        Tag castO = (Tag) o;
        return castO.getTagData().equalsIgnoreCase(this.getTagData())
                && castO.getTagName().equalsIgnoreCase(this.getTagName());
    }
}