package com.inflex;

import com.inflex.model.Funcionario;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Funcionario> lista = new ArrayList<Funcionario>();

        //Adiciona funcionários
        lista.add(registraFuncionario("Maria", "18/10/2000", 2009.44, "Operador"));
        lista.add(registraFuncionario("João", "12/05/1990", 2284.38, "Operador"));
        lista.add(registraFuncionario("Caio", "02/05/1961", 9836.14, "Coordenador"));
        lista.add(registraFuncionario("Miguel", "14/10/1988", 19119.88, "Diretor"));
        lista.add(registraFuncionario("Alice", "05/01/1995", 2234.68, "Recepcionista"));
        lista.add(registraFuncionario("Heitor", "19/11/1999", 1582.72, "Operador"));
        lista.add(registraFuncionario("Arthur", "31/03/1993", 4071.84, "Contador"));
        lista.add(registraFuncionario("Laura", "08/07/1994", 3017.45, "Gerente"));
        lista.add(registraFuncionario("Heloísa", "24/05/2003", 1606.85, "Eletricista"));
        lista.add(registraFuncionario("Helena", "02/09/1996", 2799.93, "Gerente"));

        //Remove João
        lista.removeIf(n -> n.getNome().equals("João"));

        //Imprime lista sem o João
        System.out.println("Lista de funcionários:" + lista);
        aplicaEspacamento();

        //Adiciona 10% no aumento
        lista.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10"))));

        //Instancia Map
        Map<String, List<Funcionario>> setores = new HashMap<String, List<Funcionario>>();

        //Add a key e value (filtrando por setor)
        setores.put("Operador", lista.stream().filter(n -> n.getFuncao().equals("Operador")).toList());
        setores.put("Coordenador", lista.stream().filter(n -> n.getFuncao().equals("Coordenador")).toList());
        setores.put("Diretor", lista.stream().filter(n -> n.getFuncao().equals("Diretor")).toList());
        setores.put("Recepcionista", lista.stream().filter(n -> n.getFuncao().equals("Recepcionista")).toList());
        setores.put("Gerente", lista.stream().filter(n -> n.getFuncao().equals("Gerente")).toList());
        setores.put("Eletricista", lista.stream().filter(n -> n.getFuncao().equals("Eletricista")).toList());

        //Imprime por setor
        imprimeSetor(setores, "Operador");
        imprimeSetor(setores, "Coordenador");
        imprimeSetor(setores, "Diretor");
        imprimeSetor(setores, "Recepcionista");
        imprimeSetor(setores, "Eletricista");
        aplicaEspacamento();

        //Imprime um filtro que pega somente os funcionarios que o nascimento seja do mês dez pra frente
        imprimeFiltroMesAniversario(lista, 10);
        aplicaEspacamento();

        //Procura funcionário e imprime
        imprimeFuncionarioComMaiorIdade(lista);
        aplicaEspacamento();

        //Imprime a lista de funcionários em ordem alfabetica
        imprimeOrdemAlfabetica(lista);
        aplicaEspacamento();

        //Imprime o valor total dos salarios usando reduce
        imprimeSalarioTotal(lista);
        aplicaEspacamento();

        imprimeSalarioMinimo(lista);


    }
    //Função para auxiliar na legibilidade do código, criando o funcionário com a formatação da data
    public static Funcionario registraFuncionario(String nome, String dataDeNascimento, double salario, String funcao) {
        DateTimeFormatter fmt =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new Funcionario(nome, LocalDate.parse(dataDeNascimento, fmt), BigDecimal.valueOf(salario), funcao);
    }

    public static void imprimeSetor(Map<String, List<Funcionario>> setores, String descricao) {
        StringBuilder sb = new StringBuilder();
        sb.append("Função: ").append(descricao).append('\n');

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat df = new DecimalFormat("#,##0.00");

        for(Funcionario fnc: setores.get(descricao)) {
            sb.append('\n').append("Nome: ").append(fnc.getNome()).append('\n');
            sb.append("Salário: ").append(df.format(fnc.getSalario())).append('\n');
            sb.append("Data de Nascimento: ").append(fnc.getDataNascimento().format(fmt)).append('\n');
        }

        System.out.println(sb.toString());
    }

    public static void imprimeFiltroMesAniversario(List<Funcionario> funcionarios, int mes) {
        StringBuilder sb = new StringBuilder();
        sb.append("Aniversáriantes do mês 10 a 12: " ).append('\n');
        List<Funcionario> listaFormatada = funcionarios.stream().filter(n -> n.getDataNascimento().getMonthValue() >= mes)
                .toList();

        for(Funcionario fnc: listaFormatada) {
            sb.append(fnc);
        }
        System.out.println(sb);
    }

    //Função para procurar o funcionário com a maior idade, formatar a string e imprimir
    public static void imprimeFuncionarioComMaiorIdade(List<Funcionario> funcionarios) {
        Funcionario fnc = funcionarios.get(0);

        for (int i = 1; i < funcionarios.size(); i++) {
            if(funcionarios.get(i).getDataNascimento().isBefore(fnc.getDataNascimento())) {
                fnc = funcionarios.get(i);
            }
      }

        int idade = LocalDate.now().getYear() - fnc.getDataNascimento().getYear();

        System.out.print("Funcionário com maior idade: " + fnc.getNome() + ", possui " + idade + " anos" + '\n');
    }

    public static void aplicaEspacamento() {
        System.out.println('\n'+ "-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=" + '\n');
    }

    //Função para somar todos os valores via stream->reduce
    public static void imprimeSalarioTotal(List<Funcionario> lista) {
        BigDecimal total = lista.stream()
                .map((fnc) -> fnc.getSalario())
                .reduce(BigDecimal.ZERO, (acumulador, salario) -> acumulador.add(salario));
        DecimalFormat df = new DecimalFormat("#,##0.00");


        System.out.println("Somatório de salario da empresa: R$" + df.format(total));
    }

    public static void imprimeSalarioMinimo(List<Funcionario> lista) {
        for(Funcionario fnc : lista) {
            BigDecimal minimo = fnc.getSalario().divide(BigDecimal.valueOf(1212.00), 1, RoundingMode.HALF_UP);
            System.out.println(fnc.getNome() + " recebe aproximadamente " + minimo + " salario(s) minimo(s)");
        }
    }

    public static void imprimeOrdemAlfabetica(List<Funcionario> lista) {
        StringBuilder sb = new StringBuilder();
        sb.append("Funcionários da empresa: " ).append('\n');
        List<Funcionario> listaOrdenada = lista.stream()
                .sorted()
                .toList();

        for(Funcionario fnc : listaOrdenada) {
            sb.append(fnc);
        }

        System.out.println(sb);
    }

}