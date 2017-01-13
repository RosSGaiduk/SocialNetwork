package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Rostyslav on 12.01.2017.
 */
@Entity
public class Album implements Comparable<Album>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private Date date;
    @OneToMany(mappedBy = "album",fetch = FetchType.EAGER)
    private Set<User_Images> user_images = new TreeSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    public Album(){}
    public Album(String name,Date date){this.name = name; this.date = date;}

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<User_Images> getUser_images() {
        return user_images;
    }

    public void setUser_images(Set<User_Images> user_images) {
        this.user_images = user_images;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Album o) {
        int comp = this.name.compareTo(o.getName());
        if (comp==0){
            comp = this.getUser().compareTo(o.getUser());
        }
        return comp;
    }
}
