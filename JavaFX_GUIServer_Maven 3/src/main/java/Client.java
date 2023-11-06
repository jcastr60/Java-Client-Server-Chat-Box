import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	private Consumer<Serializable> callback, CcallBack;
	
	Client(Consumer<Serializable> call, Consumer<Serializable>   clientCallBack){
	
		callback = call;
		CcallBack =  clientCallBack;
	}
	
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",5555);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
			ChatInfo message = (ChatInfo) in.readObject();
			
			if(message.message != "") {
				// TODO: Update listview of client messages
				callback.accept( message);
			}
			
			//callback.accept(message);
			CcallBack.accept(message.ListOfAllClients);
			//CcallBack.accept(message.ListOfAllClients);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void send(String data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
