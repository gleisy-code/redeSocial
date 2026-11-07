package com.ifpb.redeSocial;

import org.springframework.boot.SpringApplication;
import com.ifpb.redeSocial.model.Skill;
import com.ifpb.redeSocial.model.Usuario;
import com.ifpb.redeSocial.model.Vaga;
import com.ifpb.redeSocial.repository.UsuarioRepository;
import com.ifpb.redeSocial.repository.VagaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedeSocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedeSocialApplication.class, args);
	}
	@Bean
    CommandLineRunner iniciarMenu(UsuarioRepository usuarioRepo, VagaRepository vagaRepo) {
        return args -> {
            // 1. POPOU O BANCO COM DADOS INICIAIS
            popularBancoDeDados(usuarioRepo, vagaRepo);

            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=========================================");
            System.out.println("   BEM-VINDO AO MINI LINKEDIN (NEO4J)   ");
            System.out.println("=========================================");
            
            // SIMULAÇÃO DE LOGIN
            System.out.print("Digite seu nome de usuario para logar (ex: Gleisy, Lucas, Mariana): ");
            String usuarioLogado = scanner.nextLine().trim();

            Usuario usuarioExistente = usuarioRepo.findByNome(usuarioLogado);

            if (usuarioExistente == null) {
                System.out.println("\n[ERRO] Usuario '" + usuarioLogado + "' nao foi encontrado no banco!");
                System.out.println("Fechando aplicacao...");
                return;
            }

            // LOOP DO MENU INTERATIVO
            boolean rodando = true;
            while (rodando) {
                System.out.println("\n-----------------------------------------");
                System.out.println(" PERFIL ATUAL: " + usuarioLogado.toUpperCase());
                System.out.println("-----------------------------------------");
                System.out.println("1. Ver sugestoes de conexoes (2º Grau)");
                System.out.println("2. Ver recomendacoes de vagas por Skills");
                System.out.println("0. Sair da rede social");
                System.out.print("Escolha uma opcao: ");

                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.println("\n--- Pessoas que voce talvez conheca (2º Grau) ---");
                        List<Usuario> sugestoes = usuarioRepo.buscarConexoesDeSegundoGrau(usuarioLogado);
                        if (sugestoes.isEmpty()) {
                            System.out.println("Nenhuma sugestao encontrada no momento.");
                        } else {
                            sugestoes.forEach(u -> System.out.println(" -> " + u.getNome()));
                        }
                        break;

                    case "2":
                        System.out.println("\n--- Vagas Recomendadas para Seu Perfil ---");
                        List<Vaga> vagas = usuarioRepo.recomendarVagasPorSkill(usuarioLogado);
                        if (vagas.isEmpty()) {
                            System.out.println("Nenhuma vaga compativel com suas habilidades no momento.");
                        } else {
                            vagas.forEach(v -> System.out.println(" -> " + v.getTitulo()));
                        }
                        break;

                    case "0":
                        System.out.println("\nSaindo do Mini LinkedIn... Ate logo!");
                        rodando = false;
                        break;

                    default:
                        System.out.println("\n[Opcao invalida! Tente novamente.]");
                        break;
                }
            }
        };
    }

    // Metodo auxiliar para cadastrar dados de teste automaticamente
    private void popularBancoDeDados(UsuarioRepository usuarioRepo, VagaRepository vagaRepo) {
        usuarioRepo.deleteAll();
        vagaRepo.deleteAll();

        // 1. Criando Skills
        Skill java = new Skill("Java");
        Skill neo4j = new Skill("Neo4j");
        Skill react = new Skill("React");

        // 2. Criando Usuarios
        Usuario gleisy = new Usuario("Gleisy");
        Usuario lucas = new Usuario("Lucas");
        Usuario mariana = new Usuario("Mariana");

        // 3. Atribuindo Skills
        gleisy.adicionarSkill(java);
        gleisy.adicionarSkill(neo4j);

        lucas.adicionarSkill(java);
        mariana.adicionarSkill(react);

        // 4. Conexoes: Gleisy -> Lucas -> Mariana
        gleisy.conectarCom(lucas);
        lucas.conectarCom(mariana);

        usuarioRepo.save(gleisy);
        usuarioRepo.save(lucas);
        usuarioRepo.save(mariana);

        // 5. Vaga exigindo habilidades da Gleisy
        Vaga vagaBackend = new Vaga("Dev Backend Java/Neo4j");
        vagaBackend.adicionarSkill(java);
        vagaBackend.adicionarSkill(neo4j);

        vagaRepo.save(vagaBackend);
    }

}
