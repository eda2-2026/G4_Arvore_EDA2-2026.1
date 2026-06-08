# Sistema do Restaurante Universitário (RU) - EDA2

## Objetivo do Projeto
O objetivo deste projeto é implementar um sistema de gestão de alunos e catraca para o Restaurante Universitário. O sistema utiliza uma **Árvore AVL** como estrutura de dados central para garantir máxima eficiência nas operações de cadastro, recarga e validação de acesso na catraca, simulando a alta demanda de milhares de alunos em horário de pico.

## Características dos Dados e Arquitetura

* **Árvore AVL:** Estrutura de dados auto-balanceada que garante complexidade $O(\log (N))$ no pior caso para inserção, remoção e busca (validação da matrícula na catraca), mantendo a performance inabalável.
* **Sistema de Turnos Inteligente:** O sistema de catraca valida o acesso cruzando a tentativa de passagem com o relógio do sistema, garantindo que o usuário só consuma uma única vez dentro dos turnos estabelecidos (Café da Manhã, Almoço ou Jantar).
* **Dois Modos de Acesso (GUI):** A aplicação possui interface gráfica (via *Java Swing/JOptionPane*) dividida entre **Modo Usuário (Totem)** para autoatendimento e **Modo Administrador (Gerência)**, que permite gerenciar alunos e até "Forçar Turnos" para testes fora de horário.
* **Persistência Relacional Automatizada (Save-on-Exit):** Todos os dados do sistema são persistidos no disco rígido sem lentidão através de uma **Travessia In-Order** da Árvore AVL no momento de desligamento. O estado é desmembrado e salvo em `usuarios.csv` e `transacoes.csv` para manter o histórico financeiro intacto entre sessões.

## 💳 Gerador Probabilístico de Usuários (Massa de Dados)

Para simular o ecossistema complexo do RU, o projeto conta com o `util/GeradorUsuarios.java`, capaz de povoar o sistema instantaneamente com um banco de dados maciço e realista.

**Diferenciais do Gerador:**
* **Geração Lógica de Matrículas:** Cria matrículas no padrão universitário, garantindo unicidade matemática e evitando duplicatas.
* **Distribuição de Grupos e Saldos:** O algoritmo reconhece grupos de bolsas e vulnerabilidade. Alunos isentos (Grupo G1) têm a carteira sempre configurada adequadamente, enquanto os outros recebem saldos aleatórios simulando a realidade diária de recargas.
* **Nomes Reais e Balanceados:** Seleciona nomes variados, formatando os dados sem repetição.
* **Ordenação Prévia:** O gerador entrega os dados previamente ordenados no CSV para otimizar a carga inicial do programa.

## Divisão de Responsabilidades

Conforme a evolução do projeto e a arquitetura adotada, o desenvolvimento foi fatiado da seguinte maneira:

### Davi
* Implementação matemática e balanceamento da **Árvore AVL** (Rotações Esquerda/Direita).
* Integração das lógicas de Adição, Remoção e Busca na Árvore AVL.
* Modelagem do `GeradorUsuarios` com regras unificadas de matrícula e isenções (G1).
* Implementação do motor de persistência de dados bidimensional (`ArquivoUtil` com Save-on-Exit).

### Mateus
* Idealização e desenvolvimento do sistema de Refeições e Transações (`RestauranteService`).
* Lógica de validação de horário e impedimento de consumo duplicado no mesmo turno.

### Responsabilidade Compartilhada
* Construção e estruturação da Interface Gráfica interativa dividida em módulos (Usuário/Admin).
* Refatoração contínua e aplicação de princípios de Clean Code.
* Testes e adequação das Exceções de Regra de Negócio (ex: *SaldoInsuficienteException*, *RestauranteFechadoException*).


## 💻 Como Executar

A aplicação foi desenhada para rodar de forma leve e autônoma. O repositório não inclui os bancos de dados temporários (`usuarios.csv` e `transacoes.csv`), sendo necessário gerar os dados em sua máquina.

**Pré-requisitos:** JDK 21 ou superior.

1. Clone o repositório e abra o projeto na sua IDE de preferência (IntelliJ IDEA, Eclipse, VS Code).
2. Certifique-se de que a pasta `src/` está marcada como o diretório de fontes (*Sources Root*).
3. **Passo 1 - Gerar a Massa de Dados:**
   * Navegue até o arquivo `src/util/GeradorUsuarios.java`.
   * Execute a classe `GeradorUsuarios`.
   * O console informará a conclusão, e o arquivo `usuarios.csv` será criado na **raiz do projeto**.
4. **Passo 2 - Iniciar o Sistema do Restaurante:**
   * Navegue até o arquivo `src/Main.java`.
   * Execute a classe `Main`. O sistema carregará os usuários para a memória (dentro da AVL) e abrirá automaticamente o Painel Principal em interface gráfica.
5. **Passo 3 - Persistência do Extrato:**
   * Na primeira vez que o sistema for rodado, é normal ver um aviso no terminal informando que o `transacoes.csv` não foi encontrado. Esse banco de dados relacional é criado e atualizado no exato momento em que você clica em **"Sair"** no menu principal da aplicação, gravando o histórico e os novos saldos na raiz do projeto de forma segura.

## Equipe de Desenvolvimento

| <img src="docs/assets/fotos/Davi-UnB.png" width="120px;" alt="Davi Freitas"/><br />**Davi Freitas** | <img src="docs/assets/fotos/Mateus0xC.png" width="120px;" alt="Mateus Barreto"/><br />**Mateus Barreto** |
| :---: | :---: |
| Matrícula: **241011018** | Matrícula: **241011466** |
| <img src="https://github.com/Davi-UnB.png" width="16px;"/> [`@Davi-UnB`](https://github.com/Davi-UnB) | <img src="https://github.com/Mateus0xC.png" width="16px;"/> [`@Mateus0xC`](https://github.com/Mateus0xC)
