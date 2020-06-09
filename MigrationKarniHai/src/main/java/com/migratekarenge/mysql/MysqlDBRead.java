package com.migratekarenge.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.migratekarenge.model.Insaan;

public class MysqlDBRead {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:2929/?user=root";
	static final String USER = "root";
	static final String PASS = "admin";

	public ArrayList<Insaan> sqlKaSamaan() throws SQLException {

		Connection conn = null;
		Statement stmt = null;

		conn = DriverManager.getConnection(DB_URL, USER, PASS);
		stmt = conn.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM pareshan.insaan");

		ArrayList<Insaan> samaanAgyaSaara = new ArrayList<Insaan>();
		for (int i = 0; i < 2; i++)
			System.out.println(".");
		System.out.println(">>>>>>    MySQL se samaan ara hai    <<<<<<<");

		while (rs.next()) {
			Insaan ek = new Insaan();
			int id = rs.getInt("idinsaan");
			int age = rs.getInt("insaanKiUmar");
			String name = rs.getString("insaanKaNaam");
			Boolean pareshani = rs.getBoolean("pareshani");

			ek.setIdinsaan(id);
			ek.setInsaanKiUmar(age);
			ek.setInsaanKaNaam(name);
			ek.setPareshanHai(pareshani);

			samaanAgyaSaara.add(ek);

			System.out.print("ID: " + id);
			System.out.print(", Age: " + age);
			System.out.print(", Name: " + name);
			System.out.println(", Pareshani hai?: " + pareshani);
		}
		for (int i = 0; i < 2; i++)
			System.out.println(".");
		System.out.println(">>>>>>    MySQL ka samaan khatam bas itna he tha <<<<<<");
		for (int i = 0; i < 3; i++)
			System.out.println(".");
		System.out.println("******    Starting migration from MySQL to DynamoDB   ******");
		for (int i = 0; i < 2; i++)
			System.out.println(".");

		rs.close();
		stmt.close();
		conn.close();

		return samaanAgyaSaara;
	}

}
