package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ChatInput {
	ChatProtocol handler;
	InputStream in;
	private boolean stop=false;
	
	public void setStop(boolean stop){
		this.stop=stop;
	}
	
	public ChatInput(InputStream in, ChatProtocol handler) throws IOException {
		this.in = in;
		this.handler = handler;
	}
	
	public void doRun() throws IOException {
		String strTo;
		String strMsg, strName;
		ArrayList<String> userList;
	
		try (BufferedReader is = new BufferedReader(new InputStreamReader(in))) {
			while (!stop) {
				String line = is.readLine();
				switch (line) {
					case "NAME":
						strName = is.readLine();
						handler.sendName(strName);
						break;
					case "MESSAGE":
						strName = is.readLine();
						strMsg = is.readLine();
						handler.sendMessage(strName, strMsg);
						break;
					case "PRIVATE MESSAGE":
						strName = is.readLine();
						strTo = is.readLine();
						strMsg = is.readLine();
						handler.sendPrivateMessage(strName, strTo, strMsg);
						break;
					/*case "ULIST"://c est le serveur qui envoi ulist
						userList = new ArrayList<>();
						String x;
						while (!(x = is.readLine()).equals(".")) {
						userList.add(x);
						}
						handler.sendUserList(userList);
						break;*/
					case "AULIST":
						handler.sendAskUserList();
						break;
					case "NAME OK":
						handler.sendNameOK();
						break;
					case "NAME BAD":
						handler.sendNameBad();
						break;
					case "QUIT":
						handler.sendQuit();
						break;
					case "CREATE ROOM":
						strName = is.readLine();
						handler.sendRoomName(strName);
						break;
					case "AURLIST"://les utilisateurs qui sont dans une room
						strName = is.readLine();
						handler.sendAskUserList(strName);
						break;
					case "ARLIST":
						handler.sendAskRoomList();
						break;	
					case "ENTER ROOM":
						strName = is.readLine();
						handler.enterRoom(strName);
						break;
					case "LEAVE ROOM":
						strName = is.readLine();
						handler.leaveRoom(strName);
						break;
					case "ROOM MESSAGE":
						strName = is.readLine();
						strTo = is.readLine();
						strMsg = is.readLine();
						handler.sendRoomMessage(strName, strTo, strMsg);
						break;
					case "DELETE ROOM":
						strName = is.readLine();
						handler.deleteRoom(strName);
						break;
					case "ERR":
						//code
						break;
					case "PROPOSE FILE":
						//code
						break;
					case "ACCEPT FILE":
						//code
						break;
					case "REFUSE FILE":
						//code
						break;
					case "SEND FILE":
						strName = is.readLine();
						String FName = is.readLine();
						int FSize = Integer.parseInt(is.readLine());
						File f = File.createTempFile(FName, "t");
						try (FileOutputStream fo = new FileOutputStream(f)) {
							byte buf[] = new byte[8192];
							int len = 0;
							int reste = FSize;
							while (reste > 0000 && len != -1) {
								int toRead = buf.length;
								if (toRead > reste) toRead = reste;
								len = in.read(buf, 0, toRead);//len = in.read(buf, 0, toRead);
								fo.write(buf, 0, len);
								reste -= len;
							}
						}
						//handler.sendFile(strName, FName, f);
						break;
					default:
						throw new ChatProtocolException("Invalid input");
				}
			}
		}
	}
}
