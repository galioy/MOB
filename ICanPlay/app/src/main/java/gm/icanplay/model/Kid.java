package gm.icanplay.model;

/**
 * Created by milkncookiez on 10/26/14.
 */
public class Kid {
    private String name;
    private boolean canPlay = false;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanPlay() {
        return canPlay;
    }

    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }
}
