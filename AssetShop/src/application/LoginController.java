package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
	
	@FXML
    ArrayList<User> user_arr = new ArrayList<User>();
	
	@FXML
	private User log;
	
	int[] array_convert(String arr) {
    	if(arr.equals("[]"))
    		return null;
    	arr = arr.replaceAll("[\\[\\]\\s]", ""); // Köþeli parantezleri ve boþluklarý kaldýr
    	String[] numbers = arr.split(",");
    	int[] intArray = new int[numbers.length];
    	for (int i = 0; i < numbers.length; i++) {
    	    intArray[i] = Integer.parseInt(numbers[i]);
    	}
    	return intArray;
    }
	
	void mySQL_user() {
		try {
        	user_arr.clear();
    		Connection myConn = DriverManager.getConnection(mySQL_data.url,mySQL_data.usernam,mySQL_data.passwor);
    		Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM AssetShop_users");
            while (myRs.next()) {
            	int RSid = myRs.getInt("USER_ID");
                String RSusername = myRs.getString("USERNAME");
                String RSpassword = myRs.getString("PASSWRD");
                float RSwallet = myRs.getFloat("WALLET");
                String RSown = myRs.getString("OWNED");
                int[] RSowned = array_convert(RSown);
                User temp = new User(RSid,RSusername,RSpassword,RSwallet,RSowned);
                user_arr.add(temp);
                // System.out.println(temp.getID()+" "+temp.getUsername()+" "+temp.getPassword()+" "+temp.library);
            }
            myConn.close();
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
	}
	
	void goToMainPage(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainpage.fxml"));
	        Parent root = loader.load();
	        
	        MainpageController cc = loader.getController();
	        cc.setUser(log);
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	@FXML
    private Label info_label;
	
	@FXML
	private float Money;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;


    @FXML
    void login(ActionEvent event) {
    	for(User t : user_arr) {
    		if(t.getUsername().toLowerCase().equals(username.getText().toLowerCase())) {
    			if(t.getPassword().equals(password.getText())) {
    				int[] myarr = new int[t.library.size()];
    				int index = 0;
    				for (int element : t.library) {
    				    myarr[index++] = element;
    				}
    				log = new User(t.getID(),t.getUsername(),t.getPassword(),t.getWallet(),myarr);
        			goToMainPage(event);
        			return ;
    			}	
    		}
    	}
		info_label.setText("Hatalý Kullanýcý adý veya þifre!!");
    }

    @FXML
    void signin(ActionEvent event) {
    	mySQL_user();
    	for(User t : user_arr) {
        	if(t.getUsername().toLowerCase().equals(username.getText().toLowerCase())) {
        		info_label.setText("Bu kullanýcý adýný kullanamazsýnýz!!");
        		return ;
        	}
    	}

    	try (Connection connection = DriverManager.getConnection(mySQL_data.url, mySQL_data.usernam, mySQL_data.passwor)) {
    	    String q_name = username.getText();
    	    String q_pass = password.getText();
    	    int q_wal = 1000;

    	    String query = "INSERT INTO ASSETSHOP_USERS(USERNAME, PASSWRD, WALLET, OWNED) VALUES (?, ?, ?, ?)";
    	    PreparedStatement statement = connection.prepareStatement(query);
    	    statement.setString(1, q_name);
    	    statement.setString(2, q_pass);
    	    statement.setInt(3, q_wal);
    	    statement.setString(4, "[]");

    	    statement.executeUpdate();
    	    statement.close();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    	
    	mySQL_user();
    	login(event);
    }
    @FXML
    void initialize() {
        assert password != null : "fx:id=\"password\" was not injected: check your FXML file 'Login.fxml'.";
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file 'Login.fxml'.";
        String query1 = "CREATE TABLE IF NOT EXISTS AssetShop_Products (PRODUCT_ID INT AUTO_INCREMENT,FULL_NAME varchar(64) NOT NULL,KIND varchar(32),ENGINE varchar(32),PRICE DECIMAL(8,2),URL varchar(256),PRIMARY KEY (PRODUCT_ID));";
        String query2 = "CREATE TABLE IF NOT EXISTS AssetShop_Users (USER_ID INT AUTO_INCREMENT,USERNAME varchar(64) NOT NULL,PASSWRD varchar(64) NOT NULL,WALLET float,OWNED JSON,PRIMARY KEY (USER_ID));";
        mySQL_data.mySQL_q(query1);
        mySQL_data.mySQL_q(query2);
        mySQL_user();
    }
}
