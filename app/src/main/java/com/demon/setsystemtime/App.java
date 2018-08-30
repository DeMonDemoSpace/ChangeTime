package com.demon.setsystemtime;

import android.app.Application;

import com.instacart.library.truetime.TrueTime;

import java.io.IOException;

/**
 * @author DeMon
 * @date 2018/8/23
 * @description
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TrueTime.build().withNtpHost("time.ustc.edu.cn").initialize();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
