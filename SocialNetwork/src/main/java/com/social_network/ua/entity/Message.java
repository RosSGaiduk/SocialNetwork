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

    public Message(){}

    public Message(User user,User userTo,String text,Date dateOfMessage) {
        this.userFrom = user;
        this.text = text;
        this.userTo = userTo;
        this.dateOfMessage = dateOfMessage;
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
    }

    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
