package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {
	private Employee employee;
	private int numberOfReports;
	
	public void setEmployee(Employee emp) {
		this.employee = emp;
	}
	
	public void setNumberOfReports(int num) {
		this.numberOfReports = num;
	}
	
	public Employee getEmployee() {
		return this.employee;
	}
	
	public int getnumberOfReports() {
		return this.numberOfReports;
	}
}
