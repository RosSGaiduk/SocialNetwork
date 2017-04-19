package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String url;
    @Column
    private Date dateOfRecord;
    @Column
    private boolean hasImage;
    @Column
    private String urlUserImagePattern;
    @Column
    private String type;//image,video,audio
    @Column
    private String nameRecord; //бо коли в нас музика або відео, потрібно вказати його назву і вивести її на сторінці,
    //якщо це зображення, тоді nameRecord = ""
    @Column
    private int countLikes = 0;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private User userFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    private Community community;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "record",cascade = CascadeType.REMOVE)
    private List<LLike> likes = new ArrayList<>();

    public Record(){}

    public Record(String text, String url,Date dateOfImage) {
        this.text = text;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String urlImage) {
        this.url = urlImage;
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
        setUrlUserImagePattern(userFrom.getNewestImageSrc());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isHasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    @Override
    public int compareTo(Record o) {
        return this.dateOfRecord.compareTo(o.dateOfRecord);
    }

    public String getUrlUserImagePattern() {
        return urlUserImagePattern;
    }

    public void setUrlUserImagePattern(String urlUserImagePattern) {
        this.urlUserImagePattern = urlUserImagePattern;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameRecord() {
        return nameRecord;
    }

    public void setNameRecord(String nameRecord) {
        this.nameRecord = nameRecord;
    }

    public List<LLike> getLikes() {
        return likes;
    }

    public void setLikes(List<LLike> likes) {
        this.likes = likes;
    }

    public int getCountLikes() {
        return countLikes;
    }

    public void setCountLikes(int countLikes) {
        this.countLikes = countLikes;
    }
}
