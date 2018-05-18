package co.in.callblocker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PreferanceActivityoutgoing extends Activity implements RadioGroup.OnCheckedChangeListener {
	private SharedPreferences myPrefsout; 
	private SharedPreferences.Editor editorout; 
	private RadioButton allBlockout;
	private RadioButton unSavedout;
	private RadioButton Savedout;
	private RadioButton fromListout;
	private RadioButton cancelAllout;
	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferance_activityoutgoing);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Outgoing Preference");
		myPrefsout = this.getSharedPreferences("myPrefsout", MODE_WORLD_READABLE);
		editorout=myPrefsout.edit();

		RadioGroup radiogroup2 = (RadioGroup) findViewById(R.id.radGroup2);
		radiogroup2.setOnCheckedChangeListener(this);
		allBlockout=(RadioButton) findViewById(R.id.blockAllout);
		unSavedout=(RadioButton) findViewById(R.id.blockUnsavedout);
		Savedout=(RadioButton) findViewById(R.id.blockSavedout);
		fromListout=(RadioButton) findViewById(R.id.blockFromListout);
		cancelAllout=(RadioButton) findViewById(R.id.cancelBlockerout);
		setDefaultButtonChecked2();

	}
	private  void setDefaultButtonChecked2()
	{

		String value=getSharedPreferences();
		if(value.equals("allout"))
		{
			allBlockout.setChecked(true);
		}
		else if(value.equals("unsavedout"))
		{
			unSavedout.setChecked(true);
		}
		else if(value.equals("Savedout"))
		{
			Savedout.setChecked(true);
		}
		else if(value.equals("listout"))
		{
			fromListout.setChecked(true);
		}
		else if(value.equals("cancelout"))
		{
			cancelAllout.setChecked(true);
		}
		else {
			cancelAllout.setChecked(true);
		}




	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {

		case R.id.blockAllout:
			editorout.putString("mode", "allout"); 
			editorout.commit();
			break;
		case R.id.blockUnsavedout:
			editorout.putString("mode", "unsavedout");
			editorout.commit();

			break;
		case R.id.blockSavedout:
			editorout.putString("mode", "Savedout");
			editorout.commit();

			break;

		case R.id.blockFromListout:
			editorout.putString("mode", "listout"); 
			editorout.commit();

			break;
		case R.id.cancelBlockerout:
			editorout.putString("mode", "cancelout"); 
			editorout.commit();

			break;

		}
	}
	private String getSharedPreferences() {
		// TODO Auto-generated method stub
		myPrefsout = this.getSharedPreferences("myPrefsout", MODE_WORLD_READABLE);
		String value=myPrefsout.getString("mode", "not");
		return value;
	}

}
