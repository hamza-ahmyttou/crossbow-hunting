package hunting.crossbow.server.presenter;

import hunting.crossbow.client.view.events.ClickEvent;
import hunting.crossbow.client.view.events.PauseEvent;
import hunting.crossbow.client.view.game_view.IView;
import hunting.crossbow.server.model.Bolt;
import hunting.crossbow.server.model.CalculScore;
import hunting.crossbow.server.model.GameModel;
import hunting.crossbow.server.model.events.FinalPositionEvent;
import hunting.crossbow.server.model.events.ScoreEvent;
import hunting.crossbow.server.model.events.WinEvent;
import org.w3c.dom.events.Event;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public final class GamePresenter implements IPresenter, PresenterListener {


    private boolean pause;
    private GameModel _model;
    private IView view;


    public GameModel get_model() {
        return _model;
    }

    @Override
    public void setModel(GameModel model) {
        _model = model;
    }

    @Override
    public void addView(IView view) {
    }

    @Override
    public void sayScore(ClickEvent event) {

    }


    @Override
    public void sayPause(PauseEvent event) {

    }


    /**
     * Manages the event ScoreEvent
     * The presenter is subscribed to this method
     *
     * @param event the event triggered by Calculscore class of the model
     *              giving information about the score and the number of remaining arrows
     */
    @Override
    public void updateScore(ScoreEvent event) {
        _model.setScore(event.getScore());
        System.out.println();
        if (Integer.valueOf(_model.getIdCurrentPlayer()) == 1) {
            view.setNumberOfArrowsLeft(String.valueOf(_model.getPlayer().getNumbreOfArrowsLeft()));
            view.setScore(String.valueOf(_model.getPlayer().getScore()));
        } else {
            view.setNumberOfArrowsLeft(String.valueOf(_model.getotherPlayer().getNumbreOfArrowsLeft()));
            view.setScore(String.valueOf(_model.getotherPlayer().getScore()));
        }
        view.sendData();
    }


    /**
     * Manages the event FinalPositionEvent
     * This method is the one the presenter is subscribed to
     *
     * @param event the event triggered by Bolt class
     *              giving the final position
     */
    @Override
    public void updateFinalPosition(FinalPositionEvent event) {
        String[] positionAfterWind = new String[3];

        _model.setPositionAfterWind(event.getCoordinates());
        for (int i = 0; i < 3; i++) {
            positionAfterWind[i] = String.valueOf((event.getCoordinates())[i]);
        }
        view.setPositionAfterWind(positionAfterWind);
    }

    /**
     * Updates the winner
     * The model stores data
     * The model knows turns of the game,
     * when it figured out that the game has finished
     * it triggers the event set as a parameter
     *
     * @param event the event that determine who won and who lost
     */
    @Override
    public void updateWin(WinEvent event) {
        view.setWinPlayer(String.valueOf(event.isWinPlayer()));
    }


    public void addView(IView view, Socket socket) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Thread inputThread = new Thread(new Reception(view, input));
        inputThread.start();
    }

    /**
     * Calls the model so that it
     * does its job of calculating the new position
     * considering the wind
     */
    public void sayPositionAfterWind(IView _view, String x, String y, String z, String speedWind, String targetSize, int graphicX, int graphicY) {
        this.view = _view;
        Bolt bolt = new Bolt(3.0, 100);
        bolt.setPresenter(this);
        bolt.fly(Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), Double.valueOf(speedWind));
        sayScore(_view, String.valueOf(_model.get_positionAfterWind()[0]), String.valueOf(_model.get_positionAfterWind()[1]), targetSize, graphicX, graphicY);

    }

    /**
     * Calls the model so it computes the new score
     */

    public void sayScore(IView _view, String x, String y, String targetSize, int graphicX, int graphicY) {
        CalculScore testScore = new CalculScore();
        testScore.setPresenter(this);
        _model.setPresenter(this);
        testScore.calculScore(Double.valueOf(x), Double.valueOf(y), Integer.valueOf(targetSize), graphicX, graphicY);

    }


    /**
     * Calls the model and the view so that they update data of the current player who is playing
     */
    public void sayIdPlayer(IView _view, String id) {
        _model.setIdPlayer(id);
        _view.setIdPlayer(id);
    }


    /**
     * Calls the model to update the score of the opponent player
     */
    public void updateScoreAdversaire(String score) {
        _model.getotherPlayer().setScore(Integer.valueOf(score));
    }

    @Override
    public void handleEvent(Event evt) {
    }


    /**
     * Receives data sent by the proxy presenter in the client package
     * Sends to the model the data received for processing and return the score
     * It is launched in a reception thread
     * <p>
     * Data received is in the form of strings separated by commas to form one string,
     * here we split this string so that the model process them
     */

    final class Reception implements Runnable {


        private final IView _view;
        private final BufferedReader _input;
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        Reception(IView view, BufferedReader input) {
            _view = view;
            _input = input;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String dataSocket = _input.readLine();
                    if (dataSocket == null) {
                        System.out.println("Connexion rompue : abandon du jeu");
                        System.exit(1);
                    } else {
                        System.out.println("Presenter : j'ai reçu : " + dataSocket);
                    }
                    if (dataSocket.split(",")[0].equals("scoreAdversaire")) {
                        updateScoreAdversaire(dataSocket.split(",")[0]);
                    } else if (dataSocket.split(",")[0].equals("pause")) {
                        if (dataSocket.split(",")[1].equals("true")) {
                            pause = true;
                            _view.setPause("true");
                            _view.sendPause();
                        } else {
                            pause = false;
                            _view.setPause("false");
                            _view.sendPause();
                        }
                    } else {
                        if (!pause) {
                            String[] data = dataSocket.split(",");
                            String coordonnesArrowX = data[0];
                            String coordonnesArrowY = data[1];
                            String targetSize = data[2];
                            String speedWind = data[3];
                            String distanceFromTargetZ = data[4];
                            String idPlayer = data[5];
                            sayIdPlayer(_view, idPlayer);
                            sayPositionAfterWind(_view, coordonnesArrowX, coordonnesArrowY, distanceFromTargetZ, speedWind, targetSize, gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
