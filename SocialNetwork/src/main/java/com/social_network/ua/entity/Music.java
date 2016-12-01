package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name = "user_music",joinColumns = @JoinColumn(name = "music_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
