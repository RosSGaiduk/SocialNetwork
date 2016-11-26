package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Rostyslav on 26.11.2016.
 */
@Entity
public class Record implements Comparable<Record>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String text;
    @Column
    private String urlImage;
    @Column
    private Date dateOfRecord;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userFrom;

    public Record(){}

    public Record(String text, String urlImage,Date dateOfImage) {
        this.text = text;
        this.urlImage = urlImage;
    }

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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Date dateOfImage) {
        this.dateOfRecord = dateOfImage;
    }

    public User getUser() {
        return user;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int compareTo(Record o) {
        return this.dateOfRecord.compareTo(o.dateOfRecord);
    }
}
