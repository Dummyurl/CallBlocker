package co.in.callblocker;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import java.util.Stack;

@SuppressWarnings("deprecation")
public class PreferanceStackActivity extends ActivityGroup {
	private Stack<String> stack;
	private SharedPreferences myPreffirst; 
	private SharedPreferences.Editor editorfirst; 
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preferance_stack);
		myPreffirst = this.getSharedPreferences("myPreffirst", MODE_WORLD_READABLE);
		editorfirst=myPreffirst.edit();
		if (stack == null)
			stack = new Stack<String>();
		// start default activity
		push("FirstStackActivity", new Intent(this, MainActivity.class));

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
		
		Window window = getLocalActivityManager().startActivity(id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			stack.push(id);
			setContentView(window.getDecorView());
		}
		editorfirst.putLong("numbers",stack.size()); 
		editorfirst.commit();
	}


	@SuppressWarnings("deprecation")
	public void pop() {
		if (stack.size() == 1)
		{
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("QUIT");
			alertDialog.setMessage("Are you sure ?");
			alertDialog.setIcon(R.drawable.lounchicon);
			alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					PreferanceStackActivity.this.finish();	
				}
			});
			alertDialog.setButton2("No", new  DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
//					PreferanceStackActivity.this.finish();
				}
			});
			alertDialog.show();
			//finish();
		}
		else
		{
			LocalActivityManager manager = getLocalActivityManager();
			manager.destroyActivity(stack.pop(), true);
			if (stack.size() > 0) {
				Intent lastIntent = manager.getActivity(stack.peek()).getIntent();
				Window newWindow = manager.startActivity(stack.peek(), lastIntent);
				setContentView(newWindow.getDecorView());
				editorfirst.putLong("numbers",stack.size()); 
				editorfirst.commit();
			}
		}
	}

}