package com.inflex.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Funcionario extends Pessoa implements Comparable<Funcionario>{
    BigDecimal salario;
    String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Funcionario that = (Funcionario) o;
        return Objects.equals(salario, that.salario) && Objects.equals(funcao, that.funcao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), salario, funcao);
    }


    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        DateTimeFormatter ftm = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return  '\n' +
                "Nome: " + nome + '\n' +
                "Função: " + funcao + '\n' +
                "Salário: R$" + df.format(salario) + '\n' +
                "Data de nascimento: " + dataNascimento.format(ftm) + '\n';
    }

    @Override
    public int compareTo(Funcionario o) {
        return nome.compareTo(o.nome);
    }
}

