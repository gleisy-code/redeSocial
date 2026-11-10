package com.ifpb.redeSocial.service;

import com.ifpb.redeSocial.model.Skill;
import com.ifpb.redeSocial.model.Usuario;
import com.ifpb.redeSocial.model.Vaga;
import com.ifpb.redeSocial.repository.UsuarioRepository;
import com.ifpb.redeSocial.repository.VagaRepository;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
	private final UsuarioRepository usuarioRepo;
    private final VagaRepository vagaRepo;

    public DatabaseService(UsuarioRepository usuarioRepo, VagaRepository vagaRepo) {
        this.usuarioRepo = usuarioRepo;
        this.vagaRepo = vagaRepo;
    }

    public void popularBancoDeDados() {
        if (usuarioRepo.count() > 0) {
            return;
        }

        Skill java = new Skill("Java");
        Skill neo4j = new Skill("Neo4j");
        Skill react = new Skill("React");
        Skill python = new Skill("Python");
        Skill docker = new Skill("Docker");
        Skill spring = new Skill("Spring Boot");

        Usuario gleisy = new Usuario("Gleisy");
        Usuario lucas = new Usuario("Lucas");
        Usuario mariana = new Usuario("Mariana");

        gleisy.adicionarSkill(java);
        gleisy.adicionarSkill(neo4j);

        lucas.adicionarSkill(java);
        mariana.adicionarSkill(react);

        gleisy.conectarCom(lucas);
        lucas.conectarCom(mariana);

        usuarioRepo.save(gleisy);
        usuarioRepo.save(lucas);
        usuarioRepo.save(mariana);

        Vaga vagaBackend = new Vaga("Dev Backend Java/Neo4j");
        vagaBackend.adicionarSkill(java);
        vagaBackend.adicionarSkill(neo4j);

        Vaga vagaFrontend = new Vaga("Desenvolvedor Frontend React");
        vagaFrontend.adicionarSkill(react);

        Vaga vagaDataEngine = new Vaga("Engenheiro de Dados Python");
        vagaDataEngine.adicionarSkill(python);
        vagaDataEngine.adicionarSkill(neo4j);

        Vaga vagaDevOps = new Vaga("Especialista DevOps & Cloud");
        vagaDevOps.adicionarSkill(docker);
        vagaDevOps.adicionarSkill(java);

        Vaga vagaFullstack = new Vaga("Dev Fullstack Java/React");
        vagaFullstack.adicionarSkill(java);
        vagaFullstack.adicionarSkill(spring);
        vagaFullstack.adicionarSkill(react);

        vagaRepo.save(vagaBackend);
        vagaRepo.save(vagaFrontend);
        vagaRepo.save(vagaDataEngine);
        vagaRepo.save(vagaDevOps);
        vagaRepo.save(vagaFullstack);
    }
}