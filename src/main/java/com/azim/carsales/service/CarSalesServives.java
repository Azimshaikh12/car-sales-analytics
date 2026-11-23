package com.azim.carsales.service;

import com.azim.carsales.dto.MonthlyCountDto;
import com.azim.carsales.dto.UploadSalesResponse;
import com.azim.carsales.dto.YearlyCountDto;
import com.azim.carsales.entity.CarSales;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarSalesServives {

    UploadSalesResponse updateCsv(MultipartFile file);
    List<YearlyCountDto> getYearlyCarCount();
    List<CarSales> allCarDetails();
    List<MonthlyCountDto> getMonthlyCountByYear(int year);
}
