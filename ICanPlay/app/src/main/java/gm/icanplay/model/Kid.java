package gm.icanplay.model;

/**
 * Created by milkncookiez on 10/26/14.
 */
public class Kid {
    private String name;
    private boolean canPlay = false;
    private String phone;
    private String groupid;

    public Kid(String _name, boolean _canPlay, String _phone, String _groupid)
    {
        this.name = _name;
        this.canPlay = _canPlay;
        this.phone = _phone;
        this.groupid = _groupid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

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
