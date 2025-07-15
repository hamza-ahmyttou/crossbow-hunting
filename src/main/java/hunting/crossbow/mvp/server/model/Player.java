package hunting.crossbow.server.model;


import hunting.crossbow.client.view.game_view.elements.Arrow;



public class Player {

    private int id;
    private String name;
    private int score;
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    private int numbreOfArrowsLeft = 30;
    private Arrow arrow;


    public Player(int id) {

        this.id = id;
        score = 0;
    }

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        score = 0;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Arrow getArrow() {
        return arrow;
    }

    public void setArrow(Arrow arrow) {
        this.arrow = arrow;
    }

    public int getNumbreOfArrowsLeft() {
        return numbreOfArrowsLeft;
    }

    public void setNumbreOfArrowsLeft(int numbreOfArrowsLeft) {
        this.numbreOfArrowsLeft = numbreOfArrowsLeft;
    }


    public void setScore(int score) {
        this.score = score;
    }


}
