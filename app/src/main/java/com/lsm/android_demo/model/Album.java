package com.lsm.android_demo.model;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class Album extends LitePalSupport {

    @Column(unique = true, defaultValue = "unknown")
    private String name;

    @Column(ignore = false)
    private float price;

    private byte[] cover;

    private List<Song> songs = new ArrayList<Song>();

    // generated getters and setters.

    public byte[] getCover() {
        return cover;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }


    public float getPrice() {
        return price;
    }
}
