package com.social_network.ua.services;

import com.social_network.ua.entity.LLike;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;

import java.util.List;

/**
 * Created by Rostyslav on 15.03.2017.
 */
public interface LikeService {
    void add(LLike like);
    void delete(LLike like);
    void edit(LLike like);
    LLike findOne(long id);
    /*List<LLike> findAllByUserImage(User_Images user_images);
    List<LLike> findAllByRecord(Record record);
    List<LLike> findAllByComment(Comment comment);*/
    LLike findOneByUserAndImage(User userFrom, User_Images image);
    List<LLike> findAll();
}
