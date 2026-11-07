package com.ifpb.redeSocial.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ifpb.redeSocial.model.Usuario;
import com.ifpb.redeSocial.model.Vaga;

import java.util.List;

@Repository
public interface UsuarioRepository extends Neo4jRepository<Usuario, Long> {

    Usuario findByNome(String nome);//como essa interface deriva de neo4jrepo.. pra buscar um nó usuario onde a propriedade nome seja igual a uma string(especificado na classe)metodo derivado de consulta o spring escreve a consulta por debaixo dos panos em cypher

    // 1. RECOMENDAÇÃO DE AMIGOS doo seu amigo(2° grau)
    @Query("MATCH (u:Usuario {nome: $nomeUsuario})-[:CONECTADO_A]->(amigo:Usuario)-[:CONECTADO_A]->(sugestao:Usuario) " +
           "WHERE NOT (u)-[:CONECTADO_A]->(sugestao) AND u <> sugestao " +
           "RETURN DISTINCT sugestao")
    List<Usuario> buscarConexoesDeSegundoGrau(@Param("nomeUsuario") String nomeUsuario);
}