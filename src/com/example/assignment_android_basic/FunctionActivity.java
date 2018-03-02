package com.example.assignment_android_basic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

@SuppressLint("NewApi") public class FunctionActivity extends Activity {
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	RelativeLayout layout;
	ImageView imgAdd,imgViewClass,imgManagementStudent;
	Button dialogButtonReset, dialogButtonSave;
	EditText idClass, nameClass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_function);
		
		layout = (RelativeLayout) findViewById(R.id.Function_Layout);
		layout.setBackgroundResource(R.drawable.background4);
		imgAdd = (ImageView) findViewById(R.id.ivAdd);
		imgViewClass = (ImageView) findViewById(R.id.ivViewClass);
		imgManagementStudent = (ImageView) findViewById(R.id.ivManagementStudent);
		imgAdd.setOnClickListener(new View.OnClickListener() {
		 
			@Override
			public void onClick(View arg0) {
				//Khoi tao Dialog
				Dialog dialog = new Dialog(FunctionActivity.this);
				//set layout cho Dialog
				dialog.setContentView(R.layout.activity_add_class);
				//set Title cho Dialog
				dialog.setTitle("Add New Class");
				//Khai bao control trong Dialog de bat su kien
				dialogButtonReset = (Button) dialog.findViewById(R.id.btnReset);
				dialogButtonSave = (Button) dialog.findViewById(R.id.btnSave);
				idClass = (EditText) dialog.findViewById(R.id.edtID);
				nameClass = (EditText) dialog.findViewById(R.id.edtNameClass);
				//Bat su kien cho hai Button tren
				dialogButtonSave.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						insert();
						
					}
				});
				dialogButtonReset.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						cancel();
					}
				});
				//Hien thi Dialog
				dialog.show();
			}
		});
		imgViewClass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(FunctionActivity.this, ViewClassActivity.class);
				startActivity(intent);
				
			}
		});
		imgManagementStudent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Khoi tao Dialog
				Intent intent = new Intent(FunctionActivity.this, ManagementStudentsActivity.class);
				startActivity(intent);
			}
		});
	}
	private void insert(){
		boolean flag = false;
		if(!valiate()){
			
		}else{
			String id = idClass.getText().toString();
			String name = nameClass.getText().toString();
			ContentValues contentvalues = new ContentValues();
			contentvalues.put("ClassID", id);
			contentvalues.put("ClassName", name);
			SQLiteDatabase Database = database.initDatabase(this, DATABASE_NAME);
			Database.insert("Class", null, contentvalues);
			//Thong bao ra man hinh
			Toast.makeText(this, "Insert sucessfully!", Toast.LENGTH_SHORT).show();
			cancel();
		}
	}
	private void cancel(){
		Intent intent = new Intent(this, FunctionActivity.class);
		startActivity(intent);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.function, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.exit) {
			System.exit(0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private boolean valiate(){
		boolean valid = true;
		if(idClass.getText().toString().isEmpty() || nameClass.getText().toString().isEmpty()){
			Toast.makeText(this, "Please! Enter enough infomation", Toast.LENGTH_SHORT).show();
			valid = false;
		}else if(idClass.getText().toString().length() < 5 || idClass.getText().toString().length() > 20){
			Toast.makeText(this, "Please! Enter length string about 5 to 20.", Toast.LENGTH_SHORT).show();
			valid = false;
		}
			return valid;
		
	}
}
