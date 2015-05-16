package services;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	public List<Customer> findByField(String fieldName, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		return null;
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
		List<Product> mostPopular = new ArrayList<Product>();
		int max = 0;
		Map<Integer, List<Product>> products = new HashMap<Integer, List<Product>>();
		for (Customer customer : customers) {
			products.putAll((customer.getBoughtProducts().stream().collect(Collectors.groupingBy(Product::getId))));
		}

		for (Entry<Integer, List<Product>> entry : products.entrySet()) {
		    Integer key = entry.getKey();
		    List<Product> listProducts = entry.getValue();
		    if(listProducts.size() > max) {
		    	max = listProducts.size();
		    	mostPopular.clear();
		    	mostPopular.add(listProducts.get(0));
		    } else if(listProducts.size() == max) {
		    	mostPopular.add(listProducts.get(0));
		    }
		}
		return mostPopular;
	}

	@Override
	public int countBuys(Product p) {
		List<Product> products = new ArrayList<Product>();
		for (Customer customer : customers) {
			products.addAll(customer.getBoughtProducts().stream().filter(pr -> pr.getId() == p.getId()).collect(Collectors.toList()));
		}
		return products.size();
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		return customers.stream().filter(c -> c.getBoughtProducts().contains(p)).toArray().length;
	}

	@Override
	public void test() {
		customers.forEach(c -> c.getBoughtProducts().forEach(p -> System.out.println(c.getId() + " " + c.getName() + " " + p.getDescription())));
	}

}

