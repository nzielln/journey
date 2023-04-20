package com.example.journey.JourneyApp.Dashboard;

import java.util.ArrayList;
import java.util.HashMap;

public class NewPost {
    String postID;
    String postTitle;
    String authorID;
    String timePosted;
    String postContent;

    HashMap<String,Boolean> liked;

    public NewPost() {}

   // private ArrayList<comments>;
    public NewPost( String postID,String postTitle, String authorID, String timePosted, String postContent,HashMap<String,Boolean> liked){
        this.postID = postID;
        this.postTitle = postTitle;
        this.authorID = authorID;
        this.timePosted = timePosted;
        this.postContent = postContent;
        this.liked = liked;

    }
    public String getPostID(){return postID;}
    public String getPostTitle(){return postTitle;}
    public String getAuthorID(){return authorID;}
    public String getTimePosted(){return timePosted;}
    public String getPostContent(){return postContent;}

    public HashMap<String,Boolean> getLiked() {
        return liked;
    }
}

