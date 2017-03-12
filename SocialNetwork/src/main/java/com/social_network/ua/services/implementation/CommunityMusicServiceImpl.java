package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.CommunityMusicDao;
import com.social_network.ua.entity.Community_Music;
import com.social_network.ua.services.CommunityMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rostyslav on 12.03.2017.
 */
@Service
public class CommunityMusicServiceImpl implements CommunityMusicService {

    @Autowired
    private CommunityMusicDao communityMusicDao;


    @Override
    public void add(Community_Music community_music) {
        communityMusicDao.add(community_music);
    }

    @Override
    public void delete(Community_Music community_music) {
        communityMusicDao.delete(community_music);
    }
}
