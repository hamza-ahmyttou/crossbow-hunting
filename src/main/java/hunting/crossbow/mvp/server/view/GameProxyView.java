package hunting.crossbow.server.view;

import hunting.crossbow.client.view.game_view.IView;
import hunting.crossbow.server.presenter.PresenterListener;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public final class GameProxyView implements IView {

    private PrintWriter _output;
    private PrintWriter _output2;
    private String score;
    private String numberOfArrowsLeft;
    private String[] finalPosition = new String[3];
    private String idPlayer;
    private String dataToSend = "";
    private String pause;
    private String winPLayer = "GameNotFinishedYet";


    /**
     * Constructor
     *
     * @param socket the socket used to contact the client view
     */

    public GameProxyView(Socket socket) throws IOException {
        _output = new PrintWriter(socket.getOutputStream());
    }

    /**
     * Creates a printWriter to send data to the client view using a socket
     *
     * @param socket the socket used to send data in the form of a string
     * @throws IOException
     */
    public void setPrintWriterSocketAdversaire(Socket socket) throws IOException {
        _output2 = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void setPresenter(PresenterListener presenter) {
    }


    public void setScore(String score) {
        this.score = score;
    }

    public void setNumberOfArrowsLeft(String numberOfArrowsLeft) {
        this.numberOfArrowsLeft = numberOfArrowsLeft;
    }

    @Override
    public void setPositionAfterWind(String[] coordinates) {
        for (int i = 0; i < 3; i++) {
            this.finalPosition[i] = coordinates[i];
        }

    }

    @Override
    public void setIdPlayer(String id) {

        this.idPlayer = id;
    }

    @Override
    public void setWinPlayer(String winPLayer) {
        this.winPLayer = winPLayer;
    }


    @Override
    public void setPause(String pause) {
        this.pause = pause;
    }

    @Override
    public void sendPause() {
        dataToSend += "pause," + this.pause;
        if (_output2 != null) {
            System.out.println("Proxy View just send pause to other player");
            _output2.println(dataToSend);
        }
        System.out.println("Proxy view: j'ai envoyé : " + dataToSend);
        System.out.println("------------------------------------------------------------------");
        dataToSend = "";
        if (_output2 != null) {
            _output2.flush();
        }


    }


    /**
     * Sends to the client view data in the form of strings separated by commas to form one string
     * that we will split at reception
     */
    public void sendData() {

        dataToSend += score + "," + finalPosition[0] + "," + finalPosition[1] + "," + finalPosition[2] + "," + this.idPlayer + "," + numberOfArrowsLeft + "," + winPLayer;
        _output.println(dataToSend);
        if (_output2 != null) {

            _output2.println(dataToSend);
        }
        System.out.println("Proxy view: j'ai envoyé : " + dataToSend);
        System.out.println("------------------------------------------------------------------");

        dataToSend = "";
        _output.flush();

        if (_output2 != null) {
            _output2.flush();
        }
    }


}

