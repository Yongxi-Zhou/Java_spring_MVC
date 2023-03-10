package com.luv2code.springdemo.dao;

import java.util.List;

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
	public List<Customer> getCustomers() {
		// get current session
		Session curSessionFactory =  sessionFactory.getCurrentSession();
		
		// create a query
		Query<Customer> query = curSessionFactory.createQuery("from Customer order by lastName", Customer.class);
		
		// execute query
		List<Customer> customers = query.getResultList();
		
		// return results
		return customers;
		
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		// get current hibernate session
		Session curSessionFactory =  sessionFactory.getCurrentSession();
		
		// save the customer 
		curSessionFactory.saveOrUpdate(theCustomer);
	}

	@Override
	public Customer getCustomers(int theId) {
		// get current hibernate session
		Session curSessionFactory =  sessionFactory.getCurrentSession();
		
		// create a query
		Customer theCustomer = curSessionFactory.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {
		// get current hibernate session
		Session curSessionFactory =  sessionFactory.getCurrentSession();
		
		// delete 1
//		Customer theCustomer = curSessionFactory.get(Customer.class, theId);
//		
//		curSessionFactory.delete(theCustomer);
		
		// delete 2
		// create a query
		Query<Customer> query = curSessionFactory.createQuery("delete from Customer where id=:customerId");
		
		query.setParameter("customerId", theId);
		
		query.executeUpdate();
		
	}

	@Override
	public List<Customer> getCustomers(String theSearchName) {
		// get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {
            // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
	}

}
