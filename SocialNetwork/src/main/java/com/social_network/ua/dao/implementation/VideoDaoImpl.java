package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.VideoDao;
import com.social_network.ua.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 28.03.2017.
 */
@Repository
public class VideoDaoImpl implements VideoDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;


    @Transactional
    public void add(Video video) {
        entityManager.persist(video);
    }

    @Transactional
    public void edit(Video video) {
        entityManager.merge(video);
    }

    @Transactional
    public void delete(Video video) {
        entityManager.remove(entityManager.contains(video)? video: entityManager.merge(video));
    }

    @Transactional
    public void deleteVideoFromUser(long video_id, long user_id) {
        entityManager.createNativeQuery("DELETE FROM User_Video where video_id = ?1 and user_id = ?2").setParameter(1,video_id).setParameter(2,user_id).executeUpdate();
    }

    @Transactional
    public Video findOne(long id) {
        return entityManager.find(Video.class,id);
    }

    @Transactional
    public List<Video> findAll() {
        return entityManager.createQuery("from Video").getResultList();
    }

    @Transactional
    public List<Video> findAllByUser(User user) {
        System.out.println("Finding all videos");
        List<Object> videoIds = entityManager.createNativeQuery("SELECT uv.video_id FROM User_Video uv where uv.user_id = ?1 GROUP BY uv.id DESC").setParameter(1,user.getId()).getResultList();
        List<Video> videos = new ArrayList<>(videoIds.size());
        for (int i = 0; i < videoIds.size(); i++){
            BigInteger bigInteger = (BigInteger) videoIds.get(i);
            videos.add(findOne(bigInteger.longValue()));
        }
        System.out.println("Size: "+videos.size());
        return videos;
    }

    @Transactional
    public List<Video> findAllByCommunity(Community community) {
        List<Object> videoIds = entityManager.createNativeQuery("SELECT cv.video_id FROM Community_Video cv where cv.community_id = ?1 GROUP BY cv.id DESC").setParameter(1,community.getId()).getResultList();
        List<Video> videos = new ArrayList<>(videoIds.size());
        for (int i = 0; i < videoIds.size(); i++){
            BigInteger bigInteger = (BigInteger) videoIds.get(i);
            videos.add(findOne(bigInteger.longValue()));
        }
        return videos;
    }

    @Transactional
    public List<Video> findAllVideosPublishedByUser(User user) {
        return entityManager.createQuery("from Video where user = ?1").setParameter(1,user).getResultList();
    }

    @Transactional
    public List<Video> selectAllVideosWithTheSameUrlPhoto(String url) {
        return entityManager.createQuery("from Video where urlImageBanner like ?1").setParameter(1,url).getResultList();
    }

    @Transactional
    public boolean videoBelongsToUser(long video_id, long user_id) {
        try {
           Object o = entityManager.createNativeQuery("select id from User_Video where video_id = ?1 and user_id = ?2").setParameter(1,video_id).setParameter(2,user_id).setMaxResults(1).getSingleResult();
           return true;
        } catch (NoResultException ex){
            return false;
        }
    }

    @Transactional
    public void addVideoToUser(Video video, User user) {
        entityManager.createNativeQuery("INSERT INTO User_Video(video_id,user_id) VALUES (?1,?2)").setParameter(1,video.getId()).setParameter(2,user.getId()).executeUpdate();
    }

    @Transactional
    public Video findLastVideoOfUser(User user) {
        try {
            Object o = entityManager.createNativeQuery("select video_id from User_Video where id = (select max(id) from User_Video where user_id = ?1);").setParameter(1, user.getId()).getSingleResult();
            BigInteger bigInteger = (BigInteger)o;
            System.out.println("Big integer "+bigInteger);
            return findOne(bigInteger.longValue());
        } catch (Exception ex){
        return null;
        }
    }
}
