package co.in.callblocker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class PreferanceActivityincomming extends Activity implements RadioGroup.OnCheckedChangeListener {
	private SharedPreferences myPrefs; 
	private SharedPreferences.Editor editor; 
	private RadioButton allBlock;
	private RadioButton unSaved;
	private RadioButton Saved;
	private RadioButton fromList;
	private RadioButton cancelAll;
	//
	@SuppressLint("CommitPrefEdits")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preference);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Incoming Preference");
		myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		editor=myPrefs.edit();  
		RadioGroup radiogroup = (RadioGroup) findViewById(R.id.radGroup1);
		radiogroup.setOnCheckedChangeListener(this);
		// radio button setting
		allBlock=(RadioButton) findViewById(R.id.blockAll);
		unSaved=(RadioButton) findViewById(R.id.blockUnsaved);
		Saved=(RadioButton) findViewById(R.id.blockSaved);
		fromList=(RadioButton) findViewById(R.id.blockFromList);
		cancelAll=(RadioButton) findViewById(R.id.cancelBlocker);

		setDefaultButtonChecked();
	}
	private  void setDefaultButtonChecked()
	{
		String value=getSharedPreferences();
		if(value.equals("all"))
		{
			allBlock.setChecked(true);
		}
		else if(value.equals("unsaved"))
		{
			unSaved.setChecked(true);
		}
		else if(value.equals("Saved"))
		{
			Saved.setChecked(true);
		}
		else if(value.equals("list"))
		{
			fromList.setChecked(true);
		}
		else if(value.equals("cancel"))
		{
			cancelAll.setChecked(true);
		}
		else {
			cancelAll.setChecked(true);
		}
	}
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.blockAll:
			editor.putString("mode", "all");
			editor.commit();

			break;

		case R.id.blockUnsaved:
			editor.putString("mode", "unsaved");
			editor.commit();

			break;
		case R.id.blockSaved:
			editor.putString("mode", "Saved");
			editor.commit();

			break;

		case R.id.blockFromList:
			editor.putString("mode", "list"); 
			editor.commit();

			break;
		case R.id.cancelBlocker:
			editor.putString("mode", "cancel"); 
			editor.commit();



			break;

		}
	}

	private String getSharedPreferences() {
		// TODO Auto-generated method stub
		myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		String value=myPrefs.getString("mode", "not");
		return value;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//		Tabs.textView.setText("Preference");
	}
}
