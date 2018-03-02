package com.example.assignment_android_basic;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("NewApi") 
public class ManagementStudentsActivity extends Activity {
	final String DATABASE_NAME = "ManagerStudents.sqlite";
	ListView view;
	SQLiteDatabase Database;
	ArrayList<Student> arr;
	AdapterStudents adapter;
	
	Student students;
	EditText classid, idstudent, name,mark,email,classIDUpdate,nameUpdate,emailUpdate, markUpdate, idStudentUpdate,findStudent;
	ImageView avatar,ava;
	Spinner sp;
	Button find,cancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_management_students);
		init();
		readData();
		
	}
	private void readData() {
		// TODO Auto-generated method stub
		Database = database.initDatabase(this, DATABASE_NAME);
		Cursor cursor = Database.rawQuery("SELECT * FROM Student", null);
		arr.clear();
		for(int i=0; i<cursor.getCount(); i++){
			cursor.moveToPosition(i);
			String id = cursor.getString(0);
			String name = cursor.getString(1);
			String email = cursor.getString(2);
			double mark = cursor.getDouble(3);
			String classid = cursor.getString(4);
			byte[] avatar = cursor.getBlob(5);
			arr.add(new Student(id, name, email, classid, mark, avatar));
		}
		adapter.notifyDataSetChanged();
	}

	private void init() {
		// TODO Auto-generated method stub
		view = (ListView) findViewById(R.id.lvStudent);
		arr = new ArrayList<Student>();
		adapter = new AdapterStudents(ManagementStudentsActivity.this, arr);
		view.setAdapter(adapter);
		registerForContextMenu(view);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.management_students, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		if(id == R.id.action_adds){
			Intent intent = new Intent(ManagementStudentsActivity.this, AddStudentActivity.class);
			startActivity(intent);
		}else if(id == R.id.action_find){
			findStudent();
		}
		return super.onOptionsItemSelected(item);
	}
	public void note(){
		Toast.makeText(this, "Enter Student ID!", Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_context_studdent, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//final AdapterStudents ad = new AdapterStudents(this, arr);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		int id = item.getItemId();
		Student stu = arr.get(info.position);
		final String valueid = stu.getID();
		
		if (id == R.id.delete) {
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
	                	delete(valueid);
	                }
	            });
			//Button ben phai
			alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
	            	dialog.dismiss();
	            }
	        });
			//Tao Dialog
			final AlertDialog alertDialog = alertDialogBuilder.create();
			//Show Dialog
			alertDialog.show();
		}else if(id == R.id.update){
			update(valueid);
		}else if(id == R.id.details){
			dialogDetails(valueid);
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		ImageView iv = (ImageView) findViewById(R.id.imageViewAvatar);
		if (requestCode == 999 && resultCode == RESULT_OK) {
	    	if(data.getExtras()!=null)
	    	{
	    		Bundle extras = data.getExtras();
	    		Bitmap imageBitmap = (Bitmap) extras.get("data");
	    		iv.setImageBitmap(imageBitmap);
	    	}
	    	else{
	    		Uri uri=data.getData();
	    		iv.setImageURI(uri);
	    	}
	    	
	    }
	}
	
	
	//Hien thi thong tin chi tiet sinh vien
	public void dialogDetails(String id){
		//Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
		//Khoi tao Dialog
		Dialog dialog = new Dialog(this);
		//set layout cho Dialog
		dialog.setContentView(R.layout.sinhvien);
		//set Title cho Dialog
		dialog.setTitle("Student Details");
		//Khai bao control trong Dialog de bat su kien
		avatar = (ImageView) dialog.findViewById(R.id.imageViewAvatar);
		classid = (EditText) dialog.findViewById(R.id.IDClass);
		idstudent = (EditText) dialog.findViewById(R.id.dialogStudentUpdate);
		name = (EditText) dialog.findViewById(R.id.dialogStudentName);
		email = (EditText) dialog.findViewById(R.id.Email);
		mark = (EditText) dialog.findViewById(R.id.Mark);
		for(Student stu: arr){
			if(stu.getID().equals(id)){
				//Mo ket noi den database
				SQLiteDatabase Database = database.initDatabase(this, DATABASE_NAME);
				Cursor cursor = Database.rawQuery("SELECT * FROM Student WHERE StudentID = ?", new String[]{id});
				cursor.moveToFirst();
				String ma = cursor.getString(0);
				String ten = cursor.getString(1);
				String mail = cursor.getString(2);
				double diem = cursor.getDouble(3);
				String malop = cursor.getString(4);
				byte[] anh = cursor.getBlob(5);
				
				Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
				avatar.setImageBitmap(bitmap);
				classid.setText(malop);
				idstudent.setText(ma);
				name.setText(ten);
				email.setText(mail);
				mark.setText(String.valueOf(diem));
			}
		}
		dialog.show();
		
	}
	
	public void cancel(){
		Intent intent = new Intent(this, ManagementStudentsActivity.class);
		startActivity(intent);
	}
	
	//Phuong thuc delete sinh vien co id truyen vao
	public void delete(String id){
		//Mo ket noi den database
		SQLiteDatabase Database = database.initDatabase(this, DATABASE_NAME);
		//Goi ham delete co sang va truyen vao tham so
		Database.delete("Student", "StudentID = ?", new String[]{id});
		Toast.makeText(this, "Delete successfully!", Toast.LENGTH_SHORT).show();
		arr.clear();
		//Doc du lieu len TextView
		Cursor cursor = Database.rawQuery("SELECT * FROM Student", null);
		while(cursor.moveToNext()){
			String StudentID = cursor.getString(0);
			String StudentName = cursor.getString(1);
			String Email = cursor.getString(2);
			double Mark = cursor.getDouble(3);
			String ClassID = cursor.getString(4);
			byte[] Avatar = cursor.getBlob(5);
			arr.add(new Student(StudentID, StudentName, Email, ClassID, Mark, Avatar));
		}
		adapter.notifyDataSetChanged();
		cancel();
	}
	
	private void findStudent(){
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.find_student);
		dialog.setTitle("Enter Student ID: ");
		
		
		
		find = (Button) dialog.findViewById(R.id.btnFind);
		cancel =(Button) dialog.findViewById(R.id.buttonCancel);
		find.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				findStudent = (EditText) dialog.findViewById(R.id.edtFind);
				String id = findStudent.getText().toString().toUpperCase();
				
				for(Student stu: arr){
					if(stu.getID().equalsIgnoreCase(id)){
						dialogDetails(id);
					}
				}
				
				//Toast.makeText(ManagementStudentsActivity.this, id, Toast.LENGTH_SHORT).show();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.show();
		
	}
	
	
	//Phuong thuc update sinh vien co id truyen vao
	public void update(final String id){
		final Dialog dialog = new Dialog(this);
		dialog.setTitle("Enter information:");
		dialog.setContentView(R.layout.layout_update);
		//sp = (Spinner) dialog.findViewById(R.id.spinner1);
		classid = (EditText) dialog.findViewById(R.id.edtClassIDUpdate);
		nameUpdate = (EditText) dialog.findViewById(R.id.Name);
		emailUpdate = (EditText) dialog.findViewById(R.id.Email);
		markUpdate = (EditText) dialog.findViewById(R.id.Mark);
		ava = (ImageView) dialog.findViewById(R.id.imageView1);
		Button cancel = (Button) dialog.findViewById(R.id.Cancel);
		Button ok = (Button) dialog.findViewById(R.id.UpdateOK);
		for(Student stu: arr){
			if(stu.getID().equals(id)){
				//sp.setSelection()
					//Mo ket noi den database
					SQLiteDatabase Database = database.initDatabase(this, DATABASE_NAME);
					Cursor cursor = Database.rawQuery("SELECT * FROM Student WHERE StudentID = ?", new String[]{id});
					cursor.moveToFirst();
					String ten = cursor.getString(1);
					String mail = cursor.getString(2);
					double diem = cursor.getDouble(3);
					String malop = cursor.getString(4);
					byte[] anh = cursor.getBlob(5);
					
					Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);
					ava.setImageBitmap(bitmap);
					classid.setText(malop);
					nameUpdate.setText(ten);
					emailUpdate.setText(mail);
					markUpdate.setText(String.valueOf(diem));
				ava.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						//takePicture();
					}
				});
				ok.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(!validateUpdate()){
							
						}else{
							String valueClassID = classid.getText().toString();
							String valueName = nameUpdate.getText().toString();
							String valueEmail = emailUpdate.getText().toString();
							String valueMark = markUpdate.getText().toString();
							//Tao va dua gia tri vao mot contentvalues
							ContentValues contentvalues = new ContentValues();
							contentvalues.put("ClassID", valueClassID);
							contentvalues.put("StudentName", valueName);
							contentvalues.put("Email", valueEmail);
							contentvalues.put("Mark", valueMark);
							//Ket noi den database va cap nhat lai bang ghi
							SQLiteDatabase Database = database.initDatabase(ManagementStudentsActivity.this, DATABASE_NAME);
							Database.update("Student", contentvalues, "StudentID = ?", new String[]{id});
							Toast.makeText(ManagementStudentsActivity.this, "Update successfully!", Toast.LENGTH_SHORT).show();
							adapter.notifyDataSetChanged();
							cancel();
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
	
	public void takePicture(){
		Intent pick=new Intent(Intent.ACTION_GET_CONTENT);
		pick.setType("image/*");
		Intent pho=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		Intent chosser=Intent.createChooser(pick, "chon");
		chosser.putExtra(Intent.EXTRA_INITIAL_INTENTS,new Intent[]{pho});
		startActivityForResult(chosser,999);
	}
	
	public byte[] Image_to_Byte(ImageView h){
		BitmapDrawable drawable = (BitmapDrawable) h.getDrawable();
		Bitmap bmp = drawable.getBitmap();
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Dialog data) {
		// TODO Auto-generated method stub
		
	}
	
	//Phuong thuc bat loi tren dialog khi nhap du lieu vao
	private boolean validateUpdate() {
		// TODO Auto-generated method stub
		boolean valid = true;
		if(nameUpdate.getText().toString().isEmpty()){
			Toast.makeText(this, "Please! Enter enough infomation", Toast.LENGTH_SHORT).show();
			valid = false;
		}else if(nameUpdate.getText().toString().length() < 5 || nameUpdate.getText().toString().length() > 30){
			Toast.makeText(this, "Please! Enter length string about 5 to 30.", Toast.LENGTH_SHORT).show();
			valid = false;
		}
			return valid;
	}
	
	
}
