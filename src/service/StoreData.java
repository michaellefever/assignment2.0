package service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import model.Group;
import model.Member;
import model.Facade.Facade;

import android.content.Context;
import android.os.AsyncTask;

public class StoreData extends AsyncTask<Void, Void, Void>{
	private static final String FILENAME="db.xml";
	private List<Group> groups;
	private List<Member> members;
	private Context context;
	private Facade facade;

	public StoreData(Context context, Facade facade, List<Member> members, List<Group> groups){
		this.members = members;
		this.groups = groups;
		this.context = context;
		this.facade = facade;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		XMLWriter xmlWriter = new XMLWriter();
		try {
			String output = xmlWriter.write(members, groups);
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(output.getBytes());
			fos.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
