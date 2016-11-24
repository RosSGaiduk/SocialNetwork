package com.social_network.ua.services;

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
    User_Images findOne(long id);
    List<User_Images> findAll();
}
