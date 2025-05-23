package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@ToString
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(name = "nome_usuario", nullable = false, length = 255)
    private String nomeUsuario;

    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;

	@Column(name = "senha" , nullable = false, length = 255)
	private String senha;

	@Column(name = "numero" , nullable = false, length = 255)
	private String numero;

	@ManyToOne
    @JoinColumn(name = "idcargo", nullable = false)
    private Cargo cargo;

	@PrePersist
	public void prePersist() {
		if (this.cargo == null) {
			this.cargo = new Cargo();
			this.cargo.setIdCargo(2); // Define o status "Professor" por padrão
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + cargo.getNomeCargo().toUpperCase()));
	}

//	public Integer getIdUsuario() {
//		return idUsuario;
//	}
//
//	public void setIdUsuario(Integer idUsuario) {
//		this.idUsuario = idUsuario;
//	}

//	public String getNomeUsuario() {
//		return nomeUsuario;
//	}
//
//	public void setNomeUsuario(String nomeUsuario) {
//		this.nomeUsuario = nomeUsuario;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getSenha() {
//		return senha;
//	}
//
//	public void setSenha(String senha) {
//		this.senha = senha;
//	}
//
//	public Cargo getCargo() {
//		return cargo;
//	}
//
//	public void setCargo(Cargo cargo) {
//		this.cargo = cargo;
//	}
}

