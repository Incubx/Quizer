package com.example.quizer.userCabinet;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public class User {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "nickname")
    private String nickname;

    @DatabaseField(columnName = "email")
    private String email;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "rating")
    private int rating;

    @DatabaseField(columnName = "photo")
    private String photoFileName;



    public User() {
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
