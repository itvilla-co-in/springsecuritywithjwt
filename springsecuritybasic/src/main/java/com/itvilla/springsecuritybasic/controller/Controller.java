package com.itvilla.springsecuritybasic.controller;

import com.itvilla.springsecuritybasic.model.EmailExistException;
import com.itvilla.springsecuritybasic.model.User;
import com.itvilla.springsecuritybasic.model.UserNotFoundException;
import com.itvilla.springsecuritybasic.model.UsernameExistException;
import com.itvilla.springsecuritybasic.servicerepo.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class Controller {

	private UserService userService;

	@Autowired
	public Controller(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/Information")
	public String getNotices(String input) {
		return "Here are the Information details";
	}

	@GetMapping("/contactus")
	public String saveContactInquiryDetails(String input) {
		return "Contact details";
	}

	@GetMapping("/bankAccount")
	public String getAccountDetails(String input) {
		return "Account details";
	}

	@GetMapping("/bankBalance")
	public String getBalanceDetails(String input) {
		return "Balance details";
	}

	@GetMapping("/cardsDetails")
	public String getCardDetails(String input) {
		return "Card details";
	}

	@GetMapping("/bankLoans")
	public String getLoanDetails(String input) {
		return "Loan Details";
	}
/*
	@PostMapping("/register")
	public String register() throws UserNotFoundException, UsernameExistException, EmailExistException {
		System.out.println("coming in register");

		return "registered";

	}
	*/
	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, UsernameExistException, EmailExistException {
		System.out.println("coming in register" + user.getFirstName() + user.getUsername() + user.getEmail() + user.getLastName());

		User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
		return new ResponseEntity<>(newUser, OK);
	}



}
