package com.example.myapplication;

// id : 학번, pw : 인원, userName : 방제, profile : 프로필 사진, detail: 내용

public class User {
    private String profile;
    private String id;
    private int person;
    private String title;
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public User(){}

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User(String id, int person, String title, String detail){
        this.id = id;
        this.person = person;
        this.title = title;
        this.detail = detail;
    }
}