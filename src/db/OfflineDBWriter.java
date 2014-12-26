package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Expense;
import model.Group;
import model.Member;
import model.observer.Observer;

import android.content.Context;
import android.util.Log;

public class OfflineDBWriter implements DBWriter {

	private Context context;
	private Map<Integer, Member> members;

	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	
	private File file;

	private static final String HIGHSCORE_FILE = "scores.txt";

	public OfflineDBWriter() {
		members = new HashMap<Integer, Member>();
	}

/*	private void sort() {
		ScoreComparator comparator = new ScoreComparator();
		Collections.sort(members, comparator);
	}

	public boolean checkIfPlayerIsAlreadyInHigschores(String playerName) {
		for(Score score:members) {
			if(score.getName().equals(playerName)) {
				return true;
			}
		}
		return false;
	}

	public Score getScoreByName(String playerName) {
		for(Score score:members) {
			if(score.getName().equals(playerName)) {
				return score;
			}
		}
		return null;
	}
*/
	@Override
	public void writeMember(String firstname, String lastname, String email) throws Exception {
/*		loadScoreFile();

		if(this.checkIfPlayerIsAlreadyInHigschores(name)) {
			Score score = this.getScoreByName(name);

			if(score.getPoints() < newScore) {
				score.setPoints(newScore);
			}
		} else {
			members.add(new Score(name, newScore));
		}
*/		updateScoreFile();
	}

	@SuppressWarnings("unchecked")
	public void loadScoreFile() {
/*		try {
			File filesDir = context.getFilesDir();
			
			file = new File(filesDir, HIGHSCORE_FILE);
			file.createNewFile();
			
			inputStream = new ObjectInputStream(new FileInputStream(file));
			members = (ArrayList<Score>) inputStream.readObject();
		} catch (IOException |ClassNotFoundException e) {
			System.out.println("ERROR LOAD");
		}  finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("IO ERROR LOAD");
			}
		}
*/	}

	public void updateScoreFile() {
		try {
			outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(members);
		} catch (IOException e) {
			System.out.println("ERROR UPDATE");
		}  finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("IO ERROR UPDATE");
			}
		}
	}

	//GETTERS - SETTERS
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setMembers(Map<Integer, Member> members) {
		this.members = members;
	}

	public Map<Integer, Member> getMembers() {
		loadScoreFile();
//		sort();
		return members;
	}

	@Override
	public void closeConnection() {
		//NOTHING
	}

	@Override
	public void writeGroup(String groupname, List<Member> members) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<Integer, Group> getGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> getMembersInGroup(int groupid) {
		return null;
	}

	@Override
	public Group getGroupForId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void writeExpense(List<Member> recipients, double amount,
			String date, String description, int groupid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Expense> getExpensesPaidByMember(int memberid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Expense> getExpensesPaidToMember(int memberid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGroup(int groupid, String groupName, List<Member> membersInvited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Expense getExpenseForId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
