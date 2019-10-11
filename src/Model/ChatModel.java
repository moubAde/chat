package Model;

import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;

public class ChatModel {
	
	private static final TreeMap<String, ChatModelEvents> clientList = new TreeMap<String, ChatModelEvents>();
	private static final TreeMap<String, ChatModelEvents> roomList = new TreeMap<String, ChatModelEvents>();
	
	public static synchronized boolean registerUser(String name,HandleClient client){
		if (!existUserName(name) && !name.equals("")) {
			clientList.put(name, client);
			notifyNewName();
			return true;
		}
		return false;
	}
	
	public static synchronized void unregisterUser(String name) {
		if (existUserName(name)) {
			clientList.remove(name);
			notifyNewName();
		}
	}
	
	public static synchronized boolean renameUser(String oldname, String newname,HandleClient client){
		return false;
	}
	
	public static synchronized boolean existUserName(String name){
		return clientList.containsKey(name);
	}
	
	public static synchronized Set<String> getUserNames() {
		return clientList.keySet();
	}

	public static synchronized Set<String> getUserNames(String name) {
		return ((Room) roomList.get(name)).getUserNames();
	}
	
	public static void sendChatMessage(String from, String msg) {
		clientList.values().forEach(c->c.chatMessageSent(from, msg));
	}
	
	public static void sendPrivateChatMessage(String from, String to, String msg){
		if(existUserName(from) && existUserName(to)){
			clientList.get(to).privateChatMessageSent(from,to,msg);/////////////////////////////
		}
	}
	
	private static void notifyNewName() {
		clientList.values().forEach(ChatModelEvents::userListChanged);
	}

	
	public static void clearAll(){
		//code
	} 
	
	//Room
	public static synchronized boolean registerRoom(String name,String admin){
		if (!existRoomName(name) && !name.equals("")) {
			roomList.put(name, new Room(admin,name));
			notifyNewName();// notifyChangeRooms();
			return true;
		}
		return false;
	}
	
	public static synchronized void unregisterRoom(String nameRoom,String name) {
		if (existUserName(name)) {
			if(((Room) roomList.get(nameRoom)).getAdmin()==null){
				roomList.remove(nameRoom);
				notifyNewName(); // notifyChangeRooms();
			}
			else{
				if(name.equals(((Room) roomList.get(nameRoom)).getAdmin())){
					roomList.remove(nameRoom);
					notifyNewName(); // notifyChangeRooms();
				}
				else{
					System.out.println("Ation non autorise");
				}
			}
		}
	}
	
	public static synchronized boolean existRoomName(String name){
		return roomList.containsKey(name);
	}
	
	public synchronized boolean registerUserRoom(String nameRoom,String name,HandleClient client){
		if(existRoomName(nameRoom) || existUserName(name) ){
			return ((Room) roomList.get(nameRoom)).registerUser(name,client);
		}
		return false;
	}
	
	public synchronized void unregisterUserRoom(String nameRoom,String name){
		if(existRoomName(nameRoom) || existUserName(name) ){
			if(name.equals(((Room) roomList.get(nameRoom)).getAdmin())){
				((Room) roomList.get(nameRoom)).setAdmin(null);
			}
			((Room) roomList.get(nameRoom)).unregisterUser(name);
		}		
	}
	
	public static synchronized Set<String> getRoomNames() {
		return roomList.keySet();
	}
	
	public static void sendRoomChatMessage(String from, String to, String msg){
		Room room=((Room) roomList.get(to));
		if(room.existUserName(from)){
			room.chatMessageSent(from,msg);
		}
		else{
			System.out.println("veuillez rentrer dans la room");
		}
	}
	
	private static synchronized void notifyChangeRooms() {
		//clientList.values().forEach(ChatModelEvents::roomListChanged);
	}
}
