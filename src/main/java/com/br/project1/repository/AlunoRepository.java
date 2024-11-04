package com.br.project1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.project1.model.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // Adicionar métodos de consulta personalizados, se necessário
	
	// Busca de alunos por curso
	@Query("SELECT a FROM Aluno a JOIN a.cursos c WHERE c.id = :cursoId")
    List<Aluno> findAlunosByCursoId(@Param("cursoId") Long cursoId);
	 // Verifica se um aluno com um determinado CPF já existe no banco de dados
	boolean existsByCpf(String cpf);
}

