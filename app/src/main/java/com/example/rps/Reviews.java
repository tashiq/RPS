package com.example.rps;

public class Reviews {
    public Reviews(String reviewer, String review_title, String review_date, String review_description, int review_rating, int review_like, int review_dislike) {
        this.review_date = review_date;
        this.reviewer = reviewer;
        this.review_title = review_title;
        this.review_description = review_description;
        this.review_like = review_like;
        this.review_rating = review_rating;
        this.review_dislike = review_dislike;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReview_title() {
        return review_title;
    }

    public void setReview_title(String review_title) {
        this.review_title = review_title;
    }

    public String getReview_description() {
        return review_description;
    }

    public void setReview_description(String review_description) {
        this.review_description = review_description;
    }

    public int getReview_like() {
        return review_like;
    }

    public void setReview_like(int review_like) {
        this.review_like = review_like;
    }

    public int getReview_rating() {
        return review_rating;
    }

    public void setReview_rating(int review_rating) {
        this.review_rating = review_rating;
    }

    public int getReview_dislike() {
        return review_dislike;
    }

    public void setReview_dislike(int review_dislike) {
        this.review_dislike = review_dislike;
    }

    private String review_date;
    private String reviewer;
    private String review_title;
    private String review_description;
    private int review_like;
    private int review_rating;
    private int review_dislike;
}
