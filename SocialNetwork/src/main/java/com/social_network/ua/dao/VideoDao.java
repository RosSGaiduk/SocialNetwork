package com.social_network.ua.dao;

import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.Video;

import java.util.List;

/**
 * Created by Rostyslav on 28.03.2017.
 */
public interface VideoDao {
    void add(Video video);
    void edit(Video video);
    void delete(Video video);
    Video findOne(long id);
    List<Video> findAll();
    List<Video> findAllByUser(User user);
    List<Video> findAllByCommunity(Community community);
    List<Video> findAllVideosPublishedByUser(User user);
    List<Video> selectAllVideosWithTheSameUrlPhoto(String url);
}
