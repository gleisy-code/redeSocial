package com.ifpb.redeSocial.service;

import com.ifpb.redeSocial.model.Vaga;
import com.ifpb.redeSocial.repository.VagaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VagaService {

    private final VagaRepository vagaRepo;

    public VagaService(VagaRepository vagaRepo) {
        this.vagaRepo = vagaRepo;
    }

    public List<Vaga> recomendarVagasPorSkill(String nomeUsuario) {
        return vagaRepo.recomendarVagasPorSkill(nomeUsuario);
    }
}