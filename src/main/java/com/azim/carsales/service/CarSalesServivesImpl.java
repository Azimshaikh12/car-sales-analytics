package com.azim.carsales.service;

import com.azim.carsales.dto.MonthlyCountDto;
import com.azim.carsales.dto.UploadSalesResponse;
import com.azim.carsales.dto.YearlyCountDto;
import com.azim.carsales.entity.CarSales;
import com.azim.carsales.repositery.CarSalesRepositery;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CarSalesServivesImpl implements CarSalesServives{
    private final CarSalesRepositery repositery;

    public CarSalesServivesImpl(CarSalesRepositery repositery) {
        this.repositery = repositery;
    }

    @Override
    public UploadSalesResponse updateCsv(MultipartFile file) {
        int totalCount=0;
        int failCount=0;
        List<CarSales> cars = new ArrayList<>();
        try( BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(),StandardCharsets.UTF_8))){

//            InputStream inputStream = file.getInputStream(); //raw Bytes
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //read all the data line by line
            //CSVFormat
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true) //to skip the value of the header
                    .setIgnoreHeaderCase(true) //case sentive hata raha hai getter aur setter ke liye
                    .setTrim(true) //remove all the blank spaces
                    .build(); //build
            CSVParser parser = CSVParser.parse(reader,csvFormat);

            for (CSVRecord record:parser){
                totalCount++;
                try {
                    String carNumber = record.get("car Number");
                    boolean exits = repositery.existsByCarNumber(carNumber);
                    if (exits) {
                        failCount++;
                        System.out.println("Duplicate Data" + carNumber);
                        continue;
                    }
                    CarSales carSales = new CarSales();

                    carSales.setCarNumber(record.get("Car Number"));
                    carSales.setCarName(record.get("Brand") + " " + record.get("Model")); // Optional

                    carSales.setBrand(record.get("Brand"));
                    carSales.setModel(record.get("Model"));
                    carSales.setColor(record.get("Color"));
                    carSales.setYear(Integer.parseInt(record.get("Year")));

                    carSales.setDateOfPurchase(
                            LocalDate.parse(
                                    record.get("Date of Purchase"),
                                    DateTimeFormatter.ofPattern("dd-MM-yyyy")
                            )
                    );

                    carSales.setTimeOfPurchase(
                            LocalTime.parse(record.get("Time of Purchase"))
                    );

                    carSales.setPrice(
                            Long.parseLong(record.get("Price (Rs)"))
                    );

                    carSales.setMileage(
                            Double.parseDouble(record.get("Mileage (km/l)"))
                    );

                    carSales.setEngine(
                            Integer.parseInt(record.get("Engine (cc)"))
                    );

                    carSales.setFuelType(record.get("Fuel Type"));
                    carSales.setPaymentMod(record.get("Payment Mode"));
                    carSales.setState(record.get("State"));
                    carSales.setCity(record.get("City"));
                    carSales.setCustomerName(record.get("Customer Name"));
                    carSales.setContactNumber(record.get("Contact Number"));
                    carSales.setEmail(record.get("Email"));

                    carSales.setWarrantyPeriod(
                            Integer.parseInt(record.get("Warranty Period (years)"))
                    );
                    cars.add(carSales);
                }catch (Exception e){
                    failCount++;
                    System.out.println("Fail to process:"+record.getRecordNumber());
                }
            }
            if (!cars.isEmpty()){
                repositery.saveAll(cars);
            }
        }catch (Exception exception){
           throw new RuntimeException("Unable to parse csv:"+exception.getMessage());
        }
        int successCount = totalCount-failCount;
        return new UploadSalesResponse(totalCount,successCount,failCount);
    }

    @Override
    public List<YearlyCountDto> getYearlyCarCount() {
        return repositery.getYearlyCount();
    }

    @Override
    public List<CarSales> allCarDetails() {
        return repositery.findAll();
    }

    @Override
    public List<MonthlyCountDto> getMonthlyCountByYear(int year) {
        List<MonthlyCountDto> data = repositery.getMonthlyCountByYear(year);
        Map<Integer,Long> map = data.stream().collect(
                Collectors.toMap(
                        MonthlyCountDto::month,
                        MonthlyCountDto::count
                )
        );
        List<MonthlyCountDto> result = new ArrayList<>();
        for (int i=1;i<=12;i++){
            result.add(new MonthlyCountDto(
                    i, map.getOrDefault(i,0L)
            ));
        }
        return result;
    }

}
