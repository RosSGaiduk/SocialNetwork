package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 12.03.2017.
 */
@Entity
public class Community_Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long community_id;
    @Column
    private long music_id;

    public Community_Music(){}

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

    public long getMusic_id() {
        return music_id;
    }

    public void setMusic_id(long music_id) {
        this.music_id = music_id;
    }
}
