package co.in.callblocker;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListActivity extends Activity {
	/** Called when the activity is first created. */
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	private RemindersDbAdapter mDbAdapter;
	static ArrayList<Holder> list ;
	static ListView listView;
	MyCustomAdapter cus;
	private int clickItem;
	
	public static EditText editsearch;
	int textlength=0;
	public static int listlength;
	//	 String listview_array[];
	public static String[] listview_arrayname;
	public static String[] listview_arraynumber;
	public static String[] listview_arrayid;
	public static String[] listview_arraytime;
	public static String[] listview_arraydate;
	private ArrayList<String> array_sort= new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Black List");
		mDbAdapter=new RemindersDbAdapter(this);
		mDbAdapter.open();
		editsearch=(EditText) findViewById(R.id.search);
		list = new ArrayList<Holder>();
		listView = (ListView) findViewById(R.id.listnew);
		listView.setAdapter(null);
		list.clear();
		//registerForContextMenu(getListView());
		displayLits();
//		Tabs.about.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent=new Intent();
//				intent.setClass(ListActivity.this, AboutAuther.class);
//				ListStackActivity list= (ListStackActivity) getParent();
//				list.push("list", intent);
//			}
//		});
	}
	public void displayLits()
	{   

		Cursor c=mDbAdapter.fetchAllReminders();
		//		numberList.clear();
		//		idList.clear();
		startManagingCursor(c);

		//NEW
		if(c != null) {
			c.moveToLast();
		}
		listlength=c.getCount();
		int i=0;
		listview_arrayname=new String[listlength];
		listview_arraynumber=new String[listlength];
		listview_arrayid=new String[listlength];
		listview_arraytime=new String[listlength];
		listview_arraydate=new String[listlength];
		if(listlength==0)

		{

		}
		else
		{
			do{


				String  id= c.getString(c.getColumnIndex(RemindersDbAdapter.KEY_ROWID)); 	

				String number = c.getString(c
						.getColumnIndex(RemindersDbAdapter.KEY_NUMBER));
				String name = c.getString(c
						.getColumnIndex(RemindersDbAdapter.KEY_NAME));
				String date = c.getString(c
						.getColumnIndex(RemindersDbAdapter.KEY_DATE_TIME));

				String[] DATE=date.split(";");
				int l=number.length();
				String number1;
				if(l>10)
				{
					number1=number.substring(l-10, l);
				}
				else
				{
					number1=number;
				}



				Holder h=new Holder();

				h.setNumber(number1);
				h.setName(name);
				h.setTime(DATE[1]);
				h.setDatetime(DATE[0]);
				h.setId(id);
				list.add(h);
				listview_arrayname[i]=name;
				listview_arraynumber[i]=number1;
				listview_arrayid[i]=id;
				listview_arraytime[i]=DATE[1];
				listview_arraydate[i]=DATE[0];
				i++;
			}while(c.moveToPrevious()); 
		}

		/////////////////////////
		cus = new MyCustomAdapter(ListActivity.this,list);
		//listshowcase.setAdapter(adapter);
		listView.setAdapter(cus);
		
		editsearch.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s)
			{
				// Abstract Method of TextWatcher Interface.
			}
			public void beforeTextChanged(CharSequence s,
					int start, int count, int after)
			{
				// Abstract Method of TextWatcher Interface.
			}
			public void onTextChanged(CharSequence s,
					int start, int before, int count)
			{
				textlength = editsearch.getText().length();
				array_sort.clear();
				
//				listView.setAdapter(null);
				list.clear();
				for (int i = 0; i <  listlength; i++)
				{
					if (textlength <= listview_arrayname[i].length())
					{
						if(editsearch.getText().toString().equalsIgnoreCase(
								(String)
								listview_arrayname[i].subSequence(0,
										textlength)))
						{
							array_sort.add(listview_arrayname[i]);
							Holder h=new Holder();

							h.setNumber(listview_arraynumber[i]);
							h.setName(listview_arrayname[i]);
							h.setTime(listview_arraytime[i]);
							h.setDatetime(listview_arraydate[i]);
							h.setId(listview_arrayid[i]);
							list.add(h);
						}
					}
				}
//				listView.setAdapter(new ArrayAdapter<String>(ListActivity.this,android.R.layout.simple_list_item_1, array_sort));
				cus = new MyCustomAdapter(ListActivity.this,list);
				//listshowcase.setAdapter(adapter);
				listView.setAdapter(cus);
			}
		});	



		






		int count = listView.getCount();
		TextView textView = (TextView)findViewById(R.id.empty);
		if(count==0){
			//				TextView textView = (TextView)findViewById(R.id.empty);
			listView.setVisibility(View.GONE);
			textView.setVisibility(View.VISIBLE);
		}else{
			//				TextView textView = (TextView)findViewById(R.id.tvll);
			textView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);
		}


		//listView.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		//	@Override
		//	public boolean onItemLongClick(AdapterView<?> parent, View view,
		//			final int position, long id) {
		//		// TODO Auto-generated method stub
		//	//	final View v  = view ;
		//		String str=listView.getItemAtPosition(position).toString();
		//       
		//        //Access view object v here
		//        TextView label=(TextView) view.findViewById(R.id.id);
		//        String xyz = label.getText().toString();
		//        Toast.makeText(getApplicationContext(), xyz,Toast.LENGTH_LONG).show();
		//		mDbAdapter.deleteReminder(Integer.parseInt(xyz));	 
		//		
		////		mDbAdapter.fetchAllReminders();
		//		mDbAdapter.close();
		//		return false;
		//	}
		//});
		//listView.setOnItemClickListener(new OnItemClickListener() {
		//
		//	@Override
		//	public void onItemClick(AdapterView<?> parent, View view, final int position,
		//			long id) {
		//		
		//		// TODO Auto-generated method stub
		//		final View v  = view ;
		//		String str=listView.getItemAtPosition(position).toString();
		//        Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG).show();
		//        //Access view object v here
		//        TextView label=(TextView) v.findViewById(R.id.name);
		//        String xyz = label.getText().toString();
		//	}
		//});


	}
	
	//	@Override
	//	public int getSelectedItemPosition() {
	//		// TODO Auto-generated method stub
	//		return super.getSelectedItemPosition();
	//	}
	//	//
	//	// list item selected
	//	@Override
	//	protected void onListItemClick(ListView l, View v, int position, long id) {
	//		super.onListItemClick(l, v, position, id);
	//
	//		// clickItem=position;
	////		clickItem=Integer.parseInt(idList.get(position)); 
	////		Toast.makeText(getBaseContext(), "number  "+clickItem, Toast.LENGTH_SHORT).show();
	//
	//	}
	// creating menu 
	///////////////////////////////

	class MyCustomAdapter extends BaseAdapter {

		LayoutInflater inflater;
		ArrayList<Holder> list;


		public MyCustomAdapter(ListActivity listactivity, ArrayList<Holder> list) {
			inflater = LayoutInflater.from(listactivity);
			this.list =list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int paramInt) {
			return paramInt;
		}

		@Override
		public long getItemId(int paramInt) {
			return paramInt;
		}
		class ViewHolder {
			TextView name,number,time,date,id;
		}


		@Override
		public View getView(final int paramInt, View convertView,ViewGroup paramViewGroup) {


			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listtext, paramViewGroup,false);
				holder = new ViewHolder();


				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.number = (TextView) convertView.findViewById(R.id.number);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.id = (TextView) convertView.findViewById(R.id.id);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}


			Holder h = list.get(paramInt);

			holder.name.setText(h.getName());
			holder.number.setText(h.getNumber());
			holder.time.setText(h.getTime());
			holder.date.setText(h.getDatetime());
			holder.id.setText(h.getId());
			return convertView;

		}

	}



















//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//
//		super.onCreateContextMenu(menu, v, menuInfo);
//		MenuInflater mi = getMenuInflater(); 
//		mi.inflate(R.menu.list, menu); 
//	}
	//
	//
//	@Override
//	public boolean onContextItemSelected(MenuItem item) { 
//
//		switch(item.getItemId()) {
//		case R.id.menu_delete:
//
//			Boolean bb=    mDbAdapter.deleteReminder(clickItem);                                  
//			displayLits();
//			return true;
//		case R.id.menu_cancel:
//
//			return true;
//		}
//		return super.onContextItemSelected(item);
//	}
//	public boolean onItemLongClick(AdapterView<?> arg, View arg1, int pos,
//			long id) {
//		//		clickItem=Integer.parseInt(idList.get(pos)); 
//		//		Toast.makeText(getBaseContext(), "number  "+clickItem, Toast.LENGTH_SHORT).show();
//
//		// TODO Auto-generated method stub
//		return true;
//	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		menu.clear();
//		getMenuInflater().inflate(R.menu.list, menu);
//
//
//		return true;
//	}
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		   MenuInflater inflater = getMenuInflater();
	//		   int tab = getTabHost().getCurrentTab();
	//		   if (tab==1)
	//		       inflater.inflate(R.menu.number, menu); 
	//		   else
	//		       inflater.inflate(R.menu.number, menu); 
	//		   return true;
	//		}
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.menu_settings: 
//
//			Intent i=new Intent(this, ListActivity.class);
//			startActivity(i);
//			break;
//		case R.id.menu_show: 
//
//			Intent ii=new Intent(this, ListActivity.class);
//			startActivity(ii);
//			break;
//		}
//		return true;
//	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		list.clear();
		displayLits();
//		Tabs.textView.setText("Black List");
	}
	
}