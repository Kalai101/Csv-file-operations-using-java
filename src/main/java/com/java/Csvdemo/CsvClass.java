package com.java.Csvdemo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CsvClass {
	public static void main(String args[]) throws IOException ,SQLException, ClassNotFoundException{

		Connection con = null;
		Statement stmt = null;
		Class.forName("org.h2.Driver");
		con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/db1", "sa", "");
		stmt = con.createStatement();

		// stmt.execute("insert into csvdata ( id, name, age ) select \"id\", \"name\",
		// \"age\" from CSVREAD( '/home/samcouser/Documents/sample.csv', 'id,name,age',
		// null )");

		stmt.execute("drop table if exists csvdata");
		stmt.execute("create table csvdata (id int, name varchar(100), age int)");
		stmt.execute(
				"insert into csvdata ( id, name, age )     select \"id\", \"name\", \"age\" from CSVREAD( '/home/samcouser/Documents/sample.csv', 'id,name,age', null ) ");
		ResultSet rs = stmt.executeQuery("select * from csvdata");

		// display
		while (rs.next()) {
			System.out.println("id " + rs.getInt("id") + " name " + rs.getString("name") + " age " + rs.getInt("age"));
		}
		stmt.close();
	}

}

