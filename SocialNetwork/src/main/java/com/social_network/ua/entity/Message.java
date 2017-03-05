package com.social_network.ua.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Rostyslav on 09.11.2016.
 */
@Entity
public class Message implements Comparable<Message>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User userFrom;
    @ManyToOne(fetch = FetchType.LAZY)
    private User userTo;
    @Column
    private String text;
    @Column
    private Date dateOfMessage;
    @Column
    private long userFromIdPattern;
    @Column
    private long userToIdPattern;
    @Column
    private String newestUserFromUrlImagePattern;
    @Column
    private String newestUserToUrlImagePattern;
    @Column
    private String shortText;
    @Column
    private String userFromNameLastNamePattern;
    @Column
    private String userToNameLastNamePattern;

    public Message(){}

    public Message(User user,User userTo,String text,Date dateOfMessage) {
        this.userFrom = user;
        this.text = text;
        this.userTo = userTo;
        this.dateOfMessage = dateOfMessage;
        setUserFromIdPattern(userFrom.getId());
        setUserToIdPattern(userTo.getId());
        setNewestUserFromUrlImagePattern(userFrom.getNewestImageSrc());
        setNewestUserToUrlImagePattern(userTo.getNewestImageSrc());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
        setNewestUserFromUrlImagePattern(userFrom.getNewestImageSrc());
        setUserFromIdPattern(userFrom.getId());
        setUserFromNameLastNamePattern(userFrom.getFirstName()+" "+userFrom.getLastName());
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
        setNewestUserToUrlImagePattern(userTo.getNewestImageSrc());
        setUserToIdPattern(userTo.getId());
        setUserToNameLastNamePattern(userTo.getFirstName()+" "+userTo.getLastName());
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        try{
            shortText = text.substring(0,50);
            if (text.length()>50) shortText+="...";
        } catch (Exception ex){
            shortText = text;
        }
    }

    public Date getDateOfMessage() {
        return dateOfMessage;
    }

    public void setDateOfMessage(Date dateOfMessage) {
        this.dateOfMessage = dateOfMessage;
    }

    @Override
    public int compareTo(Message o) {
        return (int)(this.id-o.id);
    }

    public long getUserFromIdPattern() {
        return userFromIdPattern;
    }

    public void setUserFromIdPattern(long userFromIdPattern) {
        this.userFromIdPattern = userFromIdPattern;
    }

    public long getUserToIdPattern() {
        return userToIdPattern;
    }

    public void setUserToIdPattern(long userToIdPattern) {
        this.userToIdPattern = userToIdPattern;
    }

    public String getNewestUserFromUrlImagePattern() {
        return newestUserFromUrlImagePattern;
    }

    public void setNewestUserFromUrlImagePattern(String newestUserFromUrlImagePattern) {
        this.newestUserFromUrlImagePattern = newestUserFromUrlImagePattern;
    }

    public String getNewestUserToUrlImagePattern() {
        return newestUserToUrlImagePattern;
    }

    public void setNewestUserToUrlImagePattern(String newestUserToUrlImagePattern) {
        this.newestUserToUrlImagePattern = newestUserToUrlImagePattern;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getUserFromNameLastNamePattern() {
        return userFromNameLastNamePattern;
    }

    public void setUserFromNameLastNamePattern(String userFromNameLastNamePattern) {
        this.userFromNameLastNamePattern = userFromNameLastNamePattern;
    }

    public String getUserToNameLastNamePattern() {
        return userToNameLastNamePattern;
    }

    public void setUserToNameLastNamePattern(String userToNameLastNamePattern) {
        this.userToNameLastNamePattern = userToNameLastNamePattern;
    }
}
