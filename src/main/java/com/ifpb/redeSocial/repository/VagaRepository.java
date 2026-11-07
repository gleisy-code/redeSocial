package com.ifpb.redeSocial.repository;

import com.ifpb.redeSocial.model.Vaga;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends Neo4jRepository<Vaga, Long> {
	// 2. RECOMENDAÇÃO DE VAGAS INTELIGENTE
    // Busca vagas que exigem skills que o usuário possui
    @Query("MATCH (u:Usuario {nome: $nomeUsuario})-[:POSSUI_SKILL]->(s:Skill)<-[:EXIGE_SKILL]-(v:Vaga) " +
           "RETURN DISTINCT v")
    List<Vaga> recomendarVagasPorSkill(@Param("nomeUsuario") String nomeUsuario);
}