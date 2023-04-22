package com.example.journey.JourneyApp.Dashboard;

import com.example.journey.JourneyApp.Profile.Models.UserModel;
import com.google.firebase.storage.StorageReference;

public class CommentModel {
    private String commentId;
    private String commentContent;
    private String commentDate;

    private StorageReference cardImage;
    private UserModel user;

    public CommentModel(){}

    //
//    public NewComment(String commentId, String commentUserId,String commentDate,
//                      String commentContent) {
//        this.commentId = commentId;
//        this.commentUserId = commentUserId;
//        this.commentDate = commentDate;
//        this.commentContent = commentContent;
//    }
//    public NewComment(String commentId, String commentUserId,String commentDate,
//                      String commentUserName, String commentContent) {
//        this.commentId = commentId;
//        this.commentUserName = commentUserName;
//        this.commentUserId = commentUserId;
//        this.commentDate = commentDate;
//        this.commentContent = commentContent;
//    }
    public CommentModel(UserModel user,String commentId, String commentDate, String commentContent) {
        this.user = user;
        this.commentId = commentId;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
    }

    public UserModel getUser(){return user;}
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
