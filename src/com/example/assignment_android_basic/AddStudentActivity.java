package com.example.assignment_android_basic;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class AddStudentActivity extends Activity implements OnItemSelectedListener{
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	SQLiteDatabase Database;
	EditText name,email,mark,idStudent;
	Button ok, cancel;
	Spinner spn;
	ImageView avatar;
	AdapterStudents adapter;
	ArrayList<String> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_student);
		addControl();
		loadDataSpinner();
		readData();
		
	}
	//load danh sach lop vap trong spinner
	public void loadDataSpinner(){
		//ArrayList<Class> list = new ArrayList<Class>();
		list = new ArrayList<String>();
		Database = database.initDatabase(this, DATABASE_NAME);
		Cursor cursor = Database.rawQuery("SELECT * FROM Class", null);
		for(int i=0; i<cursor.getCount(); i++){
			cursor.moveToPosition(i);
			String id = cursor.getString(0);
			list.add(id);
			
		}
		ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn.setAdapter(dataAdapter);
		spn.setOnItemSelectedListener(this);
		
	}
	//phuong thuc dung de anh xa cac control 
	public void addControl(){
		spn = (Spinner) findViewById(R.id.spnClassID);
		name = (EditText) findViewById(R.id.Name);
		email = (EditText) findViewById(R.id.Email);
		mark = (EditText) findViewById(R.id.Mark);
		idStudent = (EditText) findViewById(R.id.StudentID);
		avatar = (ImageView)findViewById(R.id.imageViewAvatar);
		ok = (Button) findViewById(R.id.UpdateOK);
		cancel = (Button) findViewById(R.id.Cancel);
	}
	
	//phuong thuc dung de bat cac su kien xay ra trong activity
	public void readData(){
		ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!validateAdss()){
					
				}else{
					String id = idStudent.getText().toString();
					
					String classid = spn.getSelectedItem().toString();
					String Name = name.getText().toString();
					String Email = email.getText().toString();
					String Mark = mark.getText().toString();
					byte[] img = Image_to_Byte(avatar);
					
					
					ContentValues contentvalues = new ContentValues();
					contentvalues.put("StudentID", id);
					contentvalues.put("StudentName", Name);
					contentvalues.put("Email", Email);
					contentvalues.put("Mark", Mark);
					contentvalues.put("ClassID", classid);
					contentvalues.put("Avatar", img);
					
					SQLiteDatabase Database = database.initDatabase(AddStudentActivity.this, DATABASE_NAME);
					Database.insert("Student", null, contentvalues);
					Toast.makeText(AddStudentActivity.this, "Insert successfully!", Toast.LENGTH_SHORT).show();
					finish();
					cancel();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				takePicture();
			}
		});
		
		//adapter.notifyDataSetChanged();
	}
	//Phuong thuc dung de chuyen hinh anh sang dang byte
	public byte[] Image_to_Byte(ImageView h){
		BitmapDrawable drawable = (BitmapDrawable) h.getDrawable();
		Bitmap bmp = drawable.getBitmap();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
		
	}
	
	//phuong thuc bat loi tren activity khi nguoi dung nhap vao du lieu khong hop le
	private boolean validateAdss() {
		// TODO Auto-generated method stub
		boolean valid = true;
		if(idStudent.getText().toString().isEmpty() || name.getText().toString().isEmpty()){
			Toast.makeText(this, "Please! Enter enough infomation", Toast.LENGTH_SHORT).show();
			valid = false;
		}else if(idStudent.getText().toString().length() < 5 || idStudent.getText().toString().length() > 20){
			Toast.makeText(this, "Please! Enter length string about 5 to 20.", Toast.LENGTH_SHORT).show();
			valid = false;
		}else if(name.getText().toString().length() < 5 || name.getText().toString().length() > 30){
			Toast.makeText(this, "Please! Enter length string about 5 to 30.", Toast.LENGTH_SHORT).show();
			valid = false;
		}
			return valid;
	}
	
	//phuong thuc tao mot option menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_student, menu);
		return true;
	}
	//bat cac su kien khi nguoi dung chon voi cac id tuong ung
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_exit) {
			System.exit(0);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//Phuong thuc lay hinh anh tu thu muc hoac tu camera
	public void takePicture(){
		Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
		pick.setType("image/*");
		Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Intent chosser=Intent.createChooser(pick, "Choose: ");
		chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
		startActivityForResult(chosser, 999);
	}
	
	//ham tra ve cua ham takePicture()
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 999 && resultCode == RESULT_OK) {
	    	
	    	if(data.getExtras()!=null)
	    	{
	    		Bundle extras = data.getExtras();
	    		Bitmap imageBitmap = (Bitmap) extras.get("data");
	    		avatar.setImageBitmap(imageBitmap);
	    	}
	    	else{
	    		Uri uri=data.getData();
	    		avatar.setImageURI(uri);
	    	}
	    	
	    }

		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void cancel(){
		Intent i = new Intent(AddStudentActivity.this, ManagementStudentsActivity.class);
		startActivity(i);
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//Toast.makeText(this, list.get(position), Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
