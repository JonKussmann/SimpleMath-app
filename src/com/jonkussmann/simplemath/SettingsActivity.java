package com.jonkussmann.simplemath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

public class SettingsActivity extends Activity {
	public static final String TAG = "SettingsActivty";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
//		CheckBox sound = (CheckBox)findViewById(R.id.checkBoxSound);
//		sound.setChecked(Application.getSound());
//		sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				if (buttonView.isChecked()) {
//					Log.d(TAG, "buttonview is checked");
//				Application.setSound(true);
//			} else {
//				Application.setSound(false);
//			}
//			}
//		});
		
		Button playButton = (Button) findViewById(R.id.playButton);
		playButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		String checked = Application.getOperations();
		switch(checked) {
		case "addSubtract":
			radioGroup.check(R.id.radioAdditionSubtraction);
			break;
		case "multiply": 
			radioGroup.check(R.id.radioMultiplication);
			break;
		case "both":
			radioGroup.check(R.id.radioBoth);
			break;
		default: 
			Log.d(TAG, "some error with checking the radio group");
			break;
		}
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if (checkedId == R.id.radioAdditionSubtraction) {
					Application.setOperations("addSubtract");
					
				} else if (checkedId == R.id.radioMultiplication) {
					Application.setOperations("multiply");
				} else {
					Application.setOperations("both");
				}

				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
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
