package co.in.callblocker;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class AboutDeveloper extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_auther);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Developer");
		//		Tabs.back.setOnClickListener(new OnClickListener() {
		//			
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				AboutDeveloper.this.finish();
		//			}
		//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.developer, menu);
		return true;
	}
}
