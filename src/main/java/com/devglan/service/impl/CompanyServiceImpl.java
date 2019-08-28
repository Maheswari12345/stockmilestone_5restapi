package com.devglan.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.devglan.dao.*;
import com.devglan.model.Company;
import com.devglan.model.User;
import com.devglan.service.CompanyService;

@Service(value="CompanyService")
public class CompanyServiceImpl implements UserDetailsService,CompanyService {
	@Autowired
	UserDao userDao;
	@Autowired
	CompanyDao companydao;
	
	
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
	public List<Company> findBysectorId(int sectorId) {
		
 
		return companydao.findBysectorId(sectorId);
	}

	@Override
	public Company findBycompanyName(String companyName) {
		// TODO Auto-generated method stub
		return companydao.findBycompanyName(companyName);
	}

	@Override
	public List<Company> findBycompanyName1(String name) {
		// TODO Auto-generated method stub
		return companydao.findBycompanyName1(name);
	}


	public UserDetails loadUserByUsername1(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company insertCompany(Company c) {
		Company company=new Company();
		System.out.println(c);
		company.setCeoName(c.getCeoName());
		company.setCompanyName(c.getCompanyName());
		company.setCompanyCode(c.getCompanyCode());
		company.setDirectorsName(c.getDirectorsName());
		company.setBriefWriteUp(c.getBriefWriteUp());
		company.setSectorId(c.getSectorId());
		company.setTurnOver(c.getTurnOver());
		return c;
//		return companydao.save(company);
		
	}

	@Override
	public List<Company> delete(int id) {
		companydao.deleteById(id);
		return null;
		
	}

	@Override
	public List<Company> findAll() {
		List<Company>list=new ArrayList<>();
		companydao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Company update(Company company) {
		companydao.findBycompanyName(company.getCompanyName());
		return companydao.save(company);
		

	}

	
	
	

}
