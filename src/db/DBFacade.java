package db;

import java.util.List;
import java.util.Map;

import model.Expense;
import model.Group;
import model.Member;

public interface DBFacade {
	DBWriter getWriter();
	void setWriter(DBWriter writer);
	void writeMember(String firstname, String lastname, String email) throws Exception;
	void closeConnection();
	Map<Integer, Member> getMembers();
	void writeGroup(String groupname, List<Member> members);
	void updateGroup(int groupId, String groupName, List<Member> membersInvited);
	Map<Integer, Group> getGroups();
	List <Member> getMembersInGroup(int groupid);
	Group getGroupForId(int id);
	void writeExpense(List<Member> recipients, double amount, String date,
			String description, int groupid);
	List<Expense> getExpensesPaidByMember(int memberid);
	List<Expense> getExpensesPaidToMember(int memberid);
	Expense getExpense(int id);
}
