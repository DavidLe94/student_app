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
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AdapterClass extends BaseAdapter{
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	Activity context;
	ArrayList<Class> list;
	Class lop;
	TextView viewClassID,viewClass;
	public AdapterClass(Activity context, ArrayList<Class> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.class_view_one_row, null);
		//Anh xa tu activity class_view_one_row 
		viewClassID = (TextView) row.findViewById(R.id.tvClassID);
		viewClass = (TextView) row.findViewById(R.id.tvClassName);
		//lay vi tri cua class trong db
		lop = list.get(position);
		//hien thi du lieu lay tu db len textView
		viewClassID.setText(lop.getClassID());
		viewClass.setText(lop.getClassName());
		//Gan su kien cho layout khi nhan giu
		return row;
	}
	
	
	
	
}
