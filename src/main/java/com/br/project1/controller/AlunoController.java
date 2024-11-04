package com.br.project1.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.project1.model.Aluno;
import com.br.project1.service.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;
    
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public ResponseEntity<String> criarAluno(@RequestBody Aluno aluno) {
        alunoService.adicionarAluno(aluno);
        return ResponseEntity.ok("Aluno cadastrado com sucesso!");
    }
    
    @GetMapping
    public ResponseEntity<List<Aluno>> listarAlunos() {
        List<Aluno> alunos = alunoService.listarAlunos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunoPorId(id)
            .map(aluno -> ResponseEntity.ok(aluno))
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAluno(@PathVariable Long id, @RequestBody Aluno aluno) {
        aluno.setId(id); // Define o ID do aluno para a atualização
        alunoService.atualizarAluno(aluno);
        return ResponseEntity.ok("Aluno atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAluno(@PathVariable Long id) {
        alunoService.deletarAluno(id);
        return ResponseEntity.ok("Aluno deletado com sucesso!");
    }
    
    @GetMapping("/curso/{cursoId}")
    public List<Aluno> buscarAlunosPorCurso(@PathVariable Long cursoId) {
        return alunoService.buscarAlunosPorCurso(cursoId);
    }
    
    @PostMapping("/{alunoId}/curso/{cursoId}")
    public ResponseEntity<Aluno> vincularAlunoACurso(@PathVariable Long alunoId, @PathVariable Long cursoId) {
        Aluno alunoAtualizado = alunoService.vincularAlunoACurso(alunoId, cursoId);
        return ResponseEntity.ok(alunoAtualizado);
    }
}

