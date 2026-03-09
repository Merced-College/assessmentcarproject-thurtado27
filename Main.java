/*
Name: Trinity Hurtado 
Date: 03/09/26
Project option: A - Search by Brand
Description: Loads car data from a CSV, allows sorting by Brand, searching for all cars with a specific Brand using binary search, and computes basic statistics like average mileage and fuel type counts.
*/ 

import java.io.*;
import java.util.*;

public class Main {

    static ArrayList<Car> cars = new ArrayList<>();
    static ArrayList<Car> working;

    public static void main(String[] args) {

        loadCars("Car_Data.csv");

        working = new ArrayList<>(cars.subList(0, Math.min(2000, cars.size())));

        Scanner input = new Scanner(System.in);

        while(true){

            System.out.println("\nMENU");
            System.out.println("1. Sort by Brand");
            System.out.println("2. Search by Brand");
            System.out.println("3. Show Stats");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            String line = input.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(line);
            } catch(NumberFormatException e){
                System.out.println("Invalid input. Please enter a number 1-4.");
                continue;
            }

            if(choice == 1){
                selectionSortByBrand(working);
                System.out.println("\nFirst 10 cars after sorting by brand:");
                printFirst10();
            }

            else if(choice == 2){
                System.out.print("Enter brand to search: ");
                String brand = input.nextLine();

                // ensure list is sorted before binary search
                selectionSortByBrand(working);

                ArrayList<Car> matches = binarySearchAllBrand(working, brand);

                if(matches.size() > 0){
                    System.out.println("\nFound " + matches.size() + " car(s) with brand \"" + brand + "\":");
                    for(Car c : matches){
                        System.out.println(c);
                    }
                } else {
                    System.out.println("No cars found with brand \"" + brand + "\".");
                }
            }

            else if(choice == 3){
                showStats();
            }

            else if(choice == 4){
                System.out.println("Exiting program.");
                break;
            } else {
                System.out.println("Invalid choice. Please enter 1-4.");
            }
        }

        input.close();
    }

    // Load CSV file into cars list
    public static void loadCars(String filename){

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){

            String line = br.readLine(); // skip header

            while((line = br.readLine()) != null){

                String[] parts = line.split(",");

                if(parts.length != 7)
                    continue; // skip malformed row

                String id = parts[0];
                String brand = parts[1];
                String model = parts[2];
                int year = Integer.parseInt(parts[3]);
                String fuel = parts[4];
                String color = parts[5];
                double mileage = Double.parseDouble(parts[6]);

                cars.add(new Car(id, brand, model, year, fuel, color, mileage));
            }

            System.out.println("Cars loaded: " + cars.size());

        } catch(Exception e){
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    // Selection sort by brand
    public static void selectionSortByBrand(ArrayList<Car> list){

        for(int i = 0; i < list.size() - 1; i++){

            int minIndex = i;

            for(int j = i + 1; j < list.size(); j++){
                if(list.get(j).getBrand().compareToIgnoreCase(list.get(minIndex).getBrand()) < 0){
                    minIndex = j;
                }
            }

            Car temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }

    // Binary search for all cars matching brand
    public static ArrayList<Car> binarySearchAllBrand(ArrayList<Car> list, String brand){
        ArrayList<Car> results = new ArrayList<>();
        int low = 0;
        int high = list.size() - 1;
        int foundIndex = -1;

        // Standard binary search to find one matching index
        while(low <= high){
            int mid = (low + high) / 2;
            int compare = list.get(mid).getBrand().compareToIgnoreCase(brand);

            if(compare == 0){
                foundIndex = mid;
                break;
            }
            else if(compare < 0){
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        if(foundIndex == -1){
            return results; // empty list, no matches
        }

        // Scan left from foundIndex
        int left = foundIndex;
        while(left >= 0 && list.get(left).getBrand().equalsIgnoreCase(brand)){
            left--;
        }
        left++; // move to first match

        // Scan right from foundIndex
        int right = foundIndex;
        while(right < list.size() && list.get(right).getBrand().equalsIgnoreCase(brand)){
            right++;
        }

        // Add all matches to results
        for(int i = left; i < right; i++){
            results.add(list.get(i));
        }

        return results;
    }

    // Print first 10 cars in working list
    public static void printFirst10(){
        for(int i = 0; i < Math.min(10, working.size()); i++){
            System.out.println(working.get(i));
        }
    }

    // Show average mileage and fuel type counts
    public static void showStats(){

        double totalMileage = 0;
        HashMap<String,Integer> fuelCounts = new HashMap<>();

        for(Car c : working){
            totalMileage += c.getMileage();
            String fuel = c.getFuelType();
            fuelCounts.put(fuel, fuelCounts.getOrDefault(fuel, 0) + 1);
        }

        System.out.printf("Average Mileage: %.2f kmpl\n", totalMileage / working.size());
        System.out.println("Fuel Type Counts:");
        for(String fuel : fuelCounts.keySet()){
            System.out.println(fuel + ": " + fuelCounts.get(fuel));
        }
    }
}