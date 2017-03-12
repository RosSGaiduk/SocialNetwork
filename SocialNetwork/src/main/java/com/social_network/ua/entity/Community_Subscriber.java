package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Entity
public class Community_Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long community_id;
    @Column
    private long subscriber_id;


    public Community_Subscriber(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(long community_id) {
        this.community_id = community_id;
    }

    public long getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(long subscriber_id) {
        this.subscriber_id = subscriber_id;
    }
}
