package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 15.03.2017.
 */
@Entity
public class LLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @ManyToOne(fetch = FetchType.EAGER)
    private User_Images userImage;
    @ManyToOne(fetch = FetchType.EAGER)
    private Record record;
    @ManyToOne(fetch = FetchType.EAGER)
    private Comment comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public LLike() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User_Images getUserImage() {
        return userImage;
    }

    public void setUserImage(User_Images userImage) {
        this.userImage = userImage;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
