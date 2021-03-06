package com.social_network.ua.dao;

import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
public interface ImageDao {
    void add(User_Images image);
    void edit(User_Images image);
    void delete(User_Images image);
    User_Images findOne(long id);
    List<User_Images> findAllByAlbum(Album album);
    List<User_Images> findAllByUser(User user);
    User_Images getPreviousImageFromMainAlbum(long userId,long id);
    User_Images getNextImageFromMainAlbum(long userId,long id);
    User_Images getPreviousImageFromAlbum(Album album,long idImage);
    User_Images findByPath(String path);
    User_Images findOneByUserIdAndName(User user,String urlImage);
    List<User_Images> fundAll();
}
