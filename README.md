# Chess system CLI in Java ☕

[![License](https://img.shields.io/npm/l/react)](https://github.com/rogeriofrsouza/chess-system-java/blob/main/LICENSE)
<!--toc:start-->

- [About](#about)
- [Class diagram](#class-diagram)
- [How to run](#how-to-run)
<!--toc:end-->

## About

This is a CLI application developed during the course [**Java COMPLETO 2023 Programação Orientada a Objetos +Projetos**](https://www.udemy.com/course/java-curso-completo/), taught by the professor [Nelio Alves](https://www.udemy.com/user/nelio-alves/ "Perfil do Nelio Alves na Udemy").

This project consists of a chess game, allowing two players to execute commands and movements on their pieces in order to capture the opposing king and win the game.
It also supports special chess moves like: Castling, En Passant and Promotion.

It must be executed in a terminal with colors support for correct color displaying during runtime.

## Class diagram

![Class diagram](https://raw.githubusercontent.com/rogeriofrsouza/java-poo/main/assets/chess-system-design.png)

## How to run

Requirements: JDK 17+

```sh
git clone https://github.com/rogeriofrsouza/chess-system-java.git
cd chess-system-java
./mvnw dependency:resolve clean package
java -jar target/chess-system*
```
