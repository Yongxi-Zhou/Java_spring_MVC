package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<Customer> getCustomers() {
		// get current session
		Session curSessionFactory =  sessionFactory.getCurrentSession();
		
		// create a query
		Query<Customer> query = curSessionFactory.createQuery("from Customer", Customer.class);
		
		// execute query
		List<Customer> customers = query.getResultList();
		
		// return results
		return customers;
		
	}

}
