package com.example.siva.aznews;

/**
 * Created by siva on 16/7/18.
 */

public class techList {
    private String mTitle;
    private String mAuthor;
    private String mDescription;
    private String mImage;
    private String mUrl;
    private String mDate;

    public techList(String title, String author, String description, String image, String url, String date){
        mTitle = title;
        mAuthor = author;
        mDescription = description;
        mImage = image;
        mUrl = url;
        mDate = date;
    }
    public String getmTitle(){return mTitle;}
    public String getmAuthor(){return mAuthor;}
    public String getmDescription(){return mDescription;}
    public String getmImage(){return mImage;}
    public String getmUrl(){return mUrl;}
    public String getmDate(){return mDate;}

}
