package com.mindex.challenge.service.impl;


import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {
	
	@Autowired
    private CompensationRepository compRepo;

	@Override
	public Compensation create(Compensation comp) {
		
		compRepo.insert(comp);

        return comp;
	}

	@Override
	public Compensation read(String id) {

	        Compensation comp = compRepo.findByEmployeeId(id);

	        if (comp == null) {
	            throw new RuntimeException("Invalid employeeId: " + id);
	        }

	        return comp;
	}

}