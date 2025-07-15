package hunting.crossbow.client.view.title_view;

import hunting.crossbow.ServerMain;
import hunting.crossbow.client.presenter.GameProxyPresenter;
import hunting.crossbow.client.view.game_view.GameView;
import hunting.crossbow.client.view.utils.CustomButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import hunting.crossbow.client.view.utils.Logo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.awt.*;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

import static hunting.crossbow.ServerMain.getMessageBundle;

public class TitleView {
    private int width;
    private int height;
    private Button returnButton;

    @FXML
    private Canvas game_canvas;

    @FXML
    private Pane title_pane;

    @FXML
    void initialize() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.width = gd.getDisplayMode().getWidth();
        this.height = gd.getDisplayMode().getHeight();
        drawMenu();
    }

    /**
     * Draw the logo
     */
    private void drawLogo() {
        new Logo(width * 14 / 16, height * 2 / 16).draw(game_canvas.getGraphicsContext2D());
    }

    /**
     * Draw the background
     */
    private void drawBackground() {
        Image image = new Image(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("background2.png")),
                width,
                height,
                false,
                false

        );
        GraphicsContext context = game_canvas.getGraphicsContext2D();
        context.drawImage(image, game_canvas.getLayoutX(), game_canvas.getLayoutY());
    }

    /**
     * Draw button quit
     */
    public void drawButtonQuit() {
        Button button = new CustomButton("quitter.png",
                (width / 2),
                (height * 21 / 28),
                () -> System.out.println("click quit"),
                "quitter_hover.png");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
        title_pane.getChildren().add(button);
    }

    /**
     * Draw button help
     */
    private void drawButtonHelp() {
        Button button = new CustomButton("aide.png",
                (width / 2),
                (height / 2),
                () -> System.out.println("click help"),
                "aide_hover.png");
        title_pane.getChildren().add(button);
    }

    /**
     * Draw button play
     */
    private void drawButtonJouer() {
        Button button = new CustomButton("jouer.png",
                (width / 2),
                (height * 13 / 56),
                () -> drawDifficulty(),
                "jouer_hover.png");
        title_pane.getChildren().add(button);
    }

    /**
     * Draw button easy
     */
    private void drawButtonFacile() {
        Button button = new CustomButton("facile.png",
                (width / 2),
                (height * 13 / 56),
                () -> Level.level = Level.LEVEL1,
                "facile_hover.png");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Level.level = Level.LEVEL1;
                try {
                    handleButtonStartAction(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        title_pane.getChildren().add(button);
    }

    /**
     * Draw button medium
     */
    private void drawButtonMoyen() {
        Button button = new CustomButton("moyen.png",
                (width / 2),
                (height / 2),
                () -> Level.level = Level.LEVEL2,
                "moyen_hover.png");


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Level.level = Level.LEVEL2;
                try {
                    handleButtonStartAction(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        title_pane.getChildren().add(button);
    }

    /**
     * Draw hard button
     */
    private void drawButtonDifficile() {
        Button button = new CustomButton("difficile.png",
                (width / 2),
                (height * 21 / 28),
                () -> System.out.println("difficile"),
                "difficile_hover.png");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Level.level = Level.LEVEL3;
                try {
                    handleButtonStartAction(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        title_pane.getChildren().add(button);
    }

    /**
     * Draw back button
     */
    private void drawButtonRetour() {
        returnButton = new CustomButton("back.png",
                (width / 15),
                (height / 10),
                () -> {
                    drawMenu();
                });
        title_pane.getChildren().add(returnButton);
    }

    /**
     * Draw the menu
     */
    private void drawMenu() {
        ArrayList<Node> list = new ArrayList<>();
        for (Node node : title_pane.getChildren()) {
            list.add(node);
        }
        if (returnButton != null) {
            title_pane.getChildren().remove(returnButton);
        }
        title_pane.getChildren().remove(list);
        drawBackground();
        drawButtonQuit();
        drawButtonHelp();
        drawButtonJouer();
        drawLogo();
    }

    /**
     * Draw the difficulty buttons
     */
    private void drawDifficulty() {
        ArrayList<Node> list = new ArrayList<>();
        for (Node node : title_pane.getChildren()) {
            list.add(node);
        }
        drawButtonFacile();
        drawButtonMoyen();
        drawButtonDifficile();
        drawButtonRetour();
    }

    @FXML
    private void handleButtonStartAction(ActionEvent event) throws IOException {
        Stage stage = getStage(event);
        stage.close();

        try {
            System.out.println("File Absolute Path :" + new File(".").getAbsolutePath());
            InputStream flux = new FileInputStream("./ip.txt");
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ipAddress;
            while ((ipAddress = buff.readLine()) != null) {
                System.out.println("IP address of the server : " + ipAddress);
                startClient(stage, ServerMain.PORT_NUMBER, ipAddress.trim());
            }
            buff.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    private Stage getStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    private void startClient(Stage primaryStage, int port, String IPAddress) throws IOException {
        primaryStage.setTitle(getMessageBundle().getString("login.title"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/genielogiciel/mvp/client/view/game_screen.fxml"), getMessageBundle());
        System.out.println("Before loading");
        Parent root = loader.load();
        System.out.println("After loading");
        GameView view = loader.getController();
        try {
            Socket socket = new Socket(IPAddress, port);
            GameProxyPresenter presenter = new GameProxyPresenter(socket);
            view.setProxy(socket);
            view.setPresenter(presenter);
            presenter.addView(view);
            Scene scene = new Scene(root, 400, 120);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (ConnectException e) {
            // Connection refused
            e.printStackTrace();
        }
    }
}






