package com.ifpb.redeSocial.model;

import java.util.HashSet;
import java.util.Set;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship; 
@Node("Vaga")
public class Vaga {
    @Id 
    @GeneratedValue
    private Long id;
    private String titulo;

    @Relationship(type = "EXIGE_SKILL", direction = Relationship.Direction.OUTGOING)
    private Set<Skill> skillsExigidas = new HashSet<>();

    public Vaga() {}
    public Vaga(String titulo) { 
    	this.titulo = titulo; 
    }

    public Long getId() { 
    	return id;
    }
    public String getTitulo() {
    	return titulo; 
    }
    public Set<Skill> getSkillsExigidas() { 
    	return skillsExigidas; 
    }
    public void adicionarSkill(Skill skill) { 
    	this.skillsExigidas.add(skill); 
    }
}