package com.youxiachai.notifications4everywhere;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class Second extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((Button) findViewById(R.id.button1)).setText("ok");
	}
}
