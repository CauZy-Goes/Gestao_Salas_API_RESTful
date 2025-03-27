package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "cargo")
@Getter
@Setter
@ToString
public class Cargo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCargo;

    @Column(name = "nome_cargo", nullable = false, length = 50)
    private String nomeCargo;

//	public Integer getIdCargo() {
//		return idCargo;
//	}
//
//	public void setIdCargo(Integer idCargo) {
//		this.idCargo = idCargo;
//	}
//
//	public String getNomeCargo() {
//		return nomeCargo;
//	}
//
//	public void setNomeCargo(String nomeCargo) {
//		this.nomeCargo = nomeCargo;
//	}
//
//	@Override
//	public String toString() {
//		return "Cargo [idcargo=" + idCargo + ", nomeCargo=" + nomeCargo + "]";
//	}
	
	
}

