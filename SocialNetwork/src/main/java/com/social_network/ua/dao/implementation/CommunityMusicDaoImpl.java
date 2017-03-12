package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.CommunityMusicDao;
import com.social_network.ua.entity.Community_Music;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by Rostyslav on 12.03.2017.
 */
@Repository
public class CommunityMusicDaoImpl implements CommunityMusicDao{

    @PersistenceContext(name = "Main")
    private EntityManager entityManager;


    @Transactional
    public void add(Community_Music community_music) {
        entityManager.persist(community_music);
    }

    @Transactional
    public void delete(Community_Music community_music) {
        entityManager.remove(entityManager.contains(community_music) ? community_music : entityManager.merge(community_music));
    }

}
