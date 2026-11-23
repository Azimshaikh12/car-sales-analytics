package com.azim.carsales.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="car_sales")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CarSales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "car_name")
    private String carName;
    private String carNumber;
    private String brand;
    private String model;
    private String color;
    private int year;

    @Column(name = "date_Of_Purchase")
    private LocalDate dateOfPurchase;

    @Column(name = "time_Of_Purchase")
    private LocalTime timeOfPurchase;
    private Long price;
    private double mileage;
    private int engine;
    private String fuelType;

    @Column(name = "payment_Mod")
    private String paymentMod;
    private String state;
    private String city;
    private String customerName;

    @Column(name = "contact_Number")
    private String contactNumber;
    private String email;

    @Column(name = "warranty_Period")
    private int warrantyPeriod;
}
