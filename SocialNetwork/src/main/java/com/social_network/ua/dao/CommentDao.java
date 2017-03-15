package com.social_network.ua.dao;

import com.social_network.ua.entity.Comment;

import java.util.List;

/**
 * Created by Rostyslav on 14.03.2017.
 */
public interface CommentDao {
    void add(Comment comment);
    void edit(Comment comment);
    void delete(Comment comment);
    Comment findOne(long id);
    List<Comment> findAllByImageId(long imageId);
    List<Comment> findAll();
}
