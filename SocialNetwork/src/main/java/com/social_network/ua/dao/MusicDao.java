package com.social_network.ua.dao;

import com.social_network.ua.entity.Music;
import java.util.List;

/**
 * Created by Rostyslav on 01.12.2016.
 */
public interface MusicDao {
    void add(Music music);
    void edit(Music music);
    void delete(Music music);
    Music findOne(long id);
    List<Music> findAll();
    List<Music> findAllByCommunityId(long communityId,int limit);
    List<Music> findAllByUrl(String url);
}
