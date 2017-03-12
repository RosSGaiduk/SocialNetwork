package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.MusicDao;
import com.social_network.ua.entity.Music;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 01.12.2016.
 */
@Repository
public class MusicDaoImpl implements MusicDao {

    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Music music) {
        entityManager.persist(music);
    }

    @Transactional
    public void edit(Music music) {
        entityManager.merge(music);
    }

    @Transactional
    public void delete(Music music) {
        entityManager.remove(music);
    }

    @Transactional
    public Music findOne(long id) {
        return entityManager.find(Music.class,id);
    }

    @Transactional
    public List<Music> findAll() {
        return entityManager.createQuery("from Music").getResultList();
    }

    @Transactional
    public List<Music> findAllByCommunityId(long communityId,int limit) {
        List<BigInteger> ids = entityManager.createNativeQuery("SELECT c.music_id from Community_Music c where c.community_id = ?1 ORDER BY c.music_id DESC").setParameter(1,5l).setMaxResults(limit).getResultList();
        List<Music> musics = new ArrayList<>(ids.size());
        for (BigInteger i: ids){
            musics.add(findOne(i.longValue()));
        }
        return musics;
    }
}
