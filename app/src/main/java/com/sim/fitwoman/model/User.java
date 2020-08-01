package com.sim.fitwoman.model;

public class User {

    public static User session;

    int id ;
	String name;
    String email;
    String photo;
    String lastWeight;
    String height;
    String age;
    String mesureUnit;
    String imc_bmi;
    String imc_Result;
    String logintype;

    public User() {
    }
    public User(String name) {
        this.name = name;
    }
    public User(int id, String name, String email, String photo, String lastWeight, String height, String age, String mesureUnit, String imc_bmi, String imc_Result, String logintype) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.lastWeight = lastWeight;
        this.height = height;
        this.age = age;
        this.mesureUnit = mesureUnit;
        this.imc_bmi = imc_bmi;
        this.imc_Result = imc_Result;
        this.logintype = logintype;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLastWeight() {
        return lastWeight;
    }

    public void setLastWeight(String lastWeight) {
        this.lastWeight = lastWeight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMesureUnit() {
        return mesureUnit;
    }

    public void setMesureUnit(String mesureUnit) {
        this.mesureUnit = mesureUnit;
    }

    public String getImc_bmi() {
        return imc_bmi;
    }

    public void setImc_bmi(String imc_bmi) {
        this.imc_bmi = imc_bmi;
    }

    public String getImc_Result() {
        return imc_Result;
    }

    public void setImc_Result(String imc_Result) {
        this.imc_Result = imc_Result;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }
}
