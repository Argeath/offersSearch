/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argeath.offersSearch;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author amino_000
 */
public class Car {
    private int id;
    private String specId;
    private String brand;
    private String model;
    private String fuel;
    private int power;
    private int capacity;
    private int year;
    private int milage;
    private String condition;
    private List<String> _images;
    private String images;
    private int price;
    private String bodyType;
    private String address;
    
    Car() {
        _images = new ArrayList<>();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the specId
     */
    public String getSpecId() {
        return specId;
    }

    /**
     * @param specId the specId to set
     */
    public void setSpecId(String specId) {
        this.specId = specId;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * @return the fuel
     */
    public String getFuel() {
        return fuel;
    }

    /**
     * @param fuel the fuel to set
     */
    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    /**
     * @return the power
     */
    public int getPower() {
        return power;
    }

    /**
     * @param power the power to set
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return the milage
     */
    public int getMilage() {
        return milage;
    }

    /**
     * @param milage the milage to set
     */
    public void setMilage(int milage) {
        this.milage = milage;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return the images
     */
    public String getImages() {
        return images;
    }

    /**
     * @param image the image to add
     */
    public void addImage(String image) {
        this._images.add(image);
        images = String.join(";", _images);
    }
    
    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }
    
    public void insert() {
        Search.cars.add(this);
    }
    
    @Override
    public String toString() {
        return brand + " " + model + " (" + year + ") - poj. " + capacity + " - " + power + "KM - " + fuel + " - " + milage + "km - " + condition + " == " + price;
    }

    /**
     * @return the bodyType
     */
    public String getBodyType() {
        return bodyType;
    }

    /**
     * @param bodyType the bodyType to set
     */
    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
