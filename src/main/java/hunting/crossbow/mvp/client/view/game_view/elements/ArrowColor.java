package hunting.crossbow.client.view.game_view.elements;

public enum ArrowColor {
    BLUE("fleche_bleue.png"),
    ORANGE("fleche_orange.png");

    private String path;

    /**
     * Constructor
     *
     * @param path the path of the image from res folder
     */
    ArrowColor(String path) {
        this.path = path;
    }

    /**
     * Get the image path
     *
     * @return the image path
     */
    public String getPath() {
        return path;
    }
}
