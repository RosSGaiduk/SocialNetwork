package com.social_network.ua.services;



import com.social_network.ua.entity.MessagesUpdator;
import java.util.List;

/**
 * Created by Rostyslav on 11.12.2016.
 */
public interface MessagesUpdatorService {
    void add(MessagesUpdator messagesUpdator);
    void delete(long id);
    void edit(MessagesUpdator messagesUpdator);
    boolean findMessageBetweenUsers(long id1,long id2);
    MessagesUpdator findOne(long id);
    MessagesUpdator findOneBy2Ids(long id1, long id2);
    void deleteWhereUserFromLikeId1AndUserToLikeId2(long id1,long id2);
    int findCountByIdUserFromAndIdUserTo(long idUserFrom,long idUserTo);
    List<MessagesUpdator> findAll();
}
