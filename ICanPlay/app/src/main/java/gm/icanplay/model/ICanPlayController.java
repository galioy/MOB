package gm.icanplay.model;

import gm.icanplay.StartScreen;

/**
 * Created by Martin on 10/27/14.
 */
public class ICanPlayController {

    public void buttonAction(String name, String group, String phone, String feed, StartScreen activity, String cmd) {
        new ButtonsAsyncTask(activity).execute(name, group, phone, feed, cmd);
    }
}
