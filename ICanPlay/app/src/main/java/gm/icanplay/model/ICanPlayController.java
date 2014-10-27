package gm.icanplay.model;

import android.os.AsyncTask;

/**
 * Created by Martin on 10/27/14.
 */
public class ICanPlayController {

    RegisterTask registerKidTask;

    public void RegisterKid(String name, String group, String phone, String feed)
    {
        registerKidTask = new RegisterTask();
        registerKidTask.execute(name, group, phone, feed);
    }
}
