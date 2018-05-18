package co.in.callblocker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
	Button Incomming,Outgoing;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Preference");
		Incomming=(Button) findViewById(R.id.incomming);
		Outgoing=(Button) findViewById(R.id.outgoing);
		Incomming.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, PreferanceActivityincomming.class);
				PreferanceStackActivity prefStack = (PreferanceStackActivity) getParent();
				prefStack.push("pref", intent);
			}
		});
		Outgoing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, PreferanceActivityoutgoing.class);
				PreferanceStackActivity prefStack = (PreferanceStackActivity) getParent();
				prefStack.push("pref", intent);
			}
		});

	}
}
