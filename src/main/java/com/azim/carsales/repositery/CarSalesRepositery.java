package com.azim.carsales.repositery;

import com.azim.carsales.dto.MonthlyCountDto;
import com.azim.carsales.dto.YearlyCountDto;
import com.azim.carsales.entity.CarSales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarSalesRepositery extends JpaRepository<CarSales,Long> {

    boolean existsByCarNumber(String carNumber);

    @Query("""
         Select new com.azim.carsales.dto.YearlyCountDto(c.year,count(c))
         from CarSales c
         Group by c.year
         order by c.year
""")
    List<YearlyCountDto> getYearlyCount();

    @Query("""
      Select new com.azim.carsales.dto.MonthlyCountDto(
      Month(c.dateOfPurchase) ,COUNT(c)
      )
      FROM CarSales c
      WHERE YEAR(c.dateOfPurchase) = :year
      Group BY  Month(c.dateOfPurchase)
      ORDER BY  Month(c.dateOfPurchase)
""")
     List<MonthlyCountDto> getMonthlyCountByYear(@Param("year") int year);
}
