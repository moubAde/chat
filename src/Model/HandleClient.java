package Model;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;

public class HandleClient implements Runnable, ChatProtocol, ChatModelEvents{
	private final Socket s;
	private ChatOutput cho;
	private ChatInput chi;
	private String name = "";
	private enum ClientState {ST_INIT, ST_NORMAL};
	private ClientState state = ClientState.ST_INIT;
	private boolean stop = false;
	
	private IChatLogger logger=null;
	
	public HandleClient(Socket s, IChatLogger logger) throws IOException { //refaire le constructeur  et les variable declarer en haut
		this.s = s;
		this.logger=logger;
	}
	
	public void sendName(String name) {
		String newName = name;
		if (ChatModel.existUserName(newName)) {
			cho.sendNameBad();
		} 
		else {
			if (state == ClientState.ST_INIT) {
				ChatModel.registerUser(newName, this);
				state = ClientState.ST_NORMAL;
			} 
			else {
				ChatModel.renameUser(name, newName, this);
			}
			this.name = newName;
			cho.sendNameOK();
			logger.clientGotName(s.toString(), name);
		}
	}
	
	/*public void askUList() {//////////uLIST
		if (state == ClientState.ST_INIT) 
			return;
		cho.sendUserList(ChatModel.getUserNames());
		logger.systemMessage("liste user");//////////////
	}*/
	
	@Override
	public void sendAskUserList(){ ////////////////
		if (state == ClientState.ST_INIT) 
			return;
		cho.sendAskUserList();
	}
    
	@Override
	public void sendAskUserList(String name){ ////////////////
		if (state == ClientState.ST_INIT) 
			return;
		cho.sendAskUserList(name);
	}
	/*@Override
	public void sendUserList(Collection<String> ulist) {
		if (state == ClientState.ST_INIT) 
			return;
		//cho.sendUserList(ulist);
		for(String s:ulist){
			try {
				ChatModel.registerUser(s,new HandleClient(new Socket(),new TextChatLogger()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			//new ServerCore(1234);
		}
	}*/
	
	@Override
	public void sendQuit() {////////////////////////////////
		if (state == ClientState.ST_INIT) 
			return;
		finish();
	}
	
	public void sendMessage(String user, String msg) {
		if (state == ClientState.ST_INIT) return;
		ChatModel.sendChatMessage(name, msg);
		logger.publicChat(name, msg);
	}
	
	public void sendPrivateMessage(String user, String to ,String msg) {
		if (state == ClientState.ST_INIT) return;
		ChatModel.sendPrivateChatMessage(name,to,msg);
		logger.privateChat(name,to ,msg);
	}
	
	public void run() {
		try (Socket s1 = s) {
			cho = new ChatOutput(s1.getOutputStream());
			chi = new ChatInput(s1.getInputStream(), this);
			chi.doRun();
		} catch (IOException ex) {
			if (!stop) {
			   finish();
		    }
	      }
    }

	@Override
	public void userListChanged() {
		// TODO Auto-generated method stub
	}

	@Override
	public void chatMessageSent(String from, String message) {
		if (from != name) {
			cho.sendMessage(from, message);
		}
	}

	@Override
	public void privateChatMessageSent(String from, String to, String message) {
		// TODO Auto-generated method stub
		if (from != name) {
			cho.sendPrivateMessage(from,to, message);
		}
	}

	@Override
	public void shutdownRequested() {
		// TODO Auto-generated method stub
		
	}
	
	public synchronized void finish(){
		if (!stop) {
			stop = true;
			try {
				s.close();
			} catch (IOException ex) { ex.printStackTrace(); }
			if (name != null)
				ChatModel.unregisterUser(name);
			logger.clientDisconnected(s.toString(), name);
		}
	}
	
	public void sendRoomName(String name) {
		String newRoom = name;
		if (ChatModel.existRoomName(newRoom)) {
			cho.sendRoomNameBad();
		} 
		else {
			ChatModel.registerRoom(newRoom,this.name);
			cho.sendRoomNameOK();
			logger.clientGotRoomName(newRoom,this.name);
			enterRoom(newRoom);
		}
	}
	
	@Override
	public void sendAskRoomList(){ ////////////////
		//if (state == ClientState.ST_INIT) 
			//return;
		cho.sendAskRoomList();
	}
	
	@Override
	public void enterRoom(String salon){
		if(ChatModel.existRoomName(salon)){
			new ChatModel().registerUserRoom(salon,this.name,this);
			logger.systemMessage("entre succes");
		}
		else{
			cho.sendRoomNameBad();
		}
	}
	
	@Override
	public void leaveRoom(String salon){
		if(ChatModel.existRoomName(salon)){
			new ChatModel().unregisterUserRoom(salon,this.name);
			logger.systemMessage("sortie succes");
		}
		else{
			cho.sendRoomNameBad();
		}
	}
	
	@Override
	public void deleteRoom(String salon){
		System.out.println("ici 1");
		if(ChatModel.existRoomName(salon)){
			new ChatModel().unregisterRoom(salon,this.name);
			System.out.println("ici 3");
		}
		else{
			cho.sendRoomNameBad();
		}
	}
	
	@Override
	public void sendRoomMessage(String user, String to ,String msg) {
		if (state == ClientState.ST_INIT) return;
		ChatModel.sendRoomChatMessage(name,to,msg);
		logger.privateChat(name,to ,msg);
	}
}