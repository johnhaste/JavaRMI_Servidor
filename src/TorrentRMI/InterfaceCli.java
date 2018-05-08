package TorrentRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceCli extends Remote{
    
    public void notificarEvento(String str) throws RemoteException;
    
}
