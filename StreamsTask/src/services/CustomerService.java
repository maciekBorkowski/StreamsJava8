package services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities.Customer;
import entities.Product;

public class CustomerService implements CustomerServiceInterface {

	private List<Customer> customers;

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}
	
	@Override
	public List<Customer> findByName(String name) {
		return customers.stream().filter(n -> n.getName().equals(name)).collect(Collectors.toList());
	}

	@Override
	public List<Customer> findByField(String fieldName, Object value) throws SecurityException, NoSuchFieldException {
		List<Customer> customer = null;
		

					customer = customers.stream().filter(c -> c.getClass().getField(fieldName) == value).collect(Collectors.toList());
		
		return customer;	
	}

	@Override
	public List<Customer> customersWhoBoughtMoreThan(int number) {
		return customers.stream().filter(n -> n.getBoughtProducts().size() > number).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoSpentMoreThan(double price) {
		return customers.stream().filter(
					n -> n.getBoughtProducts().stream().count() > price
				).collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWithNoOrders() {
		return customers.stream().filter(n -> n.getBoughtProducts().size() == 0).collect(Collectors.toList());
	}

	@Override
	public void addProductToAllCustomers(Product p) {
		customers.stream().forEach(c -> c.addProduct(p));
	}

	@Override
	public double avgOrders(boolean includeEmpty) {

		double sumOrders = customers.stream().mapToDouble(
				c -> c.getBoughtProducts().stream().collect(
						Collectors.summingDouble(Product::getPrice)
						)).sum();
		int customerCount = 10;
		if(!includeEmpty) {
			customerCount = customers.stream().filter(c -> c.getBoughtProducts().size() > 0).collect(Collectors.toList()).size();
		}
		
		return sumOrders/customerCount;
	}

	@Override
	public boolean wasProductBought(Product p) {
		return customers.stream().anyMatch(c -> c.getBoughtProducts().contains(p));
	}

	@Override
	public List<Product> mostPopularProduct() {
		return customers.stream().collect(Collectors.summingInt();
	}

	@Override
	public int countBuys(Product p) {
//		return (int) customers.stream()
//				filter(c -> c.getBoughtProducts().stream().allMatch(pr -> pr.getDescription() == p.getDescription())).;
				return 0;
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		return customers.stream().filter(c -> c.getBoughtProducts().contains(p)).toArray().length;
	}

	@Override
	public void test() {
		customers.forEach(c -> c.getBoughtProducts().forEach(p -> System.out.println(c.getName() + " " + p.getDescription())));
	}

}

