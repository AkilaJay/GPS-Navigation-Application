package ca.uwaterloo_Lab4_204_04;
import android.util.Log;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class Accelerometer2 implements SensorEventListener {
	TextView output;

	float[] acceleration = new float[3];
	float[] prevAccel = {0.0f, 0.0f, 0.0f};
	float[] maxAccel = {0.0f, 0.0f, 0.0f};
	float[] wavemaxcounter= new float[5];
	float maxrange=0; 

	float maxVal=0; 
	float minVal=0; 
	final float timeDiv = 10;
	public Accelerometer2(){
	
	}
	

	public void onAccuracyChanged(Sensor s, int i) {}
	
	public void onSensorChanged(SensorEvent seA) {
		MainActivity.gravity = seA.values;
		if (seA.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			
			}
			
		
			
		}
	
	}
	


