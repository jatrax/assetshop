module AssetShop {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires java.desktop;
	requires javafx.graphics;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
}
