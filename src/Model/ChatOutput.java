package Model;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Collection;

public class ChatOutput implements ChatProtocol {
	PrintWriter os;
	public ChatOutput(OutputStream out) throws IOException {
		this.os = new PrintWriter(out, true);
	}
	
	public synchronized void sendName(String name){
		os.println("NAME");
		os.println(name);
	}
	
	public synchronized void sendUserList(Collection<String> ulist){
		os.println("ULIST");////////////
		ulist.forEach(os::println);/////////// x -> os.println(x)
		os.println(".");
	}

	@Override
	public void sendNameOK() {
		ChatProtocol.super.sendNameOK();
		os.println("NAME OK");
	}

	@Override
	public void sendNameBad() {
		ChatProtocol.super.sendNameBad();
		os.println("NAME BAD");
	}

	@Override
	public void sendMessage(String user, String msg) {
		ChatProtocol.super.sendMessage(user, msg);
		os.println("MESSAGE");
		os.println(user);
		os.println(msg);
	}

	@Override
	public void sendAskUserList() {
		ChatProtocol.super.sendAskUserList();
		os.println("AULIST");
		this.sendUserList(ChatModel.getUserNames());
	}
	
	@Override
	public void sendAskUserList(String name) {
		ChatProtocol.super.sendAskUserList();
		os.println("AURLIST");
		this.sendUserList(ChatModel.getUserNames(name));
	}

	@Override
	public void sendPrivateMessage(String from, String to, String msg) {
		ChatProtocol.super.sendPrivateMessage(from, to, msg);
		os.println("PRIVATE MESSAGE");
		os.println(from);
		os.println(to);
		os.println(msg);
	}

	@Override
	public void sendQuit() {
		ChatProtocol.super.sendQuit();
		os.println("QUIT");
	}
	
	@Override
	public void sendRoomNameOK() {
		ChatProtocol.super.sendNameOK();
		os.println("ROOM OK");
	}
	
	@Override
	public void sendRoomNameBad() {
		ChatProtocol.super.sendNameBad();
		os.println("ROOM BAD");
	}
	
	@Override
	public void sendAskRoomList() {
		os.println("ARLIST");
		this.sendRoomList(ChatModel.getRoomNames());
	}
	
	public synchronized void sendRoomList(Collection<String> rlist){
		os.println("RLIST");////////////
		rlist.forEach(os::println);/////////// x -> os.println(x)
		os.println(".");
	}
	
	/*@Override
	public void enterName(String salon){
		t
	}*/
}
