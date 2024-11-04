package com.br.project1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.br.project1.model.Curso;
import com.br.project1.repository.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<Curso> listarCursos() {
        return cursoRepository.findAll(); // Retorna todos os cursos
    }

    public void adicionarCurso(Curso curso) {
        cursoRepository.save(curso); // Persiste o curso no banco de dados
    }
}

