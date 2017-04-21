package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.UserDao;
import com.social_network.ua.entity.*;
import com.social_network.ua.repository.UserRepo;
import com.social_network.ua.services.UserService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void add(String firstName, String lastName, Date birthDate, String email, String password) {
        User user = new User();
        user.setFirstName(firstName); user.setLastName(lastName); user.setBirthDate(birthDate); user.setEmail(email);
        user.setPassword(password);
        userDao.add(user);
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public void delete(long id) {
        userDao.delete(userDao.findOne(id));
    }

    @Override
    public void edit(long id, String firstName, String lastName, Date birthDate, String email, String password) {
        User user = userDao.findOne(id);
        user.setFirstName(firstName); user.setLastName(lastName); user.setBirthDate(birthDate); user.setEmail(email);
        user.setPassword(password);
        userDao.edit(user);
    }

    @Override
    public void edit(User user) {
        userDao.edit(user);
    }

    @Override
    public User findOne(long id) {
        return userDao.findOne(id);
    }

    @Override
    public void addFriendToUser(long id1, long id2) {
        userDao.addFriendToUser(id1,id2);
    }

    @Override
    public void addMusicToUser(long idOfUser, long idOfMusic) {
        userDao.addMusicToUser(idOfUser,idOfMusic);
    }

    @Override
    public List<User> findAllThatArentFriendsOfUserAndArentSubscribersOfUser(Long[] idsOf) {
        return userDao.findAllThatArentFriendsOfUserAndArentSubscribersOfUser(idsOf);
    }

    @Override
    public List<Long> findAllIdsOfSubscribersOfUser(long id) {
        return userDao.findAllIdsOfSubscribersOfUser(id);
    }

    @Override
    public List<User> findAllUsersOfCommunity(Community community,int limit) {
        return userDao.findAllUsersOfCommunity(community,limit);
    }

    @Override
    public List<User> findAllByInput(String str) {
        return userDao.findAllByInput(str);
    }

    @Override
    public User selectUser(long id1, long id2) {
        return userDao.selectUser(id1,id2);
    }

    @Override
    public List<Music> get3LastMusicOfUser(long userId) {
        return userDao.get3LastMusicOfUser(userId);
    }

    @Override
    public User getUserOfMessage(long messageId) {
        return userDao.getUserOfMessage(messageId);
    }

    @Override
    public List<Music> getAllMusicOfUser(long userId) {
        return userDao.getAllMusicOfUser(userId);
    }

    @Override
    public User getUserToOfMessage(long messageId) {
        return userDao.getUserToOfMessage(messageId);
    }

    @Override
    public User getUserOfImage(User_Images user_images) {
        return userDao.getUserOfImage(user_images);
    }

    @Override
    public List<User> selectAllUsersWhoLikedRecord(Record record) {
        return userDao.selectAllUsersWhoLikedRecord(record);
    }

    @Override
    public List<User> selectAllUsersWhoLikedRecordWithLimit(Record record, int limit) {
        return userDao.selectAllUsersWhoLikedRecordWithLimit(record,limit);
    }

    @Override
    public List<User> getAllUsersThatLikedImage(User_Images user_images) {
        return userDao.getAllUsersThatLikedImage(user_images);
    }

    @Override
    public List<User> getAllUsersThatLikedImageWithLimit(User_Images user_images, int limit) {
        return userDao.getAllUsersThatLikedImageWithLimit(user_images,limit);
    }

    @Override
    public boolean findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user;
        try {
            user = userRepo.findByLogin(login);
            user.setIsOnline(true);
            Date lastOnline = new Date(System.currentTimeMillis());
            String timeToDb  = DateFormatUtils.format(lastOnline, "yyyy/MM/dd HH:mm:ss");
            user.setLastOnline(timeToDb);
            edit(user);
            System.out.println(user.getFirstName());
        } catch (NoResultException e){
            System.out.println("No result");
            return  null;
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()),user.getPassword(),authorities);
    }

}