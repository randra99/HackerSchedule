package com.example.wildcats;

import android.app.IntentService;
import android.content.Intent;

public class HackathonProgressService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public HackathonProgressService() {
        super("Hackathon Progress");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
