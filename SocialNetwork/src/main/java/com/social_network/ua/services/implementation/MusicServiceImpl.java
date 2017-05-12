package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.MusicDao;
import com.social_network.ua.entity.Music;
import com.social_network.ua.services.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Rostyslav on 01.12.2016.
 */
@Service
public class MusicServiceImpl implements MusicService {
    @Autowired
    private MusicDao musicDao;

    @Override
    public void add(Music music) {
        musicDao.add(music);
    }

    @Override
    public void edit(Music music) {
        musicDao.edit(music);
    }

    @Override
    public void delete(Music music) {
        musicDao.delete(music);
    }

    @Override
    public Music findOne(long id) {
        return musicDao.findOne(id);
    }

    @Override
    public List<Music> findAllByCommunityId(long communityId,int limit) {
        return musicDao.findAllByCommunityId(communityId,limit);
    }

    @Override
    public List<Music> findAll() {
        return musicDao.findAll();
    }

    @Override
    public List<Music> findAllByUrl(String url) {
        return musicDao.findAllByUrl(url);
    }
}
