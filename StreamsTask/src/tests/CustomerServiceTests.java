package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import services.CustomerService;
import services.CustomerServiceInterface;
import entities.Customer;
import entities.Product;

public class CustomerServiceTests {
	
	@Test
	public void testFindByName() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByName("Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
		
	}
	
	@Test
	public void testFindByField() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByField("name", "Customer: 1");
		
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
		
	}
	
	@Test
	public void testCustomersWhoBoughtMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.customersWhoBoughtMoreThan(2);
		
		assertNotNull("Result can't be null", res);
		assertEquals(5, res.size());
		
	}
	
	@Test
	public void testcustomersWhoSpentMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.customersWhoBoughtMoreThan(4);
		
		assertNotNull("Result can't be null", res);
		assertEquals(3, res.size());
		
	}
	
	@Test
	public void testAddProductToAllCustomers() {
		List<Customer> customers = DataProducer.createCustomersWithoutProducts(5);
		CustomerServiceInterface cs = new CustomerService(customers);
		
		cs.addProductToAllCustomers(new Product(1, "testowy", 4.5));
		
		customers.forEach(
				c ->  assertEquals(1, c.getBoughtProducts().size())
		);		
	}
	
	@Test
	public void testAvgOrders() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));

		assertEquals(1.68, cs.avgOrders(true), 0.1);
		assertEquals(2.4, cs.avgOrders(false), 0.1);
		
	}
	
	@Test
	public void testCountCustomersWhoBought() {
		List<Customer> customers = DataProducer.createCustomersWithoutProducts(5);
		CustomerServiceInterface cs = new CustomerService(customers);
		
		Product p1 = new Product(1, "testowy", 1.5);
		Product p2 = new Product(2, "testowy2", 2.5);
		
		customers.get(0).addProduct(p1);
		customers.get(1).addProduct(p1);
		customers.get(2).addProduct(p1);
		
		customers.get(3).addProduct(p2);
		customers.get(4).addProduct(p2);
		
		assertEquals(3, cs.countCustomersWhoBought(p1));
		assertEquals(2, cs.countCustomersWhoBought(p2));
	}
	
	@Test
	public void testCountBuys() {
		List<Customer> customers = DataProducer.createCustomersWithoutProducts(5);
		CustomerServiceInterface cs = new CustomerService(customers);
		
		Product p1 = new Product(1, "testowy", 1.5);
		Product p2 = new Product(2, "testowy2", 2.5);
		
		customers.get(0).addProduct(p1);
		customers.get(1).addProduct(p1);
		customers.get(2).addProduct(p1);
		customers.get(2).addProduct(p1);
		
		customers.get(3).addProduct(p2);
		customers.get(4).addProduct(p2);
		
		assertEquals(4, cs.countBuys(p1));
		assertEquals(2, cs.countBuys(p2));
	}
	
	@Test
	public void testTest() {
		List<Customer> customers = DataProducer.createCustomersWithoutProducts(5);
		CustomerServiceInterface cs = new CustomerService(customers);
		
		Product p1 = new Product(1, "testowy", 1.5);
		Product p2 = new Product(2, "testowy2", 2.5);
		
		customers.get(0).addProduct(p1);
		customers.get(1).addProduct(p1);
		customers.get(2).addProduct(p1);
		customers.get(2).addProduct(p1);
		
		customers.get(3).addProduct(p2);
		customers.get(4).addProduct(p2);
		
		cs.test();
	}
	
	@Test
	public void testMostPopularProduct() {
		List<Customer> customers = DataProducer.createCustomersWithoutProducts(5);
		CustomerServiceInterface cs = new CustomerService(customers);
		
		Product p1 = new Product(1, "testowy", 1.5);
		Product p2 = new Product(2, "testowy2", 2.5);
		Product p3 = new Product(3, "testowy3", 3.5);
		
		customers.get(0).addProduct(p1);
		customers.get(1).addProduct(p1);
		customers.get(2).addProduct(p1);
		customers.get(2).addProduct(p1);
		
		customers.get(0).addProduct(p3);
		customers.get(1).addProduct(p3);
		customers.get(2).addProduct(p3);
		customers.get(2).addProduct(p3);
		
		customers.get(3).addProduct(p2);
		customers.get(4).addProduct(p2);
		
		assertEquals(2, cs.mostPopularProduct().size());
	}
	
	
}
