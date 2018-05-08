package TorrentRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceServ extends Remote{
    
    public void fazerUpload(byte[] arquivo, String nomeArquivo, InterfaceCli refCliente) throws RemoteException;
    public ArrayList<String> consultarArquivos(InterfaceCli refCliente) throws RemoteException;
    public byte[] fazerDownload(String arquivo, InterfaceCli refCliente) throws RemoteException;
    public void registrarInteresse(String arquivo, InterfaceCli refCliente) throws RemoteException;
    public void cancelarInteresse(String arquivo, InterfaceCli refCliente) throws RemoteException;
    public void cancelarTodosOsInteresses(InterfaceCli refCliente) throws RemoteException;
}
