package com.lsm.android_demo.model;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Song extends LitePalSupport {

    @Column(nullable = false)
    private String name;

    private int duration;

    @Column(ignore = true)
    private String uselessField;

    private Album album;

    // generated getters and setters.
    
    public void setName(String name) {
        this.name = name;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public Album getAlbum() {
        return album;
    }

    public void setUselessField(String uselessField) {
        this.uselessField = uselessField;
    }

    public String getUselessField() {

        return uselessField;
    }
}
