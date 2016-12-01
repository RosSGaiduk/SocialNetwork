package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 01.12.2016.
 */
@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String nameOfSong;
    @Column
    private String urlOfSong;


    public Music() {}

    public Music(String nameOfSong, String urlOfSong) {
        this.nameOfSong = nameOfSong;
        this.urlOfSong = urlOfSong;
    }

    public String getNameOfSong() {
        return nameOfSong;
    }

    public void setNameOfSong(String nameOfSong) {
        this.nameOfSong = nameOfSong;
    }

    public String getUrlOfSong() {
        return urlOfSong;
    }

    public void setUrlOfSong(String urlOfSong) {
        this.urlOfSong = urlOfSong;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
