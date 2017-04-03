package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.LikeDao;
import com.social_network.ua.entity.LLike;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.entity.Video;
import com.social_network.ua.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rostyslav on 15.03.2017.
 */
@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeDao likeDao;

    @Override
    public void add(LLike like) {
        likeDao.add(like);
    }

    @Override
    public void delete(LLike like) {
        likeDao.delete(like);
    }

    @Override
    public void edit(LLike like) {
        likeDao.edit(like);
    }

    @Override
    public LLike findOne(long id) {
        return likeDao.findOne(id);
    }

    @Override
    public LLike findOneByUserAndImage(User userFrom, User_Images image) {
        return likeDao.findOneByUserAndImage(userFrom,image);
    }

    @Override
    public List<LLike> findAll() {
        return likeDao.findAll();
    }

    @Override
    public LLike findOneByVideoAndUser(Video video, User user) {
        return likeDao.findOneByVideoAndUser(video,user);
    }

}
