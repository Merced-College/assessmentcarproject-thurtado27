/*
Name: Trinity Hurtado 
Date: 03/09/26
Project option: A - Search by Brand
Description: Loads car data from a CSV, allows sorting by Brand, searching for all cars with a specific Brand using binary search, and computes basic statistics like average mileage and fuel type counts.
*/

public class Car {

    private String carID;
    private String brand;
    private String model;
    private int year;
    private String fuelType;
    private String color;
    private double mileage;

    public Car(String carID, String brand, String model, int year, String fuelType, String color, double mileage) {
        this.carID = carID;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.fuelType = fuelType;
        this.color = color;
        this.mileage = mileage;
    }

    public String getCarID() { return carID; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getFuelType() { return fuelType; }
    public String getColor() { return color; }
    public double getMileage() { return mileage; }

    public String toString() {
        return carID + " | " + brand + " | " + model + " | " + year + " | " +
               fuelType + " | " + color + " | " + mileage;
    }
}


