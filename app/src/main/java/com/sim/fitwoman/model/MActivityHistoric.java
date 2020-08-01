package com.sim.fitwoman.model;

public class MActivityHistoric {

    int id;
    String name;
    String day;
    String duration;
    String Description;
    String burnedCalories;
    String idUser;

    public MActivityHistoric() {
    }

    public MActivityHistoric(int id, String name, String day, String duration, String burnedCalories) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.duration = duration;

        this.burnedCalories = burnedCalories;
    }


    public MActivityHistoric(int id, String name, String day, String duration, String description, String burnedCalories, String idUser) {
        this.id = id;
        this.name = name;
        this.day = day;
        this.duration = duration;
        Description = description;
        this.burnedCalories = burnedCalories;
        this.idUser = idUser;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getBurnedCalories() {
        return burnedCalories;
    }

    public void setBurnedCalories(String burnedCalories) {
        this.burnedCalories = burnedCalories;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
