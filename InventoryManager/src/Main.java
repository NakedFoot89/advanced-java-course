/* Thomas Trarbaugh
 * 
 * COP3805C Section 01 Advanced Java Programming (5.5 Weeks) - Online Plus - 2022 Spring Quarter Term 2
 * 
 * Module 05 Course Project - Development Phase: Include Version Control
 * 
 * Instructor: Robert Kumar
 * 
 * 06/17/2022
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
		System.out.println("\nThis program manages an ");
		System.out.println("inventory of paint cans");
		System.out.println("-----------------------------");
		char option = 'q';
		Scanner scanner = new Scanner (System.in);
		
		do {
			//get input from user
			
			System.out.println("\nEnter A to add product");
			System.out.println("Enter D to display all products");
			System.out.println("Enter S to display a specific product qty");
			System.out.println("Enter R to remove a specific product");
			System.out.println("Enter Q to exit program");
			System.out.println("-----------------------------");
			option = scanner.next().charAt(0);
			
			
			//add product
			if(option == 'A' || option == 'a') {
				System.out.println("To add a product:\nPlease enter a product color");
				String productColor = scanner.next();
				System.out.println("Please enter qty of product");
				int qty = scanner.nextInt();
				AddProduct(productColor, qty);
				
			}
			
			//display product
			if (option == 'D' || option == 'd') {
				System.out.println("Display all products:"); {
					DisplayAllProducts();
				}
			}
			
			//display specific product quantity
			if(option == 'S' || option == 's') {
				System.out.println("To display the product QTY:\nPlease enter the product color");{
					String productColor = scanner.next();
					GetProductQty(productColor);
				}
			}
			
			//delete a product from the database
			if(option == 'R' || option == 'r') {
				System.out.println("To delete a product:\nPlease enter the product color");{
					String productColor = scanner.next();
					DeleteProduct(productColor);
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
	public static void AddProduct(String color, int qty) throws SQLException {
		//Product product = new Product(color);
		//products.add(product); adds to array, fake db
		
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "Tommy", "Hastetheday1!");
		PreparedStatement sql = conn.prepareStatement("insert into products(color, qty) values(?, ?)");
		sql.setString(1, color);
		sql.setInt(2, qty);
		sql.executeUpdate();
		System.out.println("Success");
	}
	
	//get quantity 
	public static void GetProductQty(String color) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "Tommy", "Hastetheday1!");
		Statement sql = conn.createStatement();
		ResultSet rs = sql.executeQuery("select * from inventory.products where color = '" +color+ "'");
		while(rs.next()) {
			System.out.println("ID:" + rs.getString(1) + " Item: " + rs.getString(2) + " QTY: " + rs.getInt(3));
		}
	}
	
	//display all products from database
	public static void DisplayAllProducts() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "Tommy", "Hastetheday1!");
		Statement sql = conn.createStatement();
		ResultSet rs = sql.executeQuery("Select * from products");
		while(rs.next()) {
			System.out.println(rs.getString(1) + " " + rs.getString(2) + " QTY " + rs.getInt(3));
		}
	}
	
	//delete a product from the database
	public static void DeleteProduct(String color) throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "Tommy", "Hastetheday1!");
		Statement sql = conn.createStatement();
		String query1 = "delete from inventory.products " + "where color = '" + color + "'";
		sql.executeUpdate(query1);
		System.out.println("color deleted:");
		}
	}


