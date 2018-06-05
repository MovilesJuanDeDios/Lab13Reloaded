package com.example.lab13_reloaded.data;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;
    private static DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
        databaseHelper = new DatabaseHelper();
        DatabaseManager.initializeInstance(databaseHelper);
    }

    public static Context getContext() {
        return context;
    }
}
