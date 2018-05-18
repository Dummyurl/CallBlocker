package co.in.callblocker;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
	private Animation myAnimation ,myAnimation2,myAnimation3;
	TextView call,blocker;
	ImageView img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_slash);
//		getActionBar().setTitle("Splash");
//		getActionBar().setIcon(
//	    		   new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		ActionBar actionBar = getActionBar();
		actionBar.setSubtitle("mytest");
		actionBar.setTitle("vogella.com");
		myAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
		myAnimation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
		myAnimation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movee);
		call=(TextView) findViewById(R.id.call);
		blocker=(TextView) findViewById(R.id.blocker);
		img=(ImageView) findViewById(R.id.img1);
		call.setAnimation(myAnimation2);
		blocker.setAnimation(myAnimation3);
		img.setAnimation(myAnimation);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				SplashActivity.this.finish();
				Intent in =new Intent(SplashActivity.this,Tabs.class);
				startActivity(in);
			}
		}, 4000);

		//		SplashActivity.this.finish();
	}
}
