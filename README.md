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
   
   ### Note importante : l'émulateur ou le smartphone doit impérativement tourner sous Android 9 (API level 28) ou + 
   
   ## Modification des fichiers par le client   
   Les fichiers ressources disponibles à l'emplacement "pl2020-plpld\java\serveur\src\main\resources" sont modifiables à tout moment par le gestionnaire du serveur. Le client peut modifier les fichiers txt à cet emplacement pour configurer simplement les parcours préconçus, les UE proposées à chaque semestre ainsi que les prérequis pour chaque UE.
   
   *Pour les fichiers contenant les UE des semestres (les fichiers textes S1, S2, S3 et S4) il faut faire précéder de '$' le nom des disciplines et les noms d'UE ne doivent pas contenir ce symbole.*
   
   *Le fichier "Prerequis.txt" doit contenir le symbole "$" avant le nom de l'UE qui demande un (ou plusieurs) prérequis et la ou les lignes suivantes doivent contenir la (ou les) UE prérequis(es) pour accéder à celle-ci. Les modifications de ce fichier sont détéctées par le serveur et le client en est notifié si cela impacte son parcours.*
   
   *Le fichier "Parcours_predefinis.txt" doit contenir le symbole "$" avant le nom du parcours et le symbole "$$" avant le numéro du semestre que l'on souhaite préconcevoir ensuite. Il n'y a pas d'obligation de définir les 4 semestres, si on en prédifinit que 2 par exemple cela sera automatiquement géré par l'application.*
   #### ATTENTION: le fichier définissant les parcours prédéfinis ainsi que celui énnonçant les prérequis doivent évidemment contenir uniquement des UE déjà listées dans les fichiers recensant les UE.
                 

## Contraintes du projet à respecter
> L'application doit permettre de se construire un parcours, en choisissant les UE (en partant du S1) pour L1 et L2.

> Côté serveur, il faut pouvoir fournir la liste des cours, enregistrer des "parcours" et les fournir.

### Modification n°1:
Il est demandé :
> D'intégrer des pré-requis comme les conditions d'accès en L3 (et s4) sans considérer les résultats : quand c'est écrit valider on condiérera "suivre" car on ne sait pas les notes qu'obtiendront les étudiants. Par contre si c'est écrit avoir valider x UE de y, mais que moins que x sont suivies, l'accès sera alors impossible... 

> Si une information est manquante pour une discipline, appliquer les mêmes informations que pour une discipline dont on aurait les informations, mais en structurant bien les données. 

> Pouvoir changer les données "facilement" : listes des cours, description des cours, "pré-requis", etc. (sans mettre à jour l'application).

> Il faut pouvoir ré-évaluer les "parcours" (pré-requis) : par exemple pour un parcours fait le 03/03 et les règles qui changent le 04/03, il faut que l'utilisateur sache que les règles ont changées depuis et qu'il puisse vérifier si les changement ont un impact.

Document complémentaire [ici](https://lms.univ-cotedazur.fr/pluginfile.php/112034/course/section/17947/modalites%20obtention%20L%20Info%20mars%202020.pdf)

### Modificaiton n°2:
Il est demandé d'intégrer au moins 2 des 4 améliorations suivantes :
> Rajouter les cours de L3 (disponible pour certaines disciplines) et le fait de pouvoir taguer un parcours publié par un autre étudiant.e. en tant que "parcours type pour une discipline" ou pour un objectif comme intégrer l'INSPE (Institut national supérieur du professorat et de le l'éducation : professeur.e.s des écoles, CAPES, AGREG, etc).

>Fixer les cours de L3 ou mention Disciplinaire comme objectifs à atteindre (ce qui restreint les choix).

>Intégrer un parcours en partie fait (par exemple le S1 ou le S2, il reste les autres semestres/cours à choisir).

>Pourvoir partager/récupérer son parcours (via courriel, un fichier texte, réseau sociaux...).

Les cours de L3 Info se trouvent [ici](http://i3s.unice.fr/master-info/programme/l3/) (les conditions d'accès sont sur la page L2)

Les cours de L3 MIAGE se trouvent [ici](https://lms.univ-cotedazur.fr/pluginfile.php/112034/course/section/17947/LICENCE%203%20MIASHS%20parcours%20MIAGE.xlsx)

Les conditions d'accès pour la L3 MIAGE se trouvent [ici](https://lms.univ-cotedazur.fr/pluginfile.php/112034/course/section/17947/Lic_MIASHS_MIAGE_modalites_obtention_prov_08_3_2020-1.pdf)
