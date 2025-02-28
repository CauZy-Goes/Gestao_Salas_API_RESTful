package ucsal.cauzy.domain.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuario")
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

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idusuario) {
		this.idUsuario = idusuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuario{" +
				"idUsuario=" + idUsuario +
				", nomeUsuario='" + nomeUsuario + '\'' +
				", email='" + email + '\'' +
				", senha='" + senha + '\'' +
				", cargo=" + cargo +
				'}';
	}
}

