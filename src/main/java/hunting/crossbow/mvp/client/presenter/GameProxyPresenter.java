package hunting.crossbow.client.presenter;

import hunting.crossbow.client.view.events.ClickEvent;
import hunting.crossbow.client.view.events.PauseEvent;
import hunting.crossbow.client.view.game_view.IView;
import hunting.crossbow.server.model.GameModel;
import hunting.crossbow.server.model.events.FinalPositionEvent;
import hunting.crossbow.server.model.events.ScoreEvent;
import hunting.crossbow.server.model.events.WinEvent;
import hunting.crossbow.server.presenter.IPresenter;
import hunting.crossbow.server.presenter.PresenterListener;
import org.w3c.dom.events.Event;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public final class GameProxyPresenter implements IPresenter, PresenterListener {

    private IView _view;
    private PrintWriter _output;

    /**
     * Constructor
     *
     * @param socket  the socket used to communicate with the presenter of the server package
     *
     */
    public GameProxyPresenter(Socket socket ) throws IOException {
        _output = new PrintWriter(socket.getOutputStream());
    }


    @Override
    public void setModel( GameModel model ) {
    }

    @Override
    public void addView( IView view ) {
        _view = view;
    }



    /**
     * Sends to the  presenter in the server package
     * that a player has paused the game, this
     * allows to inform the other player of the break .
     * [! ] Note that the pause is done in the view,
     * the view refuses to update when we pause, we could have
     * done it in the server  (Model no longer updates) but, this way w should
     * stop the recepetion thread of the server presenter
     *
     * @param event the event that tells whether the game has been paused or not
     */
    @Override
    public void sayPause(PauseEvent event) {
        String pause = String.valueOf(event.isPause());
        String dataSocket = "";

        dataSocket += "pause,"+pause;

        sendMessage(dataSocket);

    }

    /**
     *
     * Sends to the server presenter data needed by CalculScore to compute the score
     *
     *
     * @param event the event containing data needed to compute the score
     */
    @Override
    public void sayScore(ClickEvent event) {


        System.out.println("we are in say score of the presenter");

        String dataSocket = "";

        String coordonnesArrowX = String.valueOf(event.getCoordonnesArrowX());
        String coordonnesArrowY = String.valueOf(event.getCoordonnesArrowY());
        String targetSize = String.valueOf(event.getSize());
        String speedWind = String.valueOf(event.getSpeedWind());
        String distanceFromTargetZ = String.valueOf(event.getDistanceFromTargetZ());
        String idPlayer = String.valueOf(event.getId());

        dataSocket += coordonnesArrowX + "," + coordonnesArrowY + "," + targetSize + "," + speedWind + ","+distanceFromTargetZ + "," + idPlayer;


        sendMessage(dataSocket);
    }



    @Override
    public void updateScore(ScoreEvent event) {

    }


    @Override
    public void updateFinalPosition(FinalPositionEvent event) {

    }


    @Override
    public void updateWin(WinEvent event) {

    }

    /**
     *
     * Sends to the server presenter data in the form of strings separated by commas to form one string
     * that we will split at reception
     *
     * @param message the string containing data
     */
    private void sendMessage( String message ) {
        _output.println(message);
        System.out.println("Proxy presenter: j'ai envoyé : " + message);
        _output.flush();
    }



    @Override
    public void handleEvent(Event evt) {

    }
}