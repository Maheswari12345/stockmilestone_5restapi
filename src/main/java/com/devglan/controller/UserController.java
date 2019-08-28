package com.devglan.controller;

import com.devglan.dao.CompanyDao;
import com.devglan.dao.IpoDao;
import com.devglan.dao.SectorsDao;
import com.devglan.dao.stockpriceDao;
import com.devglan.model.ApiResponse;
import com.devglan.model.Company;
import com.devglan.model.Ipo_planned;
import com.devglan.model.Sectors;
import com.devglan.model.StockExchange;
import com.devglan.model.StockPrice;
import com.devglan.model.User;
import com.devglan.model.UserDto;
import com.devglan.service.CompanyService;
import com.devglan.service.IpoService;
import com.devglan.service.SectorService;
import com.devglan.service.StockPriceService;
import com.devglan.service.UserService;
import com.devglan.service.stockexchangeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    CompanyDao companydao;
    @Autowired
    SectorsDao sectorsDao;
    @Autowired
    IpoDao ipodao;
    @Autowired
    stockpriceDao stockpricedao;
    @Autowired
    private CompanyService cservice;
    @Autowired
    SectorService sectorservice;
    @Autowired
    StockPriceService spservice;
    @Autowired
    IpoService iposervice;
    @Autowired
    stockexchangeService seservice;

    
   
    @GetMapping("/sectors/price/{name}/{from_date}/{to_date}")
    public ApiResponse getsectorpriceList(@PathVariable("name") String name,@PathVariable("from_date") Date from_date,@PathVariable("to_date") Date to_date)
    {
    	System.out.println("hii");
    	Sectors sectors=sectorservice.findBysectorName(name);
    	List<Integer> stockprice=new ArrayList<Integer>();
    	List<Company> companies =new ArrayList<>(); 
        cservice.findBysectorId(sectors.getId()).forEach(companies::add);
    	int[] companyCode=new int[10];
    	int total=0;
    	for(int i=0,k=0;i<companies.size();i++,k++)
    	{
    	 companyCode[k]=companies.get(i).getCompanyCode();
    	  spservice.getStockPrice(companyCode[k], from_date, to_date).forEach(stockprice::add);
    	}
    	for(int j=0;j<stockprice.size();j++)
    	{
    		total=total+stockprice.get(j);
    	}
    	System.out.println(stockprice);
    	//return stockprice;
    	return new ApiResponse<>(HttpStatus.OK.value(), "PRICE FETCHED SUCCESSFULLY.",total);
    	
    }
    @GetMapping("/company/price/{name}/{from_date}/{to_date}")
    public ApiResponse<List<Integer>> getStockPriceList(@PathVariable("name") String name,@PathVariable("from_date") Date from_date,@PathVariable("to_date") Date to_date)
    {
    	Company companies=cservice.findBycompanyName(name);

    	return new ApiResponse<>(HttpStatus.OK.value(), "price list fetched successfully",spservice.getStockPrice(companies.getCompanyCode(), from_date, to_date));
    	
    }
   

   
    @GetMapping("/sector/company/{name}")
    public ApiResponse<List<Company>> getCompanyList(@PathVariable("name") String name)
    {
    	Sectors sectors= sectorservice.findBysectorName(name);
    	System.out.print(sectors.getId());
    	return new ApiResponse<>(HttpStatus.OK.value(), "Company_names fetched using sector name successfully.",cservice.findBysectorId(sectors.getId()));
    }

 
    @GetMapping("/company/{name}")
    public ApiResponse<List<Ipo_planned>> getIpoList1(@PathVariable("name") String name)
    {
    	Company companies=cservice.findBycompanyName(name);
    	System.out.println(companies.getCompanyCode());
    	return new ApiResponse<>(HttpStatus.OK.value(), "Company IPO_details fetched using company name successfully.",iposervice.findBycompanyCode(companies.getCompanyCode()));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable int id) {
        userService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully.", null);
    }
    @GetMapping("/company/pattern/{name}")
    public ApiResponse<List<Company>> getCompanyNamematching(@PathVariable("name") String name)
    {
    	return new ApiResponse<>(HttpStatus.OK.value(), "Companies matching pattern",cservice.findBycompanyName1(name));
    }
    @PostMapping("company/insert")
    public ApiResponse insertCompany(@RequestBody Company company)
    {
    	System.out.println(company);
    	return new ApiResponse<>(HttpStatus.OK.value(),"companies inserted successfully",cservice.insertCompany(company));
    }
    @GetMapping("ipo/insert")
    public ApiResponse insertIpo(@RequestBody Ipo_planned ipo)
    {
    	System.out.println(ipo);
    	return new ApiResponse<>(HttpStatus.OK.value(),"ipo details inserted successfully",iposervice.insertIpo(ipo));
    }
    @GetMapping("sector/insert")
    public ApiResponse insertSector(@RequestBody Sectors sec)
    {
    	System.out.println(sec);
    	return new ApiResponse<>(HttpStatus.OK.value(),"sector details inserted successfully",sectorservice.insertSector(sec));
    }
    @GetMapping("stockprice/insert")
    public ApiResponse insertPrice(@RequestBody StockPrice price)
    {
    	System.out.println(price);
    	return new ApiResponse<>(HttpStatus.OK.value(),"stockprice details inserted successfully",spservice.insertPrice(price));
    }
    @GetMapping("stockexchange/insert")
    public ApiResponse insertstockexchange(@RequestBody StockExchange stockex)
    {
    	return new ApiResponse<>(HttpStatus.OK.value(),"stockexchange details inserted successfully",seservice.insertse(stockex));
    }
    @DeleteMapping("/company/delete/{id}")
    public ApiResponse<List<Company>> deletecompany(@PathVariable int id)
    {
    	cservice.delete(id);
    	return new ApiResponse<>(HttpStatus.OK.value(),"company deleted successfully",null);
    }
    @DeleteMapping("/ipo/delete/{id}")
    public ApiResponse<Void> deleteipo(@PathVariable int id)
    {
    	iposervice.deleteIpo(id);
    	return new ApiResponse<>(HttpStatus.OK.value(),"ipo details deleted successfully",null);
    }
    @DeleteMapping("/sector/delete/{id}")
    public ApiResponse<Void> deletesector(@PathVariable int id)
    {
    	sectorservice.deletesector(id);
    	return new ApiResponse<>(HttpStatus.OK.value(),"sector details deleted successfully",null);
    }
    @DeleteMapping("/price/delete/{id}")
    public ApiResponse<Void> deleteprice(@PathVariable int id)
    {
    	spservice.deleteprice(id);
    	return new ApiResponse<>(HttpStatus.OK.value(),"sectorprice  deleted successfully",null);
    }
    @DeleteMapping("/stockexchange/delete/{id}")
    public ApiResponse<Void> deletestockexchange(@PathVariable int id)
    {
    	seservice.deletestockexchange(id);
    	return new ApiResponse<>(HttpStatus.OK.value(),"sectorprice  deleted successfully",null);
    }
    @GetMapping("/company/list")
    public ApiResponse<List<Company>> findAll()
    {
    	return new ApiResponse<>(HttpStatus.OK.value(),"company details fetched successfully",cservice.findAll());
    }
    @GetMapping("/sector/list")
    public ApiResponse<List<Sectors>> findsectorall()
    {
    	return new ApiResponse<>(HttpStatus.OK.value(),"sector list fetched successfully",sectorservice.findall());
    }
    @GetMapping("/ipo/list")
    public ApiResponse<List<Ipo_planned>> findipolist()
    {
    	return new ApiResponse<>(HttpStatus.OK.value(),"ipo details fetched successfully",iposervice.findall());
    }
    @GetMapping("/price/list")
    public ApiResponse<List<StockPrice>> findstockpricelist()
    {
    	return new ApiResponse<>(HttpStatus.OK.value(),"stockprice list fetched successfully",spservice.findall());
    }
    @GetMapping("/stockexchange/list")
    public ApiResponse<List<StockExchange>> fetchstockexchangelist()
    {
    	return new ApiResponse<>(HttpStatus.OK.value(),"stockexchange list fetched successfully",seservice.findall());
    }
    @PutMapping("/company/update/{name}")
    public ApiResponse<Company> update(@RequestBody Company company) {
        return new ApiResponse<>(HttpStatus.OK.value(), "User updated successfully.",cservice.update(company));
    }
    @GetMapping("/signup")
    public ApiResponse <User> saveUser(@RequestBody User user){
    	return new ApiResponse<>(HttpStatus.OK.value(),"stockexchange list fetched successfully", userService.save(user));
    }
    
    
    
}
