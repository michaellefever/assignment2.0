package model.Facade;
import java.util.List;
import java.util.Map;

import model.Expense;
import model.Group;
import model.Member;
import model.Settings;
import db.DBWriter;
import db.DBWriterFactory;
import db.GroupDB;
import db.MemberDB;
import db.DBFacade;
import db.DBFacadeImpl;

public class FacadeImpl implements Facade{
	private MemberDB memberDB;
	private GroupDB groupDB;
	private DBFacade dbFacade;
	private Settings settings = Settings.getInstance();
	private DBWriter writer;

	public FacadeImpl(String dbWriterType){
		writer = DBWriterFactory.createDBWriter(dbWriterType);
		dbFacade = new DBFacadeImpl(writer);
		memberDB = MemberDB.getInstance();
		groupDB = GroupDB.getInstance();
	}
	
	public DBWriter getDBWriter(){
		return writer;
	}

	@Override
	public void createGroup(String groupName, List<Member> membersInvited) {
		//Member m = getCurrentMember();
		dbFacade.writeGroup(groupName, membersInvited);
	}
	
	@Override
	public void updateGroup(int groupId, String groupName, List<Member> membersInvited) {
		dbFacade.updateGroup(groupId, groupName, membersInvited);
	}

	@Override
	public Member getCurrentMember() {
		return memberDB.getCurrMember();
	}

	@Override
	public Map<Integer, Member> getMembers() {
		return memberDB.getMembers();
	}
	
	@Override
	public Map<Integer,Member> getMembersOnline() {
		return dbFacade.getMembers();
	}


	public List<Member> getMembersInGroup(int groupId){
		return groupDB.getMembersInGroup(groupId);
	//	return dbFacade.getMembersInGroup(groupId);
	}

	public Group getGroupForId(int id){
		return dbFacade.getGroupForId(id);
	}

	@Override
	public void settlePayments() {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<Integer,Group> getGroups() {
		return groupDB.getGroups();
	}
	
	@Override
	public Map<Integer,Group> getGroupsOnline() {
		return dbFacade.getGroups();
	}

	public void writeExpense(List<Member> recipients, double amount, String date,
			String description, int groupid){
		dbFacade.writeExpense(recipients, amount, date, description, groupid);
	}

	@Override
	public List<Expense> getExpensesPaidByMember(int memberid) {
		return dbFacade.getExpensesPaidByMember(memberid);
	}

	@Override
	public List<Expense> getExpensesPaidToMember(int memberid) {
		return dbFacade.getExpensesPaidToMember(memberid);
	}

	public double getAmountIPaidMember(int memberid){
		Map<Integer, Member> members = getMembers();
		double amount = 0;		
		Member me = members.get(settings.getCurrentMember().getId());
		for(Expense e : me.getExpenses().values()){
			if(e.getMembersPaidFor().contains(memberid)){					//I paid member
				amount += e.getAmount()/e.getMembersPaidFor().size(); 
			}
		}
		return amount;
	}
	
	public double getAmountMemberPaidMe(int memberid){
		Map<Integer, Member> members = getMembers();
		double amount = 0;	
		Member member = members.get(memberid);
		Member me = members.get(settings.getCurrentMember().getId());
		for(Expense e : member.getExpenses().values()){
			if(e.getMembersPaidFor().contains(me.getId())){				//member paid me
				amount += e.getAmount()/e.getMembersPaidFor().size(); 	
			}
		}
		return amount;
	}

	public double getAmount(int memberid){
		return getAmountIPaidMember(memberid)-getAmountMemberPaidMe(memberid);
	}
	
	public Expense getExpense(int id){
		return dbFacade.getExpense(id);
	}
}
