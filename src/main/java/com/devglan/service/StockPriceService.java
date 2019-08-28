package com.devglan.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import com.devglan.model.StockPrice;

public interface StockPriceService {
	public List<Integer> getStockPrice(@Param("companyCode") int companyCode,@Param("from_date") Date from_date,@Param("to_date") Date to_date);
    public StockPrice insertPrice(StockPrice price);
    public void deleteprice(int id);
    public List<StockPrice> findall();
   
}
