package com.ifpb.redeSocial;

import com.ifpb.redeSocial.model.Usuario;
import com.ifpb.redeSocial.model.Vaga;
import com.ifpb.redeSocial.service.DatabaseService;
import com.ifpb.redeSocial.service.UsuarioService;
import com.ifpb.redeSocial.service.VagaService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class RedeSocialApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedeSocialApplication.class, args);
    }

    @Bean
    CommandLineRunner iniciarMenu(UsuarioService usuarioService, VagaService vagaService, DatabaseService seederService) {
        return args -> {
            seederService.popularBancoDeDados();

            Scanner scanner = new Scanner(System.in);

            System.out.println("\n=========================================");
            System.out.println("   BEM-VINDO AO MINI LINKEDIN (NEO4J)   ");
            System.out.println("=========================================");

            boolean sistemaRodando = true;

            while (sistemaRodando) {
                System.out.println("\n--- TELA INICIAL ---");
                System.out.println("1. Entrar em uma conta existente");
                System.out.println("2. Criar novo usuario (Novo No)");
                System.out.println("0. Sair da aplicacao");
                System.out.print("Escolha uma opcao: ");

                String opcaoInicial = scanner.nextLine().trim();

                if (opcaoInicial.equals("0")) {
                    System.out.println("\nSaindo do Mini LinkedIn... Ate logo!");
                    break;
                }

                String usuarioLogado = "";

                if (opcaoInicial.equals("2")) {
                    System.out.print("\nDigite o nome do novo usuario: ");
                    String novoNome = scanner.nextLine().trim();

                    if (novoNome.isEmpty()) {
                        System.out.println("[ERRO] O nome nao pode ser vazio!");
                        continue;
                    }

                    if (usuarioService.existeUsuario(novoNome)) {
                        System.out.println("[ERRO] Ja existe um usuario com o nome '" + novoNome + "'!");
                        continue;
                    }

                    usuarioService.criarUsuario(novoNome);
                    System.out.println("[SUCESSO] Usuario '" + novoNome + "' cadastrado no Neo4j!");
                    usuarioLogado = novoNome;

                } else if (opcaoInicial.equals("1")) {
                    System.out.print("Digite o nome do usuario (ex: Gleisy, Lucas, Mariana): ");
                    usuarioLogado = scanner.nextLine().trim();

                    if (!usuarioService.existeUsuario(usuarioLogado)) {
                        System.out.println("[ERRO] Usuario '" + usuarioLogado + "' nao foi encontrado!");
                        continue;
                    }
                } else {
                    System.out.println("[Opcao invalida! Tente novamente.]");
                    continue;
                }

                boolean sessaoAtiva = true;
                while (sessaoAtiva) {
                    System.out.println("\n-----------------------------------------");
                    System.out.println(" PERFIL ATUAL: " + usuarioLogado.toUpperCase());
                    System.out.println("-----------------------------------------");
                    System.out.println("1. Ver sugestoes de conexoes (2º Grau)");
                    System.out.println("2. Ver recomendacoes de vagas por Skills");
                    System.out.println("3. Seguir/Conectar com uma pessoa");
                    System.out.println("4. Adicionar nova Skill ao meu perfil");
                    System.out.println("5. Trocar / Cadastrar usuario");
                    System.out.println("0. Sair do programa");
                    System.out.print("Escolha uma opcao: ");

                    String opcao = scanner.nextLine().trim();

                    switch (opcao) {
                        case "1":
                            System.out.println("\n--- Pessoas que voce talvez conheca (2º Grau) ---");
                            List<Usuario> sugestoes = usuarioService.buscarSugestoesConexao(usuarioLogado);
                            if (sugestoes.isEmpty()) {
                                System.out.println("Nenhuma sugestao encontrada no momento.");
                            } else {
                                sugestoes.forEach(u -> System.out.println(" -> " + u.getNome()));
                            }
                            break;

                        case "2":
                            System.out.println("\n--- Vagas Recomendadas para Seu Perfil ---");
                            List<Vaga> vagas = vagaService.recomendarVagasPorSkill(usuarioLogado);
                            if (vagas.isEmpty()) {
                                System.out.println("Nenhuma vaga compativel com suas habilidades no momento.");
                            } else {
                                vagas.forEach(v -> System.out.println(" -> " + v.getTitulo()));
                            }
                            break;

                        case "3":
                            System.out.print("\nDigite o nome da pessoa que voce deseja seguir/conectar: ");
                            String nomeParaConectar = scanner.nextLine().trim();

                            if (nomeParaConectar.equalsIgnoreCase(usuarioLogado)) {
                                System.out.println("[ERRO] Voce nao pode seguir a si mesmo!");
                                break;
                            }

                            if (!usuarioService.existeUsuario(nomeParaConectar)) {
                                System.out.println("[ERRO] Usuario '" + nomeParaConectar + "' nao foi encontrado!");
                            } else {
                                usuarioService.conectarUsuarios(usuarioLogado, nomeParaConectar);
                                System.out.println("[SUCESSO] Voce agora esta conectado com " + nomeParaConectar + "!");
                            }
                            break;

                        case "4":
                            System.out.print("\nDigite a nova Skill que voce quer adicionar (ex: Python, Docker, Spring): ");
                            String nomeSkill = scanner.nextLine().trim();

                            if (nomeSkill.isEmpty()) {
                                System.out.println("[ERRO] O nome da Skill nao pode ser vazio!");
                                break;
                            }

                            usuarioService.adicionarSkillAoUsuario(usuarioLogado, nomeSkill);
                            System.out.println("[SUCESSO] Skill adicionada ao perfil de " + usuarioLogado + "!");
                            break;

                        case "5":
                            System.out.println("\n[LOGOUT] Efetuando logout do perfil " + usuarioLogado + "...");
                            sessaoAtiva = false;
                            break;

                        case "0":
                            System.out.println("\nSaindo do Mini LinkedIn... Ate logo!");
                            sessaoAtiva = false;
                            sistemaRodando = false;
                            break;

                        default:
                            System.out.println("\n[Opcao invalida! Tente novamente.]");
                            break;
                    }
                }
            }
        };
    }
}