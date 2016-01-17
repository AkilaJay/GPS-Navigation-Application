package ca.uwaterloo_Lab4_204_04;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class RotationVector implements SensorEventListener {
	TextView output;
	float[] rotVector = new float[3];
	
	
	public RotationVector (TextView outputView){
		output = outputView;
	}
	public void onAccuracyChanged(Sensor s, int i) {}
	
	public void onSensorChanged(SensorEvent se) {
		
		if (se.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			//Max Value
			MainActivity.testVector = se.values;
			for(int i = 0; i < 3; i++) {
				rotVector[i] = se.values[i];
				if(Math.abs(rotVector[i]) > MainActivity.maxVector[i]) {
					MainActivity.maxVector[i] = Math.abs(rotVector[i]);
				}
				
			}
			
			String s = String.format("(%.2f, %.2f, %.2f)", rotVector[0], rotVector[1], rotVector[2]);
			//String max = String.format("(%.2f, %.2f, %.2f)", MainActivity.maxVector, MainActivity.maxVector[1], MainActivity.maxVector[2]);
			//String maxValues = "\nMax Rotation Vector: " + max;
			//String rotVectorMsg = "Rotational Vector Value: " + s + maxValues;
			//output.setText(rotVectorMsg);
			
		}
	
	}

}
