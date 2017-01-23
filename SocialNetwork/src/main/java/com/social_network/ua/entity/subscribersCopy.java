package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 22.01.2017.
 */
@Entity
public class subscribersCopy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long user_id;
    @Column
    private long subscriber_id;

    public subscribersCopy(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(long subscriber_id) {
        this.subscriber_id = subscriber_id;
    }
}
