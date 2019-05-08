# MY-REST-API
Ce projet est une API REST qui consiste à servir un blog.

## Dependencies nécessaires
* Spring Boot-2.1.4.RELEASE.
* Java-1.8.
* Frameworks : Maven 3, SpringMVC, Mockito, JSON.
* Base de données embarquée, H2 et hsqldb
* Tomcat-9
* IDE : Eclipse : 2018-09 (4.9.0) 

## Guide d'installation & utilisation
Ce projet utilise une base de donnée embarquées avec un script d'initialisation des données tests data.sql.
<br>
<h3>Installation du projet</h3>
* Importer le projet dans l'IDE
* Importer les dépendences necessaires, 
* ex. dans l'IDE Eclipse faire : Clic droit sur le projet -> Maven -> Update project...
* Lancer le projet :
* ex. Clic droit sur le projet ->Run -> Run As -> Choisir le serveur Tomcat 9

<h3>Arborescence du projet</h3>
* Les pages JSP se trouvent dans le dossier : /webapp/WEB-INF/views
* Les fichiers de configuration .properties se trouvent dans le dossier : /src/main/resources
* Le fichier application-dev.properties contient les configurations en mode Developpement
* Le fichier application-prod.properties contient les configurations en mode Production
* Ces deux fichiers sont détectés automatiquement par Spring selon que le cycle de vie du projet

## Guide d'installation & utilisation
_
<h3>Services REST</h3>
* POST :  /post/{id}/comment – création d’un nouveau commentaire sur Post du blog avec {id} du Post
* GET : /post/{id}/comments – retourne tous les commentaires du Post identifié par {id}
* PUT : /post/{id}/comments/{idc}/update – mise à jour d’un commentaire identifié par {idc} pour le Post dentifié par {id}
* DELETE : /post/{id}/comments/{idc}/delete – supprimer un commentaire identifié par {idc} pour le Post dentifié par {id}}
<h3>Interface CRUD</h3>
* Spring MVC avec JSTL 1.2 sont utilisées pour la conception des interfaces CRUD
* [Liste des Posts publies](http://localhost:8080/my-rest-api/posts)
* [Modifier un Post par son identifiant id](http://localhost:8080/my-rest-api/posts/{id}/update)
* [Lire un Post par son identifiant id](http://localhost:8080/my-rest-api/posts/{id})
* [Creer un commentaire à un Post par son identifiant id](http://localhost:8080/my-rest-api/posts/{id}/comments/add)
* [Modifier le commentaire avec identifiant idc d'un Post par son identifiant id](http://localhost:8080/my-rest-api/posts/{id}/comments/{idc}/update)
* [Supprimer le commentaire avec identifiant idc d'un Post par son identifiant id](http://localhost:8080/my-rest-api/posts/{id}/comments/{idc}/delete)
