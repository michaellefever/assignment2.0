package org.khl.assignment2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.khl.assignment2.adapter.AddedMemberAdapter;

import service.FetchData;
import model.Member;
import model.Facade.Facade;
import model.Facade.FacadeImpl;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class AddExpenseActivity extends Activity implements OnItemSelectedListener {
	private Facade facade;
	private int groupid;
	private EditText description, amount;
	private FetchData fetchData;
	private Spinner spinner;
	private ArrayAdapter<Member> memberAdapt;
	private AddedMemberAdapter addedMemberAdapt;
	private List<Member> members;
	private List<Member> recipients = new ArrayList<Member>();
	private ListView addMembersList;
	private Member selectedMember;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);
		Bundle b = getIntent().getExtras();
        groupid = b.getInt(GroupDetailActivity.GROUP_ID);
		//fetchNewData();
		fetchData = new FetchData(this.getApplicationContext());
		String dbWriterType = (fetchData.checkIfConnected()? "OnlineDBWriter": "OfflineDBWriter");
		facade = new FacadeImpl(dbWriterType);
		members = new ArrayList<Member>(facade.getMembers().values());
		initializeComponents();
	}
	

	private void fetchNewData(){
		fetchData.execute();
	}

	private void initializeComponents(){
		description = (EditText)findViewById(R.id.description);
		amount = (EditText)findViewById(R.id.amount);
		spinner = (Spinner) findViewById(R.id.spinner);
		memberAdapt = new ArrayAdapter<Member>(this,  android.R.layout.simple_spinner_item, members);
		memberAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(memberAdapt);
		spinner.setOnItemSelectedListener(this); 
		addedMemberAdapt = new AddedMemberAdapter(this,recipients, memberAdapt, members, spinner);
		addMembersList = (ListView)findViewById(android.R.id.list);
		addMembersList.setAdapter(addedMemberAdapt);
	}

	public void addExpense(View v){
		facade.writeExpense(recipients, Double.parseDouble(amount.getText().toString()), getCurrentDateTime(), description.getText().toString(), groupid);
		finish();
	}
	
	private String getCurrentDateTime(){
		Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(c.getTime());
	}

	public void cancel(View v){
		finish();
	}

	public void invite(View v){
		if(selectedMember != null){
			recipients.add(selectedMember);
			members.remove(selectedMember);
			if(members.isEmpty()){
				selectedMember = null;
			}
			refreshData();
		}
	}

	private void refreshData(){
		addedMemberAdapt.notifyDataSetChanged();
		memberAdapt.notifyDataSetChanged();
		spinner.setAdapter(memberAdapt);
	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		selectedMember = (Member)parent.getItemAtPosition(pos);
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}