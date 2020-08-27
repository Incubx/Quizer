package com.example.quizer.userCabinet;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "User")
public class User {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "nickname")
    private String nickname;

    @DatabaseField(columnName = "rating")
    private int rating;

    @DatabaseField(columnName = "photo")
    private String photoFileName;

    public User() {
    }

    public User(String nickname) {
        this.nickname = nickname;
        this.rating=0;
        this.photoFileName = "User_photo_"+nickname+".jpg";
    }

    public User(String nickname, int rating) {
        this.nickname = nickname;
        this.rating = rating;
        this.photoFileName = "User_photo_"+nickname+".jpg";
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setPhotoFileName(String photoFileName) {
        this.photoFileName = photoFileName;
    }

    public String getPhotoFileName() {
        return photoFileName;
    }
}
