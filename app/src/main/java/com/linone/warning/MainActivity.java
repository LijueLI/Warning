package com.linone.warning;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private JsonObject[] Data = null;
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private TextView warning_t,waterdepth,rain1,rain24,temp,vol;
    private ImageView warning;
    private AudioManager audioManager;
    private Ringtone r;
    private Vibrator v;
    private Uri uri;
    private boolean blink = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        warning_t = findViewById(R.id.warning_t);
        warning = findViewById(R.id.warning);
        waterdepth = findViewById(R.id.waterdepth_d);
        rain1 = findViewById(R.id.rain_1h_d);
        rain24 = findViewById(R.id.rain_24h_d);
        temp = findViewById(R.id.temp_d);
        vol = findViewById(R.id.voltage_d);
        getData();
        audioManager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),AudioManager.FLAG_PLAY_SOUND);
        uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        v=(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
        warningimgblink();
    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                       Document document = Jsoup.connect("https://api.thingspeak.com/channels/699140/feeds.html?results=1").get();
                       Elements elements = document.select("body");
                       String S = elements.text();
                       com.google.gson.JsonObject j = gson.fromJson(S, com.google.gson.JsonObject.class);
                       JsonArray K =j.get("feeds").getAsJsonArray();
                       Data = gson.fromJson(K,JsonObject[].class);
                    } catch (IOException e) {
                        Log.e("getdata()", "connecterror");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            r = RingtoneManager.getRingtone(MainActivity.this, uri);
                            if(Integer.valueOf(Data[0].getAlarm())==1){
                                r.stop();
                                r.play();
                                v.vibrate(9000);
                                warning_t.setVisibility(View.VISIBLE);
                                blink = true;
                            }
                            else{
                                r.stop();
                                warning_t.setVisibility(View.INVISIBLE);
                                blink = false;
                            }
                            waterdepth.setText(Data[0].getWaterLevel());
                            rain1.setText(Data[0].getRainfall_1hour());
                            rain24.setText(Data[0].getRainfall_24hour());
                            temp.setText(Data[0].getTemperature());
                            vol.setText(Data[0].getVoltage());
                        }
                    });
                    try{
                        Thread.sleep(10000);
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }

    private void warningimgblink(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(blink) {
                                if (warning.getVisibility() == View.INVISIBLE)
                                    warning.setVisibility(View.VISIBLE);
                                else warning.setVisibility(View.INVISIBLE);
                            }
                            else{
                                if (warning.getVisibility() == View.VISIBLE)
                                    warning.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
                    try{
                        Thread.sleep(800);
                    }
                    catch (InterruptedException e) {
                    }
                }
            }
        }).start();
    }
}
