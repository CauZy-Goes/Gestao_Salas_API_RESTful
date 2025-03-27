package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_acoes")
@Getter
@Setter
@ToString
public class LogAcoes implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLogAcoes;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @Column(name = "acao", nullable = false, length = 255)
    private String acao;

//	public Integer getIdLogAcoes() {
//		return idLogAcoes;
//	}
//
//	public void setIdLogAcoes(Integer idlogacoes) {
//		this.idLogAcoes = idlogacoes;
//	}
//
//	public LocalDateTime getDataHora() {
//		return dataHora;
//	}
//
//	public void setDataHora(LocalDateTime dataHora) {
//		this.dataHora = dataHora;
//	}
//
//	public Usuario getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Usuario usuario) {
//		this.usuario = usuario;
//	}
//
//	public String getAcao() {
//		return acao;
//	}
//
//	public void setAcao(String acao) {
//		this.acao = acao;
//	}
//
//	@Override
//	public String toString() {
//		return "LogAcoes [idlogacoes=" + idLogAcoes + ", dataHora=" + dataHora + ", usuario=" + usuario + ", acao="
//				+ acao + "]";
//	}
}

