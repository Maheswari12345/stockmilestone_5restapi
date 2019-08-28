package com.devglan.dao;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devglan.model.*;


public interface SectorsDao extends JpaRepository<Sectors, Integer>{

	public Sectors findBysectorName(String sectorName);

}
