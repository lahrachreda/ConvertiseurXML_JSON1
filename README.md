# Convertisseur XML <-> JSON

Une application de bureau **JavaFX** simple et efficace permettant la conversion bidirectionnelle entre les formats de données XML et JSON.

## Description

Ce projet est un outil pratique pour les développeurs ayant besoin de transformer rapidement des données. Il propose une interface graphique intuitive séparée en deux panneaux : Entrée et Sortie.

L'application démontre deux approches techniques :
1. **L'utilisation de bibliothèques tierces** (Jackson) pour le parsing robuste.
2. **L'algorithmique personnalisée** (Récursivité) pour la reconstruction manuelle de structures de données.

---

##  Démonstration

Voici une vidéo de présentation montrant le fonctionnement de l'application :

### [▶️ Voir la vidéo de démonstration du projet](https://docs.google.com/videos/d/1OL6LrBhjKr29o7_emmdynmDWua9UzBiVR-MeYMwtEgE/edit?usp=sharing)

*(Cliquez sur le lien ci-dessus pour accéder à l'enregistrement sur Google Drive)*

---

##  Fonctionnalités

* **XML vers JSON** : Conversion automatique utilisant l'API Jackson.
* **JSON vers XML** : Moteur de conversion "maison" utilisant un algorithme récursif pour parcourir l'arbre JSON et générer les balises XML.
* **Interface Graphique (GUI)** : Interface claire réalisée avec JavaFX.
* **Gestion d'erreurs** : Alertes visuelles en cas de syntaxe invalide ou de champs vides.

## Technologies Utilisées

* **Langage** : Java
* **Interface** : JavaFX (FXML)
* **Bibliothèque JSON/XML** : `com.fasterxml.jackson` (Core, Databind, Dataformat-XML)

## Logique Technique

### 1. XML vers JSON
Utilisation de `XmlMapper` pour lire le flux d'octets et de `ObjectMapper` pour réécrire le contenu au format JSON indenté (Pretty Print).

### 2. JSON vers XML (Algorithme Manuel)
Au lieu d'utiliser une conversion automatique, cette partie implémente une méthode récursive `construireXmlRecursif` qui :
* Détecte si l'objet est une **Map** (Objet JSON) -> Crée des balises `<clé>`.
* Détecte si l'objet est une **List** (Tableau JSON) -> Crée des balises génériques `<item>`.
* Gère l'indentation pour une sortie lisible.

