package Model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/*import java.util.Hashtable;
import javax.naming.directory.DirContext;*/

public class ServerMain {
	public static void main(String[] args) {
		int port = args.length==1? Integer.parseInt(args[0]): 5000;
		try{
			ServerCore core = new ServerCore(port);
			/*Hashtable env = new Hashtable();
			DirContext dirContext;*/
		}
		catch(IOException e){
			System.out.println("Error during initialisation:"+e.toString());
		}
	}
}