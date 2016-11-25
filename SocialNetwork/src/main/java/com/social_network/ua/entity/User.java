package com.social_network.ua.entity;

import javax.persistence.Entity;



import javax.persistence.*;
import java.util.*;

/**
 * Created by Rostyslav on 21.10.2016.
 */
@Entity
public class User implements Comparable<User>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Date birthDate;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String newestImageSrc;
    @Transient
    private String confirmPassword;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="subscribers",
            joinColumns=@JoinColumn(name="subscriber_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> friends = new TreeSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="subscribers",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="subscriber_id")
    )
    private Set<User> subscribers = new TreeSet<>();

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "user")
    private Set<User_Images> userImages = new TreeSet<>();

    @OneToMany(mappedBy = "userFrom",fetch = FetchType.EAGER)
    private List<Message> messages;

    @OneToMany(mappedBy = "userTo",fetch = FetchType.EAGER)
    private List<Message> messagesUserTo;

    public User(){}


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Message> getMessagesUserTo() {
        return messagesUserTo;
    }

    public void setMessagesUserTo(List<Message> messagesUserTo) {
        this.messagesUserTo = messagesUserTo;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> friendOf) {
        this.subscribers = friendOf;
    }

    @Override
    public int compareTo(User o) {
        int compare = this.lastName.compareTo(o.getLastName());
        if (compare == 0){
            compare = this.firstName.compareTo(o.firstName);
            if (compare == 0)
                compare = this.birthDate.compareTo(o.getBirthDate());
                if (compare == 0)
                    compare = this.email.compareTo(o.email);
                    if (compare == 0)
                        compare = this.password.compareTo(o.password);
        }
        return compare;
    }

    public Set<User_Images> getUserImages() {
        return userImages;
    }

    public void setUserImages(Set<User_Images> userImages) {
        this.userImages = userImages;
    }

    public String getNewestImageSrc() {
        return newestImageSrc;
    }

    public void setNewestImageSrc(String newestImageSrc) {
        this.newestImageSrc = newestImageSrc;
    }
}
