package com.sim.fitwoman.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Meal {
  public   int id;
    public    String type;
    private     Date day;
    public    int totalCalories;
    private   int idUser;


    public Meal(int id, String day, String type, int totalCalories, int idUser ) {

        this.id = id ;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fmt.parse(day);
            this.day = date ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.type = type ;
        this.totalCalories = totalCalories ;
        this.idUser = idUser ;


    }
    public Meal(int id, String day, String type, int totalCalories) {

        this.id = id ;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fmt.parse(day);
            this.day = date ;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.type = type ;
        this.totalCalories = totalCalories ;



    }

    public Meal(int id, Date day, String type, int totalCalories) {
        this.id = id;
        this.day = day;
        this.type = type;
   this.totalCalories = totalCalories;

    }

    public int getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(int totalCalories) {
        this.totalCalories = totalCalories;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public Meal(String type, String day, int idUser) {

        this.idUser = idUser;
        this.type = type ;
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = fmt.parse(day);
            this.day = date ;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }


    public Meal(String type, Date day, int totalCalories) {
        this.type = type;
        this.day = day;

        this.totalCalories = totalCalories;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", day=" + day +
                ", totalCalories=" + totalCalories +
                ", idUser=" + idUser +
                '}';
    }
}
