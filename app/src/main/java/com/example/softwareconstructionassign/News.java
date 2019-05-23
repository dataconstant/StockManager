/************
 * Project: Software Construction
 * Author: Mansoor Ali Halari
 */
package com.example.softwareconstructionassign;

public class News {

    //variables of news class
    private String sNewsText;
    private String sNewURL;
    private String sNewImageURL;

    /***
     * Contructor of news class
     * @param text
     * @param URL
     * @param imageURL
     */
    public News(String text, String URL, String imageURL ) {
        sNewsText = text;
        sNewURL = URL;
        sNewImageURL = imageURL;
    }

    /***
     * Get news
     * @return
     */
    public String getNewsText() {
        return sNewsText;
    }

    /***
     * Get URL
     * @return
     */
    public String getNewsURL() {
        return sNewURL;
    }

    /***
     * Get image URL
     * @return
     */
    public String getNewImageURL() {return sNewImageURL;}
}
