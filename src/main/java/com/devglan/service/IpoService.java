package com.devglan.service;

import java.util.List;

import com.devglan.model.Ipo_planned;

public interface IpoService {
	public List<Ipo_planned> findBycompanyCode(int companyCode);
	public Ipo_planned insertIpo(Ipo_planned ipo);
	public void deleteIpo(int id);
	public List<Ipo_planned> findall();
}
