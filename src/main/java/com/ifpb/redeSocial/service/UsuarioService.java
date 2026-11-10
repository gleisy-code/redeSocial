package com.ifpb.redeSocial.service;

import com.ifpb.redeSocial.model.Skill;
import com.ifpb.redeSocial.model.Usuario;
import com.ifpb.redeSocial.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public boolean existeUsuario(String nome) {
        return usuarioRepo.findByNome(nome) != null;
    }

    public Usuario buscarPorNome(String nome) {
        return usuarioRepo.findByNome(nome);
    }

    public Usuario criarUsuario(String nome) {
        Usuario novoUsuario = new Usuario(nome);
        return usuarioRepo.save(novoUsuario);
    }

    public List<Usuario> buscarSugestoesConexao(String nomeUsuario) {
        return usuarioRepo.buscarConexoesDeSegundoGrau(nomeUsuario);
    }

    public boolean conectarUsuarios(String nomeOrigem, String nomeDestino) {
        if (nomeOrigem.equalsIgnoreCase(nomeDestino)) {
            return false;
        }

        Usuario origem = usuarioRepo.findByNome(nomeOrigem);
        Usuario destino = usuarioRepo.findByNome(nomeDestino);

        if (origem == null || destino == null) {
            return false;
        }

        origem.conectarCom(destino);
        usuarioRepo.save(origem);
        return true;
    }

    public void adicionarSkillAoUsuario(String nomeUsuario, String nomeSkill) {
        Usuario usuario = usuarioRepo.findByNome(nomeUsuario);
        if (usuario != null) {
            String nomeSkillFormatado = nomeSkill.substring(0, 1).toUpperCase() + nomeSkill.substring(1).toLowerCase();
            Skill novaSkill = new Skill(nomeSkillFormatado);
            usuario.adicionarSkill(novaSkill);
            usuarioRepo.save(usuario);
        }
    }
}