package TorrentRMI;

import java.time.LocalDateTime;
import java.util.Date;

public class Interesse {
    private String arquivo;
    private InterfaceCli cliente;
    private LocalDateTime horaFim;

    public Interesse(String arquivo, InterfaceCli cliente, long tempo) {
        this.arquivo = arquivo;
        this.cliente = cliente;
        this.horaFim = LocalDateTime.now().plusSeconds(tempo);
    }

    public String getArquivo() {
        return arquivo;
    }

    public InterfaceCli getCliente() {
        return cliente;
    }
    
    public LocalDateTime getHoraFim(){
        return horaFim;
    }
}
