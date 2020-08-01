package com.sim.fitwoman.model;

public class MealHistoric {
    int id;
    String day;
    String type;

    String totalCalories;

    public MealHistoric(int id,String day, String type,  String totalCalories) {
        this.id = id;
        this.day = day;
        this.type = type;
       this.totalCalories = totalCalories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(String totalCalories) {
        this.totalCalories = totalCalories;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public MealHistoric(int id, String day,String type, String totalCalories, int idUser) {
        this.id = id;
        this.day = day;
        this.type = type;



        this.totalCalories = totalCalories;
        this.idUser = idUser;
    }

    public MealHistoric() {
    }

    int idUser;
}
