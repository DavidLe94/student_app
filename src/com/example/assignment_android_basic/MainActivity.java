package com.example.assignment_android_basic;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {
	RelativeLayout layout;
	Button login, exit;
	EditText user,pass;
	CheckBox chk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (RelativeLayout) findViewById(R.id.manHinh);
        layout.setBackgroundResource(R.drawable.background2);
        login = (Button) findViewById(R.id.btnLogin);
        exit = (Button) findViewById(R.id.btnExit);
        user = (EditText) findViewById(R.id.edtUser);
        pass = (EditText) findViewById(R.id.edtPass);
        chk = (CheckBox) findViewById(R.id.checkStatus);
        login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				GoToNextActivity();
			}
		});
        //Gan su kien cho nut exit
        exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//Khoi tao dialog
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
				//thiet lap noi dung cho dialog
				alertDialogBuilder.setMessage("Do you want exit?");
				alertDialogBuilder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface arg0, int arg1) {
	                        System.exit(0);
	                        // button "Yes" thoat khoi ung dung
	                    }
	                });
				alertDialogBuilder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // button "no" an dialog
                    }
                });
				//Tao Dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				//Show Dialog
				alertDialog.show();
			}
		});
    }
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	savingSharedrefereces();
    }
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	restoringPreferences();
    }
    public void GoToNextActivity(){
    	String user,pass;
		String usersys = "leconghau",passsys="123456";
		user = String.valueOf(MainActivity.this.user.getText());
		pass = String.valueOf(MainActivity.this.pass.getText());
		if(user.equalsIgnoreCase(usersys) && pass.equalsIgnoreCase(passsys)){
			Intent intent = new Intent(MainActivity.this, FunctionActivity.class);
			startActivity(intent);
			finish();
		}else{
			//Khoi tao dialog
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
			//thiet lap noi dung cho dialog
			alertDialogBuilder.setMessage("Sorry! User doesn't exist.");
			alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // button "ok" an dialog
                }
            });
			//Tao Dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
			//Show Dialog
			alertDialog.show();
		}
		
    }
    private void savingSharedrefereces(){
    	//tao doi tuong getSharedRferences
    	SharedPreferences sharedreferences = getSharedPreferences("data-login", MODE_PRIVATE);
    	//tao doi tuong editor de luu thay doi
    	SharedPreferences.Editor editor = sharedreferences.edit();
    	String username = user.getText().toString();
    	String password = pass.getText().toString();
    	Boolean check = chk.isChecked();
    	if(!check){
    		//xoa moi thong tin luu truoc do
    		editor.clear();
    	}else{
    		//luu vao editor
    		editor.putString("username", username);
    		editor.putString("password", password);
    		editor.putBoolean("saveStatus", check);
    	}
    	//chap nhan luu xuong file
    	editor.commit();
    }
    private void restoringPreferences(){
    	SharedPreferences pref = getSharedPreferences("data-login", MODE_PRIVATE);
    	boolean check = pref.getBoolean("saveStatus", false);
    	if(check){
    		String username = pref.getString("username", "");
    		String password = pref.getString("password", "");
    		user.setText(username);
    		pass.setText(password);
    	}
    	chk.setChecked(check);
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
}
