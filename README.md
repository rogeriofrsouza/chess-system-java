# PROJETO: Sistema jogo de xadrez
[![NPM](https://img.shields.io/npm/l/react)](https://github.com/rogeriofrsouza/workshop-springboot3-mongodb/blob/main/LICENSE)


## Sobre o projeto
Esta é uma aplicação de console desenvolvida durante o curso [**Java COMPLETO 2023 Programação Orientada a Objetos +Projetos**](https://www.udemy.com/course/java-curso-completo/), ministrado pelo professor [Nelio Alves](https://www.udemy.com/user/nelio-alves/ "Perfil do Nelio Alves na Udemy").

Este projeto consiste em um jogo de xadrez, permitindo que dois jogadores executem comandos e movimentos em suas peças a fim de capturar o rei oponente e vencer o jogo. Deve ser executado em um terminal gráfico para a correta exibição de cores durante a execução. Possui também a lógica dos movimentos especiais do xadrez: Castling, En Passant e Promotion.


## Modelo conceitual
![Modelo Conceitual](https://raw.githubusercontent.com/rogeriofrsouza/java-poo/main/assets/chess-system-design.png)


## Como executar o projeto
Pré-requisitos: Java 17, Apache Maven

```bash
# Clonar repositório
git clone https://github.com/rogeriofrsouza/chess-system-java.git

# Entrar na pasta do projeto
cd chess-system-java

# Compilar o projeto
mvn compile
mvn package

# Executar o projeto
java -jar target/chess.system-0.1.0.jar
```

## Autor

Rogério Ferreira de Souza

https://www.linkedin.com/in/rogeriofrsouza
