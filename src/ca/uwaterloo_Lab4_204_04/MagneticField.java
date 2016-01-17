package ca.uwaterloo_Lab4_204_04;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MagneticField implements SensorEventListener {
	TextView output;
	

	float geomagnetic[] = new float[3];
	public static float East;
	public static float Angle;
	public static float disN = 0; 
	public static float disE = 0;
	public static float def;
	float preAngle=0;
		
	float[] magneticField = new float[3];
	float[] maxField = {0.0f, 0.0f, 0.0f};
	public MagneticField(TextView outputView){
		output = outputView;
	}
	public void onAccuracyChanged(Sensor s, int i) {}
	
	public void onSensorChanged(SensorEvent se) {
		//Max Value
		if (se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
			
			float[] R = new float[9];
			float[] I = new float[9];
			boolean success = SensorManager.getRotationMatrix(R, I,MainActivity.gravity, se.values);
			if (success && MainActivity.gravity != null && se.values != null){
				float orientation[] = new float[3];
				SensorManager.getOrientation(R, orientation);
				
				Angle= (float) Math.round(orientation[0]*180/3.14159f);
//				if(Math.abs(Angle-preAngle)<40){
//					Angle = preAngle;
//					preAngle = Angle;
//				}
//				else {
//					preAngle = Angle;
//				}
				if(Angle < 0){
					def =  360 + Angle;
				}
				else{
					def = Angle;
				}
						
				//output.setText(magFieldMsg + "\n");
				
			}
			
			
		}
	
	}

}
