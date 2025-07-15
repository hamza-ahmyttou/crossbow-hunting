package hunting.crossbow;

import hunting.crossbow.client.view.utils.Closeable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


public final class ClientMain extends Application {

    public static void main(String[] args ) {
        launch(args);
    }

    @Override
    public void start( final Stage primaryStage ) throws Exception {

        String xmlLocation = "./client/view/title_screen.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(xmlLocation));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnHidden(e -> {
            if(loader.getController() instanceof Closeable) {
                Closeable closeable = loader.getController();
                closeable.close();
            }
        });
    }

    @Override
    public void stop() throws Exception {

    }
}
