package com.devglan.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devglan.dao.UserDao;
import com.devglan.dao.stockpriceDao;
import com.devglan.model.StockPrice;
import com.devglan.model.User;
import com.devglan.service.StockPriceService;

@Service(value="StockPriceService")
public class StockPriceServiceImpl implements UserDetailsService,StockPriceService{

	@Autowired
	UserDao userDao;
	@Autowired
	stockpriceDao stockpricedao;
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
	}

	private List<SimpleGrantedAuthority> getAuthority() {
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
	}

	@Override
	public List<Integer> getStockPrice(int companyCode, Date from_date, Date to_date) {
		
		return stockpricedao.getStockPrice(companyCode, from_date, to_date);
	}

	@Override
	public StockPrice insertPrice(StockPrice price) {

		return stockpricedao.save(price);
	}

	@Override
	public void deleteprice(int id) {
		stockpricedao.deleteById(id);
		
	}

	@Override
	public List<StockPrice> findall() {
		List<StockPrice> list=new ArrayList<>();
		stockpricedao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	


}
