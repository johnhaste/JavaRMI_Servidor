package TorrentRMI;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorMain{
    public static void main(String[] args) {
        try {
            InterfaceServ servImpl = new Servidor();
            
        } catch (RemoteException ex) {
            Logger.getLogger(ServidorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
