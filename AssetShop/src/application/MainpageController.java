package application;


import javafx.scene.control.Label;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainpageController {

	public User log = new User(-1,"","",0,null);
	public ArrayList<Integer> library_int = new ArrayList<Integer>();
	ArrayList<Products> library_arr = new ArrayList<Products>();
    ArrayList<Products> product_arr = new ArrayList<Products>();
    @FXML
    private boolean Shop_mode = true;
    public void setVallet(float money) {
    	Vallet_label.setText(Float.toString(money)+"$");
    }
	public void setUser(User user) {
	    this.log = user;
	    ID_LABEL.setText(log.getID()+"");
        User_label.setText(log.getUsername());
        Vallet_label.setText(log.getWallet()+"$");
        library_int = log.library;
        Create_products();
	}
	
	void new_product(int i,String names,float prices,String imageUrl,int id) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Product.fxml"));
        try {
            Pane productPane = loader.load();
            ProductController prd = loader.getController();

            Button btn = (Button) loader.getNamespace().get("btn");
            if(id == -1)
            	btn.setVisible(false);
            prd.user = log;
            prd.buy = Shop_mode;
            prd.my_id = id;
            Label name = (Label) loader.getNamespace().get("name");
            name.setText(names);
            
            String pricess = Float.toString(prices);
            Label price = (Label) loader.getNamespace().get("price");
            price.setText(pricess+"$");
            if(Shop_mode == false) {
                btn.setText("Ýndir");
                price.setText("");
                btn.setStyle("-fx-background-color: #88cc88;");
            }
            
            ImageView dwnload = (ImageView) loader.getNamespace().get("image");
            try {
                InputStream stream = new URL(imageUrl).openStream();
                Image imageFile = new Image(stream);
                stream.close();
                dwnload.setImage(imageFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch(i) {
            case 0:
                s0.getChildren().add(productPane);
                break;
            case 1:
                s1.getChildren().add(productPane);
                break;
            case 2:
                s2.getChildren().add(productPane);
                break;
            case 3:
                s3.getChildren().add(productPane);
                break;
            case 4:
                s4.getChildren().add(productPane);
                break;
            case 5:
                s5.getChildren().add(productPane);
                break;
            case 6:
                s6.getChildren().add(productPane);
                break;
            case 7:
                s7.getChildren().add(productPane);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	void update_library(int user_id,int product_id) {

    	try (Connection connection = DriverManager.getConnection(mySQL_data.url, mySQL_data.usernam, mySQL_data.passwor)) {
    	    String query = "INSERT INTO ASSETSHOP_USERS(USERNAME, PASSWRD, WALLET, OWNED) VALUES (?, ?, ?, ?)";
    	    PreparedStatement statement = connection.prepareStatement(query);
    	    statement.setString(4, "[]");

    	    statement.executeUpdate();
    	    statement.close();
    	} catch (SQLException e) {
    	    e.printStackTrace();
    	}
    }
	
    @FXML
    private Label ID_LABEL;
    
    @FXML
    private int myID = -1;
	void set_ID(int myID) {
		this.myID = myID;
	}
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane Anchor;

    @FXML
    private Button Apply_B;
    
    @FXML
    private GridPane Grd;
    
    @FXML
    private Label User_label;

    @FXML
    private Label Vallet_label;
    
    @FXML
    private ColumnConstraints grd00;

    @FXML
    private ColumnConstraints grd01;
    
    @FXML
    private Button MainPage_B;
    
    @FXML
    private ChoiceBox<String > Engine_Choice;
    
    @FXML
    private ChoiceBox<String> Tur_choice;

    @FXML
    private TextField max_price_text;

    @FXML
    private TextField min_price_text;
    
    @FXML
    private AnchorPane s0;

    @FXML
    private AnchorPane s1;

    @FXML
    private AnchorPane s2;

    @FXML
    private AnchorPane s3;

    @FXML
    private AnchorPane s4;

    @FXML
    private AnchorPane s5;

    @FXML
    private AnchorPane s6;

    @FXML
    private AnchorPane s7;
    
    @FXML
    private int page = 0;
    
    
    boolean max_filter(float i) {
        String maxPriceText = max_price_text.getText().trim();
        if (!maxPriceText.isEmpty()) {
            float j = Float.parseFloat(maxPriceText);
            if (i <= j)
                return true;
        }
        return false;
    }

    boolean min_filter(float i) {
        String minPriceText = min_price_text.getText().trim();
        if (!minPriceText.isEmpty()) {
            float j = Float.parseFloat(minPriceText);
            if (i >= j)
                return true;
        }
        return false;
    }
    private boolean srch_lib(int iii){
    	for(int tmp : library_int) {
    		if(iii == tmp)
    			return true;
    	}
    	return false;
    }
    ArrayList<Products> filter_arr = new ArrayList<Products>();
    void Create_products(){
    	filter_arr.clear();
        for (Products i : product_arr) {
        	String maxPriceText = max_price_text.getText();
            if (maxPriceText != null && !maxPriceText.trim().isEmpty()) {
                if (!max_filter(i.getPrice()))
                    continue;
            }
            
            String minPriceText = min_price_text.getText();
            if (minPriceText != null && !minPriceText.trim().isEmpty()) {
                if (!min_filter(i.getPrice()))
                    continue;
            }
            if (Tur_choice.getValue() != null && !Tur_choice.getValue().equals(i.getTur()) && Tur_choice.getValue() != "BOÞ") {
                continue;
            }
            if (Engine_Choice.getValue() != null && !Engine_Choice.getValue().equals(i.getMotor()) && Engine_Choice.getValue() != "BOÞ") {
                continue;
            }
            if(Shop_mode == true && srch_lib(i.getId()) == true){
            	continue;
            }
            else if(Shop_mode == false && srch_lib(i.getId()) == false) {
            	continue;
            }
            filter_arr.add(i);
        }
        int sizee = filter_arr.size();
        Products tmp = new Products(-1,"",0,"","","https://pbs.twimg.com/profile_images/1246486049964068865/PMGeB3d0_400x400.jpg");
        for(int i = 0  ;i < 8-sizee%8;i++) {
        	filter_arr.add(tmp);
        }
        for (int i = 0; i < 8 ; i++) {
            new_product(i, filter_arr.get(i + (page*8)).getName(), filter_arr.get(i + (page*8)).getPrice(), filter_arr.get(i + (page*8)).getURL(),filter_arr.get(i+(page*8)).getId());
        }
    }
    @FXML
    void LOG_OUT(ActionEvent event) {
    	try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    @FXML
    void next_b(ActionEvent event) {
    	if(page*8<filter_arr.size()-8) {
    		page++;
        	Create_products();
    	}
    }

    @FXML
    void prev_b(ActionEvent event) {
    	if(page>0) {
    		page--;
    		Create_products();
    	}

    }
    
    boolean isNumeric(String in) {
        if (in != null && !in.trim().isEmpty()) {
            try {
                Double.parseDouble(in);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }
    
    @FXML
    void Apply_B_Clicked(ActionEvent event) {
    	page = 0;
    	if(!isNumeric(max_price_text.getText()))
        	max_price_text.setText(null);
    	if(!isNumeric(min_price_text.getText()))
        	min_price_text.setText(null);
    	Create_products();	
    	
    }
    
    @FXML
    void Library_b(ActionEvent event) {
    	Shop_mode = false;
    	Create_products();
    }
    
    @FXML
    void MainPage_B_Clicked(ActionEvent event) {
    	Shop_mode = true;
    	Create_products();
    }
    int[] array_convert(String arr) {
    	arr = arr.replaceAll("[\\[\\]\\s]", ""); // Köþeli parantezleri ve boþluklarý kaldýr
    	String[] numbers = arr.split(",");
    	int[] intArray = new int[numbers.length];
    	for (int i = 0; i < numbers.length; i++) {
    	    intArray[i] = Integer.parseInt(numbers[i]);
    	}
    	return intArray;
    }
    @FXML
    void initialize() {
    	
        assert Anchor != null : "fx:id=\"Anchor\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert Apply_B != null : "fx:id=\"Apply_B\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert MainPage_B != null : "fx:id=\"MainPage_B\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert Tur_choice != null : "fx:id=\"Tur_choice\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert max_price_text != null : "fx:id=\"max_price_text\" was not injected: check your FXML file 'Mainpage.fxml'.";
        assert min_price_text != null : "fx:id=\"min_price_text\" was not injected: check your FXML file 'Mainpage.fxml'.";
    	
        // product input
        try {
    		Connection myConn = DriverManager.getConnection(mySQL_data.url,mySQL_data.usernam,mySQL_data.passwor);
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
        
        Anchor.setStyle("-fx-background-color: #2b2b2b;");
        String[] Tur_arr = {"Karakter","Yapýlar","Doðal Yapýlar","Araçlar","BOÞ"};
        Tur_choice.getItems().addAll(Tur_arr);
        String[] Engine_arr = {"Unreal Engine 5","Unreal Engine 4","Unity","BOÞ"};
        Engine_Choice.getItems().addAll(Engine_arr);
        
        
        
           
    }
}