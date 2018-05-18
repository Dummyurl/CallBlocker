package co.in.callblocker;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;


@SuppressWarnings("deprecation")
public class Tabs extends TabActivity {
	public static TabHost tabHost;
	private static final String Preferance = "Preferance";
	private static final String Number = "Number";
	private static final String List = "List";
	int tab;
	public static TextView textView;
	public static Button about,back;
	int a=0,b=0;
	private SharedPreferences myPref,myPreffirst; 
	//	private SharedPreferences.Editor editors,editorsfirst;
	Context context;
	public static ActionBar actionbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_ACTION_BAR);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_tabs);
//		actionbar = getActionBar();
//		actionbar.setIcon(
//				new ColorDrawable(getResources().getColor(android.R.color.transparent)));
//		actionbar.setTitle("Call Blocker");

		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.mytitle);
		//		textView = (TextView)findViewById(R.id.custom_title_text);
		//		about=(Button) findViewById(R.id.about);
		//		back=(Button) findViewById(R.id.back);
		//		setContentView(R.layout.activity_tabs);

		tabHost = getTabHost();
		tabHost.getTabWidget().setStripEnabled(false);

		TabSpec PrefSpec = tabHost.newTabSpec(Preferance);
		//		PrefSpec.setIndicator("Preferance");
		PrefSpec.setIndicator(prepareTabView(Preferance,R.drawable.preferenceiconfile));
		Intent prefIntent = new Intent(this, PreferanceStackActivity.class);
		prefIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PrefSpec.setContent(prefIntent);

		TabSpec NunberSpec = tabHost.newTabSpec(Number);
		//		NunberSpec.setIndicator("Add Contact");
		NunberSpec.setIndicator(prepareTabView(Number, R.drawable.addcontacticonfile));
		Intent numberIntent = new Intent(this, NumberAddStackActivity.class);
		numberIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		NunberSpec.setContent(numberIntent);



		TabSpec ListSpec = tabHost.newTabSpec(List);
		//		ListSpec.setIndicator("Black List");
		ListSpec.setIndicator(prepareTabView(List, R.drawable.blacklisticonfile));
		Intent listIntent = new Intent(this, ListStackActivity.class);
		listIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		ListSpec.setContent(listIntent);


		tabHost.addTab(PrefSpec);
		tabHost.addTab(NunberSpec);
		tabHost.addTab(ListSpec);

	}
	private View prepareTabView(String text, int resId) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabss, null);
		ImageView iv = (ImageView) view.findViewById(R.id.TabImageView);
		iv.setBackgroundResource(resId);
		return view;
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();

	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.clear();
		MenuInflater inflater = getMenuInflater();
		int currentTab = tabHost.getCurrentTab();

		if (currentTab == 0)
		{
			//			getActionBar().setTitle("ok");
			myPreffirst = Tabs.this.getSharedPreferences("myPreffirst", MODE_WORLD_READABLE);
			Long nums=myPreffirst.getLong("numbers", 0);	
			if(nums==1)
			{
				inflater.inflate(R.menu.developer, menu); 
			}
			else
			{
				inflater.inflate(R.menu.developercancel, menu); 
			}
		}
		else if (currentTab == 1) {
			//			getActionBar().setTitle("Add Contact");
			inflater.inflate(R.menu.preference, menu); 
		} 

		else   
		{
			//			getActionBar().setTitle("Black List");
			myPref = Tabs.this.getSharedPreferences("myPref", MODE_WORLD_READABLE);
			Long num=myPref.getLong("number", 0);	
			if(num==1)
			{
				inflater.inflate(R.menu.list, menu); 
			}
			else
			{
				inflater.inflate(R.menu.second_list, menu); 
			}

		} 

		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_settings: 
			Tabs.tabHost.setCurrentTab(0);
			break;
		case R.id.menu_developer: 
			Intent intent=new Intent();
			intent.setClass(Tabs.this, AboutDeveloper.class);
			Activity activity = getLocalActivityManager().getCurrentActivity();
			((PreferanceStackActivity) activity).push("Homes"+a, intent);
			a++;
			break;
		case R.id.menu_developerr:
			//			SecondListActivity.this.finish();
			Activity activities = getLocalActivityManager().getCurrentActivity();
			((PreferanceStackActivity) activities).pop();
			break;
		case R.id.menu_delete: 
			int i=ListActivity.listlength;
			if(i==0)
			{

			}
			else
			{
				ListActivity.editsearch.setVisibility(View.GONE);

				Intent in=new Intent();
				in.setClass(Tabs.this, SecondListActivity.class);
				Activity activityy = getLocalActivityManager().getCurrentActivity();
				((ListStackActivity) activityy).push("list"+b, in);
				b++;
			}
			break;
		case R.id.menu_search: 
			int j=ListActivity.listlength;
			if(j==0)
			{
				ListActivity.editsearch.setVisibility(View.GONE);
			}
			else
			{
				ListActivity.editsearch.setVisibility(View.VISIBLE);

			}

			break;
		case R.id.menu_searchh: 
			SecondListActivity.editsearch.setVisibility(View.VISIBLE);
			break;
		case R.id.menu_cancel:
			//			SecondListActivity.this.finish();
			Activity activityyy = getLocalActivityManager().getCurrentActivity();
			((ListStackActivity) activityyy).pop();
			break;

		}
		return true;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
