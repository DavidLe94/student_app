package com.example.assignment_android_basic;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class AdapterStudents extends BaseAdapter {
	final int RESULT_OK = 0;
	Activity context;
	ArrayList<Student> list;
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	final int REQUEST_TAKE_PHOTO = 123;
	final int REQUEST_CHOOSE_PHOTO = 123;
	Student students;
	
	public AdapterStudents(Activity context, ArrayList<Student> list) {
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
		View row = inflater.inflate(R.layout.student_view_one_row, null);
		//ImageView avatar = (ImageView) row.findViewById(R.id.imageViewAvatar);
		TextView name = (TextView) row.findViewById(R.id.tvName);
		TextView id = (TextView) row.findViewById(R.id.tvID);
		//lay vi tri cua class trong db
		students = list.get(position);
		name.setText(students.getName());
		id.setText(students.getID());
		return row;
	}
}
