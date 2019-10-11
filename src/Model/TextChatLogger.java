package Model;

public class TextChatLogger implements IChatLogger {

	@Override
	public void clientConnected(String ip) {
		System.out.println("clientConnected==>"+ip);
		
	}

	@Override
	public void clientDisconnected(String ip, String name) {
		// TODO Auto-generated method stub
		System.out.println("ip:"+ip+" name:"+name);
	}

	@Override
	public void clientGotName(String ip, String name) {
		// TODO Auto-generated method stub
		System.out.println("ip:"+ip+" name:"+name);
	}

	@Override
	public void clientGotCommand(String name, int command) {
		// TODO Auto-generated method stub
		System.out.println("name:"+name+" command:"+command);
	}

	@Override
	public void publicChat(String from, String msg) {
		// TODO Auto-generated method stub
		System.out.println("from:"+from+" msg:"+msg);
	}

	@Override
	public void privateChat(String from, String to, String msg) {
		// TODO Auto-generated method stub
		System.out.println("from:"+from+" to:"+" msg:"+msg);
	}

	@Override
	public void systemMessage(String msg) {
		System.out.println("system " +msg);
	}
	
	@Override
	public void clientGotRoomName(String name, String admin) {
		// TODO Auto-generated method stub
		System.out.println("Name:"+name+" admin:"+admin);
	}
}
