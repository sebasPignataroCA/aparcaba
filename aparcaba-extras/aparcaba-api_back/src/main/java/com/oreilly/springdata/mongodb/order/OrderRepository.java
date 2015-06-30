
package com.oreilly.springdata.mongodb.order;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.oreilly.springdata.mongodb.core.Customer;


public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

	
	List<Order> findByCustomer(Customer customer);
}
