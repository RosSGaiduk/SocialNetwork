package com.social_network.ua.dao;

import com.social_network.ua.entity.MessagesUpdator;
import java.util.List;

/**
 * Created by Rostyslav on 11.12.2016.
 */

public interface MessagesUpdatorDao {
    void add(MessagesUpdator messagesUpdator);
    void delete(MessagesUpdator messagesUpdator);
    void edit(MessagesUpdator messagesUpdator);
    MessagesUpdator findOne(long id);
    int findCountByIdUserFromAndIdUserTo(long idUserFrom,long idUserTo);
    MessagesUpdator findOneBy2Ids(long id1, long id2);
    boolean findMessageBetweenUsers(long id1, long id2);
    void deleteWhereUserFromLikeId1AndUserToLikeId2(long id1,long id2);
    List<MessagesUpdator> findAll();
}
