package appli;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Roa extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		String fileFXML = "src/main/java/view/viewRoa.fxml";
		try {
			URL urlFXML = new File(fileFXML).toURI().toURL();
			Parent root = FXMLLoader.load(urlFXML);
			Scene scene = new Scene(root,1680,800);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Roa");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
