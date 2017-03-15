package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
@Entity
public class User_Images implements Comparable<User_Images>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String urlOfImage;
    @Column
    private Date dateOfImage;

    @OneToMany(mappedBy = "userImage",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Album album;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "userImage")
    private List<LLike> likes = new ArrayList<>();

    @Column
    private int countLikes = 0;

    public User_Images(){}
    public User_Images(String urlOfImage,Date date) {
        this.urlOfImage = urlOfImage;
        dateOfImage = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlOfImage() {
        return urlOfImage;
    }

    public void setUrlOfImage(String urlOfImage) {
        this.urlOfImage = urlOfImage;
    }

    public Date getDateOfImage() {
        return dateOfImage;
    }

    public void setDateOfImage(Date dateOfImage) {
        this.dateOfImage = dateOfImage;
    }

    @Override
    public int compareTo(User_Images o) {
        int compare = this.dateOfImage.compareTo(o.dateOfImage);
        if (compare == 0){
            compare = this.urlOfImage.compareTo(o.urlOfImage);
            if (compare==0){
                compare = (int)(this.id - o.getId());
            }
        }
        return compare;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<LLike> getLikes() {
        return likes;
    }

    public void setLikes(List<LLike> likes) {
        this.likes = likes;
    }

    public int getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(int countLikes) {
        this.countLikes = countLikes;
    }
}
