package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.CommunityDao;
import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private CommunityDao communityDao;

    @Override
    public void add(Community community) {
        communityDao.add(community);
    }

    @Override
    public void delete(Community community) {
        communityDao.delete(community);
    }

    @Override
    public void edit(Community community) {
        communityDao.edit(community);
    }

    @Override
    public Community findOne(long id) {
        return communityDao.findOne(id);
    }

    @Override
    public User findUserOfCommunity(long idCommunity) {
        return communityDao.findUserOfCommunity(idCommunity);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }
}
