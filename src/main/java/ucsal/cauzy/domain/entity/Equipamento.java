package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "equipamento")
@Getter
@Setter
@ToString
public class Equipamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEquipamento;

    @Column(name = "nome_equipamento", nullable = false, length = 255)
    private String nomeEquipamento;

    @ManyToOne
    @JoinColumn(name = "idespacofisico")
    private EspacoFisico espacoFisico;

//	public Integer getIdEquipamento() {
//		return idEquipamento;
//	}
//
//	public void setIdEquipamento(Integer idEquipamento) {
//		this.idEquipamento = idEquipamento;
//	}
//
//	public String getNomeEquipamento() {
//		return nomeEquipamento;
//	}
//
//	public void setNomeEquipamento(String nomeEquipamento) {
//		this.nomeEquipamento = nomeEquipamento;
//	}
//
//	public EspacoFisico getEspacoFisico() {
//		return espacoFisico;
//	}
//
//	public void setEspacoFisico(EspacoFisico espacoFisico) {
//		this.espacoFisico = espacoFisico;
//	}
//
//	@Override
//	public String toString() {
//		return "Equipamento [idequipamento=" + idEquipamento + ", nomeEquipamento=" + nomeEquipamento
//				+ ", espacoFisico=" + espacoFisico + "]";
//	}
}

