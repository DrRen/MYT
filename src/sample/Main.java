package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        DBMembersController dbMembersController;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DBMembers.fxml"));
        Parent root = loader.load();
        dbMembersController = loader.getController();
        dbMembersController.stage = primaryStage;
        Scene scene = new Scene(root);


        primaryStage.setTitle("MYT");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
