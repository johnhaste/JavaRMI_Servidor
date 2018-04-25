/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorld;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author John Haste
 */
public class MainServ {
    public static void main(String[] args) {
        try {
            InterfaceServ servImpl = new ServImpl();
            Registry servicoNomes = LocateRegistry.createRegistry(1099);
            servicoNomes.rebind("Servidor", servImpl);
            
        } catch (RemoteException ex) {
            Logger.getLogger(MainServ.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
