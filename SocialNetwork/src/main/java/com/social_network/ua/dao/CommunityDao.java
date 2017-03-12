package com.social_network.ua.dao;

import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.User;

import java.util.List;

/**
 * Created by Rostyslav on 06.03.2017.
 */
public interface CommunityDao {
    void add(Community community);
    void delete(Community community);
    void edit(Community community);
    Community findOne(long id);
    User findUserOfCommunity(long idCommunity);
    List<Community>findAll();
}
