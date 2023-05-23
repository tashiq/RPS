package com.example.rps;

public class Comments {
    private String comment;
    private String commentor;
    private String date;
    private int post_id;
    private int comment_id;

    public Comments(int comment_id,  int post_id, String commentor, String date, String comment) {
        this.comment = comment;
        this.commentor = commentor;
        this.date = date;
        this.post_id = post_id;
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentor() {
        return commentor;
    }

    public String getDate() {
        return date;
    }

    public int getPost_id() {
        return post_id;
    }

    public int getComment_id() {
        return comment_id;
    }
}
