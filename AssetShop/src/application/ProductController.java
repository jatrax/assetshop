package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class ProductController {
	
    @FXML
    private AnchorPane anc1;
	@FXML
	public int my_id;
	
	@FXML
	public User user;
	
	@FXML
	public boolean buy = true;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private Button btn;
    
    @FXML
    private Label name;

    @FXML
    private ImageView image;

    @FXML
    private Label price;

    @FXML
    private AnchorPane rAnchor;
    
    @FXML
    private String download;
    
    @FXML
    void Buy_b(ActionEvent event) {
    	if(buy) {
    		String Str = price.getText().substring(0, price.getText().length() - 1);
    		float floatVal = Float.parseFloat(Str);
    		float new_wallet = user.getWallet() - (float) floatVal;
    		String query = "UPDATE ASSETSHOP_USERS SET OWNED = JSON_ARRAY_APPEND(OWNED, '$', "+my_id+") WHERE USER_ID = "+user.getID()+";";
    		String query2 = "UPDATE ASSETSHOP_USERS SET WALLET = "+new_wallet+" WHERE USER_ID = "+user.getID()+";";
    		mySQL_data.mySQL_q(query);
    		mySQL_data.mySQL_q(query2);
    		try{
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainpage.fxml"));
    	        Parent root = loader.load();
    	        MainpageController cc = loader.getController();
                user.library.add(my_id);
    	        cc.setUser(user);
                MainpageController prd = loader.getController();
                prd.setVallet(new_wallet);
        		Scene scene = new Scene(root);
    	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	        stage.setScene(scene);
    	        stage.show();
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	else {
    		System.out.println(name.getText()+" Ýndirildi");
    	}
    	
    }
    
    @FXML
    void initialize() {
        assert name != null : "fx:id=\"name\" was not injected: check your FXML file 'Product.fxml'.";
        assert image != null : "fx:id=\"photo\" was not injected: check your FXML file 'Product.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'Product.fxml'.";
        assert rAnchor != null : "fx:id=\"rAnchor\" was not injected: check your FXML file 'Product.fxml'.";
        anc1.setStyle("-fx-background-color: #2b2b2b;");
    	rAnchor.setStyle("-fx-background-color: white;");
    	String name_v = "Product";
    	name.setText(name_v);
    	int price_v = 0;
    	price.setText(price_v+"$");
    	
    }

}

