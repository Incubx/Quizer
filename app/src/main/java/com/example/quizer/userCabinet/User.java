package com.example.quizer.userCabinet;


public class User {

    private String nickname;

    private String email;

    private String password;

    private int rating;

    private String photoFileName;



    public User() {
    }
    public User(String email,String password) {
        this.email = email;
        this.password= password;
        this.rating=0;
        this.photoFileName = "User_photo_"+nickname+".jpg";
    }

    public User(String nickname,String email,String password) {
        this.nickname = nickname;
        this.email = email;
        this.password= password;
        this.rating=0;
        this.photoFileName = "User_photo_"+nickname+".jpg";
    }


    public String getNickname() {
        return nickname;
    }


    public int getRating() {
        return rating;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }



    public String getPhotoFileName() {
        return photoFileName;
    }

    public void increaseRating(int points){
        rating+=points;
    }
}
