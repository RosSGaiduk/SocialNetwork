package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 28.03.2017.
 */
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String url;
    @Column
    private Date date;
    @Column
    private String urlImageBanner;
    @Column
    private double widthPhoto;
    @Column
    private double heightPhoto;
    @Column
    private double ratio;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Video",joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Community_Video",joinColumns = @JoinColumn(name = "video_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    private List<Community> communities = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "video")
    private List<Comment> comments = new ArrayList<>();

    public Video(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUrlImageBanner() {
        return urlImageBanner;
    }

    public void setUrlImageBanner(String urlImageBanner) {
        this.urlImageBanner = urlImageBanner;
    }

    public double getWidthPhoto() {
        return widthPhoto;
    }

    public void setWidthPhoto(double widthPhoto) {
        this.widthPhoto = widthPhoto;
    }

    public double getHeightPhoto() {
        return heightPhoto;
    }

    public void setHeightPhoto(double heightPhoto) {
        this.heightPhoto = heightPhoto;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public void setRatio(){
        this.ratio = widthPhoto/heightPhoto;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
