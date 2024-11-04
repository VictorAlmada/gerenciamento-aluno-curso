package com.br.project1.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.project1.model.Aluno;
import com.br.project1.model.Curso;
import com.br.project1.repository.AlunoRepository;
import com.br.project1.repository.CursoRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Service
public class AlunoService {

	private final Validator validator;
	@Autowired
	private final AlunoRepository alunoRepository; // Repositório de Aluno
	@Autowired
	private CursoRepository cursoRepository; // Para acessar os cursos

	public AlunoService(AlunoRepository alunoRepository) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
		this.alunoRepository = alunoRepository;
	}

	public void adicionarAluno(Aluno aluno) {
		// Verifica se o aluno já existe
		if (alunoRepository.existsByCpf(aluno.getCpf())) {
			throw new IllegalArgumentException("Aluno com CPF já cadastrado.");
		}

		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Erro de validação:");
			for (ConstraintViolation<Aluno> violation : violations) {
				errorMessage.append("\n").append(violation.getMessage());
			}
			throw new IllegalArgumentException(errorMessage.toString());
		}

		alunoRepository.save(aluno); // Método que persiste o aluno
	}

	public List<Aluno> listarAlunos() {
		return alunoRepository.findAll(); // Retorna todos os alunos
	}

	public Optional<Aluno> buscarAlunoPorId(Long id) {
		return alunoRepository.findById(id); // Busca aluno por ID
	}

	public void atualizarAluno(Aluno aluno) {
		// Verifica se o aluno existe
		if (!alunoRepository.existsById(aluno.getId())) {
			throw new RuntimeException("Aluno não encontrado com o ID: " + aluno.getId());
		}
		
		// Validação do aluno
		Set<ConstraintViolation<Aluno>> violations = validator.validate(aluno);
		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Erro de validação:");
			for (ConstraintViolation<Aluno> violation : violations) {
				errorMessage.append("\n").append(violation.getMessage());
			}
			throw new IllegalArgumentException(errorMessage.toString());
		}
		
		// Salva as alterações
		alunoRepository.save(aluno);
	}

	public void deletarAluno(Long id) {
		alunoRepository.deleteById(id); // Remove o aluno pelo ID
	}

	public List<Aluno> buscarAlunosPorCurso(Long cursoId) {
		return alunoRepository.findAlunosByCursoId(cursoId); // Busca alunos pelo id do curso
	}

	// Vincular um aluno à um curso
	public Aluno vincularAlunoACurso(Long alunoId, Long cursoId) {
		Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
		Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new RuntimeException("Curso não encontrado"));

		// Adiciona o curso à lista de cursos do aluno
		if (!aluno.getCursos().contains(curso)) {
			aluno.getCursos().add(curso);
			alunoRepository.save(aluno);
		}

		return aluno;
	}
}
