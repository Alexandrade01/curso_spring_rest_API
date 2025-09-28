package curso.api.rest.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;

@Entity
@JsonPropertyOrder({"id","conta","dataNascimento","login","senha","telefones"})
public class Cliente implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column(name = "conta")
	private String conta;
	
	//padrão será yyyy-MM-dd
	@Column(name = "data_nascimento")
	private LocalDate dataNascimento;
	
	@Column(name = "login")
	private String login;
	
	@Column(name = "senha")
	private String senha;
	
	@OneToMany(mappedBy = "cliente", orphanRemoval = true,cascade = CascadeType.ALL)
	private List<Telefone> telefones = new ArrayList<>();
	
	
	//uma tabela com o codigo usuario_id e codigo role_id

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(
			//Define a tabela intermediária que vai armazenar os relacionamentos entre usuários e roles. - Nome da tabela: usuarios_role.
			name = "usuarios_role",
			//uniqueConstraints - Garante que não haverá duplicidade de registros com o mesmo usuario_id e role_id.
			//Nome da constraint: unique_role_user.
			uniqueConstraints = @UniqueConstraint(
			columnNames = {"usuario_id", "role_id"},
			name = "unique_role_user"
    ),
			joinColumns = @JoinColumn(
					//"usuario_id": nome da coluna na tabela usuarios_role.
					name = "usuario_id",
					referencedColumnName = "id",
					table = "usuario",
					unique = false,
					foreignKey = @ForeignKey(name = "usuario_fk", value = ConstraintMode.CONSTRAINT)
					),
			inverseJoinColumns = @JoinColumn(
					//"role_id": nome da coluna na tabela usuarios_role.
					name = "role_id",
					referencedColumnName = "id",
					table = "role",
					unique = false,
					foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)
    )
			 )
	private List<Role> roles; // os papeis ou acessos


	
	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(Id, other.Id);
	}
	
	//São os acessos do usuario exemplo ROLE_ADMIN , ROLE_VISITANTE
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return roles;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.senha;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.login;
	}


	

}
