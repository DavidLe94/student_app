package com.example.assignment_android_basic;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends ActionBarActivity {
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	EditText newID,newName;
	Button btnCancel,btnUpdate;
	String id,name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		addControls();
		unitUI();
		
	}

	private void unitUI() {
		// TODO Auto-generated method stub
		
		//Lay intent truyen di tu AdapterClass
		Intent intent = getIntent();
		//Lay ra id tu intent
		id = intent.getStringExtra("ID");
		//Toast.makeText(this, id, Toast.LENGTH_LONG).show();
		//Mo ket noi den database
		SQLiteDatabase Database = database.initDatabase(this, DATABASE_NAME);
		//Tao cau lenh truy van
		Cursor cursor = Database.rawQuery("SELECT * FROM Class WHERE ClassID = ?", new String[]{id});
		//Dua con tro ve vi tri dau bang
		cursor.moveToFirst();
		//Lay gia tri tu database va gan vao TextView
		name = cursor.getString(1).toString();
		newName.setText(name);
		
		btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cancel();
			}
		});
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				update();
			}
		});
	}
	private void update(){
		if(!validate()){
			
		}else{
			String name = newName.getText().toString();
			//Tao va dua gia tri vao mot contentvalues
			ContentValues contentvalues = new ContentValues();
			contentvalues.put("ClassName", name);
			//Ket noi den database va cap nhat lai bang ghi
			SQLiteDatabase Database = database.initDatabase(this, DATABASE_NAME);
			Database.update("Class", contentvalues, "ClassID = ?", new String[]{id});
			//Tro ve activity ViewClassActivity
			cancel();
		}
	}
	private boolean validate() {
		// TODO Auto-generated method stub
		boolean valid = true;
		if(newName.getText().toString().equalsIgnoreCase(name)){
			Toast.makeText(this, "Please, Enter new values.", Toast.LENGTH_SHORT).show();
			valid = false;
		}else if(newName.getText().toString().isEmpty()){
			Toast.makeText(this, "Please, Enter infomation", Toast.LENGTH_SHORT).show();
			valid = false;
		}
		return valid;
	}

	private void cancel(){
		Intent intent = new Intent(this, ViewClassActivity.class);
		startActivity(intent);
	}
	private void addControls() {
		// TODO Auto-generated method stub
		//newID = (EditText) findViewById(R.id.edtNewID);
		newName = (EditText) findViewById(R.id.edtNewNameClass);
		btnUpdate = (Button) findViewById(R.id.btnConfirmUpdate);
		btnCancel = (Button) findViewById(R.id.btnCancelUpdate);
	}
}
