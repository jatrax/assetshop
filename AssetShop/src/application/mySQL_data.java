package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class mySQL_data {

	static String url = "jdbc:mysql://localhost:3306/assetshop";
	static String usernam = "root";
	static String passwor = "";
	
	static void mySQL_q(String query) {
    	try (Connection connection = DriverManager.getConnection(mySQL_data.url, mySQL_data.usernam, mySQL_data.passwor)) {
    	    PreparedStatement statement = connection.prepareStatement(query);
    	    statement.executeUpdate();
    	    statement.close();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
}
