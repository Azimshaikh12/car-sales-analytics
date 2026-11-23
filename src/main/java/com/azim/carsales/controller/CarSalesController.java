package com.azim.carsales.controller;

import com.azim.carsales.commons.response.ApiResponse;
import com.azim.carsales.dto.MonthlyCountDto;
import com.azim.carsales.dto.UploadSalesResponse;
import com.azim.carsales.dto.YearlyCountDto;
import com.azim.carsales.entity.CarSales;
import com.azim.carsales.service.CarSalesServives;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/car-sales")
public class CarSalesController {
    private final CarSalesServives carSalesServives;

    public CarSalesController(CarSalesServives carSalesServives) {
        this.carSalesServives = carSalesServives; //Autowire only one methhod
    }
    @GetMapping("/FetchAll-Data")
    public ResponseEntity<ApiResponse<List<CarSales>>> getAllData(){
        List<CarSales> apiAllData = carSalesServives.allCarDetails();
        ApiResponse<List<CarSales>> apiResponse = new ApiResponse<>(
                true,
                "All Present Data",
                apiAllData,
                HttpStatus.OK.value()
        );
        return new ResponseEntity<ApiResponse<List<CarSales>>>(apiResponse,HttpStatus.ACCEPTED);
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<ApiResponse<UploadSalesResponse>> uploadFile(@RequestParam("file")MultipartFile file){

        //file is not available
        if (file.isEmpty()){
           UploadSalesResponse response = new UploadSalesResponse(0,0,0);
            //resp
         ApiResponse<UploadSalesResponse> apiResponse   =new ApiResponse<UploadSalesResponse>(
                    false,
                    "File is Empty",
                       response,
                    HttpStatus.BAD_REQUEST.value()
                    );
            return new ResponseEntity<ApiResponse<UploadSalesResponse>>(apiResponse,HttpStatus.BAD_REQUEST);
        }
        //resp
        UploadSalesResponse response = carSalesServives.updateCsv(file);
        ApiResponse<UploadSalesResponse> apiResponse=getApiresponse(response);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/yearlycount")
    public ResponseEntity< ApiResponse<List<YearlyCountDto>>> getYearlyCount(){
        List<YearlyCountDto> carsCount = carSalesServives.getYearlyCarCount();
        ApiResponse<List<YearlyCountDto>> response= new ApiResponse<>(
                true,
                "Data Read Successfully",
                carsCount,
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
    private ApiResponse<UploadSalesResponse> getApiresponse(UploadSalesResponse response){
        String message;
        boolean suceess;
        if (response.getFailedCount()==0){
            message="All record is inserted Successfully";
            suceess=true;
        } else if (response.getSuccessCount()==0) {
            message="All record got failed to update";
            suceess=false;
        }else {
            message="Uploaded with some errors"+response.getFailedCount()+" rows failed";
            suceess=false;
        }
        return new ApiResponse<>(suceess,message,response,HttpStatus.OK.value());
    }
    @GetMapping("/monthly-count")
    public ResponseEntity<ApiResponse<List<MonthlyCountDto>>> monthlyCount(@RequestParam int year){
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Monthly Data Read Successfully",
                        carSalesServives.getMonthlyCountByYear(year),
                        HttpStatus.OK.value()
                )
        );
    }
}
