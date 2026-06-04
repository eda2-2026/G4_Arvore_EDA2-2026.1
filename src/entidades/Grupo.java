package entidades;

public enum Grupo {
    G1("Grupo 1", new Dinheiro(0,0), new Dinheiro(0,0), "Estudantes de graduação e pós-graduação com renda até 1,5 SM per capita (PNAES/UnB); indígenas e quilombolas; estudantes de programas de convênio (PeC-G, PeC-PLe); mobilidade acadêmica com perfil de assistência; cotas de escola pública – baixa renda; cotas para pessoas trans; cadastrados no CadÚnico até 0,5 SM per capita."),
    G2("Grupo 2", new Dinheiro(2,0), new Dinheiro(4,50), "Estudantes de graduação e pós-graduação; estagiários vinculados ao DGP/UnB; estudantes de programas de residência regulamentados."),
    G3("Grupo 3", new Dinheiro(7,5), new Dinheiro(15,20), "Servidores da UnB; trabalhadores terceirizados; visitantes em geral."),
    G4("Grupo 4", new Dinheiro(1,50), new Dinheiro(2,50), "Estudantes de graduação oriundos de cotas de escola pública (exceto baixa renda).");

    private final String nome;
    private final Dinheiro precoDesjejum;
    private final Dinheiro precoAlmocoJantar;
    private final String descricao;

    Grupo(String nome, Dinheiro precoDesjejum, Dinheiro precoAlmocoJantar, String descricao) {
        this.nome = nome;
        this.precoDesjejum = precoDesjejum;
        this.precoAlmocoJantar = precoAlmocoJantar;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Dinheiro getPrecoDesjejum() {
        return precoDesjejum;
    }

    public Dinheiro getPrecoAlmocoJantar() {
        return precoAlmocoJantar;
    }

}