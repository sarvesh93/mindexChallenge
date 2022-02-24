package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private CompensationService compService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }
    
    @PostMapping("/compensation")
    public Compensation createComp(@RequestBody Compensation comp) {
    	
    	Employee emp = comp.employee;
    	if(emp.getEmployeeId()==null || emp.getEmployeeId().replaceAll("\\s", "").equals("")) {
    		String id = UUID.randomUUID().toString();
    		comp.employeeId = id;
    	}else {
    		String id = emp.getEmployeeId();
    		comp.employeeId = id;
    	}
        return compService.create(comp);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }
    
    @GetMapping("/compensation/{id}")
    public Compensation getComp(@PathVariable String id) {

        return compService.read(id);
    }
    
    @GetMapping("/employeeReports/{id}")
    public ReportingStructure getReports(@PathVariable String id) {
    	ReportingStructure rep = new ReportingStructure();
    	try {
    			
        	int totalReports = 0;
        	List<String> empIds = new ArrayList<String>();
        	Employee emp = employeeService.read(id);
        	List<Employee> reports = emp.getDirectReports();
        	if(reports!=null) {
        		totalReports = totalReports + reports.size();
        		for(Employee e:reports) {
            		Queue<Employee> q = new LinkedList<Employee>();
            		q.add(e);
            		
            		while(q.size()>0) {
            			Employee partial = q.poll();
            			Employee full = employeeService.read(partial.getEmployeeId());
            			List<Employee> elist = full.getDirectReports();
            			
            			if(elist!=null) {
            				for(Employee e2:elist) {
            					q.add(e2);
            					if(!empIds.contains(e2.getEmployeeId())) {
            						empIds.add(e2.getEmployeeId());
            					}
            				}
            			}
            			
            		}
            	}
        		
        		totalReports = totalReports + empIds.size();
        		rep.setEmployee(emp);
        		rep.setNumberOfReports(totalReports);
        	}
    	}catch(Exception e) {
    		return null;
    	}
    
    	  	
        return rep;
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }
}
