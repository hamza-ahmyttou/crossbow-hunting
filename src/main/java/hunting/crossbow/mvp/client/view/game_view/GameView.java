package hunting.crossbow.client.view.game_view;

import hunting.crossbow.client.view.events.ClickEvent;
import hunting.crossbow.client.view.events.PauseEvent;
import hunting.crossbow.client.view.game_view.elements.*;
import hunting.crossbow.client.view.utils.*;
import hunting.crossbow.client.view.utils.Cursor;
import hunting.crossbow.server.presenter.PresenterListener;
import hunting.crossbow.client.view.utils.Popup;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;


import static hunting.crossbow.client.view.title_view.Level.*;


public final class GameView implements IView, Closeable {


    private int width;
    private int height;
    private Popup popup;
    private int id = 2; // Player id read from playerInfo text file
    private int idPlayer; //Id of the opponent player read from playerInfo text file
    private int score;
    private int scoreAdversaire;
    private double[] finalPosition = new double[3];
    private double[] finalPositionAdversaire = new double[3];
    private Zoom zoom;
    private ArrowColor colorArrow;
    private ArrowColor colorArrowAdversaire;
    private int size;
    private double speedWind = 50;
    private double coordonnesArrowX;
    private double coordonnesArrowY;
    private double distanceFromTargetZ;
    private BufferedReader _input;
    private PresenterListener _presenter;

    /**
     * Note that even though the view interacts with the presenter, it has no idea
     * of its existence thanks to the Observer interface (Presenterlistener) that represent
     * a firewall.
     */



    @Override
    public void setPresenter(PresenterListener presenter) {
        _presenter = presenter;
    }


    /**
     * update the score after receiving in the reception class
     * the player’s score data
     * [!] the opponent score was drawn directly in
     * the class of reception
     * @param score: final score that will be drawn
     */
    @Override
    public void setScore(String score) {


        this.score = Integer.valueOf(score);
        drawScore(Integer.valueOf(score));
    }

    @Override
    public void setNumberOfArrowsLeft(String arrows) {

    }

    /**
     * update position after receiving in the reception class
     * the data of the new position of the arrow considering the wind
     *
     * @param finaPosition: position of the deflected arrow
     */

    @Override
    public void setPositionAfterWind(String[] finalPosition) {

        this.finalPosition[0] = Double.valueOf(finalPosition[0]);
        this.finalPosition[1] = Double.valueOf(finalPosition[1]);
        this.finalPosition[2] = Double.valueOf(finalPosition[2]);

        drawArrow(this.finalPosition[0], this.finalPosition[1], colorArrow, 30);


    }


    /**
     * update the opponent arrow position considering the wind
     * @param finaPosition : arrow position deflected by the wind
     */
    public void setPositionAdversaireAfterWind(String[] finalPosition) {

        this.finalPositionAdversaire[0] = Double.valueOf(finalPosition[0]);
        this.finalPositionAdversaire[1] = Double.valueOf(finalPosition[1]);
        this.finalPositionAdversaire[2] = Double.valueOf(finalPosition[2]);
    }

    @Override
    public void setIdPlayer(String id) {

        this.idPlayer = Integer.valueOf(id);
    }

    @Override
    public void setWinPlayer(String winPlayer) {

    }


    @Override
    public void sendData() {

    }

    @Override
    public void sendPause() {

    }


    @Override
    public void setPause(String pause) {

    }

    /**
     * initialize the contact with the proxyView and start the reception thread
     */

    public void setProxy(Socket socket) throws IOException {
        _input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread inputThread = new Thread(new GameView.Reception());
        inputThread.start();
    }

    /**
     * This reception class will be launched in a
     * reception thread , this class will take care of receiving the
     * information sent by the proxyView in the server package
     * this information has been processed by the model and sent to the
     * presenter to send to the proxyView to finally land here
     * in the real view, the view updates with the new score
     * and the new position of the arrow
     */

    final class Reception implements Runnable {


        @Override
        public void run() {
            while (true) {
                try {
                    String dataReceived = _input.readLine();
                    if (dataReceived == null) {
                        System.out.println("Connexion rompue : abandon");
                        System.exit(1);
                    } else {


                        if (dataReceived.split(",")[1].equals("PopupStop")) {

                            id = Integer.valueOf(dataReceived.split(",")[0]);
                            if (id == 1) {

                                colorArrow = ArrowColor.BLUE;
                                colorArrowAdversaire = ArrowColor.ORANGE;
                            } else {
                                colorArrow = ArrowColor.ORANGE;
                                colorArrowAdversaire = ArrowColor.BLUE;
                            }

                            Platform.runLater(() -> {

                                popup.remove();

                            });
                        } else if (dataReceived.split(",")[0].equals("pause")) {

                            if (dataReceived.split(",")[1].equals("true")) {


                                Platform.runLater(() -> {

                                    Popup popupEnd = new Popup("Vous avez fini vos fleches ,Attendez que votre adversaire finisse pour avoir le resultat...", width / 2, height * 3 / 4, game_pane);
                                    popup = new Popup("En attente du joueur 2...", width / 2, height * 3 / 4, game_pane);
                                    popup.launch();


                                });
                            } else {

                                Platform.runLater(() -> {


                                    popup.remove();
                                });

                            }
                        } else {
                            String[] data = dataReceived.split(",");

                            String score = data[0];

                            String[] finalPositonReceived = new String[3];
                            finalPositonReceived[0] = data[1];
                            finalPositonReceived[1] = data[2];
                            finalPositonReceived[2] = data[3];

                            String idReceived = data[4];
                            String numberOfArrowsLeft = data[5];
                            String win = data[6];

                            System.out.println("------------------------------------------------------------------");
                            System.out.println("View: j'ai reçu :");
                            System.out.println("\tId : " + idReceived);
                            System.out.println("\tscore : " + score);
                            System.out.println("\tfinalPosition : " + finalPositonReceived[0] + ", " + finalPositonReceived[1] + ", " + finalPositonReceived[2]);


                            if (Integer.valueOf(idReceived) == id) {


                                Platform.runLater(() -> {

                                    setScore(score);

                                    if (Integer.valueOf(numberOfArrowsLeft) < 0) {
                                        Popup popupEnd = new Popup("Vous avez fini vos fleches , Attendez que votre adversaire finisse pour avoir le resultat..", width / 2, height * 3 / 4, game_pane);

                                        popupEnd.launch();

                                        if (win.equals("true")) {

                                            Popup popupWin = new Popup("Vous avez gagné..", width / 2, height * 3 / 4, game_pane);
                                            popupWin.launch();
                                        } else if (win.equals("false")) {
                                            Popup popupWin = new Popup("Vous avez perdu..", width / 2, height * 3 / 4, game_pane);
                                            popupWin.launch();

                                        }

                                    } else {
                                        setPositionAfterWind(finalPositonReceived);

                                        drawTabArrow(Integer.valueOf(numberOfArrowsLeft));

                                    }

                                });
                            } else {
                                setPositionAdversaireAfterWind(finalPositonReceived);

                                scoreAdversaire = Integer.valueOf(score);
                                Platform.runLater(() -> {

                                    drawArrow(finalPositionAdversaire[0], finalPositionAdversaire[1], colorArrowAdversaire, 30);
                                    drawScoreAdversaire(scoreAdversaire);

                                    if (win.equals("false")) {

                                        Popup popupWin = new Popup("Vous avez gagné..", width / 2, height * 3 / 4, game_pane);
                                        popupWin.launch();
                                    } else if (win.equals("true")) {
                                        Popup popupWin = new Popup("Vous avez perdu..", width / 2, height * 3 / 4, game_pane);
                                        popupWin.launch();

                                    }
                                });

                            }

                            System.out.println("------------------------------------------------------------------");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @FXML
    private Canvas game_canvas;

    @FXML
    private Pane game_pane;


    /**
     * Generates the event at the moment of click on the target and invokes
     * the appropriate notification on the listener
     * Wakes the presenter up to do his job
     *
     * Here we are talking about delegation of user interactions
     */
    private void handleSayScore(double coordonnesArrowX, double coordonnesArrowY, int size, double speedWind, double distanceFromTargetZ, int id) {

        ClickEvent event = new ClickEvent(this, coordonnesArrowX, coordonnesArrowY, size, speedWind, distanceFromTargetZ, id);
        ;

        _presenter.sayScore(event);
    }


    /**
     * Generates the event at the moment of click on the target and invokes
     * the appropriate notification on the listener
     * Wakes the presenter up to do his job
     *
     * Here we are talking about delegation of user interactions
     */
    private void handlePause(boolean pause) {

        PauseEvent pauseEvent = new PauseEvent(this, pause);
        _presenter.sayPause(pauseEvent);
    }


    /**
     * javafx initialize method that manages the GUI
     */
    @FXML
    void initialize() {
        System.out.println("Level : " + level);


        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.width = gd.getDisplayMode().getWidth();
        this.height = gd.getDisplayMode().getHeight();
        distanceFromTargetZ = (double) (width / 2);


        switch (level) {
            case LEVEL1:

                size = 300;

                drawBackground(Background.NIGHT);
                drawWind((int) speedWind, 0.0);
                break;

            case LEVEL2:

                drawBackground(Background.NIGHT);
                size = 200;

                drawWind((int) speedWind, 45.0);
                break;

            case LEVEL3:

                drawBackground(Background.NIGHT);
                size = 200;

                drawWind((int) speedWind, 90.0);
                CursorVelocity.launch(100);
                break;
        }

        drawButtonHelp();
        drawButtonQuit();
        drawButtonPause();
        drawTarget();
        drawScore(0);
        drawScoreAdversaire(0);


        game_pane.setOnMouseClicked((e) -> {
            double x = e.getX();
            double y = e.getY();
            this.coordonnesArrowX = x;
            this.coordonnesArrowY = y;

            System.out.println("before handleSayScore");
            this.handleSayScore(this.coordonnesArrowX, this.coordonnesArrowY, this.size, this.speedWind, this.distanceFromTargetZ, this.id);
            System.out.println("After handle Sayscore");


        });
        game_canvas.setCursor(Cursor.getInstance().getShooter());
        zoom = new Zoom(game_canvas, height, width, size);
        zoom.init();
        drawTabArrow(30);
        game_canvas.setCursor(Cursor.getInstance().getShooter());
        zoom = new Zoom(game_canvas, height, width, size);
        zoom.init();
        popup = new Popup("En attente du joueur 2...", width / 2, height * 3 / 4, game_pane);
        popup.launch();

    }


    /**
     * Draw new background
     *
     * @param background the background to draw
     */
    private void drawBackground(Background background) {
        Image image = new Image(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(background.getPath())),
                width,
                height,
                false,
                false
        );
        GraphicsContext context = game_canvas.getGraphicsContext2D();
        context.drawImage(image, game_canvas.getLayoutX(), game_canvas.getLayoutY());
    }

    /**
     * Draw new score
     *
     * @param score the new score
     */
    private void drawScore(int score) {
        new Score(width * 1 / 2, height / 16, game_pane, false).draw(game_canvas.getGraphicsContext2D(), score);
    }

    /**
     * Draw new opponent score
     *
     * @param score
     */
    private void drawScoreAdversaire(int score) {
        new Score(width * 1 / 2, height * 15 / 16, game_pane, true).draw(game_canvas.getGraphicsContext2D(), score);
    }


    private void drawButtonHelp() {
        Button button = new CustomButton("aide2.png",
                (width * 7 / 8 + 67 * 2),
                (33),
                () -> System.out.println("click help"),
                "aide2_hover.png");
        game_pane.getChildren().add(button);
        button.setOnAction(
                event -> {
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.WINDOW_MODAL);
                    dialog.initOwner(game_canvas.getScene().getWindow());
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.getChildren().add(new Text("Help"));
                    Scene dialogScene = new Scene(dialogVbox, 300, 200);
                    dialog.setScene(dialogScene);
                    dialog.show();
                    CursorVelocity.stop();
                    dialog.setOnCloseRequest(event1 -> {
                        CursorVelocity.launch(CursorVelocity.getLastSize());
                    });
                });

    }

    /**
     * Draw quit button
     */
    private void drawButtonQuit() {
        Button button = new CustomButton("quitter2.png",
                (width * 7 / 8),
                (33),
                () -> System.out.println("click quit"),
                "quitter2_hover.png");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
        game_pane.getChildren().add(button);
    }

    /**
     * Draw pause
     *
     * @param canClose if the player can stop pause
     */
    private void launchPause(boolean canClose) {
        // Pause
        final Stage dialog = new Stage();
        if (canClose) handlePause(canClose);
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.setResizable(false);
        dialog.setHeight(900);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(game_canvas.getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        String text = canClose ? "Retour au jeu" : "En attente de votre adversaire...";
        Button b = new Button(text);
        b.setTranslateY(700);
        b.setPrefSize(500, 100);
        b.setAlignment(Pos.BASELINE_CENTER);
        b.setTextAlignment(TextAlignment.CENTER);
        b.setContentDisplay(ContentDisplay.CENTER);
        b.setDisable(!canClose);
        b.setOnAction(event -> {
            // Stop pause
            handlePause(false);
            dialog.close();
        });
        b.setStyle("-fx-text-fill: rgb(49, 89, 23);\n" +
                "    -fx-border-color: rgb(49, 89, 23);\n" +
                "    -fx-border-radius: 5;\n" +
                "    -fx-padding: 3 6 6 6;");
        dialogVbox.getChildren().add(b);
        dialogVbox.setStyle("-fx-background-color: transparent;");
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialogScene.setFill(Color.TRANSPARENT);
        dialog.setScene(dialogScene);
        dialog.show();
        CursorVelocity.stop();
        dialog.setOnCloseRequest(event1 -> {
            CursorVelocity.launch(CursorVelocity.getLastSize());
        });
    }

    /**
     * Draw pause button
     */
    private void drawButtonPause() {
        Button button = new CustomButton("pause.png",
                (width * 7 / 8 + 67),
                (33),
                () -> {
                    launchPause(true);
                },
                "pause_hover.png");
        game_pane.getChildren().add(button);
    }

    /**
     * Draw target
     */
    private void drawTarget() {

        GraphicsContext context = game_canvas.getGraphicsContext2D();
        Image image = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("target.png")));
        context.drawImage(image, (width - size) / 2, (height - size) / 2, size, size);
    }


    /**
     * Draw arrow
     *
     * @param x     the x position
     * @param y     the y position
     * @param color the color
     * @param size  the size
     */
    private void drawArrow(double x, double y, ArrowColor color, int size) {
        new Arrow(x, y, color, size).draw(game_canvas.getGraphicsContext2D());
    }


    /**
     * Draw wind icon
     *
     * @param speed   the speed of wind
     * @param degrees the orientation of wind
     */
    private void drawWind(int speed, double degrees) {
        new Wind(width * 17 / 20, height * 12 / 14, game_pane, degrees).draw(game_canvas.getGraphicsContext2D(), speed);
    }

    /**
     * Draw new arrow amount
     *
     * @param arrows the new amount
     */
    private void drawTabArrow(int arrows) {
        new TabArrow(width / 16, height / 12, game_pane).draw(game_canvas.getGraphicsContext2D(), arrows);
    }


    @Override
    public void close() {
        zoom.stop();
        CursorVelocity.stop();
    }
}
