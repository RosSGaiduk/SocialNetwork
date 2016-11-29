package com.social_network.ua.services;

import com.social_network.ua.entity.Record;

import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 26.11.2016.
 */
public interface RecordService {
    void add(Record record);
    void add(String text, String urlOfImage, Date dateOfRecord);
    void edit(long id, String text, String urlOfImage, Date dateOfRecord);
    void edit(Record record);
    void delete(long id);
    Record findOne(long id);
    List<Record> findAll();
    List<Record> findAllInTheWallOf(long id);
}
