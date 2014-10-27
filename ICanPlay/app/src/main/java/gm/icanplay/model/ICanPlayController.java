package gm.icanplay.model;

import android.os.AsyncTask;

import gm.icanplay.StartScreen;

/**
 * Created by Martin on 10/27/14.
 */
public class ICanPlayController {

    public void RegisterKid(String name, String group, String phone, String feed,StartScreen activity)
    {
        new RegisterTask(activity).execute(name, group, phone, feed);
    }
}
