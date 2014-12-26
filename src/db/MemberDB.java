package db;

import java.util.HashMap;
import java.util.Map;

import model.Member;

public class MemberDB {
	private Member CurrMember;
	private Map<Integer, Member> members;
	private volatile static MemberDB instance;
	
	private MemberDB(){
		members = new HashMap<Integer, Member>();
	}
	
	public static MemberDB getInstance(){
		if(instance == null){
			synchronized(MemberDB.class){
				if(instance == null){
					instance = new MemberDB();
				}
			}
		}
		return instance;
	}
	
	public boolean addMember(Member member){
		if(member == null){
			return false;
		}else{
			members.put(member.getId(), member);
			return true;
		}
	}
	
	public boolean deleteMember(Member member){
		if(member == null){
			return false;
		}else{
			members.remove(member.getId());
			return true;
		}
	}
	
	public Member getCurrMember() {
		return CurrMember;
	}
	
	public void setCurrMember(Member currMember) {
		CurrMember = currMember;
	}
	
	public Map<Integer, Member> getMembers() {
		return members;
	}
	
	public void setMembers(Map<Integer, Member> members) {
		this.members = members;
	}	
	
	
}
