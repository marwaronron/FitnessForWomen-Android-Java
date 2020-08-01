package com.sim.fitwoman.model;

public class MBMIHistoric {
    int id;

    String day;
    String weight;
    String BMI;
   String BMI_result;
    String idUser;

    public MBMIHistoric(String day, String weight, String BMI, String BMI_result) {
        this.day = day;
        this.weight = weight;
        this.BMI = BMI;
        this.BMI_result = BMI_result;
    }

    public MBMIHistoric(int id,String day, String weight, String BMI, String BMI_result, String idUser) {
        this.id = id;
        this.day = day;
        this.weight = weight;
        this.BMI = BMI;
        this.BMI_result = BMI_result;
        this.idUser = idUser;
    }

    public MBMIHistoric(String day, String BMI) {
        this.day = day;
        this.BMI = BMI;
    }

    public MBMIHistoric() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getBMI_result() {
        return BMI_result;
    }

    public void setBMI_result(String BMI_result) {
        this.BMI_result = BMI_result;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
