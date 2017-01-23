package com.social_network.ua.services;

import com.social_network.ua.entity.subscribersCopy;

/**
 * Created by Rostyslav on 23.01.2017.
 */
public interface SubscriberService {
    void add(subscribersCopy subscribers);
    void delete(subscribersCopy subscribers);
}
