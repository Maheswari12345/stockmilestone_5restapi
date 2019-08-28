package com.devglan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devglan.dao.*;
import com.devglan.model.StockExchange;
import com.devglan.model.User;
import com.devglan.service.*;
@Service(value="stockexchangeService")
public class stockExchangeServiceImpl  implements UserDetailsService,stockexchangeService{
	@Autowired
	UserDao userDao;
	@Autowired
	StockExchangeDao sedao;
	
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
	public StockExchange insertse(StockExchange se) {
		// TODO Auto-generated method stub
		return sedao.save(se);
	}

	@Override
	public void deletestockexchange(int id) {
		sedao.deleteById(id);
		
	}

	@Override
	public List<StockExchange> findall() {
	List<StockExchange> list=new ArrayList<>();
	sedao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	

}
