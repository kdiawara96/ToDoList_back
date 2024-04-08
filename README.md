# ToDoList

## Description

Développer une mini-application permettant de gérer une liste de tâches (ToDo List). L'application comporte une simple API back-end et une interface utilisateur front-end. Si le temps le permet, et si tu juges cela pertinent, tu peux demander une simple vue mobile utilisant la même API.

## Prérequis

- Java 17
- Spring Boot 3.2.4
- Maven 3.8.4

## Installation

1. Clonez ce dépôt avec `git clone https://github.com/kdiawara96/ToDoList_back.git`.
3. Installez les dépendances avec `mvn install`.

## Dépendances

Ce projet utilise les dépendances suivantes :

- spring-boot-starter-data-jpa
- mysql-connector-j
- spring-boot-starter-web
- spring-boot-starter-oauth2-client
- spring-boot-starter-oauth2-resource-server
- spring-boot-devtools
- lombok
- spring-boot-starter-test

## Exigence du projet

Backend terminé

## Exécution du projet

1. Exécutez le projet avec `mvn spring-boot:run`.

## Sécurité

L'application est sécurisée avec JWT en mode asymétrique avec une clé publique et une clé privée. Les endpoints sont sécurisés avec un intercepteur.

## Problèmes courants

Si vous rencontrez des problèmes lors de l'exécution de l'application, essayez de suivre ces étapes :

- Assurez-vous que votre version de Java correspond à celle spécifiée dans le fichier `pom.xml`.
- Assurez-vous que votre version de Spring Boot correspond à celle spécifiée dans le fichier `pom.xml`.

Si vous continuez à rencontrer des problèmes, n'hésitez pas à ouvrir une issue sur ce dépôt.
