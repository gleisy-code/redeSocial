package com.ifpb.redeSocial.model;

import java.util.HashSet;
import java.util.Set;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("Usuario")
public class Usuario {
    @Id 
    @GeneratedValue
    private Long id;
    private String nome;

    //relacionamento de amigos conectados
    @Relationship(type = "CONECTADO_A", direction = Relationship.Direction.OUTGOING)
    private Set<Usuario> conexoes = new HashSet<>();

    @Relationship(type = "POSSUI_SKILL", direction = Relationship.Direction.OUTGOING)
    private Set<Skill> skills = new HashSet<>();

    public Usuario() {}
    public Usuario(String nome) { 
    	this.nome = nome; 
    }

    public Long getId() {
    	return id; 
    }
    public String getNome() { 
    	return nome; 
    }
    public Set<Usuario> getConexoes() {
    	return conexoes; 
    }
    public Set<Skill> getSkills() { 
    	return skills;
    }

    public void conectarCom(Usuario outro) { 
    	this.conexoes.add(outro); 
    }
    public void adicionarSkill(Skill skill) { 
    	this.skills.add(skill); 
    }
}