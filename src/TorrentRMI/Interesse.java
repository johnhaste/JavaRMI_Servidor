package TorrentRMI;

public class Interesse {
    private String arquivo;
    private InterfaceCli cliente;

    public Interesse(String arquivo, InterfaceCli cliente) {
        this.arquivo = arquivo;
        this.cliente = cliente;
    }

    public String getArquivo() {
        return arquivo;
    }

    public InterfaceCli getCliente() {
        return cliente;
    }    
}
