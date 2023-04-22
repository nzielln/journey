package com.example.journey.JourneyApp.Dashboard;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.google.firebase.storage.StorageReference;

public class NewComment {

    private String commentUserId;
    private String commentId;
    private String commentContent;
    private String commentDate;

   public NewComment(){}

    public NewComment(String commentId, String commentUserId, String commentDate, String commentContent) {
        this.commentId = commentId;
        this.commentUserId = commentUserId;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
    }

    public String getCommentUserId() {
        return commentUserId;
    }

    public String getCommentId() {
        return commentId;
    }
    public String getCommentDate(){
        return commentDate;
    }
    public String getCommentContent(){
        return commentContent;
    }
}
