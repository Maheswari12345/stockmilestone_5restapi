package com.devglan.service.impl;

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

import com.devglan.dao.CompanyDao;
import com.devglan.dao.IpoDao;
import com.devglan.dao.UserDao;
import com.devglan.model.Ipo_planned;
import com.devglan.model.User;
import com.devglan.service.IpoService;

@Service(value="IpoService")
public class IpoServiceImpl implements UserDetailsService,IpoService{
	@Autowired
	UserDao userDao;
	@Autowired
	CompanyDao companydao;
	@Autowired
	IpoDao ipodao;
	
	
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
	public List<Ipo_planned> findBycompanyCode(int companyCode) {
	
		return ipodao.findBycompanyCode(companyCode);
	}

	@Override
	public Ipo_planned insertIpo(Ipo_planned ipo) {
		// TODO Auto-generated method stub
		return ipodao.save(ipo);
	}

	@Override
	public void deleteIpo(int id) {
	ipodao.deleteById(id);
		
	}

	@Override
	public List<Ipo_planned> findall() {
	List<Ipo_planned>list=new ArrayList<>();
	ipodao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}
	
}
