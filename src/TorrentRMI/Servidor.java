package TorrentRMI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends UnicastRemoteObject implements InterfaceServ {

    private ArrayList<InterfaceCli> listaDeUsuarios;
    private ArrayList<String> listaDeArquivos;
    private ArrayList<Interesse> listaDeInteressesArq;
    private File file;
    
    public Servidor() throws RemoteException {
  
        listaDeUsuarios = new ArrayList<>();
        listaDeArquivos = new ArrayList<>();
        listaDeInteressesArq = new ArrayList<>();
        file = new File(".\\SHARE\\");
        listaDeArquivos.addAll(Arrays.asList(file.list()));
        Registry servicoNomes = LocateRegistry.createRegistry(1099);
        servicoNomes.rebind("Servidor", this);
    }
    
    //OBTEM O ARQUIVO DO USUÁRIO, SALVA NA PASTA CORRETA 
    //E ATUALIZA A LISTA DE USUÁRIOS CHAMANDO O MÉTODO DE INTERESSES CASO HOUVER
    @Override
    public void fazerUpload(byte[] arquivo, String nomeArquivo, InterfaceCli refCliente) throws RemoteException {
        try {
            
            FileOutputStream fileOS = new FileOutputStream(file.getName()+"\\"+nomeArquivo);
            fileOS.write(arquivo);
            fileOS.close();
            this.listaDeArquivos.add(nomeArquivo);
            this.verificaInteresse(nomeArquivo);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Retorna a lista de arquivos para o usuário.
    @Override
    public ArrayList<String> consultarArquivos(InterfaceCli refCliente) throws RemoteException {
        listaDeArquivos.clear();
        listaDeArquivos.addAll(Arrays.asList(file.list()));
        return this.listaDeArquivos;    
    }
    //Envia o arquivo desejado para o usuário
    @Override
    public byte[] fazerDownload(String arquivo, InterfaceCli refCliente) throws RemoteException {
        if(this.listaDeArquivos.contains(arquivo))
        {
            System.out.println("SELECIONANDO O ARQUIVO SOLICITADO EM: .\\ARQUIVOS_SERVIDOR\\"+arquivo);
            Path path = Paths.get("ARQUIVOS_SERVIDOR\\"+arquivo);
            
            try {
                return Files.readAllBytes(path);
            } catch (IOException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("ARQUIVO NÃO ENCONTRADO");
        return null;
    }
    //Salva o interesse por um determinado arquivo na lista virtual de interesses
    @Override
    public void registrarInteresse(String arquivo, InterfaceCli refCliente) throws RemoteException {
        this.listaDeInteressesArq.add(new Interesse(arquivo, refCliente));
    }
    //Retira o interesse por determinado arquivo atrelado ao cliente solicitante
    @Override
    public void cancelarInteresse(String arquivo, InterfaceCli refCliente) throws RemoteException {
        for(Interesse i:this.listaDeInteressesArq){
            if(i.getArquivo().equals(arquivo) && i.getCliente() == refCliente)
                this.listaDeInteressesArq.remove(i);
        }
        System.out.println("O cliente "+refCliente.toString()+" removeu seu interesse no arquivo "+arquivo);
    }
    //Retira todos os interesses registrados atrelado ao cliente solicitante
    @Override
    public void cancelarTodosOsInteresses(InterfaceCli refCliente) throws RemoteException {
        for(Interesse i:this.listaDeInteressesArq){
            if(i.getCliente() == refCliente)
                this.listaDeInteressesArq.remove(i);
        }
        System.out.println("O cliente "+refCliente.toString()+" removeu todos os seus interesses em arquivos");
    }

    private void verificaInteresse(String nomeArquivo) throws RemoteException {
        for(Interesse i:this.listaDeInteressesArq){
            if(i.getArquivo().equals(nomeArquivo))
                i.getCliente().notificarEvento("O arquivo " + nomeArquivo + "que você havia solicitado já está disponível.");
        }
    }
}
