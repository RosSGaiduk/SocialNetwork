package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 28.03.2017.
 */
@Entity
public class User_Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long user_id;
    @Column
    private long video_id;

    public User_Video() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(long video_id) {
        this.video_id = video_id;
    }
}
