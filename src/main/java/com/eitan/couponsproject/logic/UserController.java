package com.eitan.couponsproject.logic;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import com.eitan.couponsproject.entities.Company;
import com.eitan.couponsproject.entities.User;
import com.eitan.couponsproject.cache.ICacheController;
import com.eitan.couponsproject.dao.IUserDao;
import com.eitan.couponsproject.data.LoggedInUserData;
import com.eitan.couponsproject.data.LoginResponseDataObject;
import com.eitan.couponsproject.enums.ErrorType;
import com.eitan.couponsproject.enums.UserType;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.utils.DateUtils;
import com.eitan.couponsproject.utils.StringValidation;

@Controller
public class UserController {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private CompanyController companyController;

	@Autowired
	private ICacheController cachController;

	@Value("${maxFailedLogin}")
	private int maxOfFailedLogin;


	public LoginResponseDataObject login(String userName, String password) throws ApplicationException {

		User user = userDao.findUserByUserNameAndPassword(userName, password);

		if(user == null) {
			throw new ApplicationException(ErrorType.LOGIN_FAILED, "Login failed. Please try again.");
		}

		UserType userType = user.getType();
		Company company = user.getCompany();
		long userId = user.getId();

		LoggedInUserData loggedInUserData; 

		if(company != null) {
			loggedInUserData = new LoggedInUserData(userType, company, userId);
		}else {
			loggedInUserData = new LoggedInUserData(userType, null, userId);
		}

		int token = generateToken(userName, loggedInUserData);
		loggedInUserData.setToken(token);

		String strToken = String.valueOf(token); 
		cachController.put(strToken, loggedInUserData);

		LoginResponseDataObject loginResponse = new LoginResponseDataObject(loggedInUserData.getUserType(), token);

		return loginResponse;
	}

	@PostConstruct
	public void postConstruct() {
		System.out.println("This is the max number of login tries " + maxOfFailedLogin);
	}

	private int generateToken(String userName, LoggedInUserData loggedInUserData) {

		Random rnd = new Random();
		String salt = "#####";
		int token = (userName + rnd.nextInt(9999999) + salt + loggedInUserData.getUserId()).hashCode();

		return token;
	}

	public long createUser(User user) throws ApplicationException {

		if (isUserExistByName(user.getUserName())) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() 
					+ "Error in userController.createUser(), User's name already exists.");
		}

		validateUser(user);

		userDao.save(user);

		return user.getId();
	}

	public List<User> getAllUsersByCompany(@PathVariable("companyId") long companyId) throws ApplicationException{

		Company company = companyController.getCompanyById(companyId);
		List<User> companyUsers = (List<User>) userDao.findAllByCompanyId(company);

		return companyUsers;
	}

	public void updateUser(User user) throws ApplicationException {

		User originUser = getUserById(user.getId());
		String UserOriginUserName = originUser.getUserName();

		if (!user.getUserName().equalsIgnoreCase(UserOriginUserName)) {
			if (isUserExistByName(user.getUserName())) {
				throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() 
						+ "Error in userController.updateUser(), User's name already exists.");
			}
		}

		validateUser(user);

		userDao.save(user);
	}

	public User getUserById(long userId) throws ApplicationException {

		User user = userDao.findById(userId).get();
		return user;
	}

	public void deleteUser (long userId) throws ApplicationException {

		User user = userDao.findById(userId).get();
		userDao.delete(user);
	}

	public boolean isUserExistByName(String userName) throws ApplicationException {
		return userDao.existsByName(userName);
	}

	public List<User> getAllUsers() throws ApplicationException {

		List<User> users = (List<User>) userDao.findAll();
		return 	users;
	}

	private void validateUser(User user) throws ApplicationException {

		if (user == null) {
			throw new ApplicationException(ErrorType.MUST_INSERT_A_VALUE, DateUtils.getCurrentDateAndTime() 
					+ "Error in userController.validateUser(), You must insert details");	
		}

		if (user.getUserName() == null) {
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, DateUtils.getCurrentDateAndTime()
					+ "Error in companyController.validateCompny() " + user + "Null name, you must insert a name.");
		}

		if  (user.getUserName().isEmpty()){
			throw new ApplicationException(ErrorType.MUST_ENTER_NAME, DateUtils.getCurrentDateAndTime()
					+ "Error in companyController.validateCompny() " + user + "An empty name, you must insert a name.");
		}

		if (!StringValidation.isPasswordValid(user.getPassword())) {
			throw new ApplicationException(ErrorType.INVALID_PASSWORD, DateUtils.getCurrentDateAndTime()
					+ "Error in StringValidation.isPasswordValid(), Invalid password.");
		}
	}

}

