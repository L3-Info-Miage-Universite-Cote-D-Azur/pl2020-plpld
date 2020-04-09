# Bilan des tests unitaires [Iteration 8](https://github.com/L3-Info-Miage-Universite-Cote-D-Azur/pl2020-plpld/milestone/8)

## Avancement des tests
Coté serveur :
- Les classe "GestionnaireDeReseau" et "Serveur" ont été testé avec Mock/Spy et en utilisant d'éventuelles ressources de teste. 

Coté client :
- Les tests instrumenté ont été légèrement modifié pour qu'ils fonctionnent de nouveaux.
- Ajout d'un nouveau test instrumenté "SimulationParcoursTest".
- (Les tests instrumenté sont toujours sous la dépendance du serveur, ceci devrait être corrigé a la prochaine itération).

## Avancement futur des tests
Coté client :
- Les tests instrumenté ne seront plus sous la dépendance du serveur.
- Des tests unitaire seront réalisé sur toutes les autres classes coté client.
