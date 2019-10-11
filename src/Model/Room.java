package Model;

import java.util.Set;
import java.util.TreeMap;


public class Room implements ChatProtocol,ChatModelEvents {

	//enum RoomState {ST_INIT, ST_NORMAL};
	//RoomState state = RoomState.ST_INIT;
	private String admin;
	private String name;
	private final TreeMap<String, ChatModelEvents> clientList = new TreeMap<String, ChatModelEvents>();
	
	public void setAdmin(String admin){
		this.admin=admin;
	}
	
	public String getAdmin(){
		return this.admin;
	}
	
	public Room(String admin,String name){
		this.admin=admin;
		this.name=name;
	}
	
	public synchronized boolean registerUser(String name,HandleClient client){
		if (!existUserName(name) && !name.equals("")) {
			clientList.put(name, client);
			notifyNewName();
			return true;
		}
		return false;
	}
	
	public synchronized void unregisterUser(String name) {
		if (existUserName(name)) {
			clientList.remove(name);
			notifyNewName();
		}
	}
	
	public void sendChatMessage(String from, String msg) {
		clientList.values().forEach(c->c.chatMessageSent(from, msg));
	}
	
	public synchronized Set<String> getUserNames() {
		return clientList.keySet();
	}
	
	public synchronized boolean existUserName(String name){
		return clientList.containsKey(name);
	}
	
	private void notifyNewName() {
		clientList.values().forEach(ChatModelEvents::userListChanged);
	}
	
	@Override
	public void userListChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chatMessageSent(String from, String message) {
		clientList.values().forEach(c->c.chatMessageSent(from, message));
		
	}

	@Override
	public void privateChatMessageSent(String from, String to, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdownRequested() {
		// TODO Auto-generated method stub
		
	}

}
