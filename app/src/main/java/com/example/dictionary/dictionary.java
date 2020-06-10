package com.example.dictionary;

import android.app.Application;

public class dictionary extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setSecondLogin(true);
    }

    private boolean secondLogin;

    public boolean isSecondLogin() {
        return secondLogin;
    }

    public void setSecondLogin(boolean secondLogin) {
        this.secondLogin = secondLogin;
    }
}
