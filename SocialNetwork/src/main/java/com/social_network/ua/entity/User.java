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
    @Column
    private long newestImageId;
    @Column
    private Boolean isOnline;
    @Column
    private String lastOnline;
    @Transient
    private String confirmPassword;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Community_Subscriber",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id"))
    private List<Community> communities = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private Set<Record> recordsToUser = new TreeSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "userFrom")
    private Set<Record> recordsFromUser = new TreeSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Community> communitiesCreated = new ArrayList<>();

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

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private Set<User_Images> userImages = new TreeSet<>();

    @OneToMany(mappedBy = "userFrom",fetch = FetchType.LAZY)
    private List<Message> messages;

    @OneToMany(mappedBy = "userTo",fetch = FetchType.LAZY)
    private List<Message> messagesUserTo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_music",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "music_id"))
    private List<Music> musics = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Album> albums = new TreeSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<LLike> likes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Video",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id"))
    private List<Video> videos = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "user")
    private List<Video> myVideos = new ArrayList<>();

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

    public Set<Record> getRecordsToUser() {
        return recordsToUser;
    }

    public void setRecordsToUser(Set<Record> recordsToUser) {
        this.recordsToUser = recordsToUser;
    }

    public Set<Record> getRecordsFromUser() {
        return recordsFromUser;
    }

    public void setRecordsFromUser(Set<Record> recordsFromUser) {
        this.recordsFromUser = recordsFromUser;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean online) {
        isOnline = online;
    }

    public String getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(String lastOnline) {
        this.lastOnline = lastOnline;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public List<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(List<Community> communities) {
        this.communities = communities;
    }

    public List<Community> getCommunitiesCreated() {
        return communitiesCreated;
    }

    public void setCommunitiesCreated(List<Community> communitiesCreated) {
        this.communitiesCreated = communitiesCreated;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getNewestImageId() {
        return newestImageId;
    }

    public void setNewestImageId(long newestImageId) {
        this.newestImageId = newestImageId;
    }

    public List<LLike> getLikes() {
        return likes;
    }

    public void setLikes(List<LLike> likes) {
        this.likes = likes;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getMyVideos() {
        return myVideos;
    }

    public void setMyVideos(List<Video> myVideos) {
        this.myVideos = myVideos;
    }
}
