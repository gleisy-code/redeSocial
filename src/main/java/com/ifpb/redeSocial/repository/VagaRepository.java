package com.ifpb.redeSocial.repository;

import com.ifpb.redeSocial.model.Vaga;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VagaRepository extends Neo4jRepository<Vaga, Long> {
}