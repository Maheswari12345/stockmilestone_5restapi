package com.devglan.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devglan.model.*;

public interface IpoDao extends JpaRepository<Ipo_planned , Integer> {

	public List<Ipo_planned> findBycompanyCode(int companyCode);
}
