package model.Facade;

import java.util.List;
import java.util.Map;

import db.DBWriter;

import model.Expense;
import model.Group;
import model.Member;

public interface Facade {
	
	Member getCurrentMember();
	Map<Integer,Member> getMembers();
	Map<Integer,Group> getGroups();
	Map<Integer,Member> getMembersOnline();
	Map<Integer,Group> getGroupsOnline();
	void createGroup(String groupName, List<Member> membersInvited);
	void settlePayments();
	List<Member> getMembersInGroup(int groupid);
	Group getGroupForId(int id);
	void writeExpense(List<Member> recipients, double amount, String date,
			String description, int groupid);
	double getAmountIPaidMember(int memberid);
	double getAmountMemberPaidMe(int memberid);
	double getAmount(int id);
	void updateGroup(int groupId, String string, List<Member> membersInvited);
	DBWriter getDBWriter();
	List<Expense> getExpensesPaidToMember(int memberid);
	List<Expense> getExpensesPaidByMember(int memberid);
	Expense getExpense(int id);
}
