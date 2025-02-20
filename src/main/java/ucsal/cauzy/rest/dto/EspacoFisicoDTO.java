package ucsal.cauzy.rest.dto;


public class EspacoFisicoDTO {

    private Integer idEspacoFisico;
    private Integer numero;
    private Integer idTipoSala;

    public Integer getIdEspacoFisico() {
        return idEspacoFisico;
    }

    public void setIdEspacoFisico(Integer idEspacoFisico) {
        this.idEspacoFisico = idEspacoFisico;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getIdTipoSala() {
        return idTipoSala;
    }

    public void setIdTipoSala(Integer idTipoSala) {
        this.idTipoSala = idTipoSala;
    }

    @Override
    public String toString() {
        return "EspacoFisicoDTO [idEspacoFisico=" + idEspacoFisico + ", numero=" + numero + ", idTipoSala=" + idTipoSala + "]";
    }
}
