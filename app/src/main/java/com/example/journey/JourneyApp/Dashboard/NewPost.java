package com.example.journey.JourneyApp.Dashboard;

import java.util.ArrayList;

public class NewPost {
    String postID;
    String postTitle;
    String authorID;
    String timePosted;
    String postContent;

    public NewPost() {}

   // private ArrayList<comments>;
    public NewPost( String postID,String postTitle, String authorID, String timePosted, String postContent){
        this.postID = postID;
        this.postTitle = postTitle;
        this.authorID = authorID;
        this.timePosted = timePosted;
        this.postContent = postContent;

    }
    public String getPostID(){return postID;}
    public String getPostTitle(){return postTitle;}
    public String getAuthorID(){return authorID;}
    public String getTimePosted(){return timePosted;}
    public String getPostContent(){return postContent;}

}

