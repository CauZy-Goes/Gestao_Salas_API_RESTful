package ucsal.cauzy.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "espaco_fisico")
public class EspacoFisico implements Serializable {

	@Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer idEspacoFisico;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "idtiposala", nullable = true)
    private TipoSala tipoSala;

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

	public TipoSala getTipoSala() {
		return tipoSala;
	}

	public void setTipoSala(TipoSala tipoSala) {
		this.tipoSala = tipoSala;
	}

	@Override
	public String toString() {
		return "EspacoFisico [idEspacoFisico=" + idEspacoFisico + ", numero=" + numero + ", tipoSala=" + tipoSala
				+ "]";
	}
	
}

