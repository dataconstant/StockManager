package com.example.softwareconstructionassign;

public class News {

    private String sNewsText;
    private String sNewURL;
    private String sNewImageURL;

    public News(String text, String URL, String imageURL ) {
        sNewsText = text;
        sNewURL = URL;
        sNewImageURL = imageURL;
    }

    public String getNewsText() {
        return sNewsText;
    }

    public String getNewURL() {
        return sNewURL;
    }
    
    public String getNewImageURL() {return sNewImageURL;}
}
