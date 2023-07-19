package application;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MainpageController {
	String mySQL_username = "root";
	String mySQL_password = "";
	String datab = "assetshop";
	String mySQL_url = "jdbc:mysql://localhost:3306/"+datab;
	
	void mySQL_q(String query) {
		try (Connection connection = DriverManager.getConnection(mySQL_url, mySQL_username, mySQL_password)) {
		    PreparedStatement statement = connection.prepareStatement(query);
		    statement.executeUpdate();
		    statement.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}

    ArrayList<Products> product_arr = new ArrayList<Products>();
    
    @FXML
    private TextField search;
    

    @FXML
    void search_changed() {
        String searchText = search.getText();
        listbx.getItems().clear();
        if (searchText.isEmpty()) {
            for (Products product : product_arr) {
                listbx.getItems().add(product.getId() + " " + product.getName() + "    " + product.getPrice() + "$");
            }
        } else {
            for (Products product : product_arr) {
                if (product.getName().toLowerCase().contains(searchText.toLowerCase())) {
                    listbx.getItems().add(product.getId() + " " + product.getName() + "    " + product.getPrice() + "$");
                }
            }
        }
    }
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<String> listbx;

    @FXML
    private TextField urun_ad;

    @FXML
    private TextField urun_fiyat;

    @FXML
    private ChoiceBox<String> urun_motor;

    @FXML
    private ChoiceBox<String> urun_tur;

    @FXML
    private TextField urun_url;

    @FXML
    void ekle(ActionEvent event) {
        String name = urun_ad.getText();
        float price = Float.parseFloat(urun_fiyat.getText());
        String tur = urun_tur.getValue();
        String motor = urun_motor.getValue();
        String url = urun_url.getText();

        Products newProduct = new Products(-1,name, price, tur, motor, url);

        String query = "INSERT INTO AssetShop_Products (FULL_NAME, PRICE, KIND, ENGINE, URL) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(mySQL_url, mySQL_username, mySQL_password)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newProduct.getName());
            statement.setFloat(2, newProduct.getPrice());
            statement.setString(3, newProduct.getTur());
            statement.setString(4, newProduct.getMotor());
            statement.setString(5, newProduct.getURL());
            statement.executeUpdate();
            statement.close();
            Statement stt = connection.createStatement();
            ResultSet myRs = stt.executeQuery("SELECT * FROM AssetShop_Products");
            int new_id = -1;
            while(myRs.next())
            	new_id = myRs.getInt("PRODUCT_ID");
            newProduct.setId(new_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        product_arr.add(newProduct);

        listbx.getItems().add(newProduct.getId() + " " + newProduct.getName() + "    " + newProduct.getPrice() + "$");

        // Clear the input fields
        urun_ad.clear();
        urun_fiyat.clear();
        urun_tur.getSelectionModel().clearSelection();
        urun_motor.getSelectionModel().clearSelection();
        urun_url.clear();
    }

    @FXML
    void sil(ActionEvent event) {
        int selectedIndex = listbx.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            listbx.getItems().remove(selectedIndex);

            Products selectedProduct = product_arr.get(selectedIndex);
            product_arr.remove(selectedProduct);

            int productId = selectedProduct.getId();
            String query = "DELETE FROM AssetShop_Products WHERE PRODUCT_ID = " + productId;
            mySQL_q(query);

            urun_ad.clear();
            urun_fiyat.clear();
            urun_tur.getSelectionModel().clearSelection();
            urun_motor.getSelectionModel().clearSelection();
            urun_url.clear();
        }
    }
    @FXML
    void güncelle(ActionEvent event) {
        // Get the index of the selected item in the ListView
        int selectedIndex = listbx.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            Products selectedProduct = product_arr.get(selectedIndex);

            selectedProduct.setName(urun_ad.getText());
            selectedProduct.setPrice(Float.parseFloat(urun_fiyat.getText()));
            selectedProduct.setTur(urun_tur.getValue());
            selectedProduct.setMotor(urun_motor.getValue());
            selectedProduct.setURL(urun_url.getText());

            int productId = selectedProduct.getId();
            String query = "UPDATE AssetShop_Products SET FULL_NAME = ?, PRICE = ?, KIND = ?, ENGINE = ?, URL = ? WHERE PRODUCT_ID = ?";
            try (Connection connection = DriverManager.getConnection(mySQL_url, mySQL_username, mySQL_password)) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, selectedProduct.getName());
                statement.setFloat(2, selectedProduct.getPrice());
                statement.setString(3, selectedProduct.getTur());
                statement.setString(4, selectedProduct.getMotor());
                statement.setString(5, selectedProduct.getURL());
                statement.setInt(6, productId);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            listbx.getItems().set(selectedIndex, selectedProduct.getId() + " " + selectedProduct.getName() + "    " + selectedProduct.getPrice() + "$");
        }
    }
    @FXML
    void initialize() {
        assert listbx != null : "fx:id=\"listbx\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert urun_ad != null : "fx:id=\"urun_ad\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert urun_fiyat != null : "fx:id=\"urun_fiyat\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert urun_motor != null : "fx:id=\"urun_motor\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert urun_tur != null : "fx:id=\"urun_tur\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert urun_url != null : "fx:id=\"urun_url\" was not injected: check your FXML file 'Mainpage.fxml'.";
        String query1 = "CREATE TABLE IF NOT EXISTS AssetShop_Products (PRODUCT_ID INT AUTO_INCREMENT,FULL_NAME varchar(64) NOT NULL,KIND varchar(32),ENGINE varchar(32),PRICE DECIMAL(8,2),URL varchar(256),PRIMARY KEY (PRODUCT_ID));";
        String query2 = "CREATE TABLE IF NOT EXISTS AssetShop_Users (USER_ID INT AUTO_INCREMENT,USERNAME varchar(64) NOT NULL,PASSWRD varchar(64) NOT NULL,WALLET float,OWNED JSON,PRIMARY KEY (USER_ID));";
        mySQL_q(query1);
        mySQL_q(query2);
        listbx.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Split the selected value into ID, Name, and Price
                String[] parts = newValue.split(" ");
                int selectedId = Integer.parseInt(parts[0]);

                // Find the selected product from the product_arr list
                Products selectedProduct = null;
                for (Products product : product_arr) {
                    if (product.getId() == selectedId) {
                        selectedProduct = product;
                        break;
                    }
                }

                if (selectedProduct != null) {
                    // Set the values of the text fields and choice boxes with the selected product's data
                    urun_ad.setText(selectedProduct.getName());
                    urun_fiyat.setText(String.valueOf(selectedProduct.getPrice()));
                    urun_tur.setValue(selectedProduct.getTur());
                    urun_motor.setValue(selectedProduct.getMotor());
                    urun_url.setText(selectedProduct.getURL());
                }
            }
        });
        
        try {
    		Connection myConn = DriverManager.getConnection(mySQL_url,mySQL_username,mySQL_password);
    		Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM AssetShop_Products");
            while (myRs.next()) {
            	int RSid = myRs.getInt("PRODUCT_ID");
                String RSname = myRs.getString("FULL_NAME");
                String RSkind = myRs.getString("KIND");
                String RSengine = myRs.getString("ENGINE");
                String RSurl = myRs.getString("URL");
                float RSprice = myRs.getFloat("PRICE");
                Products temp = new Products(RSid,RSname,RSprice,RSkind,RSengine,RSurl);
                product_arr.add(temp);
            }
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
        for(Products i : product_arr) {
        	listbx.getItems().add(i.getId()+" "+i.getName()+"    "+i.getPrice()+"$");
        }
        
        String[] t1 = {"Karakter","Yapýlar","Doðal Yapýlar","Araçlar"};
        urun_tur.getItems().addAll(t1);
        String[] t2 = {"Unreal Engine 5","Unreal Engine 4","Unity"};
        urun_motor.getItems().addAll(t2);
        
    }

}

