package com.devglan.service;

import java.util.List;

import com.devglan.model.Sectors;

public interface SectorService {
	public Sectors findBysectorName(String sectorName);
	public Sectors insertSector(Sectors sec);
	public void deletesector(int id);
	public List<Sectors> findall();

}
