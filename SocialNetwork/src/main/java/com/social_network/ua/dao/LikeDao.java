package com.social_network.ua.dao;

import com.social_network.ua.entity.*;

import java.util.List;

/**
 * Created by Rostyslav on 15.03.2017.
 */
public interface LikeDao {
    void add(LLike like);
    void delete(LLike like);
    void edit(LLike like);
    LLike findOne(long id);
    LLike findOneByUserAndImage(User userFrom,User_Images image);
    /*List<LLike> findAllByUserImage(User_Images user_images);
    List<LLike> findAllByRecord(Record record);
    List<LLike> findAllByComment(Comment comment);*/
    List<LLike> findAll();
    LLike findOneByVideoAndUser(Video video,User user);
}
