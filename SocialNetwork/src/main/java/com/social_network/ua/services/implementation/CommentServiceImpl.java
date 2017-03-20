package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.CommentDao;
import com.social_network.ua.entity.Comment;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rostyslav on 14.03.2017.
 */
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentDao commentDao;

    @Override
    public void add(Comment comment) {
        commentDao.add(comment);
    }

    @Override
    public void edit(Comment comment) {
        commentDao.edit(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Override
    public Comment findOne(long id) {
        return commentDao.findOne(id);
    }

    @Override
    public List<Comment> findAllByImageId(long imageId) {
        return commentDao.findAllByImageId(imageId);
    }

    @Override
    public void updateCommentsNewestImageSrcOfUser(User user) {
        commentDao.updateCommentsNewestImageSrcOfUser(user);
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }
}
