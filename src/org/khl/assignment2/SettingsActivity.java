package org.khl.assignment2;

import model.Facade.Facade;
import model.Facade.FacadeImpl;

import service.FetchData;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SettingsActivity extends Activity implements OnItemSelectedListener {
	private Facade facade;
	private FetchData fetchData;
	private Spinner spinner;
	private String[] currencies = {"EUR", "GBP", "USD"};
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//fetchNewData();
		fetchData = new FetchData(this.getApplicationContext());
		String dbWriterType = (fetchData.checkIfConnected()? "OnlineDBWriter": "OfflineDBWriter");
		facade = new FacadeImpl(dbWriterType);
		initializeComponents();
	}
	

	private void fetchNewData(){
		fetchData = new FetchData(this.getApplicationContext());
		fetchData.execute();
	}

	private void initializeComponents(){
		ArrayAdapter<String> currencyAdapt = new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_item, currencies);
		currencyAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner = (Spinner) findViewById(R.id.spinner);
		spinner.setAdapter(currencyAdapt);
		spinner.setOnItemSelectedListener(this); 
	}
	
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void cancel(View v){
		finish();
	}

}
