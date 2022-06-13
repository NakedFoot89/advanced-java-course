/* Thomas Trarbaugh
 * 
 * COP3805C Section 01 Advanced Java Programming (5.5 Weeks) - Online Plus - 2022 Spring Quarter Term 2
 * 
 * Module 03 Course Project - Development Phase: Troubleshooting/Alerts
 * 
 * Instructor: Robert Kumar
 * 
 * 06/01/2022
 * 
 * revision 06/10/2022
 * 
 * test to Git
 * 
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class Main {
	
	//list used to simulate a db
	//the db will be included later
	public static ArrayList<Product> products = new ArrayList<Product>();
	
	//logging class
	static Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) throws SQLException {
		System.out.println("Hello!");
		System.out.println("Thank you for choosing");
		System.out.println("INVENTORY MANAGEMENT PROGRAM");
		System.out.println("-----------------------------");
		char option = 'q';
		Scanner scanner = new Scanner (System.in);
		
		do {
			//get input from user
			
			System.out.println("\nEnter A to add product");
			System.out.println("Enter D to display all products");
			System.out.println("Enter S to display a specific product QTY");
			System.out.println("Enter Q to exit program");
			System.out.println("-----------------------------");
			option = scanner.next().charAt(0);
			
			
			//add product
			if(option == 'A' || option == 'a') {
				System.out.println("To add a product:\nPlease enter a product color");
				String productColor = scanner.next();
				AddProduct(productColor);
				
			}
			
			//display product
			if (option == 'D' || option == 'd') {
				System.out.println("Display all products:"); {
					DisplayProducts();
				}
			}
			
			//display specific product quantity
			if(option == 'S' || option == 's') {
				System.out.println("To display the product QTY:\nPlease enter the product color");{
					String productColor = scanner.next();
					System.out.println(GetProductQty(productColor));
				}
			}
			
			
		}while (option !='q');
		System.out.println("BYE!");
		
		//log managing code
		try {
			LogManager.getLogManager().readConfiguration(new FileInputStream("mylogging.properties"));
		} catch (SecurityException | IOException e1) {
			e1.printStackTrace();
		}
		logger.setLevel(Level.FINE);
		logger.addHandler(new ConsoleHandler());
	}
	
	//add products to database
	public static void AddProduct(String color) throws SQLException {
		Product product = new Product(color);
		//products.add(product); adds to array, fake db
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "Tommy", "Hastetheday1!");
		PreparedStatement sql = conn.prepareStatement("insert into products(color, qty) values(?, ?)");
		sql.setString(1, color);
		sql.setInt(2, 1);
		sql.executeUpdate();
		System.out.println("Success");
	}
	
	//read function
	public static Product GetProduct(String color) {
		for (Product product : products) {
			if (product.getColor().equals(color)) {
				return product;
			}
		}
		return null;
	}
	
	//get quantity 
	public static int GetProductQty(String color) {
		int total = 0;
		for (Product product : products) {
			if (product.getColor().equals(color)) {
				total++;
			}
		}
		return total;
		
	}
	
	//display all products
	public static void DisplayProducts() {
		for (Product product : products) {
			System.out.println(product.getColor());
		}
	}

}
