package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.VideoDao;
import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.Video;
import com.social_network.ua.services.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rostyslav on 28.03.2017.
 */
@Service
public class VideoServiceImpl implements VideoService{
    @Autowired
    private VideoDao videoDao;

    @Override
    public void add(Video video) {
        videoDao.add(video);
    }

    @Override
    public void edit(Video video) {
        videoDao.edit(video);
    }

    @Override
    public void delete(Video video) {
        videoDao.edit(video);
    }

    @Override
    public void deleteVideoFromUser(long video_id, long user_id) {
        videoDao.deleteVideoFromUser(video_id,user_id);
    }

    @Override
    public boolean videoBelongsToUser(long video_id, long user_id) {
        return videoDao.videoBelongsToUser(video_id,user_id);
    }

    @Override
    public Video findOne(long id) {
        return videoDao.findOne(id);
    }

    @Override
    public List<Video> findAll() {
        return videoDao.findAll();
    }

    @Override
    public List<Video> findAllByUser(User user) {
        return videoDao.findAllByUser(user);
    }

    @Override
    public List<Video> findAllByCommunity(Community community) {
        return videoDao.findAllByCommunity(community);
    }

    @Override
    public List<Video> findAllVideosPublishedByUser(User user) {
        return videoDao.findAllVideosPublishedByUser(user);
    }

    @Override
    public List<Video> selectAllVideosWithTheSameUrlPhoto(String url) {
        return videoDao.selectAllVideosWithTheSameUrlPhoto(url);
    }

    @Override
    public void addVideoToUser(Video video, User user) {
        videoDao.addVideoToUser(video,user);
    }

    @Override
    public Video findLastVideoOfUser(User user) {
        return videoDao.findLastVideoOfUser(user);
    }
}
