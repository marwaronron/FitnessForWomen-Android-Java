package com.sim.fitwoman.model;

public class Ingredient {

  private   int id;
  private   String name;
 private    int quantity;

    public int getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(int idIngredient) {
        this.idIngredient = idIngredient;
    }

    private   int calories;
private     int idMeal;
    private     int idIngredient;

    public Ingredient(String name, int calories) {
        this.name = name;
        this.calories = calories;
    }

    public Ingredient(int id, String name, int quantity, int calories, int idMeal) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.idMeal = idMeal;
    }

    public Ingredient(int id, String name, int quantity, int calories, int idMeal, int idIngredient) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.idMeal = idMeal;
        this.idIngredient = idIngredient;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(int idMeal) {
        this.idMeal = idMeal;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", calories=" + calories +
                ", idMeal=" + idMeal +
                '}';
    }
}
