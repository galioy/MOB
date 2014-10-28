package gm.icanplay.model;

import gm.icanplay.StartScreen;

/**
 * Created by Martin on 10/27/14.
 */
public class ICanPlayController {

    public void buttonAction(String name, String group, String phone, String feed, StartScreen activity, String cmd) {
        new ButtonsAsyncTask(activity).execute(name, group, phone, feed, cmd);
    }

//    public void kidCanPlay(String name, String group, String feed, StartScreen activity) {
//        new CanPlayTask(activity).execute(name, group, feed);
//    }
//
//    public void kidCannotPlay(String name, String group, String feed, StartScreen activity) {
//        new CannotPlayTask(activity).execute(name, group, feed);
//    }
}
