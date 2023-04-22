package com.example.journey.JourneyApp.Dashboard;

import com.example.journey.JourneyApp.Profile.Models.UserModel;

public class CommentsModel {
    private String commentId;
    private String commenterName;
    private String commentContent;
    private String commentDate;


    public CommentsModel(String commentId,String commentDate,
                          String commenterName,String commentContent) {
        this.commentId = commentId;
        this.commentDate = commentDate;
        this.commenterName= commenterName;
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }
    public String getCommenterName(){
        return commenterName;
    }
    public String getCommentDate(){
        return commentDate;
    }
    public String getCommentContent(){
        return commentContent;
    }
}
