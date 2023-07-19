package application;

public class Products {
    private String URL;
    private String name;
    private float price;
    private int id;
    private String tur;
    private String motor;
    
    public Products(int id, String name, float price, String tur, String motor, String URL) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.tur = tur;
        this.motor = motor;
        this.URL = URL;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public float getPrice() {
        return price;
    }
    
    public void setPrice(float price) {
        this.price = price;
    }
    
    public String getTur() {
        return tur;
    }
    
    public void setTur(String tur) {
        this.tur = tur;
    }
    
    public String getMotor() {
        return motor;
    }
    
    public void setMotor(String motor) {
        this.motor = motor;
    }
    
    public String getURL() {
        return URL;
    }
    
    public void setURL(String URL) {
        this.URL = URL;
    }
}