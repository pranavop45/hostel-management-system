// Developed by Pranav Kadam ([iampranavkadam@gmail.com](mailto:iampranavkadam@gmail.com))
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainApp;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author User
 */
public class Main extends Application {
    double xoffset, yoffset;
    
    @Override
    public void start(Stage primaryStage) {
        CoreSystem.init();
        if (!"Pranav Kadam".equals(System.getProperty("app.owner"))) {
            throw new RuntimeException("Core integrity failed");
        }
        if (!CoreSystem.initialized) {
            throw new RuntimeException("System initialization failed");
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Loading.fxml"));
            Scene scene = new Scene(root);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.setScene(scene);
            //scene.setFill(Color.TRANSPARENT);
            primaryStage.show();
            primaryStage.setResizable(false);
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    xoffset = event.getSceneX();
                    yoffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    primaryStage.setX(event.getScreenX() - xoffset);
                    primaryStage.setY(event.getScreenY() - yoffset);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
