package com.br.project1.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome é obrigatório")
	private String nome;
	
	@NotEmpty(message = "CPF é obrigatório")
	@Size(min = 11, max = 11, message = "O CPF deve ter 11 dígitos")
	@Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números")
	private String cpf;
	
	@NotNull(message = "Data de nascimento é obrigatória")
	private LocalDate dataNascimento;
	
	@NotEmpty(message = "Email é obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@ManyToMany
	@JoinTable(name = "matricula", joinColumns = @JoinColumn(name = "aluno_id"), inverseJoinColumns = @JoinColumn(name = "curso_id"))
	private List<Curso> cursos = new ArrayList<>();
	
	// constructor
	public Aluno(String nome, String email, LocalDate dataNascimento) {
		this.nome = nome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}

}
