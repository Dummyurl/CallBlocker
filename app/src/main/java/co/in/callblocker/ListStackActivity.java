package co.in.callblocker;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import java.util.Stack;

@SuppressWarnings("deprecation")
public class ListStackActivity extends ActivityGroup {
	private Stack<String> stack;
	private SharedPreferences myPref; 
	private SharedPreferences.Editor editors; 
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_stack);
		myPref = this.getSharedPreferences("myPref", MODE_WORLD_READABLE);
		editors=myPref.edit();
		if (stack == null)
			stack = new Stack<String>();
		// start default activity
		push("FirstStackActivity", new Intent(this, ListActivity.class));

	}
	@Override
	public void finishFromChild(Activity child) {
		pop();
	}

	@Override
	public void onBackPressed() {
		pop();
	}

	public void push(String id, Intent intent) {

		Window window = getLocalActivityManager().startActivity(id,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			stack.push(id);
			setContentView(window.getDecorView());
		}
		editors.putLong("number",stack.size()); 
		editors.commit();
	}


	public void pop() {
		if (stack.size() == 1)
			//finish();
			Tabs.tabHost.setCurrentTab(0);
		LocalActivityManager manager = getLocalActivityManager();
		manager.destroyActivity(stack.pop(), true);
		if (stack.size() > 0) {

			Intent lastIntent = manager.getActivity(stack.peek()).getIntent();
			Window newWindow = manager.startActivity(stack.peek(), lastIntent);
			setContentView(newWindow.getDecorView());
			editors.putLong("number",stack.size()); 
			editors.commit();
		}
	}
}