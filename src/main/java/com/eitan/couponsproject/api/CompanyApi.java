package com.eitan.couponsproject.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.logic.CompanyController;

@RestController

@RequestMapping("/companies")
public class CompanyApi {

	@Autowired
	private CompanyController companyController;

	@PostMapping
	public void createCompany(@RequestBody Company company) throws ApplicationException{
		companyController.createCompany(company);
	}

	@PutMapping
	public void updateCompany(@RequestBody Company company) throws ApplicationException{
		companyController.updateCompany(company);
	}

	@GetMapping
	@RequestMapping("/getCompany/{companyId}")
	public Company getCompanyById(@PathVariable("companyId") long companyId) throws ApplicationException{
		return companyController.getCompanyById(companyId);
	}

	@DeleteMapping
	@RequestMapping("/{companyId}")
	public void deleteCompany(@PathVariable("companyId") long companyId) throws ApplicationException{
		companyController.deleteCompany(companyId);
	}

	@GetMapping
	public List<Company> getAllCompanies() throws ApplicationException{
		return companyController.getAllCompanies();
	}


}
