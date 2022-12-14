package com.masai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.exception.CustomerException;
import com.masai.exception.UserException;
import com.masai.model.Customer;
import com.masai.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo custRepo;

	@Override
	public Customer addCustomer(Customer cust) throws CustomerException, UserException {

		Customer existingCustomer = custRepo.findByEmail(cust.getEmail());
		if (existingCustomer != null)
			throw new CustomerException("Customer Already Registered with Email...!");

		Customer savedCustomer = custRepo.save(cust);

		return savedCustomer;

	}

	@Override
	public Customer removeCustomer(Customer cust) throws CustomerException {
		Optional<Customer> cus = custRepo.findById(cust.getCustomerId());
		if (!cus.isPresent())
			throw new CustomerException("This customer doesn't exist");
		custRepo.delete(cust);
		return cus.get();
	}

	@Override
	public Customer updateCustomer(Customer cust) throws CustomerException {
		Optional<Customer> optional = custRepo.findById(cust.getCustomerId());
		if (!optional.isPresent())
			throw new CustomerException("No customer exists with this information");

		Customer customer = custRepo.save(cust);
		if (customer == null)
			throw new CustomerException("customer not updated");
		return customer;
	}

	@Override
	public List<Customer> ViewAllCustomers(String location) throws CustomerException {

		List<Customer> viewAllCust = custRepo.findAll();
		if (viewAllCust.size() == 0) {
			throw new CustomerException("No Customer are there");
		} else {
			return viewAllCust;
		}

	}

	@Override
	public Customer viewCustomer(Integer customerId) throws CustomerException {
		Optional<Customer> optional = custRepo.findById(customerId);
		if (!optional.isPresent())
			throw new CustomerException("Customer not found");
		return optional.get();
	}

}
