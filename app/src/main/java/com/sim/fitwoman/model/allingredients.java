package com.sim.fitwoman.model;

public class allingredients {
    private   int id;
    private   String name;


    public allingredients(int id, String name, int calories) {
        this.id = id;
        this.name = name;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "allingredients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                '}';
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

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public allingredients(String name, int calories) {
        this.name = name;

        this.calories = calories;
    }

    public allingredients() {
    }

    private   int calories;
}
