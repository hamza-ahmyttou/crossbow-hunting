package hunting.crossbow.client.view.game_view;

public enum Background {
    DAY("background.jpg"),
    LAKE("background-lake.jpg"),
    NIGHT("background-night.jpg"),
    PATH("background-path.jpg"),
    STORM("background-storm.jpg");

    private String path;

    /**
     * Constructor
     *
     * @param path the path of the background from res folder
     */
    Background(String path) {
        this.path = path;
    }

    /**
     * Get the path of the background
     *
     * @return the path of the background
     */
    public String getPath() {
        return path;
    }
}
