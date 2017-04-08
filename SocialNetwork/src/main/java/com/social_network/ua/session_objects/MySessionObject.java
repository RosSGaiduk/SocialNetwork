package com.social_network.ua.session_objects;

/**
 * Created by Rostyslav on 05.04.2017.
 */
public class MySessionObject {
    private String title;
    private int countChanged = 0;

    public MySessionObject() {}

    public MySessionObject(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCountChanged() {
        return countChanged;
    }

    public void setCountChanged(int countChanged) {
        this.countChanged = countChanged;
    }
}
