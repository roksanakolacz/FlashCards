package com.example.FlashCards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class FlashCardsApplication {

	public static void main(String[] args) {



		String connectionString = "jdbc:sqlserver://LAPTOP-HGPO10F6:1433;databaseName=flashcards;integratedSecurity=true;encrypt=false;";


		try {
			Connection connection = DriverManager.getConnection(connectionString);
			System.out.println("Connection established");
			connection.close();
		} catch (SQLException e) {
			System.out.println("Error connecting to the database");
			e.printStackTrace();
		}

		Properties props = new Properties();
		props.setProperty("characterEncoding", "UTF-8");


		SpringApplication.run(FlashCardsApplication.class, args);
	}

}
