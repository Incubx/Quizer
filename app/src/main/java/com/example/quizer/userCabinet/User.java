package com.example.quizer.userCabinet;


public class User {

    private String nickname;

    private String password;

    private int rating;

    private String photoFileName;



    public User() {
    }

    public User(String nickname,String password) {
        this.nickname = nickname;
        this.password= password;
        this.rating=0;
        this.photoFileName = "User_photo_"+nickname+".jpg";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
