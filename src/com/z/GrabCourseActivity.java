package com.z;

import java.util.Calendar;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class GrabCourseActivity extends Activity {
	
	 private EditText showDate = null;
     private Button pickDate = null;
     private EditText showTime = null;
     private Button pickTime = null;
     private TextView grab_course_hint;
     
     
     
     
     private static final int SHOW_DATAPICK = 0; 
     private static final int DATE_DIALOG_ID = 1;  
     private static final int SHOW_TIMEPICK = 2;
     private static final int TIME_DIALOG_ID = 3;
 
     private int mYear;  
     private int mMonth;
     private int mDay; 
     private int mHour;
     private int mMinute;
     
     private int grab_course_times = 0; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_grab_course);
		
		//+++++++++++++++++++++++++++++++++++++
		grab_course_hint = (TextView)findViewById(R.id.grab_course_hint);
		
		
		
		grab_course_hint.setText("当前有"+grab_course_times+"次抢课任务");
		//+++++++++++++++++++++++++++++++++++
		initializeViews();
        
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);  
        mMonth = c.get(Calendar.MONTH);  
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);
        
        setDateTime(); 
        setTimeOfDay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grab_course, menu);
		return true;
	}
	
	private void initializeViews(){
        showDate = (EditText) findViewById(R.id.showdate);  
        pickDate = (Button) findViewById(R.id.pickdate); 
        showTime = (EditText)findViewById(R.id.showtime);
        pickTime = (Button)findViewById(R.id.picktime);
        
        pickDate.setOnClickListener(new View.OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                   Message msg = new Message(); 
                   if (pickDate.equals((Button) v)) {  
                      msg.what = GrabCourseActivity.SHOW_DATAPICK;  
                   }  
                   GrabCourseActivity.this.dateandtimeHandler.sendMessage(msg); 
                        }
                });
        
        pickTime.setOnClickListener(new View.OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                   Message msg = new Message(); 
                   if (pickTime.equals((Button) v)) {  
                      msg.what = GrabCourseActivity.SHOW_TIMEPICK;  
                   }  
                   GrabCourseActivity.this.dateandtimeHandler.sendMessage(msg); 
                        }
                });
    }
	
	 private void setDateTime(){
	       final Calendar c = Calendar.getInstance();  
	       
	       mYear = c.get(Calendar.YEAR);  
	       mMonth = c.get(Calendar.MONTH);  
	       mDay = c.get(Calendar.DAY_OF_MONTH); 
	  
	       updateDateDisplay(); 
	        }
	        
	        /**
	         * 更新日期显示
	         */
	        private void updateDateDisplay(){
	       showDate.setText(new StringBuilder().append(mYear).append("-")
	                       .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
	               .append((mDay < 10) ? "0" + mDay : mDay)); 
	        }
	        
	    /** 
	     * 日期控件的事件 
	     */  
	    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {  
	  
	       public void onDateSet(DatePicker view, int year, int monthOfYear,  
	              int dayOfMonth) {  
	           mYear = year;  
	           mMonth = monthOfYear;  
	           mDay = dayOfMonth;  

	           updateDateDisplay();
	       }  
	    }; 
	        
	        /**
	         * 设置时间
	         */
	        private void setTimeOfDay(){
	           final Calendar c = Calendar.getInstance(); 
	       mHour = c.get(Calendar.HOUR_OF_DAY);
	       mMinute = c.get(Calendar.MINUTE);
	       updateTimeDisplay();
	        }
	        
	        /**
	         * 更新时间显示
	         */
	        private void updateTimeDisplay(){
	       showTime.setText(new StringBuilder().append(mHour).append(":")
	               .append((mMinute < 10) ? "0" + mMinute : mMinute)); 
	        }
	    
	    /**
	     * 时间控件事件
	     */
	    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
	                
	                @Override
	                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	                        mHour = hourOfDay;
	                        mMinute = minute;
	                        
	                        updateTimeDisplay();
	                }
	        };
	        
	        @Override  
	        protected Dialog onCreateDialog(int id) {  
	           switch (id) {  
	           case DATE_DIALOG_ID:  
	               return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,  
	                      mDay);
	           case TIME_DIALOG_ID:
	                   return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
	           }
	                   
	           return null;  
	        }  
	      
	        @Override  
	        protected void onPrepareDialog(int id, Dialog dialog) {  
	           switch (id) {  
	           case DATE_DIALOG_ID:  
	               ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);  
	               break;
	           case TIME_DIALOG_ID:
	                   ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
	                   break;
	           }
	        }  
	      
	        /** 
	         * 处理日期和时间控件的Handler 
	         */  
	        Handler dateandtimeHandler = new Handler() {
	      
	           @Override  
	           public void handleMessage(Message msg) {  
	               switch (msg.what) {  
	               case GrabCourseActivity.SHOW_DATAPICK:  
	                   showDialog(DATE_DIALOG_ID);  
	                   break; 
	               case GrabCourseActivity.SHOW_TIMEPICK:
	                       showDialog(TIME_DIALOG_ID);
	                       break;
	               }  
	           }  
	      
	        }; 
	        }


