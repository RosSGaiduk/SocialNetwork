package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.Date;

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

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


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
        }
        return compare;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
