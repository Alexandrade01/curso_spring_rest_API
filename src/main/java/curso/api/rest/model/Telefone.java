package curso.api.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Telefone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String numero;

	@JsonIgnore // É necessario ignorar o objeto Cliente no json para não gerar um looping
				// Usuario - Telefone
	@ManyToOne(optional = false) // Muitos telefones para um usuario // optional garante que exista um cliente antes de cadastrar o telefone
	@JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_telefones_cliente"))
	private Cliente cliente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
