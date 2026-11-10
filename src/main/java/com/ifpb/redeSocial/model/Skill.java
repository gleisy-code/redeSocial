package com.ifpb.redeSocial.model;


import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Skill")
public class Skill {
    @Id
    private String nome;

    public Skill() {}
    public Skill(String nome) { 
    	this.nome = nome; 
    }

    public String getNome() { 
    	return nome; 
    }
}