package com.eitan.couponsproject.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eitan.couponsproject.entities.User;
import com.eitan.couponsproject.enums.UserType;
import com.eitan.couponsproject.consts.Constants;
import com.eitan.couponsproject.data.LoggedInUserData;
import com.eitan.couponsproject.data.LoginResponseDataObject;
import com.eitan.couponsproject.data.UserLoginDetailsDataObject;
import com.eitan.couponsproject.exceptions.ApplicationException;
import com.eitan.couponsproject.logic.CompanyController;
import com.eitan.couponsproject.logic.UserController;

@RestController
@RequestMapping("/users")
public class UserApi {

	@Autowired
	private UserController userController;

	@Autowired
	private CompanyController companyController;

	@PostMapping
	@RequestMapping("/login")
	public LoginResponseDataObject login(@RequestBody UserLoginDetailsDataObject userLoginDetailsDataObject) throws ApplicationException{

		String userName = userLoginDetailsDataObject.getUserName();
		String password = userLoginDetailsDataObject.getPassword();

		LoginResponseDataObject loginResponseDataObject = userController.login(userName, password);

		return loginResponseDataObject;
	}

	@PostMapping
	public long createUser(@RequestBody User user) throws ApplicationException{

		return userController.createUser(user);
	}

	@PutMapping
	public void updateUser(HttpServletRequest request ,@RequestBody User user) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
		user.setId(userData.getUserId());

		userController.updateUser(user);
	}

	@GetMapping
	@RequestMapping("/editDetails")
	public UserType gteUserType(HttpServletRequest request) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
		return userData.getUserType();
	}

	@GetMapping
	@RequestMapping("/get")
	public User getUserById(@PathVariable("userId") long userId) throws ApplicationException{
		return userController.getUserById(userId);
	}

	@GetMapping
	@RequestMapping("/userLoginDetails")
	public User getUserLoginDetails(HttpServletRequest request) throws ApplicationException{

		LoggedInUserData userData = (LoggedInUserData) request.getAttribute(Constants.USER_DATA_KEY);
		User user = userController.getUserById(userData.getUserId());

		return user;
	}

	@GetMapping
	@RequestMapping("/getAllUsersByCompany/{companyId}")
	public List<User> getAllUsersByCompany(@PathVariable("companyId") long companyId) throws ApplicationException{
		return userController.getAllUsersByCompany(companyId);
	}

	@DeleteMapping
	@RequestMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") long userId) throws ApplicationException{
		userController.deleteUser(userId);
	}

	@GetMapping
	public List<User> getAllUsers() throws ApplicationException{
		return userController.getAllUsers();
	}
}
