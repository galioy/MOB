package gm.icanplay.model;

/**
 * Created by Martin on 10/27/14.
 */
public class ICanPlayController {

    RegisterTask registerKidTask;

    public void RegisterKid(String name, String group, String phone, String feed)
    {
        registerKidTask = new RegisterTask();
        String[] params = new String[4];
        params[0] = name;
        params[1] = group;
        params[2] = phone;
        params[3] = feed;

        registerKidTask.doInBackground(params);
    }
}
