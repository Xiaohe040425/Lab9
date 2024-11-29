package com.example.lab9;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MyService extends Service {

    static Boolean flag = false;
    private int h = 0, m = 0, s = 0;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        flag = intent.getBooleanExtra("flag", false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    s++;
                    if (s >= 60) {
                        s = 0;
                        m++;
                        if (m >= 60) {
                            m = 0;
                            h++;
                        }
                    }

                    Intent broadcastIntent = new Intent("MyMessage");
                    broadcastIntent.putExtra("H", h);
                    broadcastIntent.putExtra("M", m);
                    broadcastIntent.putExtra("S", s);
                    sendBroadcast(broadcastIntent);
                }
            }
        }).start();

        return START_STICKY;
    }

}
