package hunting.crossbow.server.model;

import hunting.crossbow.server.model.events.WinEvent;
import hunting.crossbow.server.presenter.PresenterListener;


public class GameModel {

    private String idCurrentPlayer;
    private double[] _positionAfterWind;
    private int id;
    private PresenterListener _presenter;
    private Player player;
    private Player otherPlayer;

    /**
     * Note that even though the model interacts with the presenter, it has no idea
     * of its existence thanks to the Observer interface (Presenterlistener)
     * that represents the firewall.
     */


    public void initPlayers(int idPlayer, int idOtherPlayer) {
        player = new Player(idPlayer);
        otherPlayer = new Player(idOtherPlayer);
    }

    public void setPresenter(PresenterListener presenter) {
        _presenter = presenter;
    }

    public Player getotherPlayer() {
        return otherPlayer;
    }

    public Player getPlayer() {
        return player;
    }

    public String getIdCurrentPlayer() {
        return idCurrentPlayer;
    }

    public int getId() {
        return id;
    }

    public double[] get_positionAfterWind() {
        return _positionAfterWind;
    }


    public void setScore(int score) {


        if (Integer.valueOf(idCurrentPlayer) == 1) {


            player.setScore(player.getScore() + score);
            player.setNumbreOfArrowsLeft(player.getNumbreOfArrowsLeft() - 1);

        } else {


            otherPlayer.setScore(otherPlayer.getScore() + score);
            otherPlayer.setNumbreOfArrowsLeft(otherPlayer.getNumbreOfArrowsLeft() - 1);

        }

        checkWin();
    }

    /**
     * Updates the position considering the  wind
     */
    public void setPositionAfterWind(double[] coordinates) {

        _positionAfterWind = new double[3];

        _positionAfterWind[0] = coordinates[0];
        _positionAfterWind[1] = coordinates[1];
        _positionAfterWind[2] = coordinates[2];

    }

    public void setIdPlayer(String id) {

        this.idCurrentPlayer = id;
    }

    /**
     * unleashes the Win event when the game is finished
     * which makes it possible to notify
     * the presenter that will send this information to the view
     * that displays the popup of the win
     */

    public void checkWin() {


        if (player.getNumbreOfArrowsLeft() < 0 && otherPlayer.getNumbreOfArrowsLeft() < 0) {

            WinEvent winEvent;
            if (Integer.valueOf(idCurrentPlayer) == 1) {
                if (player.getScore() > otherPlayer.getScore()) {
                    winEvent = new WinEvent(this, true, false);
                    _presenter.updateWin(winEvent);
                } else {
                    winEvent = new WinEvent(this, false, true);
                    _presenter.updateWin(winEvent);
                }
            } else {

                if (player.getScore() > otherPlayer.getScore()) {
                    winEvent = new WinEvent(this, false, true);
                    _presenter.updateWin(winEvent);
                } else {
                    winEvent = new WinEvent(this, true, false);
                    _presenter.updateWin(winEvent);
                }


            }
        }
    }

}
