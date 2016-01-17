package ca.uwaterloo_Lab4_204_04;
import ca.uwaterloo_Lab4_204_04.MainActivity.PlaceholderFragment;
import android.util.Log;

import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class Accelerometer implements SensorEventListener {
	TextView output;

	float[] acceleration = new float[3];
	float[] prevAccel = {0.0f, 0.0f, 0.0f};
	float[] maxAccel = {0.0f, 0.0f, 0.0f};
	float[] wavemaxcounter= new float[5];
	static float Xdirection;
	static PointF up;
	static float Ydirection;
	float findangle = 0;
	float maxrange=0; 
	float rads = 0; 
	float def=0; 
	float error = 0; 
	float maxVal=0; 
	float minVal=0; 
	final float timeDiv = 10;
	public Accelerometer(TextView outputView){
		output = outputView;
	}
	

	public void onAccuracyChanged(Sensor s, int i) {}
	
	public void onSensorChanged(SensorEvent seA) {
		
		if (seA.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
				
				
				for(int i = 0; i < 3; i++) {
					prevAccel[i] = acceleration[i];
					acceleration[i] = lowPassFilter(seA.values[i], prevAccel[i], timeDiv);
					if(Math.abs(acceleration[i]) > maxAccel[i]) {
						maxAccel[i] = Math.abs(acceleration[i]);
					}
					
				
					
				}
		
			
			
			
			MainActivity.graph.addPoint(acceleration);
			
			String s = String.format("(%.2f, %.2f, %.2f)", acceleration[0], acceleration[1], acceleration[2]);
			String max = String.format("(%.2f, %.2f, %.2f)", maxAccel[0], maxAccel[1], maxAccel[2]);
			String maxValues = "\nMax Acceleration (m/s^2): " + max;
			//State Machine 
			if (MainActivity.state ==0 && acceleration[2]>0)
			{
				for (int i = 0; i < 3; i++) {
					MainActivity.maxVector[i] = 0.0f;
				}
				MainActivity.state=1;
				
				
			}
			else if (MainActivity.state == 0)
			{
				MainActivity.state=0;
			}
			if (MainActivity.state ==1 && prevAccel[2]>acceleration[2] && Math.abs(acceleration[2])>=.2 && Math.abs(acceleration[2])<=2.4)
			{
				MainActivity.state=2;
				Log.e(Float.toString(Math.abs(prevAccel[2]+acceleration[2])/2), "Max");
				maxVal=Math.abs((prevAccel[2]+acceleration[2])/2);
				
			}
			else if(MainActivity.state==1)
			{
				MainActivity.state=0; 
			}
			if (MainActivity.state==2 && acceleration[2]<0)
			{
				MainActivity.state =3; 
				
				
			}
			else if (MainActivity.state==2 && prevAccel[2]<acceleration[2])
			{
				MainActivity.state =0; 
			}
			if (MainActivity.state==3 && prevAccel[2]<acceleration[2])
			{
				MainActivity.state =4; 
				
				minVal=Math.abs((prevAccel[2]+acceleration[2])/2);
			}
			if (MainActivity.state==4 && acceleration[2]>0 && (MainActivity.maxVector[0]<0.2) && (MainActivity.maxVector[1]<0.2)&& (MainActivity.maxVector[2]>0)&& (MainActivity.maxVector[0]<1.1))
			{
				MainActivity.startP=MainActivity.userP;
				findangle= MagneticField.def;
				def = findangle;
				Log.e("FGFG", String.valueOf(def));
				rads = (float) Math.toRadians(def);
				MainActivity.stepsNorth = (float) (MainActivity.stepsNorth + Math.cos(rads));
				MainActivity.stepsEast = (float) (MainActivity.stepsEast + Math.sin(rads));
				 
				
				
				
				if(MainActivity.startP != null){
					rads = (float) Math.toRadians(def);
					Xdirection = (float) (2*Math.sin((Math.PI)-(rads + 0.34906585f)));
					Ydirection = (float) ( 2*Math.cos((Math.PI)-(rads + 0.34906585f)));
					updateUserPoint(Xdirection, Ydirection);
					
					
						
							
						
				
				}
				
				Log.e("efewfe",String.valueOf(MainActivity.userP));
				Log.e("efewfe",String.valueOf(MainActivity.startP));
				MainActivity.state =0;
				
				MainActivity.mv.setUserPoint(MainActivity.userP);
				
				MainActivity.step++;
				
				MainActivity.PlaceholderFragment.originChangedt(MainActivity.mv, MainActivity.userP);
				
				MainActivity.PlaceholderFragment.destinationChangedt(MainActivity.mv, MainActivity.endP);
				
				
				
				error = (float) Math.sqrt(Math.pow(MainActivity.stepsEast,2)+Math.pow(MainActivity.stepsNorth,2) )*100/MainActivity.step;
		        
				
			}
			else if (MainActivity.state==4 && prevAccel[2]>acceleration[2])
			{
				MainActivity.state =0;
				 
			}
			String accelerationMsg = "Acceleration Value (m/s^2): " + s + maxValues + "\n\n" + MainActivity.step;
			
			output.setText( "\n\nAngle: " +MagneticField.def + "\n\nStep Count: \n" + MainActivity.step +"\n\nNorth Steps: " + MainActivity.stepsNorth + "\nEast Steps: "+ MainActivity.stepsEast + "\nError: " + error);
			
		}
	
	}
	
	public float lowPassFilter(float in, float previous, float timeDiv) {
		float out = 0.0f;
		out = previous;
		out += (in - previous) / timeDiv;
		
		return out;
	}
	public static void updateUserPoint(double addedX, double addedY){
		MainActivity.userP.x = (float) ( addedX) + MainActivity.userP.x;
		MainActivity.userP.y = (float) ( addedY) + MainActivity.userP.y;
		if (Math.sqrt(Math.pow(Math.abs(MainActivity.endP.x - MainActivity.userP.x),2) + Math.pow(Math.abs(MainActivity.endP.y - MainActivity.userP.y),2))<=2){
			MainActivity.one.setText("You Have Arrived at Your Destination!");
		}
		
    }
//	public float lowPassFilter2(float in, float previous, float timeDiv) {
//		float out = 0.0f;
//		out = previous;
//		out += (in - previous) / 20;
//		
//		return out;
//	}

}
