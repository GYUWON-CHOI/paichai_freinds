package com.example.myapplication;

public class Movie {

    private String movieNm;
    private String rank;
    private int imageResId; // 이미지 리소스 ID를 저장할 필드

    public Movie(String movieNm, String rank, int imageResId) {
        this.movieNm = movieNm;
        this.rank = rank;
        this.imageResId = imageResId;
    }

    public String getMovieNm() {
        return movieNm;
    }

    public String getRank() {
        return rank;
    }

    public int getImageResId() {
        return imageResId;
    }
}
