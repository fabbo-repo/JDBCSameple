package model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.parser.ParseException;

public class QueryExecutor {
	private ConnectionHandler connectionHandler;
	private final String sectionQuery = ""
			+ "SELECT nombrearticulo, seccion, precio, paisdeorigen "
			+ "FROM productos "
			+ "WHERE seccion=?";
	private final String countryQuery = ""
			+ "SELECT nombrearticulo, seccion, precio, paisdeorigen "
			+ "FROM productos "
			+ "WHERE paisdeorigen=?";
	private final String scQuery = ""
			+ "SELECT nombrearticulo, seccion, precio, paisdeorigen "
			+ "FROM productos "
			+ "WHERE seccion=? AND paisdeorigen=?";
	private final String allQuery = ""
			+ "SELECT nombrearticulo, seccion, precio, paisdeorigen "
			+ "FROM productos ";
	
	public QueryExecutor(ConnectionHandler cH) {
		this.connectionHandler = cH;
	}
	
	public ResultSet dbFilter(String section, String country) throws FileNotFoundException, 
											IOException, ParseException, SQLException {
		if(!this.connectionHandler.isConnected())
			this.connectionHandler.connectDB();
		Connection cnn = this.connectionHandler.getConnection();
		
		PreparedStatement pStatement = null;
		// Usuario esogi� una seccion espec�fica
		if(!section.equals("Todos") && country.equals("Todos")) {
			pStatement = cnn.prepareStatement(this.sectionQuery);
			pStatement.setString(1, section);
		}
		// Usuario esogi� un pais espec�fico
		else if(section.equals("Todos") && !country.equals("Todos")) {
			pStatement = cnn.prepareStatement(this.countryQuery);
			pStatement.setString(1, country);
		}
		// Usuario esogi� una seccion espec�fica y un pais espec�fico
		else if(!section.equals("Todos") && !country.equals("Todos")) {
			pStatement = cnn.prepareStatement(this.scQuery);
			pStatement.setString(1, section);
			pStatement.setString(2, country);
		}
		// Usuario no esogi� una seccion espec�fica ni un pais espec�fico
		else {
			pStatement = cnn.prepareStatement(this.allQuery);
		}
		return pStatement.executeQuery();
	}
}
