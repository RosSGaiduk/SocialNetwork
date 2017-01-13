package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.ImageDao;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
@Service
public class ImageServiceImpl implements ImageService{

    @Autowired
    private ImageDao imageDao;

    @Override
    public void add(User_Images image) {
        imageDao.add(image);
    }

    @Override
    public void add(String urlOfImage, Date dateOfImage) {
        User_Images user_image = new User_Images(urlOfImage,dateOfImage);
        imageDao.add(user_image);
    }

    @Override
    public void delete(long id) {
        imageDao.delete(findOne(id));
    }

    @Override
    public void edit(long id, String urlOfImage, Date dateOfImage) {
        User_Images user_images = findOne(id);
        user_images.setUrlOfImage(urlOfImage); user_images.setDateOfImage(dateOfImage);
        imageDao.edit(user_images);
    }

    @Override
    public void edit(User_Images user_images) {
        imageDao.edit(user_images);
    }

    @Override
    public User_Images findOne(long id) {
        return imageDao.findOne(id);
    }

    @Override
    public List<User_Images> findAll() {
        return  imageDao.fundAll();
    }
}
