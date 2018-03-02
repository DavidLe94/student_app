package com.example.assignment_android_basic;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewClassActivity extends Activity {
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	ListView view;
	SQLiteDatabase Database;
	ArrayList<Class> list;
	AdapterClass adapter;
	TextView viewClassID,viewClass;
	EditText name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_class);
		adControls();
		readData();
	}

	private void adControls() {
		// TODO Auto-generated method stub
		view = (ListView) findViewById(R.id.viewClass);
		list = new ArrayList<Class>();
		adapter = new AdapterClass(this, list);
		view.setAdapter(adapter);
		registerForContextMenu(view);
	}
	
	private void readData(){
		Database = database.initDatabase(this, DATABASE_NAME);
		Cursor cursor = Database.rawQuery("SELECT * FROM Class", null);
		list.clear();
		for(int i=0; i<cursor.getCount(); i++){
			cursor.moveToPosition(i);
			String id = cursor.getString(0);
			String name = cursor.getString(1);
			list.add(new Class(id, name));
		}
		adapter.notifyDataSetChanged();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.view_class, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if (id == R.id.action_back) {
			Intent intent = new Intent(this, FunctionActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.context_menu_class, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		AdapterClass ad = new AdapterClass(this, list);
		int id = item.getItemId();
		if (id == R.id.delete_class) {
			dialog(info.position);
		}else if(id == R.id.update_class) {
			
			Class l = list.get(info.position);
			String idvalue = l.getClassID();
			//Toast.makeText(this, idvalue, Toast.LENGTH_SHORT).show();
			update(idvalue);
		}
		return super.onContextItemSelected(item);
	}
	
	public View dialog(final int position){
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.class_view_one_row, null);
		//Khoi tao dialog
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		//thiet lap noi dung cho dialog
		//Set title cho dialog
		alertDialogBuilder.setMessage("Are you sure delete?");
		//Gan icon
		alertDialogBuilder.setIcon(android.R.drawable.ic_delete);
		//Gan Button ben trai
		alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                	Class l = list.get(position);
                	//Goi den ham delete va truyen tham so la vi tri id trong database ma nguoi dung chon
                	delete(l.getClassID());
                	//goi ham cancel de tro ve man hinh chinh
                	cancel();
                }
            });
		//Button ben phai
		alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancel();
            }
        });
		//Tao Dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		//Show Dialog
		alertDialog.show();
		return row;
	}
	
	private void delete(String idClass){
		//Mo ket noi den database
		SQLiteDatabase Database = database.initDatabase(ViewClassActivity.this, DATABASE_NAME);
		//Goi ham delete co sang va truyen vao tham so
		Database.delete("Class", "ClassID = ?", new String[]{idClass});
		Toast.makeText(ViewClassActivity.this, "Delete successfully!", Toast.LENGTH_SHORT).show();
		list.clear();
		//Doc du lieu len TextView
		Cursor cursor = Database.rawQuery("SELECT * FROM Class", null);
		while(cursor.moveToNext()){
			String ClassID = cursor.getString(0);
			String NameClass = cursor.getString(1);
			list.add(new Class(ClassID, NameClass));
		}
		
		adapter.notifyDataSetChanged();
	}
	
	private void cancel(){
		Intent intent = new Intent(this, ViewClassActivity.class);
		startActivity(intent);
	}
	
	public void update(final String id){
		final Dialog dialog = new Dialog(this);
		dialog.setTitle("Enter information for update: ");
		dialog.setContentView(R.layout.activity_update);
		name = (EditText) dialog.findViewById(R.id.edtNewNameClass);
		Button ok = (Button) dialog.findViewById(R.id.btnConfirmUpdate);
		Button cancel = (Button) dialog.findViewById(R.id.btnCancelUpdate);
		for(Class l: list){
			if(l.getClassID().equalsIgnoreCase(id)){
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(!validate()){
							Toast.makeText(ViewClassActivity.this, "Please! Enter information!", Toast.LENGTH_SHORT).show();
						}else{
							Update(id);
						}
					}
				});
				
				cancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
			}
		}
		dialog.show();
	}
	
	protected void Update(String id) {
		// TODO Auto-generated method stub
		String valueName = name.getText().toString();
		//Tao va dua gia tri vao mot contentvalues
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("ClassName", valueName);
		//Ket noi den database va cap nhat lai bang ghi
		SQLiteDatabase Database = database.initDatabase(ViewClassActivity.this, DATABASE_NAME);
		Database.update("Class", contentvalues, "ClassID = ?", new String[]{id});
		Toast.makeText(this, "Update successfully!", Toast.LENGTH_SHORT).show();
		adapter.notifyDataSetChanged();
		cancel();
	}
	
	private boolean validate(){
		boolean valid = true;
		if(name.getText().toString().isEmpty()){
			valid = false;
		}
		return valid;
		
	}
	
}
