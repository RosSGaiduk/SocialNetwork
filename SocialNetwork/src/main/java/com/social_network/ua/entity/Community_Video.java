package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 28.03.2017.
 */
@Entity
public class Community_Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long community_id;
    @Column
    private long video_id;


    public Community_Video(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(long community_id) {
        this.community_id = community_id;
    }

    public long getVideo_id() {
        return video_id;
    }

    public void setVideo_id(long video_id) {
        this.video_id = video_id;
    }
}
