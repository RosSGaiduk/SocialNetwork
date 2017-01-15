package com.social_network.ua.services;

import com.social_network.ua.entity.Album;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rostyslav on 12.01.2017.
 */
public interface AlbumService {
    void add(Album album);
    void edit(long id);
    void edit(Album album);
    void delete(Album album);
    Album findOne(long id);
    Album findOneByNameAndUserId(String nameAlbum,long userId);
    List<Album> findAll();
}