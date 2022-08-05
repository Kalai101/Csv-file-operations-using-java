package com.java.Csvdemo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class WriteCsv {
	public static void main(String args[])
			throws ClassNotFoundException, SQLException, FileNotFoundException, IOException, CsvException {
		File file = new File("/home/samcouser/Documents/write.csv");
		try {

			FileWriter outputfile = new FileWriter(file);
			CSVWriter csv = new CSVWriter(outputfile);
			String head[] = { "Name", "Class", "Marks" };
			csv.writeNext(head);
			String[] data1 = { "kalai", "10", "620" };
			csv.writeNext(data1);
			String[] data2 = { "selvan", "10", "600" };
			csv.writeNext(data2);
			csv.close();
			if (csv != null)
				System.out.println("file is already exists:");

		} catch (IOException e) {

			e.printStackTrace();

		}
		Connection con = null;
		Statement stmt = null;

		Class.forName("org.h2.Driver");
		String filename = "/home/samcouser/Documents/write.csv";
		try (CSVReader reader = new CSVReader(new FileReader(filename))) {
			List<String[]> r = reader.readAll();

			r.forEach(x -> System.out.println(Arrays.toString(x)));
		}

		try {
			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/db1", "sa", "");
			stmt = con.createStatement();
			stmt.execute("drop table CSV if exists");
			stmt.execute("CREATE TABLE CSV(NAME VARCHAR(20),CLASS varchar(20),MARKS varchar(20))");
			System.out.println("TABLE IS CREATED");
			stmt.execute(
					"insert into CSV ( NAME,CLASS,MARKS ) select \"NAME\", \"CLASS\", \"MARKS\" from CSVREAD( '/home/samcouser/Documents/write.csv', 'NAME,CLASS,MARKS', null )");
			ResultSet rs = stmt.executeQuery("select * from CSV");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

/*
 * try {
 * 
 * FileWriter outputfile = new FileWriter(file); CSVWriter csv = new
 * CSVWriter(outputfile); String head[]= {"Name", "Class", "Marks" };
 * csv.writeNext(head); String[] data1 = { "kalai", "10", "620" };
 * csv.writeNext(data1); String[] data2 = {"selvan","10","600"};
 * csv.writeNext(data2); csv.close(); if(csv != null)
 * System.out.println("file is already exists:");
 * 
 * 
 * }catch(IOException e) {
 * 
 * e.printStackTrace();
 * 
 * }
 * 
 */
