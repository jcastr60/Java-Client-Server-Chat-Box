
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application{

	
	TextField s1,s2,s3,s4, c1;
	Button serverChoice,clientChoice,b1;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	BorderPane clientGui = new BorderPane();
	Server serverConnection;
	Client clientConnection;
	ArrayList<String> AllClients;
	
	ListView<String> listItems, listItems2;
	ListView<String> clientID;
	ArrayList<String> allC;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	ChatInfo info;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("The Networked Client/Server GUI Example");
		
		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 300px");
		this.serverChoice.setStyle("-fx-pref-height: 300px");
		
		this.serverChoice.setOnAction(e->{ 	sceneMap.get("server").getRoot().setStyle("-fx-font-family: 'serif'");
											primaryStage.setScene(sceneMap.get("server"));
											primaryStage.setTitle("This is the Server");
				serverConnection = new Server(
						data -> {
							Platform.runLater(()->{
								listItems.getItems().add(data.toString());
							});
						},
						clientObject->{
							Platform.runLater(()->{
								//info = (ChatInfo) clientObject;
								listItems.getItems().add(clientObject.toString());
								//messages.getItems().add(clientObject.toString());
								//clientID.getItems().add(info.);
							});
					

				});
											
		});
		
		
		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 300px");
		this.clientChoice.setStyle("-fx-pref-height: 300px");
		
		this.clientChoice.setOnAction(e-> {	sceneMap.get("client").getRoot().setStyle("-fx-font-family: 'serif'");
											primaryStage.setScene(sceneMap.get("client"));
											primaryStage.setTitle("This is a client");
					clientConnection = new Client(
							data->{
								Platform.runLater(()->{
									info =  (ChatInfo)data;
									listItems2.getItems().add(info.message.toString());
									
								});
							},
							list->{
								Platform.runLater(()->{	
									clientID.getItems().clear();
									
									clientID.getItems().addAll((ArrayList<String>)list);
									
																		
								});
										
					});
							
											clientConnection.start();
		});
		
		this.buttonBox = new HBox(400, serverChoice, clientChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);
		
		startScene = new Scene(startPane, 800,800);
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		clientID = new ListView<String>();
		
		c1 = new TextField();
		b1 = new Button("Send");
		b1.setOnAction(e->{clientConnection.send(c1.getText()); c1.clear();});
		
		sceneMap = new HashMap<String, Scene>();
		
		startScene.getRoot().setStyle("-fx-font-family: 'serif'");
		sceneMap.put("server",  createServerGui());
		sceneMap.put("client",  createClientGui());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, 500, 400);
		
		
	}
	
	public Scene createClientGui() {
		
		clientBox = new VBox(10, c1,b1,listItems2);
		clientGui.setLeft(clientID);
		clientGui.setCenter(clientBox);
		clientBox.setStyle("-fx-background-color: blue");
		return new Scene(clientGui, 400, 300);
		
	}
	void updateClientButtonLists(ArrayList<String> clientList) {
		for(int i = 0; i < clientList.size(); i++) {
			
			clientID.getItems().add(clientList.get(i));
			
		}
		//int client = clientList.get(clientList.size()-1);
	}

}
