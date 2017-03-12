package com.social_network.ua.dao;

import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.Record;
import java.util.List;

/**
 * Created by Rostyslav on 26.11.2016.
 */
public interface RecordDao {
    void add(Record record);
    void edit(Record record);
    void delete(Record record);
    Record findOne(long id);
    List<Record> findAll();
    List<Record> findAllByCommunity(Community community);
    List<Record> findAllInTheWallOf(long id);
}