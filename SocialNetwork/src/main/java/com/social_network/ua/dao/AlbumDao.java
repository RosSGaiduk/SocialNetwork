package com.social_network.ua.dao;

import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;

import java.util.List;

/**
 * Created by Rostyslav on 12.01.2017.
 */

public interface AlbumDao {
    void add(Album album);
    void edit(Album album);
    void delete(Album album);
    Album findOne(long id);
    Album findOneByNameAndUserId(String albumName,long userId);
    Album findMainAlbumOfUser(User user);
    Album findOneByImageId(long imageId);
    List<Album> findAllAlbumsByUser(User user);
    List<Album> findAll();
}
