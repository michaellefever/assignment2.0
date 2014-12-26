package db;

import java.util.List;
import java.util.Map;

import model.Expense;
import model.Group;
import model.Member;
import model.observer.Subject;

public interface DBWriter extends Subject{	
	void writeMember(String firstname, String lastname, String email) throws Exception;
	Map<Integer, Member> getMembers();
	void closeConnection();
	Map<Integer, Group> getGroups();
	void writeGroup(String groupname, List<Member> members);
	List<Member> getMembersInGroup(int groupid);
	Group getGroupForId(int id);
	void writeExpense(List<Member> recipients, double amount, String date,
			String description, int groupid);
	List<Expense> getExpensesPaidByMember(int memberid);
	List<Expense> getExpensesPaidToMember(int memberid);
	void updateGroup(int groupid, String groupName, List<Member> membersInvited);
	Expense getExpenseForId(int id);
}
