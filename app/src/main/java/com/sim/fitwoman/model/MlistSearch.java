package com.sim.fitwoman.model;

public class MlistSearch {
    int id;
    String name;
    String icon;
    String met;

    public MlistSearch() {
    }

    public MlistSearch(String name, String met) {
        this.name = name;
        this.met = met;
    }

    public MlistSearch(String name, String icon ,String met) {
        this.name = name;
        this.icon = icon ;
        this.met = met;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMet() {
        return met;
    }

    public void setMet(String met) {
        this.met = met;
    }
}
