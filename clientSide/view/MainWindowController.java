package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import view.PipeGameDisplayer.theme;
import viewModel.PipeGameViewModel;


public class MainWindowController implements Initializable{

	PipeGameViewModel vm;
	ListProperty<char[]> board;
	BooleanProperty isGoal;

	@FXML
	private TextField Steps;

	@FXML
	private TextField Time;

	@FXML
	PipeGameDisplayer pipeGameDisplayer;

	@FXML
	private MenuItem Lava;
	@FXML

	private MenuItem Silver;

	@FXML
	public void changeTheme(ActionEvent chosenTheme) {
		if(chosenTheme.getSource() == Lava) {
			pipeGameDisplayer.setCurrentTheme(theme.Lava);
		}
		else {
			pipeGameDisplayer.setCurrentTheme(theme.Silver);
		}
	}
	public MainWindowController(PipeGameViewModel vm) {
		this.vm = vm;
		this.board = new SimpleListProperty<>();
		this.board.bind(vm.board);
		this.isGoal = new SimpleBooleanProperty();
		this.isGoal.bind(vm.isGoal);
	}

	public void mute() {
		pipeGameDisplayer.mute();
	}

	public void openFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open Pipe Game file");
		fc.setInitialDirectory(new File("./resources/Levels"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null)
		{
			vm.loadLevel(chosen);
			pipeGameDisplayer.redraw();
		}
	}

	public void openSavedFile() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open Pipe Game file");
		fc.setInitialDirectory(new File("./resources/Saves"));
		File chosen = fc.showOpenDialog(null);
		if(chosen != null)
		{
			vm.loadSavedLevel(chosen);
			pipeGameDisplayer.redraw();
		}
	}

	//settings dialog
	public void settings() throws FileNotFoundException {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Server settings");
		dialog.setHeaderText("Server settings");

		// Set the icon (must be included in the project).
		dialog.setGraphic(new ImageView(new Image(new FileInputStream("./resources/settings.png"))));

		// Set the button types.
		ButtonType saveButtonType = new ButtonType("save", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		// Create the port and host labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);

		TextField port = new TextField();
		port.setPromptText(String.valueOf(vm.model.getPort()));
		TextField host = new TextField();
		host.setPromptText(vm.model.getHost());

		grid.add(new Label("Port number:"), 0, 0);
		grid.add(port, 1, 0);
		grid.add(new Label("Host:"), 0, 1);
		grid.add(host, 1, 1);

		// Enable/Disable save button depending on whether a port was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(saveButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		port.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the port field by default.
		Platform.runLater(() -> port.requestFocus());

		// Convert the result to a port-host-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				return new Pair<>(port.getText(), host.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		if(result.isPresent()) {
			String resultPort = result.get().getKey();
			String resultHost = result.get().getValue();
			vm.setPort(Integer.parseInt(resultPort));
			vm.setHost(resultHost);
		}

	}
	// About dialog
	public void about() {
		this.alert("About Water Pipe Game",
				"Water Pipe Game is one of the best puzzle games in the market. " +
						"All you need to do is to rotate pipes, connect them and make a working pipeline."+ '\n'  + '\n' +
						"Touch the pipes to turn them."+ '\n' +
						"Construct a water path from start to goal."+ '\n' +  '\n' +
						"If you can't find the solution you can always click on solve button and our genius algorithm will solve it for you."+ '\n' + '\n' +
						"This game was built by Idan Shviro and Bar Kazzaz." + '\n' 
						+"For more information you can contact us at: idanshviro@gmail.com or barkazzaz@gmail.com",
						AlertType.INFORMATION);
	}

	//error alert
	public void alertError() {
		this.alert("ERROR","There is no solution for this game",AlertType.ERROR);

	}

	//alert for successful save
	public void alertSaveSuccess() {
		this.alert("Saved","Game saved",AlertType.INFORMATION);
	}

	//alert for error while saving
	public void alertSaveError() {
		this.alert("ERROR","Save error",AlertType.ERROR);
	}

	//if the server is not up and you press Solve
	public void alertConnectionError(){
		this.alert("ERROR","Server Connection Error\nTo adjust server settings go to:\nGame->Settings",AlertType.ERROR);
	}

	//You Won! dialog
	public void alertWonMessage() {
		this.alert("Congratulations","You Won!",AlertType.INFORMATION);
	}

	//alert when requesting a solution for an already solved game
	public void alertAlreadySolved() {
		this.alert("ERROR","Game already solved",AlertType.WARNING);
	}

	public void alert(String alertTitle, String alertBody,AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(alertTitle);
		alert.setHeaderText(null);
		alert.setContentText(alertBody);
		alert.showAndWait();
	}

	public void addWinningListener() {

		isGoal.addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean arg1, Boolean arg2) {
				if(arg2) {
					alertWonMessage();
				}
			}
		});
	}

	public void Save() throws FileNotFoundException {
		if(vm.save()) {
			alertSaveSuccess();
		}
		else
			alertWonMessage();
	}

	public void solve() {
		List<String> solution;
		try {
			solution = vm.solve();
		} catch (Exception e) {
			alertConnectionError();
			return;
		}
		if(!solution.isEmpty()){
			{
				if(solution.get(0).equals("null")) {
					alertError();
				}
				if(solution.get(0).equals("solved")) {
					alertAlreadySolved();
				}
			}
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					for(int i=0; i<solution.size();i++) {
						int y,x,numberOfRotations;
						String[] line = solution.get(i).split(",");
						y = Integer.parseInt(line[0]);
						x = Integer.parseInt(line[1]);
						numberOfRotations = Integer.parseInt(line[2]);
						for(int j=0;j<numberOfRotations;j++) {
							Platform.runLater(()->{
								vm.rotate(y,x);
								pipeGameDisplayer.setPipeGameBoard(vm.board);
								vm.isGoal();
							});
							Thread.sleep(250);
						}
					}
					return null;
				}
			};
			new Thread(task).start();
		}
	}

	public void addClickOnPipeBoardHandler() {
		if(pipeGameDisplayer==null)
			return;

		pipeGameDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{pipeGameDisplayer.requestFocus();});

		pipeGameDisplayer.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				double w = pipeGameDisplayer.getW();
				double h = pipeGameDisplayer.getH();
				int x = (int) (event.getX()/w);
				int y = (int) (event.getY()/h);
				vm.rotate(y, x);
				pipeGameDisplayer.setPipeGameBoard(vm.board); //set calls for redraw automatically
				vm.isGoal();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		pipeGameDisplayer.setPipeGameBoard(vm.board);
		addClickOnPipeBoardHandler();
		addWinningListener();
	}
}
