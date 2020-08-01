package com.sim.fitwoman.model;

public class diet {

    int id;
    String  BMI;
    String Day;
    String Type;
    String image;
    String description;
    String calories;

    public diet() {
    }

    public diet(String day, String type, String image, String calories) {
       this.Day = day;
      this.Type = type;
        this.image = image;

        this.calories = calories;

    }

    public diet(String day, String type, String image, String description, String calories) {
        this.Day = day;
        this.Type = type;
        this.image = image;
        this.description=description;
        this.calories = calories;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public diet(int id, String BMI, String day, String type, String image, String description, String calories) {
        this.id = id;
        this.BMI = BMI;
        Day = day;
        Type = type;
        this.image = image;

        this.description = description;
        this.calories = calories;
    }


}


