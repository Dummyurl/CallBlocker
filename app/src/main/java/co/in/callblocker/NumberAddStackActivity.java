package co.in.callblocker;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Window;

import java.util.Stack;

public class NumberAddStackActivity extends ActivityGroup {
	private Stack<String> stack;
	private static final int PICK_CONTACT = 0;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_add_stack);
		if (stack == null)
			stack = new Stack<String>();
		// start default activity
		push("FirstStackActivity", new Intent(this, AddNumberActivity.class));

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
		@SuppressWarnings("deprecation")
		Window window = getLocalActivityManager().startActivity(id,
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
			stack.push(id);
			setContentView(window.getDecorView());
		}
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
		}
	}

	@Override
	public void onActivityResult(int reqCode, int resultCode, Intent data){
		super.onActivityResult(reqCode, resultCode, data);
		String phoneNumber = null;
		switch(reqCode){
		case (PICK_CONTACT):
			if (resultCode == Activity.RESULT_OK){
				Uri contactData = data.getData();
				Cursor c = managedQuery(contactData, null, null, null, null);

				if (c.moveToFirst()){
				}

				//	             String phoneNumber = null;
				String email = null;

				Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
				String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
				String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

				Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
				String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
				String DATA = ContactsContract.CommonDataKinds.Email.DATA;


				StringBuffer output = new StringBuffer();

				ContentResolver contentResolver = getContentResolver();

				Cursor cursor = contentResolver.query(contactData, null,null, null, null);	

				// Loop for every contact in the phone
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						String contact_id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
						String name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
						int hasPhoneNumber = Integer.parseInt(c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
						if (hasPhoneNumber > 0) {

							output.append("\n First Name:" + name);

							// Query and loop for every phone number of the contact
							Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

							while (phoneCursor.moveToNext()) {
								phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)).replace(" ","");
								output.append("\n Phone number:" + phoneNumber);
//								Toast.makeText(getApplicationContext(), name+":"+phoneNumber, Toast.LENGTH_SHORT).show();

								int a=phoneNumber.length();

								if(a>=10)
								{
									String number=phoneNumber.substring(a-10, a);
									if(phoneNumber.contains("+91"))
									{
										AddNumberActivity.number.setText(phoneNumber);
									}
									else
									{
										AddNumberActivity.number.setText("+91"+number);
									}

									//AddNumberActivity.number.setText(bb);
								}
								else
								{
									AddNumberActivity.number.setText(phoneNumber);
								}
								AddNumberActivity.name.setText(name);
							}


							phoneCursor.close();


							// Query and loop for every email of the contact
							Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,	null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);

							while (emailCursor.moveToNext()) {

								email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

								output.append("\nEmail:" + email);


							}

							emailCursor.close();
						}

						output.append("\n");
					}

				}
			}
		}
	}
}