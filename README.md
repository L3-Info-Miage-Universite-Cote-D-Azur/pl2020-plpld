# Projet de licence - Groupe PLPLD

## Notre équipe
- Florian SPIRE
- Sofian MOUMEN
- Dimitri MORELLE
- Ossama ASHRAF
- Jeremy HIRTH DAUMAS

## Notre projet
Réalisation, en équipe, d'un développement Java/Android avec interface graphique sur tablette, avec gestion explicite du projet et gestion d'un changement conséquent en cours de réalisation.

## Description du projet
*Le client désire déployer une application Android autour du choix des cours dans le nouveau portail "Sciences et Technologies" de la licence (informatique) à Valrose.*

La liste complète des cours est disponible [ici](https://lms.univ-cotedazur.fr/pluginfile.php/112034/course/section/17947/listes%20des%20cours%20semestre%201%20%C3%A0%204.xlsx).

La liste complète des UE transversales est disponible [ici](https://lms.univ-cotedazur.fr/pluginfile.php/112034/course/section/17947/plaquette_competences_transversales.pdf).

Les deux fiches décompositions par UE et ECUE sont disponibles [ici](https://lms.univ-cotedazur.fr/pluginfile.php/251642/mod_folder/content/0/1A-Portail.pdf?forcedownload=1) et [ici](https://lms.univ-cotedazur.fr/pluginfile.php/251642/mod_folder/content/0/2A-Portail.pdf?forcedownload=1).

## Usage

Pour démarrer le serveur : lancer un terminal et exécuter :

   1 - `git clone https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plpld`
   
   2 - `cd pl2020-plpld/java`
   
   3 - `mvn clean install`   
   
   4 - `mvn exec:java`
   
 Pour démarrer l'application Android :

   5 - `Lancer Android Studio`
   
   6 - `Ouvrir pl2020-plpld/AndroidApp`
   
   7 - `Construire l'apk et l'exécuter depuis un émulateur ou un smartphone Android`
   
   # Note importante : l'émulateur ou le smartphone doit impérativement tourner sous Android 9 (API level 28) ou + 
            
                 

## Contraintes du projet à respecter
> L'application doit permettre de se construire un parcours, en choisissant les UE (en partant du S1) pour L1 et L2.

> Côté serveur, il faut pouvoir fournir la liste des cours, enregistrer des "parcours" et les fournir.

> Prendre en compte l'évolution 1 du sujet (à déterminer) le 11 mars 2020.

> Prendre en compte l'évolution 2 du sujet (à déterminer) le 1er avril 2020.
