package com.eitan.couponsproject.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.dao.ICompanyDao;
import com.eitan.couponsproject.enums.ErrorType;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.utils.DateUtils;
import com.eitan.couponsproject.utils.StringValidation;

@Controller
public class CompanyController {

	@Autowired
	private ICompanyDao companyDao;

	public void createCompany(Company company) throws ApplicationException {

		if (isCompanyExistByName(company.getName())) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() 
					+ "Error in companyController.createCompany(), company's name already exists.");
		}

		validateCompany(company);

		companyDao.save(company);
	}

	public void updateCompany(Company company) throws ApplicationException {

		Company originCompany = getCompanyById(company.getId());
		String companyOriginName = originCompany.getName();

		if (!company.getName().equalsIgnoreCase(companyOriginName)) {
			if (isCompanyExistByName(company.getName())) {
				throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() 
						+ "Error in companyController.updateCompany(), company name already exists.");			}
		}

		validateCompany(company);

		companyDao.save(company);
	}

	public Company getCompanyById(long companyId) throws ApplicationException {

		Company company = companyDao.findById(companyId).get();
		return company;
	}

	public void deleteCompany(long companyId) throws ApplicationException {

		Company company = getCompanyById(companyId);
		companyDao.delete(company);
	}

	public List<Company> getAllCompanies() throws ApplicationException {

		List<Company> companies = (List<Company>) companyDao.findAll();
		return companies;
	}

	private boolean isCompanyExistByName(String companyName) throws ApplicationException {

		return companyDao.isExistsByName(companyName);
	}

	private void validateCompany(Company company) throws ApplicationException {

		if (company.getName() == null) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, DateUtils.getCurrentDateAndTime()
					+ "Error in companyController.validateCompany() " + company + "Null name.");
		}

		if  (company.getName().isEmpty()){
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, DateUtils.getCurrentDateAndTime()
					+ "Error in companyController.validateCompany() " + company + "An empty name.");
		}

		if (company.getAddress() == null) {
			throw new ApplicationException(ErrorType.MUST_ENTER_ADDRESS, DateUtils.getCurrentDateAndTime()
					+ "Error in companyController.validateCompany() " + company + "The company address is Null.");		
		}

		if (company.getAddress().isEmpty()) {
			throw new ApplicationException(ErrorType.MUST_ENTER_ADDRESS, DateUtils.getCurrentDateAndTime()
					+ "Error in companyController.validateCompany() " + company + "The company address is empty.");		
		}

		if (!StringValidation.isEmailAddressValid(company.getEmail())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL, DateUtils.getCurrentDateAndTime()
					+ "Error in StringValidation.isEmailAddressValid(), InValid Email.");		
		}
	}

}

