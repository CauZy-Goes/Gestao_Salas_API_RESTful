package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "tipo_sala")
@Getter
@Setter
@ToString
public class TipoSala implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoSala;

    @Column(name = "nome_sala", nullable = false, length = 50)
    private String nomeSala;

//	public Integer getIdTipoSala() {
//		return idTipoSala;
//	}
//
//	public void setIdTipoSala(Integer idtiposala) {
//		this.idTipoSala = idtiposala;
//	}
//
//	public String getNomeSala() {
//		return nomeSala;
//	}
//
//	public void setNomeSala(String nomeSala) {
//		this.nomeSala = nomeSala;
//	}
//
//	@Override
//	public String toString() {
//		return "TipoSala [idtiposala=" + idTipoSala + ", nomeSala=" + nomeSala + "]";
//	}

}

