package service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Group;
import model.Member;
import model.Facade.Facade;
import model.Facade.FacadeImpl;
import model.observer.Observer;
import model.observer.Subject;

import org.khl.assignment2.MainActivity;
import org.xmlpull.v1.XmlPullParserException;

import db.GroupDB;
import db.MemberDB;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class FetchData extends AsyncTask<Void, Void, Void> implements Subject{

	private ConnectivityManager connectivityManager;
	private NetworkInfo netwerkInfo;
	private static final String FILENAME= "db.xml";
	private Context context;
	private boolean isConnected = false;
	public static final String GAME = "game";
	private MemberDB memberDB = MemberDB.getInstance();
	private GroupDB groupDB = GroupDB.getInstance();
	private Facade facade;
	private List<Observer> observers;

	public FetchData(Context context) {
		this.context = context;
		observers = new ArrayList<Observer>();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		String dbWriterType = (checkIfConnected()? "OnlineDBWriter": "OfflineDBWriter");
		facade = new FacadeImpl(dbWriterType);
	}

	@Override
	protected Void doInBackground(Void...params) {
		Map<Integer, Member> members = new HashMap<Integer, Member>();
		Map<Integer, Group> groups = new HashMap<Integer, Group>();

		if(!isConnected) {		//OFFLINE

			XMLParser xmlParser = new XMLParser();
			FileInputStream fin = null;
			try {
				fin = context.openFileInput(FILENAME);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				members = xmlParser.parse(fin).get("members");
				memberDB.setMembers(members);
				groups = xmlParser.parse(fin).get("groups");
				groupDB.setGroups(groups);
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {		  //ONLINE
			members = facade.getMembersOnline();
			memberDB.setMembers(members);
			groups = facade.getGroupsOnline();
			groupDB.setGroups(groups);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		notifyObservers();
		connectivityManager = null;
		netwerkInfo = null;
		
	}

	public boolean checkIfConnected(){
		connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		netwerkInfo = connectivityManager.getActiveNetworkInfo();	

		if(netwerkInfo != null && netwerkInfo.isConnected()) {
			isConnected = true;
		}
		return isConnected;
	}

	public boolean isConnected(){
		return isConnected;
	}

	@Override
	public void notifyObservers() {
		for(Observer o : observers){
			o.update();
		}
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}


}