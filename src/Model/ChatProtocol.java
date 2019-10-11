package Model;

import java.util.Collection;

public interface ChatProtocol {
	default void sendName(String name){}
	default void sendNameOK(){}
	default void sendNameBad() {}
	default void sendMessage(String user, String msg){}
	default void sendAskUserList() {}
	default void sendAskUserList(String name) {}
	default void sendUserList(Collection<String> ulist) {}
	default void sendPrivateMessage(String from, String to, String msg){}
	default void sendQuit(){}
	default void sendRoomNameOK(){}
	default void sendRoomNameBad() {}
	default void sendRoomName(String name){}
	default void sendAskRoomList() {}
	default void enterRoom(String salon){}
	default void leaveRoom(String salon){}
	default void deleteRoom(String salon){}
	default void sendRoomMessage(String from, String to, String msg){}
}
