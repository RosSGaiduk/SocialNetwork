package com.social_network.ua.services;

import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
public interface ImageService {
    void add(User_Images image);
    void add(String urlOfImage, Date dateOfImage);
    void delete(long id);
    void edit(long id,String urlOfImage,Date dateOfImage);
    void edit(User_Images user_images);
    User_Images findOne(long id);
    List<User_Images> findAllByAlbum(Album album);
    User_Images findByPath(String path);
    List<User_Images> findAllByUser(User user);
    User_Images findOneByUserIdAndName(User user, String urlImage);
    List<User_Images> findAll();
}
