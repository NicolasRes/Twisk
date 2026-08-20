# Twisk

Application de simulation de flux de clients développée en **Java** dans le cadre d’un projet universitaire de **L2 Informatique**.

Twisk permet de **concevoir graphiquement un monde composé d'activités et de guichets**, puis de **simuler la circulation de clients dans ce monde**.  
La simulation repose sur une architecture mêlant **Java et C** avec génération dynamique de code C pour exécuter la simulation.

![til](./src/main/ressources/presentation_twisk.gif)

---

# 📦 Installation

## Prérequis

- Java **JDK 21**
- **JavaFX SDK 21+**
- **gcc** (pour compiler le code C généré)
- Linux / Ubuntu recommandé (ne fonctionne pas sur MacOS)

---

## Étapes

1. Télécharger ou cloner le dépôt :
git clone https://github.com/votre-utilisateur/twisk.git

2. Ouvrir le projet dans **IntelliJ IDEA** (par exemple)

3. Configurer JavaFX dans les VM options :
--module-path /chemin/vers/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml


4. Lancer l'application.

---

# 🎮 Fonctionnalités

## Construction graphique du monde

L'utilisateur peut créer un monde composé de :

- **Activités** : étapes où les clients passent un certain temps
- **Guichets** : files d'attente avec un nombre limité de jetons
- **Arcs** reliant les étapes
- **Entrées** indiquant par où les clients doivent arriver au début de la simulation
- **Sorties** indiquant où les clients doivent sortir à la fin de la simulation

---

## Interface graphique

L’application propose une interface permettant :

- de dessiner un monde
- de modifier ses paramètres
- de choisir le nombre de clients
- de sélectionner la loi d’arrivée
- de lancer et arrêter la simulation
- d'observer la progression des clients

La simulation est exécutée dans **un thread séparé**, tandis que les mises à jour de l’interface sont effectuées par **le thread JavaFX**.

---
## Simulation des clients

La simulation repose sur l’exécution de processus en langage C représentant les clients.

Lorsqu'une simulation est lancée :

1. l’application Java génère dynamiquement le code C correspondant au monde créé
2. ce code est compilé
3. la simulation est exécutée et les positions des clients sont récupérées afin de mettre à jour l’interface graphique

---

## Lois d'arrivée des clients

La simulation propose plusieurs modèles probabilistes pour générer les arrivées de clients :

- **loi uniforme**
- **loi gaussienne**
- **loi exponentielle**

Ces lois permettent de simuler différents types de systèmes (flux constants, pics d’activité, etc.).

---

# 🧠 Architecture

Le projet suit une architecture inspirée du modèle **MVC (Model – View – Controller)**.

# 🎓 Objectifs pédagogiques

- programmation orientée objet
- génération dynamique de code **C**
- conception d’interface graphique
- gestion de processus et synchronisation
- mise en place de l'architecture MVC
- travail en équipe

--

## 📦 Créer et lancer un `.jar`

### Créer le `.jar` avec IntelliJ IDEA

Dans IntelliJ IDEA :

1. Aller dans **File → Project Structure**
2. Sélectionner **Artifacts**
3. Cliquer sur **+ → JAR → From modules with dependencies**
4. Vérifier la configuration de l'artifact puis valider.
5. Générer le JAR depuis **Build → Build Artifacts → Build**.

Le fichier `.jar` sera généralement généré dans le dossier `out/artifacts/`.

### Lancer le `.jar`

Pour lancer le programme depuis un autre projet ou atelier Java, il est possible d'ajouter le JAR au classpath.

Se placer dans le dossier contenant les projets concernés, puis adapter les chemins suivants à son environnement :

```bash
javac -cp "<CHEMIN_VERS_LE_JAR>/twisk.jar:." <CHEMIN_VERS_L_ATELIER>/ClientMonde.java
```

Puis :

```bash
java -cp "<CHEMIN_VERS_LE_JAR>/twisk.jar:." <CHEMIN_VERS_L_ATELIER>/ClientMonde.java
```

> **Remarque :** les chemins ci-dessus sont volontairement génériques. Remplacer `<CHEMIN_VERS_LE_JAR>` et `<CHEMIN_VERS_L_ATELIER>` par les chemins correspondant à votre installation.

### Exemple

Si le projet est organisé de la manière suivante :

```text
projets/
├── twisk/
│   └── out/
│       └── artifacts/
│           └── twisk_jar/
│               └── twisk.jar
└── atelier/
    └── ClientMonde.java
```

Depuis le dossier `projets/`, les commandes deviennent :

```bash
javac -cp "twisk/out/artifacts/twisk_jar/twisk.jar:." atelier/ClientMonde.java
java -cp "twisk/out/artifacts/twisk_jar/twisk.jar:." atelier/ClientMonde.java
```

> **Attention :** la syntaxe `:` utilisée pour séparer les éléments du classpath correspond à Linux/macOS. Sous Windows, il faut utiliser `;` à la place.
