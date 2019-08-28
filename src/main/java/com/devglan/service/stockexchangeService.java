package com.devglan.service;
import java.util.List;

import com.devglan.controller.*;
import com.devglan.dao.*;
import com.devglan.model.*;

public interface stockexchangeService {
	public StockExchange insertse(StockExchange se);
	public void deletestockexchange(int id);
	public List<StockExchange> findall();

}
