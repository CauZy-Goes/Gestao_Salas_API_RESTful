package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "status")
@Getter
@Setter
@ToString
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;

    @Column(name = "nome_status", nullable = false, length = 50)
    private String nomeStatus;

//	public Integer getIdStatus() {
//		return idStatus;
//	}
//
//	public void setIdStatus(Integer idstatus) {
//		this.idStatus = idstatus;
//	}
//
//	public String getNomeStatus() {
//		return nomeStatus;
//	}
//
//	public void setNomeStatus(String nomeStatus) {
//		this.nomeStatus = nomeStatus;
//	}
//
//	@Override
//	public String toString() {
//		return "Status [idstatus=" + idStatus + ", nomeStatus=" + nomeStatus + "]";
//	}

    
}

