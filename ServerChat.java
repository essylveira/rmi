import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ServerChat implements IServerChat {
    private static Registry registry;
    private ArrayList<String> roomList;

    public ServerChat() throws RemoteException {
        super();
        this.roomList = new ArrayList<String>();
    }

    public ArrayList<String> getRooms() throws RemoteException {
        return this.roomList;
    }

    public void createRoom(String roomName) throws RemoteException {
        this.roomList.add(roomName);
        try {
            IRoomChat roomChat = new RoomChat(roomName);
            IRoomChat stub = (IRoomChat) UnicastRemoteObject.exportObject(roomChat, 0);
            registry.rebind(roomName, stub);
            System.out.println("Add");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            IServerChat serverChat = new ServerChat();
            IServerChat stub = (IServerChat) UnicastRemoteObject.exportObject(serverChat, 0);
            registry = LocateRegistry.createRegistry(2020);
            registry.rebind("Servidor", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
