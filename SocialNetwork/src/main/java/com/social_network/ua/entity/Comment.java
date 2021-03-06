package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 14.03.2017.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String text;
    @Column
    private long userFromIdPattern;
    @Column
    private String userFromNewestUrlImage;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private User_Images userImage;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "comment")
    private List<LLike> likes = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    public Comment(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getUserFromIdPattern() {
        return userFromIdPattern;
    }

    public void setUserFromIdPattern(long userFromIdPattern) {
        this.userFromIdPattern = userFromIdPattern;
    }

    public String getUserFromNewestUrlImage() {
        return userFromNewestUrlImage;
    }

    public void setUserFromNewestUrlImage(String userFromNewestUrlImage) {
        this.userFromNewestUrlImage = userFromNewestUrlImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User_Images getUserImage() {
        return userImage;
    }

    public void setUserImage(User_Images userImage) {
        this.userImage = userImage;
    }

    public List<LLike> getLikes() {
        return likes;
    }

    public void setLikes(List<LLike> likes) {
        this.likes = likes;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
