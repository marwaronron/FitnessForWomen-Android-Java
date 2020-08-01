package com.sim.fitwoman.model;

public class MFitness {
    int id;
    String Name;
    String BMI_category;
    String Description;
    String Steps;
    String met;
    String Image;
    String Video;
    String mistakes;

    public MFitness() {
    }

    public MFitness(String name, String description, String image) {
        Name = name;
        Description = description;
        Image = image;
    }

    public MFitness(String name, String description, String image, String video ,String mistakes) {
        Name = name;
        Description = description;

        Image = image;
        Video = video;
        this.mistakes = mistakes;
    }
    public MFitness(String name, String description, String image, String mistakes) {
        Name = name;
        Description = description;
      //  Steps = steps;
        Image = image;
        this.mistakes = mistakes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBMI_category() {
        return BMI_category;
    }

    public void setBMI_category(String BMI_category) {
        this.BMI_category = BMI_category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSteps() {
        return Steps;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public String getMet() {
        return met;
    }

    public void setMet(String met) {
        this.met = met;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getMistakes() {
        return mistakes;
    }

    public void setMistakes(String mistakes) {
        this.mistakes = mistakes;
    }
}
