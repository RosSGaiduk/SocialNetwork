package com.social_network.ua.entity;

import javax.persistence.*;

/**
 * Created by Rostyslav on 11.12.2016.
 */
@Entity
public class MessagesUpdator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private long idUserTo;
    @Column
    private long idUserFrom;


    public MessagesUpdator(){}

    public MessagesUpdator(long idUserFrom,long idUserTo) {
        this.idUserTo = idUserTo;
        this.idUserFrom = idUserFrom;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long  getIdUserTo() {
        return idUserTo;
    }

    public void setIdUserTo(int idUserTo) {
        this.idUserTo = idUserTo;
    }

    public long  getIdUserFrom() {
        return idUserFrom;
    }

    public void setIdUserFrom(int idUserFrom) {
        this.idUserFrom = idUserFrom;
    }
}

