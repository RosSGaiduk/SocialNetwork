package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.AlbumDao;
import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Rostyslav on 12.01.2017.
 */
@Service
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao albumDao;

    @Override
    public void add(Album album) {
        albumDao.add(album);
    }

    @Override
    public void edit(long id) {
        albumDao.edit(albumDao.findOne(id));
    }

    @Override
    public void edit(Album album) {
        albumDao.edit(album);
    }

    @Override
    public void delete(Album album) {
        albumDao.delete(album);
    }

    @Override
    public Album findOne(long id) {
        return albumDao.findOne(id);
    }

    @Override
    public Album findOneByNameAndUserId(String nameAlbum,long userId) {
        return albumDao.findOneByNameAndUserId(nameAlbum,userId);
    }

    @Override
    public Album findMainAlbumOfUser(User user) {
        return albumDao.findMainAlbumOfUser(user);
    }

    @Override
    public Album findOneByImageId(long idImage) {
        return albumDao.findOneByImageId(idImage);
    }

    @Override
    public List<Album> findAllAlbumsByUser(User user) {
        return albumDao.findAllAlbumsByUser(user);
    }

    @Override
    public List<Album> findAll() {
        return albumDao.findAll();
    }
}
