/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jmg.scoutingapp;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

/**
 *
 * @author HP_500GB_SSD
 */
public class Player {
    private final StringProperty name;
    private final DoubleProperty weightText;
    private final StringProperty race;
    private final StringProperty birthPlace;
    private final StringProperty appId;
    private final StringProperty profilePicPath; // New field for the image path
    private final StringProperty dateOfBirth;
    private double[] stats;

    public Player(String name, String race, String birthPlace, String appId, String profilePicPath,String dateofBirth) {
        this.name = new SimpleStringProperty(name);
        this.race = new SimpleStringProperty(race);
        this.birthPlace = new SimpleStringProperty(birthPlace);
        this.appId = new SimpleStringProperty(appId);
        this.weightText = new SimpleDoubleProperty(0.0); // Initialize with a default value
        this.profilePicPath = new SimpleStringProperty(profilePicPath);
        this.dateOfBirth=new SimpleStringProperty(dateofBirth);
    }
    
    public Player(String name, double weightText, String race, String birthPlace, String appId, String profilePicPath,String dateofBirth) {
        this.name = new SimpleStringProperty(name);
        this.weightText = new SimpleDoubleProperty(weightText);
        this.race = new SimpleStringProperty(race);
        this.birthPlace = new SimpleStringProperty(birthPlace);
        this.appId = new SimpleStringProperty(appId);
        this.profilePicPath = new SimpleStringProperty(profilePicPath);
        this.dateOfBirth= new SimpleStringProperty(dateofBirth);
    }

    // Getters for properties
    public StringProperty nameProperty() { return name; }
    public DoubleProperty weightTextProperty() { return weightText; }
    public StringProperty raceProperty() { return race; }
    public StringProperty birthPlaceProperty() { return birthPlace; }
    public StringProperty appIdProperty() { return appId; }
    public StringProperty profilePicPathProperty() { return profilePicPath; }
    public StringProperty dateOfBirthProperty() { return dateOfBirth; }


   
    
    // Getters and setters for the underlying values
    public String getName() { return name.get(); }
    public void setName(String name) { this.name.set(name); }

    public double getWeightText() { return weightText.get(); }
    public void setWeightText(double weightText) { this.weightText.set(weightText); }

    public String getRace() { return race.get(); }
    public void setRace(String race) { this.race.set(race); }

    public String getBirthPlace() { return birthPlace.get(); }
    public void setBirthPlace(String birthPlace) { this.birthPlace.set(birthPlace); }

    public String getAppId() { return appId.get(); }
    public void setAppId(String appId) { this.appId.set(appId); }
    
    public String getProfilePicPath() { return profilePicPath.get(); }
    public void setProfilePicPath(String profilePicPath) { this.profilePicPath.set(profilePicPath); }
    
 
    

    public void setStats(double[] stats) {
        if (stats != null && stats.length == 10) {
            this.stats = stats;
        } else {
            throw new IllegalArgumentException("Stats array must have 10 elements");
        }
    }
}