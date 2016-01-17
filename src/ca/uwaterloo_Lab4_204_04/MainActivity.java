package ca.uwaterloo_Lab4_204_04;


import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;







import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{
	 
	public static float[] maxVector = {0.0f, 0.0f, 0.0f};
	public static int state = 0;
	public static int step = 0;
	public static int valcount=0;
	public static float[] gravity = {0.0f, 0.0f, 0.0f};
	static MapView mv;
	static float stepsNorth=0;
	static List<PointF> pointL = new ArrayList<PointF>();
	static List<InterceptPoint> interceptP = new ArrayList<InterceptPoint>();
	static float stepsEast=0;
	public static float[] testVector = new float[3]; 
	static LineGraphView graph;
	static PointF startP = new PointF(0,0);
	static PointF endP;
	static PointF userP;
	public static TextView one; 
	static PointF cordinates;
	public float orgin;
	public static int steps;
	public static int steps2;
	public float orgin1;
	static PointF check;
	static int angle2 = 0;
	int place=0; 
	int lol =0;
	static String output; 
	static PointF[] map1 = new PointF[4];
	static PointF test = new PointF(0,0);
	static PointF test2 = new PointF(50,50);
	String direction;
	static int i = 0;
	static int angle = 0;
	static NavigationalMap map;
	@Override
	public void onClick(View v){}
	public void buttonClick(View V){
		step =0;
	}
	
	public void buttonClick1(View V){
		stepsNorth =0;
		stepsEast =0;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		LinearLayout layout = ((LinearLayout)findViewById(R.id.label0));
		
//		b1 = (Button)findViewById(R.id.button1);
//		b1.setOnClickListener(new View.OnClickListener()
//		{
//			@Override
//			public void onClick(View v){
//			
//				
//			}
//			
//		});
		graph = new LineGraphView(getApplicationContext(), 100, Arrays.asList("x", "y", "z"));
		graph.setVisibility(View.VISIBLE);
		
		
		
		mv = new  MapView(getApplicationContext(), 1200, 700, 25, 25);
		
		
		registerForContextMenu(mv);
		map = MapLoader.loadMap(getExternalFilesDir(null),"E2-3344.svg");
		
		
		
	    mv.setMap(map);
		
		mv.addListener(new PositionListener(){
			  
			@Override
			public void originChanged(MapView source, PointF loc) {
				pointL.clear();
				startP = loc;
				pointL.add(startP);
				userP = startP;
				
			}
			
			@Override
			public void destinationChanged(MapView source, PointF dest) {
				
				endP = dest;
				map1[1]= new PointF(3.8618f, 18.2655f);
				map1[2] = new PointF(12.8469f, 18.2654f);
				map1[3] = new PointF(20.6143f, 18.26546f);
				map1[0] = new PointF(21.3685f, 6.6246f);
				interceptP = map.calculateIntersections(endP,startP);
				int counter =0;
				int current = 0; 
				int secondc = 0; 
				int finalcount = 0; 
				if (interceptP.size() != 0){
				while(counter<map1.length){
					if (map.calculateIntersections(startP, map1[counter]).size()==0){
						
						if (Math.sqrt(Math.pow((map1[counter]).x,2))+ (Math.pow((map1[counter]).y,2))> Math.sqrt(Math.pow((map1[current]).x,2))+(Math.pow((map1[current]).y,2))){
							current = counter; 
							
						}
					}
					
					if (map.calculateIntersections(endP, map1[counter]).size()==0){
						
						if (Math.sqrt(Math.pow((map1[counter]).x,2))+ (Math.pow((map1[counter]).y,2))> Math.sqrt(Math.pow((map1[secondc]).x,2))+(Math.pow((map1[secondc]).y,2))){
							secondc = counter; 
							
						}
					}
					counter++;
				}
//				if (current<=secondc){
//				while (current<=secondc){
//					pointL.add(map1[current]);
//					current++;
//				}
//				}else {
//				while (current>=secondc){
//					pointL.add(map1[secondc]);
//					current--;
//				}
//				}
				if ((secondc==3||secondc== 2||secondc==1 ||current==3||current== 2||current==1)&& map.calculateIntersections(endP, map1[current]).size()==0 ) {
					pointL.add(map1[current]);
				}
				else{
				if (current== 0){
					pointL.add(map1[current]);
					pointL.add(map1[3]);
					pointL.add(map1[secondc]);
				}
				else if (secondc==0){
					pointL.add(map1[current]);
					pointL.add(map1[3]);
					pointL.add(map1[secondc]);
				}
				else {
					pointL.add(map1[current]);
					pointL.add(map1[secondc]);
				}
				
			
				}
				}
			    
			    
			    pointL.add(endP);
			    mv.setUserPath(pointL);
			    output = "";
			    steps = (int) ((int) (Math.sqrt(Math.pow((pointL.get(1).y - pointL.get(0).y), 2) + (Math.pow((pointL.get(1).x - pointL.get(0).x), 2))))/1.7)+2;
				while ((i +2)< pointL.size() ){
					angle =0;
					angle =(int) Math.toDegrees(Math.atan2(pointL.get(i+2).x - pointL.get(i+1).x,pointL.get(i+2).y - pointL.get(i+1).y)-
		                    Math.atan2(pointL.get(i).x- pointL.get(i+1).x,pointL.get(i).y- pointL.get(i+1).y));
					angle2 = (int) Math.toDegrees(Math.atan2(pointL.get(i+1).y - pointL.get(i).y, pointL.get(i+1).x - pointL.get(i).x));
					steps = (int) ((int) (Math.sqrt(Math.pow((pointL.get(i+1).y - pointL.get(i).y), 2) + (Math.pow((pointL.get(i+1).x - pointL.get(i).x), 2))))/1.7);
					Log.e("steps",String.valueOf(steps));
					Log.e(String.valueOf(pointL.get(i+1).y),String.valueOf(pointL.get(i).y));
					Log.e(String.valueOf(pointL.get(i+1).x),String.valueOf(pointL.get(i).x));
					
				    if ((i +2)== pointL.size()-1){
				    	steps2 = (int) ((int) (Math.sqrt(Math.pow((pointL.get(i+2).y - pointL.get(i+1).y), 2) + (Math.pow((pointL.get(i+2).x - pointL.get(i+1).x), 2))))/1.7);
				    	Log.e("wqeqw","oiioioui");
				    	Log.e(String.valueOf(pointL.get(i+1).y),String.valueOf(pointL.get(i).y));
						Log.e(String.valueOf(pointL.get(i+1).x),String.valueOf(pointL.get(i).x));
				    	if(angle < 0){
					        angle += 360;
					    }
					    if (angle>180){
					    	angle = angle -180;
					    }
					    else if(angle<180){
					    	angle = 180 -angle;
					    }
					    if(angle2 < 0){
					        angle += 360;
					    }
					    if (angle2>180){
					    	angle = angle -180;
					    }
					    else if(angle2<180){
					    	angle = 180 -angle;
					    }
						Log.e("lol", "ewrwer");
						if (current==0 && i ==0  ){
						
							if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
					        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "2");
					        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "3");
					        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "4");
					        }
						}
						else if((current ==3 && secondc ==3) && pointL.get(i).y>=pointL.get(i+2).y ){
							if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
								output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
					        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "2");
					        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "3as");
					        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "4");
					        }
						}
						else if (i ==0 || i ==2){
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
							output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "3");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "4");
				        }
						}
						else{
							if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
								output =output + "\n Go STRAIGHT  " + steps + " and steps and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
					        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "2");
					        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "3");
					        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
					        	output =output + "\n Go STRAIGHT  " + steps + " and steps and RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
					        	Log.e("lol", "4");
					        }
						}
						i++;	
				    	
				    }
				    else {
					if(angle < 0){
				        angle += 360;
				    }
				    if (angle>180){
				    	angle = angle -180;
				    }
				    else if(angle<180){
				    	angle = 180 -angle;
				    }
				    if(angle2 < 0){
				        angle += 360;
				    }
				    if (angle2>180){
				    	angle = angle -180;
				    }
				    else if(angle2<180){
				    	angle = 180 -angle;
				    }
					Log.e("lol", "ewrwer");
					if (current==0 && i ==0  ){
					
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
				        	Log.e("lol", "3");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
				        	Log.e("lol", "4");
				        }
					}
					else if((current ==3 && secondc ==3) && pointL.get(i).y>=pointL.get(i+2).y ){
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
							output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
				        	Log.e("lol", "3as");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
				        	Log.e("lol", "4");
				        }
					}
					else if (i ==0 || i ==2){
					if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
						output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
			        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
			        	Log.e("lol", "2");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
			        	Log.e("lol", "3");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e("lol", "4");
			        }
					}
					else{
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
							output =output + "\n Go STRAIGHT  " + steps + " and steps and GO LEFT at an angle of " + angle;
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO RIGHT at an angle of " + angle;
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO LEFT at an angle of " + angle;
				        	Log.e("lol", "3");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT  " + steps + " and steps and RIGHT at an angle of " + angle;
				        	Log.e("lol", "4");
				        }
					}
					i++;
			    }
				
				}
		        i=0;
		        if (map.calculateIntersections(endP,startP).size()==0){
					output = "Go STRAIGHT for " + steps + " steps";
				}
				
		        one.setText(output);
		    	secondc=0;
				current = 0;
			    counter=0;
			    MainActivity.mv.setUserPoint(MainActivity.userP);
			    
			}
			
		  });
        

		  
		  
		 
		
		
		
		
	}
	
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		mv.onCreateContextMenu(menu,v,menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		return super.onContextItemSelected(item)|| mv.onContextItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	



	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
  
		public PlaceholderFragment() {
		
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
		
			
			LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.label0);
			TextView tvaccel = new TextView(rootView.getContext());
			TextView tvmagfield = new TextView(rootView.getContext());
			one = new TextView(rootView.getContext());
			TextView two = new TextView(rootView.getContext());
			TextView three = new TextView(rootView.getContext());
			TextView four = new TextView(rootView.getContext());
			TextView five = new TextView(rootView.getContext());
			TextView tvrotation = new TextView(rootView.getContext());
			layout.setOrientation(LinearLayout.VERTICAL);
//			layout.addView(graph);
			layout.addView(tvaccel);
			layout.addView(tvmagfield);
			layout.addView(tvrotation);
			layout.addView(one);
			layout.addView(mv);
			
			layout.addView(graph);
		

			SensorManager sensorManager = (SensorManager) rootView.getContext().getSystemService(SENSOR_SERVICE);
			
			Sensor accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
			SensorEventListener s = new Accelerometer(tvaccel);
			sensorManager.registerListener(s, accelSensor, SensorManager.SENSOR_DELAY_GAME);
			
			Sensor rotSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
			SensorEventListener r = new RotationVector(tvrotation);
			sensorManager.registerListener(r, rotSensor, SensorManager.SENSOR_DELAY_GAME);
			
			//Magnetic Field Sensor
			Sensor magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			SensorEventListener m = new MagneticField(tvmagfield);
			sensorManager.registerListener(m, magSensor, SensorManager.SENSOR_DELAY_GAME);
			
			Sensor accelS = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			SensorEventListener s2 = new Accelerometer2();
			sensorManager.registerListener(s2, accelS, SensorManager.SENSOR_DELAY_GAME);
			return rootView;
		
			
		}
		public static void originChangedt(MapView source, PointF loc) {
			pointL.clear();
			startP=userP;
			startP = loc;
			pointL.add(userP);
			
			
		}
		
		public static void destinationChangedt(MapView source, PointF dest) {
			endP = dest;
			map1[1]= new PointF(3.8618f, 18.2655f);
			map1[2] = new PointF(12.8469f, 18.2654f);
			map1[3] = new PointF(20.6143f, 18.26546f);
			map1[0] = new PointF(21.3685f, 6.6246f);
			interceptP = map.calculateIntersections(endP,startP);
			int counter =0;
			int current = 0; 
			int secondc = 0; 
			int finalcount = 0; 
			if (interceptP.size() != 0){
			while(counter<map1.length){
				if (map.calculateIntersections(startP, map1[counter]).size()==0){
					
					if (Math.sqrt(Math.pow((map1[counter]).x,2))+ (Math.pow((map1[counter]).y,2))> Math.sqrt(Math.pow((map1[current]).x,2))+(Math.pow((map1[current]).y,2))){
						current = counter; 
						
					}
				}
				
				if (map.calculateIntersections(endP, map1[counter]).size()==0){
					
					if (Math.sqrt(Math.pow((map1[counter]).x,2))+ (Math.pow((map1[counter]).y,2))> Math.sqrt(Math.pow((map1[secondc]).x,2))+(Math.pow((map1[secondc]).y,2))){
						secondc = counter; 
						
					}
				}
				counter++;
			}
//			if (current<=secondc){
//			while (current<=secondc){
//				pointL.add(map1[current]);
//				current++;
//			}
//			}else {
//			while (current>=secondc){
//				pointL.add(map1[secondc]);
//				current--;
//			}
//			}
			if ((secondc==3||secondc== 2||secondc==1 ||current==3||current== 2||current==1)&& map.calculateIntersections(endP, map1[current]).size()==0 ) {
				pointL.add(map1[current]);
			}
			else if((current==3 || current==1||current==2) && (secondc==2 || secondc==1 || secondc==3) && map.calculateIntersections(userP, map1[current]).size()==0 && map.calculateIntersections(userP, map1[secondc]).size()==0 ) {
				pointL.add(map1[secondc]);
			}
			else{
			if (current== 0){
				pointL.add(map1[current]);
				pointL.add(map1[3]);
				pointL.add(map1[secondc]);
			}
			else if (secondc==0){
				pointL.add(map1[current]);
				pointL.add(map1[3]);
				pointL.add(map1[secondc]);
			}
			else {
				pointL.add(map1[current]);
				pointL.add(map1[secondc]);
			}
			
		
			}
			}
		    
		    
		    pointL.add(endP);
		    mv.setUserPath(pointL);
		    output = "";
		    steps = (int) ((int) (Math.sqrt(Math.pow((pointL.get(1).y - pointL.get(0).y), 2) + (Math.pow((pointL.get(1).x - pointL.get(0).x), 2))))/1.55)+2;
			while ((i +2)< pointL.size() ){
				angle =0;
				angle =(int) Math.toDegrees(Math.atan2(pointL.get(i+2).x - pointL.get(i+1).x,pointL.get(i+2).y - pointL.get(i+1).y)-
	                    Math.atan2(pointL.get(i).x- pointL.get(i+1).x,pointL.get(i).y- pointL.get(i+1).y));
				angle2 = (int) Math.toDegrees(Math.atan2(pointL.get(i+1).y - pointL.get(i).y, pointL.get(i+1).x - pointL.get(i).x));
				steps = (int) ((int) (Math.sqrt(Math.pow((pointL.get(i+1).y - pointL.get(i).y), 2) + (Math.pow((pointL.get(i+1).x - pointL.get(i).x), 2))))/1.55);
				Log.e("steps",String.valueOf(steps));
				Log.e(String.valueOf(pointL.get(i+1).y),String.valueOf(pointL.get(i).y));
				Log.e(String.valueOf(pointL.get(i+1).x),String.valueOf(pointL.get(i).x));
				
			    if ((i +2)== pointL.size()-1){
			    	steps2 = (int) ((int) (Math.sqrt(Math.pow((pointL.get(i+2).y - pointL.get(i+1).y), 2) + (Math.pow((pointL.get(i+2).x - pointL.get(i+1).x), 2))))/1.55);
			    	Log.e("wqeqw","oiioioui");
			    	Log.e(String.valueOf(pointL.get(i+1).y),String.valueOf(pointL.get(i).y));
					Log.e(String.valueOf(pointL.get(i+1).x),String.valueOf(pointL.get(i).x));
			    	if(angle < 0){
				        angle += 360;
				    }
				    if (angle>180){
				    	angle = angle -180;
				    }
				    else if(angle<180){
				    	angle = 180 -angle;
				    }
				    if(angle2 < 0){
				        angle += 360;
				    }
				    if (angle2>180){
				    	angle = angle -180;
				    }
				    else if(angle2<180){
				    	angle = 180 -angle;
				    }
					Log.e("lol", "ewrwer");
					if (current==0 && i ==0  ){
					
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "3");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "4");
				        }
					}
					else if((current ==3 && secondc ==3) && pointL.get(i).y>=pointL.get(i+2).y ){
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
							output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "3as");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "4");
				        }
					}
					else if (i ==0 || i ==2){
					if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
						output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
			        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
			        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
			        	Log.e("lol", "2");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
			        	Log.e("lol", "3");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
			        	Log.e("lol", "4");
			        }
					}
					else{
						if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
							output =output + "\n Go STRAIGHT  " + steps + " and steps and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
				        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "2");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO LEFT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "3");
				        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
				        	output =output + "\n Go STRAIGHT  " + steps + " and steps and RIGHT at an angle of " + angle+ " and go " +steps2 + " steps";
				        	Log.e("lol", "4");
				        }
					}
					i++;	
			    	
			    }
			    else {
				if(angle < 0){
			        angle += 360;
			    }
			    if (angle>180){
			    	angle = angle -180;
			    }
			    else if(angle<180){
			    	angle = 180 -angle;
			    }
			    if(angle2 < 0){
			        angle += 360;
			    }
			    if (angle2>180){
			    	angle = angle -180;
			    }
			    else if(angle2<180){
			    	angle = 180 -angle;
			    }
				Log.e("lol", "ewrwer");
				if (current==0 && i ==0  ){
				
					if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
			        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e("lol", "2");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e("lol", "3");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
			        	Log.e("lol", "4");
			        }
				}
				else if((current ==3 && secondc ==3) && pointL.get(i).y>=pointL.get(i+2).y ){
					if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
						output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
			        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
			        	Log.e("lol", "2");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e("lol", "3as");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
			        	Log.e("lol", "4");
			        }
				}
				else if (i ==0 || i ==2){
				if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
					output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
		        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
		        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
		        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
		        	Log.e("lol", "2");
		        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
		        	output =output + "\n Go STRAIGHT " + steps + " and GO RIGHT at an angle of " + angle;
		        	Log.e("lol", "3");
		        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
		        	output =output + "\n Go STRAIGHT " + steps + " and GO LEFT at an angle of " + angle;
		        	Log.e("lol", "4");
		        }
				}
				else{
					if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
						output =output + "\n Go STRAIGHT  " + steps + " and steps and GO LEFT at an angle of " + angle;
			        	Log.e(String.valueOf(pointL.get(i).y + pointL.get(i+1).y), "1");
			        }else if (pointL.get(i).y<=pointL.get(i+1).y && pointL.get(i).x>=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO RIGHT at an angle of " + angle;
			        	Log.e("lol", "2");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x<=pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT " + steps + " and steps and GO LEFT at an angle of " + angle;
			        	Log.e("lol", "3");
			        }else if (pointL.get(i).y>=pointL.get(i+1).y && pointL.get(i).x >= pointL.get(i+2).x ){
			        	output =output + "\n Go STRAIGHT  " + steps + " and steps and RIGHT at an angle of " + angle;
			        	Log.e("lol", "4");
			        }
				}
				i++;
		    }
			
			}
	        i=0;
	        if (map.calculateIntersections(endP,startP).size()==0){
				output = "Go STRAIGHT for " + steps + " steps";
			}
	        if (Math.sqrt(Math.pow(Math.abs(MainActivity.endP.x - MainActivity.userP.x),2) + Math.pow(Math.abs(MainActivity.endP.y - MainActivity.userP.y),2))<=2){
				MainActivity.one.setText("You Have Arrived at Your Destination!");
			}
	        else{
	        one.setText(output);
	        }
	    	secondc=0;
			current = 0;
		    counter=0;
		    MainActivity.mv.setUserPoint(MainActivity.userP);
		    
		    
		}
		
		
	}




	
}
