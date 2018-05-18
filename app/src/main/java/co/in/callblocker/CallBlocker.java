package co.in.callblocker;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import co.in.internal.telephony.ITelephony;

public class CallBlocker extends BroadcastReceiver {
	private static final int MODE_WORLD_READABLE = 1;
	private ITelephony telephonyService;
	private String incommingNumber,outgoingnumber;
	private String incommingName=null,outgoingName=null;
	private SharedPreferences myPrefs,myPrefsout,myPrefscheck; 
	private SharedPreferences.Editor editorcheck; 
	public static String array[]=new String [5];
	Bundle bb;
	String state ;
	AudioManager maudio;
	int ringerMode;
	//	public static final String outgoing = "android.intent.action.NEW_OUTGOING_CALL" ;
	//	IntentFilter intentFilter = new IntentFilter(outgoing);
	@SuppressWarnings("static-access")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
//		maudio=(AudioManager)context.getSystemService(context.AUDIO_SERVICE);
//		ringerMode=maudio.getRingerMode();
//		maudio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		bb = intent.getExtras();  
		state = bb.getString(TelephonyManager.EXTRA_STATE);
//		if ((state != null)&& (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)))    
//		{
//			//maudio.setRingerMode(ringerMode);
//		}
		myPrefs = context.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
		String blockingMode=myPrefs.getString("mode", "not retrieved");

		myPrefsout = context.getSharedPreferences("myPrefsout", MODE_WORLD_READABLE);
		String blockingModeout=myPrefsout.getString("mode", "not retrieved");

		myPrefscheck = context.getSharedPreferences("myPrefsmyPrefs", MODE_WORLD_READABLE);
		Boolean first=myPrefscheck.getBoolean("first",false);
		if(!first)
		{
			if(state!=null)
			{

			}
			else
			{
				outgoingnumber= intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				editorcheck=myPrefscheck.edit();
				editorcheck.putBoolean("first", true);
				editorcheck.putString("number", outgoingnumber);
				editorcheck.commit();
			}
		}
		else
		{
			if(state.equalsIgnoreCase("idel"))
			{

			}
			else
			{
				editorcheck=myPrefscheck.edit();
				editorcheck.putBoolean("first", false);
				editorcheck.commit();
			}
		}
		//		array[i]=outgoingnumber;
		//		i++;
		if(!blockingMode.equals("cancel")) 
		{
			//			Bundle bb = intent.getExtras();  
			//			String state = bb.getString(TelephonyManager.EXTRA_STATE);
			if ((state != null)&& (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)))     
			{
				incommingNumber = bb.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				if(blockingMode.equals("all"))
				{
					blockCall(context, bb);
				}
				else if(blockingMode.equals("unsaved"))
				{

					incommingName=getContactDisplayNameByNumber(incommingNumber, context);
					if((incommingName==null)){
						blockCall(context, bb);
					}
				}
				else if(blockingMode.equals("Saved"))
				{

					incommingName=getContactDisplayNameByNumber(incommingNumber, context);
					if((incommingName!=null)){
						blockCall(context, bb);
					}
				}
				else if(blockingMode.equals("list"))
				{
					RemindersDbAdapter mDbAdapter=new RemindersDbAdapter(context);
					mDbAdapter.open();
					Cursor c= mDbAdapter.fetchAllReminders();  
					if(c.moveToFirst())
					{

						while (c.isAfterLast() == false) {  
							String	title= c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_NUMBER)); 
							if(title.equals(incommingNumber))      
							{

								blockCall(context, bb);
							}
							c.moveToNext();
						} 
					}
					c.close();
					mDbAdapter.close();
				}
				else
				{

				}

			}
		}
		else
		{
			//maudio.setRingerMode(ringerMode);
		}
		if(!blockingModeout.equals("cancelout"))
		{
			Bundle bb = intent.getExtras();  
			String state = bb.getString(TelephonyManager.EXTRA_STATE);
			if ((state != null)&& (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)))     
			{
				if(blockingModeout.equals("allout"))
				{
					blockCall(context, bb);
				}
				else if(blockingModeout.equals("unsavedout"))
				{
					outgoingnumber=myPrefscheck.getString("number","");
					outgoingName=getContactDisplayNameByNumber(outgoingnumber, context);
					if((outgoingName==null)){
						blockCall(context, bb);
					}
				}
				else if(blockingModeout.equals("Savedout"))
				{
					outgoingnumber=myPrefscheck.getString("number","");
					outgoingName=getContactDisplayNameByNumber(outgoingnumber, context);
					
					if((outgoingName!=null)){
						blockCall(context, bb);
					}
				}
				else if(blockingModeout.equals("listout"))
				{
					RemindersDbAdapter mDbAdapter=new RemindersDbAdapter(context);
					mDbAdapter.open();
					outgoingnumber=myPrefscheck.getString("number","");
					Cursor c= mDbAdapter.fetchAllReminders();  
					if(c.moveToFirst())
					{

						while (c.isAfterLast() == false) {  
							String	title= c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_NUMBER)); 
							if(title.substring(title.length()-10, title.length()).equals(outgoingnumber))      
							{

								blockCall(context, bb);
							}
							c.moveToNext();
						} 
					}
					c.close();
					mDbAdapter.close();
				}
				else
				{

				}
			}
		}
	}
	public void blockCall(Context c, Bundle b)
	{

		TelephonyManager telephony = (TelephonyManager) 
				c.getSystemService(Context.TELEPHONY_SERVICE);  
		try {
			Class cls = Class.forName(telephony.getClass().getName());
			Method m = cls.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(telephony);
			//telephonyService.silenceRinger();
			telephonyService.endCall();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//maudio.setRingerMode(ringerMode);
	}
	public String getContactDisplayNameByNumber(String number, Context c) {

		Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
		String name = "?";
		String data=null;
		ContentResolver contentResolver =c.getContentResolver();
		Cursor contactLookup = contentResolver.query(uri, new String[] {BaseColumns._ID,
				ContactsContract.PhoneLookup.DISPLAY_NAME }, null, null, null);

		try {
			if (contactLookup != null && contactLookup.getCount() > 0) {
				contactLookup.moveToNext();
				data = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
				//String contactId = contactLookup.getString(contactLookup.getColumnIndex(BaseColumns._ID));
			}
		} finally {
			if (contactLookup != null) {
				contactLookup.close();

			}
		}


		return data;
	}  
}
