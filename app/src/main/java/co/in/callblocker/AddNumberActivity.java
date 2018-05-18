package co.in.callblocker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNumberActivity extends Activity implements OnClickListener{
	private RemindersDbAdapter mDbAdapter;
	public static EditText number,name;
	private Button btnAdd;
	private Button BtnPickContact;
	private static final int PICK_CONTACT = 0;
	String Number,Name;
	public static String[] listview_arraynumber;
	public static String[] listview_arrayname;

	int listlength,numbermatch,namematch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Add Contact");
		mDbAdapter=new RemindersDbAdapter(this);
		mDbAdapter.open();
		number=(EditText) findViewById(R.id.editNumber);
		name=(EditText) findViewById(R.id.editname);
		//		number.setEnabled(true);
		//		name.setEnabled(true);
		btnAdd=(Button) findViewById(R.id.btnSave);
		BtnPickContact=(Button) findViewById(R.id.pick_contact);
		btnAdd.setOnClickListener(this);
		BtnPickContact.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				number.setText("");
				name.setText("");
				number.setError(null);
				name.setError(null);
				NumberAddStackActivity parentActivity = (NumberAddStackActivity)getParent();

				Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				//startActivityForResult(intent, PICK_CONTACT);
				parentActivity.startActivityForResult(intent,PICK_CONTACT);

			}
		});
		//		Tabs.about.setOnClickListener(new OnClickListener() {
		//
		//			@Override
		//			public void onClick(View v) {
		//				// TODO Auto-generated method stub
		//				Intent intent=new Intent();
		//				intent.setClass(AddNumberActivity.this, AboutAuther.class);
		//				NumberAddStackActivity numadd= (NumberAddStackActivity) getParent();
		//				numadd.push("add", intent);
		//			}
		//		});
		name.addTextChangedListener(new TextWatcher() 
		{
			public void afterTextChanged(Editable s) {
				// put the code of save Database here 
				name.setError(null);
				number.setError(null);
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
	}
	@SuppressLint("SimpleDateFormat")
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btnSave:
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			String Date = df.format(c.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss:a",Locale.US);
			String Time = sdf.format(c.getTime());

			Name=name.getText().toString();
			if(Name.equalsIgnoreCase(""))
			{
				Name="Unknown";
			}
			Number=number.getText().toString();
			if((Number.equalsIgnoreCase(""))||(Number.length()<10))//&&(name.length()>=10))
			{	
				number.setError("Fill correct number");
				number.setFocusable(true);
				number.setText("");
			}
			else
			{


				Cursor cur=mDbAdapter.fetchAllReminders();
				startManagingCursor(cur);
				if(cur != null) {
					cur.moveToFirst();
				}
				listlength=cur.getCount();
				int i=0;
				listview_arraynumber=new String[listlength];
				listview_arrayname=new String[listlength];

				if(listlength==0)
				{

				}
				else
				{
					do{
						String number = cur.getString(cur
								.getColumnIndex(RemindersDbAdapter.KEY_NUMBER));
						String name = cur.getString(cur
								.getColumnIndex(RemindersDbAdapter.KEY_NAME));

						listview_arraynumber[i]=number;
						listview_arrayname[i]=name;
						i++;
					}while(cur.moveToNext()); 
				}	

				for(int j=0;j<listlength;j++)
				{
					if(Number.equalsIgnoreCase(listview_arraynumber[j]))
					{
						numbermatch=1;
						break;
					}
					else
					{
						numbermatch=0;
					}
					if(Name.equalsIgnoreCase(listview_arrayname[j]))
					{
						namematch=1;
						break;
					}
					else
					{
						namematch=0;
					}

				}
				if(numbermatch==0)
				{

					if(namematch==0)
					{
						mDbAdapter.createReminder(Number, Name, Date+";"+Time);	 
						mDbAdapter.close();
						//			  finish();
						Tabs.tabHost.setCurrentTab(2);
					}
					else
					{
						if(Name.equalsIgnoreCase("Unknown"))
						{
							mDbAdapter.createReminder(Number, Name, Date+";"+Time);	 
							mDbAdapter.close();
							Tabs.tabHost.setCurrentTab(2);
						}
						else
						{
							name.setText("");
							name.setError("This name allready exists");
							name.requestFocus();
						}
					}
				}
				else
				{
					number.setText("");
					number.setError("This number allready exists");
					number.requestFocus();
				}
			}
		default:
			break;
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mDbAdapter.close();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//	Tabs.textView.setText("Add Contact");
	}
}
