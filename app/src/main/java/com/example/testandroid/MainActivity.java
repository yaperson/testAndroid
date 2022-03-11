package com.example.testandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    View btn1;
    SensorManager sensorManager;
    Sensor sensor;

    private SensorManager senSensorManager;
    private Sensor senAccelerometer;

    private int accX;
    private int accY;
    int cpt = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void heDoesNotAssume(View view) {
        cpt ++;

        TextView text = (TextView)findViewById(R.id.textView);
        TextView score = (TextView)findViewById(R.id.score);

        score.setText("Score = " + cpt);
        text.setText("T'es sur de toi ?");

        if (cpt >= 100) {
            text.setText("Tu est donc définitivement débile, tapé 100 fois sur un bouton... ");
            Button btnNot = (Button) this.findViewById(R.id.actionButtonNo);
            btnNot.setVisibility(View.INVISIBLE);
        }

        Toast.makeText(this, "es tu sur ?", Toast.LENGTH_LONG).show();

    }

    public void HE_SAID_YES(View view) {
        TextView text = (TextView)findViewById(R.id.textView);
        Toast.makeText(this, "Je sais", Toast.LENGTH_LONG).show();
        text.setText("Je le savait déjà...");

        Button btnNot = (Button) this.findViewById(R.id.actionButtonNo);
        btnNot.setVisibility(View.VISIBLE);
        if (cpt >= 10) {
            cpt = 0;
            text.setText("");
            Toast.makeText(this, "Et en plus il abandonne !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = event.values[0];
            double y = event.values[1];

            TextView textViewX =  (TextView)findViewById(R.id.textViewX);
            TextView textViewY =  (TextView)findViewById(R.id.textViewY);
            Button btnNot = (Button) this.findViewById(R.id.actionButtonNo);

            float targetX, targetY;

            accX = (int) (x*100);
            accY = (int) (y*100);

            int  sizeX = getWindowManager().getDefaultDisplay().getWidth();
            int  sizeY = getWindowManager().getDefaultDisplay().getHeight();


            textViewX.setText("Value X =" + accX);
            textViewY.setText("Value Y =" + accY);


            if (Math.abs(accX) > 50) {
                targetX = btnNot.getTranslationX() + (accX / 10);
                if ((targetX > -20) && (targetX < sizeX - 70))
                    btnNot.setTranslationX((targetX));
            }

            if (Math.abs(accY) > 50) {
                targetY = btnNot.getTranslationY() + (accY / 10);
                if ((targetY > -20) && (targetY < sizeY - 70))
                    btnNot.setTranslationY((targetY));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void onResume() {
        super.onResume();
        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

}