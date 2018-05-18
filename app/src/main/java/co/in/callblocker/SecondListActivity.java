package co.in.callblocker;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SecondListActivity extends Activity  {
	private static final int ACTIVITY_CREATE=0;
	private static final int ACTIVITY_EDIT=1;
	private RemindersDbAdapter mDbAdapter;
	static ArrayList<Holder> list ;
	static ListView listView;
	MyCustomAdapter cus;
	private int clickItem;
	Button delete;
	public static TextView selected;
	//	ArrayList <String> checkedValue;
	List checkedValue = new ArrayList();
	public static EditText editsearch;
	int textlength=0;
	private ArrayList<String> array_sort= new ArrayList<String>();
	int listlength;
	public static String[] listview_arrayname;
	public static String[] listview_arraynumber;
	public static String[] listview_arrayid;
	public static String[] listview_arraytime;
	public static String[] listview_arraydate;
	//	public static final int len=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second_list);
//		ActionBar action = Tabs.actionbar;
//		action.setTitle("Delete");
		mDbAdapter=new RemindersDbAdapter(this);
		mDbAdapter.open();
		//		ListActivity.editsearch.setVisibility(View.GONE);
		delete=(Button) findViewById(R.id.delete);
		selected=(TextView) findViewById(R.id.totalselected);
		editsearch=(EditText) findViewById(R.id.editsearch);
		list = new ArrayList<Holder>();
		listView = (ListView) findViewById(R.id.listnew);
		listView.setAdapter(null);
		list.clear();
		displayLits();
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int length=checkedValue.size();
				for(int i=0;i<length;i++)
				{
					mDbAdapter.deleteReminder(Integer.parseInt((String) checkedValue.get(i)));	 

				}
				//				mDbAdapter.fetchAllReminders();
				//				mDbAdapter.close();
				//				list.clear();
				//				displayLits();
				SecondListActivity.this.finish();

			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,  int position,
					long id) {

				// TODO Auto-generated method stub
				final View v  = view ;
				//				String str=listView.getItemAtPosition(position).toString();
				//				Toast.makeText(getApplicationContext(), str,Toast.LENGTH_LONG).show();
				//				//Access view object v here
				//				TextView label=(TextView) v.findViewById(R.id.name);
				//				String xyz = label.getText().toString();


				CheckBox cb = (CheckBox) v.findViewById(R.id.deletecheck);
				//				TextView tv = (TextView) v.findViewById(R.id.number);
				cb.performClick();
				//				  if (cb.isChecked()) {
				//				    
				//				   checkedValue.add(tv.getText().toString());
				//				  } else if (!cb.isChecked()) {
				//				   checkedValue.remove(tv.getText().toString());
				//				  }	
			}
		});
	}
	public void displayLits()
	{   

		Cursor c=mDbAdapter.fetchAllReminders();
		startManagingCursor(c);
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

		cus = new MyCustomAdapter(SecondListActivity.this,list);
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
				cus = new MyCustomAdapter(SecondListActivity.this,list);
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
			SecondListActivity.this.finish();
		}else{
			//				TextView textView = (TextView)findViewById(R.id.tvll);
			textView.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);

		}
	}

	class MyCustomAdapter extends BaseAdapter {

		LayoutInflater inflater;
		ArrayList<Holder> list;


		public MyCustomAdapter(SecondListActivity listactivity, ArrayList<Holder> list) {
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
			TextView name,number,id;
			CheckBox check;
			int len;
		}


		@Override
		public View getView(final int paramInt, View convertView,ViewGroup paramViewGroup) {


			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.secondlisttext, paramViewGroup,false);
				holder = new ViewHolder();


				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.number = (TextView) convertView.findViewById(R.id.number);
				holder.check = (CheckBox) convertView.findViewById(R.id.deletecheck);
				holder.id = (TextView) convertView.findViewById(R.id.id);

				convertView.setTag(holder);

				holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						if (isChecked) {
							checkedValue.add(holder.id.getText().toString());
						}else{
							checkedValue.remove(holder.id.getText().toString());
						}
						int	len=checkedValue.size();
						selected.setText(len+" selected");
						if(len==0)
						{
							//							delete.setVisibility(View.GONE);
							delete.setEnabled(false);
						}
						else
						{
							delete.setEnabled(true);
						}
					}

				});
			} else {
				holder = (ViewHolder) convertView.getTag();
			}


			Holder h = list.get(paramInt);

			holder.name.setText(h.getName());
			holder.number.setText(h.getNumber());
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.second_list, menu);


		return true;
	}
	//	public boolean onCreateOptionsMenu(Menu menu) {
	//		   MenuInflater inflater = getMenuInflater();
	//		   int tab = getTabHost().getCurrentTab();
	//		   if (tab==1)
	//		       inflater.inflate(R.menu.number, menu); 
	//		   else
	//		       inflater.inflate(R.menu.number, menu); 
	//		   return true;
	//		}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_search: 
			editsearch.setVisibility(View.VISIBLE);
			break;
		case R.id.menu_cancel:
			SecondListActivity.this.finish();
			break;
		}
		return true;
	}


}