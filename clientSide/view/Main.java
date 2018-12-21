package view;

import java.net.URL;

import javafx.application.Application;
import javafx.stage.Stage;
import model.PipeGameModel;
import viewModel.PipeGameViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			PipeGameModel m = new PipeGameModel();
			PipeGameViewModel vm = new PipeGameViewModel(m);
			FXMLLoader fxmlLoader = new FXMLLoader();
			MainWindowController mwc = new MainWindowController(vm);
			fxmlLoader.setController(mwc);
			BorderPane root = fxmlLoader
					.load(getClass()
							.getResource("MainWindow.fxml")
							.openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
