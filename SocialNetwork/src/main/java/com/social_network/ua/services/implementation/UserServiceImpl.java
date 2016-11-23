package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.UserDao;
import com.social_network.ua.entity.User;
import com.social_network.ua.repository.UserRepo;
import com.social_network.ua.services.UserService;
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
    public User selectUser(long id1, long id2) {
        return userDao.selectUser(id1,id2);
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
            System.out.println(user.getFirstName());
        } catch (NoResultException e){
            System.out.println("No result");
            return  null;
        }


        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()),user.getPassword(),authorities);
        return user1;
    }

}