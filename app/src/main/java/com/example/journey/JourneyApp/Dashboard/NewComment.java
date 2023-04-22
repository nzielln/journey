package com.example.journey.JourneyApp.Dashboard;

public class NewComment {
    private String commentId;
    private String commentUserId;
    private String commentUserName;
    private String commentContent;
    private String commentDate;


    public NewComment(String commentId, String commentUserId,String commentDate,
                      String commentContent) {
        this.commentId = commentId;
        this.commentUserId = commentUserId;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
    }
    public NewComment(String commentId, String commentUserId,String commentDate,
                      String commentUserName, String commentContent) {
        this.commentId = commentId;
        this.commentUserName = commentUserName;
        this.commentUserId = commentUserId;
        this.commentDate = commentDate;
        this.commentContent = commentContent;
    }

    public String getCommentId() {
        return commentId;
    }
    public String getCommentUserId(){return commentUserId;}
    public String getCommentUserName(){return commentUserName;}
    public String getCommentDate(){
        return commentDate;
    }
    public String getCommentContent(){
        return commentContent;
    }
}
