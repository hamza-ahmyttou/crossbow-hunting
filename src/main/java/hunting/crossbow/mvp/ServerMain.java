package hunting.crossbow;

import hunting.crossbow.client.view.game_view.IView;
import hunting.crossbow.server.model.GameModel;
import hunting.crossbow.server.presenter.GamePresenter;
import hunting.crossbow.server.view.GameProxyView;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;

public final class ServerMain {

    public static final int PORT_NUMBER = 2019;
    private PrintWriter _output;

    public static void main(String[] args) {
        new ServerMain().startServer(PORT_NUMBER);
    }

    public static ResourceBundle getMessageBundle() {
        return ResourceBundle.getBundle("hunting.crossbow.MessageBundle");
    }

    private void startServer(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Le serveur est à l'écoute du port : " + port + " et attend les clients.");
            GamePresenter presenter = new GamePresenter();
            GameModel model = new GameModel();
            presenter.setModel(model);
            Thread thread = new Thread(new ClientsConnection(serverSocket, presenter));
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final class ClientsConnection implements Runnable {

        private final ServerSocket _serverSocket;
        private final GamePresenter _presenter;

        ClientsConnection(ServerSocket serverSocket, GamePresenter presenter) {
            _serverSocket = serverSocket;
            _presenter = presenter;
        }


        @Override
        public void run() {
            try {

                Socket socket[] = new Socket[2];
                int idPlayer = 0;
                int idPlayerAdv = 0;
                String idText;

                System.out.println("File Absolute Path :" + new File(".").getAbsolutePath());
                InputStream flux = new FileInputStream("./playerInfo.txt");
                InputStreamReader lecture = new InputStreamReader(flux);
                BufferedReader buff = new BufferedReader(lecture);
                while ((idText = buff.readLine()) != null) {
                    System.out.println("ID of the players  : " + idText);
                    idPlayer = Integer.valueOf(idText.split(",")[0]);
                    idPlayerAdv = Integer.valueOf(idText.split(",")[1]);
                    _presenter.get_model().initPlayers(idPlayer, idPlayerAdv);
                }
                buff.close();
                IView view[] = new GameProxyView[2];
                // Accept only 2 clients for demonstration.
                for (int i = 0; i < 2; i++) {
                    socket[i] = _serverSocket.accept();
                    System.out.println("Serveur : un client vient de se connecter.");
                    view[i] = new GameProxyView(socket[i]);
                }

                for (int i = 0; i < 2; i++) {
                    if (socket[1 - i] != null) {
                        ((GameProxyView) view[i]).setPrintWriterSocketAdversaire(socket[1 - i]);
                    }
                    _presenter.addView(view[i], socket[i]);
                    System.out.println("Serveur : le jeu peut commencer");
                    _output = new PrintWriter(socket[0].getOutputStream());
                    _output.println(String.valueOf(idPlayer) + ",PopupStop");
                    _output.flush();
                    _output = new PrintWriter(socket[1].getOutputStream());
                    _output.println(String.valueOf(idPlayerAdv) + ",PopupStop");
                    _output.flush();
                }
            } catch (IOException e) {
                System.err.println("Erreur serveur: " + e.getMessage());
            }
        }


    }
}
