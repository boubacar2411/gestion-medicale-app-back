# Système de Gestion des Malades

## Description
Ce projet est une application de gestion des patients, médecins et rendez-vous médicaux. Il permet de gérer efficacement les dossiers des patients, les plannings des médecins et le suivi médical dans un établissement de santé.

## Fonctionnalités
- Gestion des patients (ajout, consultation, modification)
- Gestion des médecins (ajout, consultation, modification)
- Planification et gestion des rendez-vous médicaux
- Suivi médical des patients
- API RESTful pour l'intégration avec d'autres systèmes
- Interface Swagger pour la documentation et le test de l'API

## Technologies Utilisées
- **Backend**: Spring Boot 3.4.3, Java 21
- **Persistance des données**: Spring Data JPA, Hibernate
- **Base de données**: MySQL
- **Sécurité**: Spring Security
- **Documentation API**: SpringDoc OpenAPI (Swagger)
- **Outils de développement**: Maven, Spring Boot DevTools
- **Frontend** (prévu): Angular (référence au CORS configuré pour localhost:4200)

## Prérequis
- JDK 21
- Maven 3.x
- MySQL 8.x

## Installation

```bash
# Cloner le dépôt
git clone https://github.com/votre-username/gestion-malade.git

# Accéder au répertoire du projet
cd gestion-malade

# Compiler le projet avec Maven
mvn clean install

# Configurer la base de données
# Créer une base de données MySQL nommée 'gestion_malade'

# Exécuter l'application
mvn spring-boot:run
```

## Configuration
L'application est configurée pour s'exécuter sur le port 8888. Vous pouvez modifier cette configuration dans le fichier `src/main/resources/application.properties`.

Principales configurations:
```properties
server.port=8888
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_malade
spring.datasource.username=root
spring.datasource.password=
```

## Structure du Projet
```
gestion-malade/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── GestionMalade/
│   │   │           ├── controller/    # Contrôleurs REST
│   │   │           ├── entity/        # Entités JPA
│   │   │           ├── exception/     # Gestion des exceptions
│   │   │           ├── Repos/         # Repositories JPA
│   │   │           ├── security/      # Configuration de sécurité
│   │   │           ├── service/       # Services métier
│   │   │           └── sn/            # Configuration principale
│   │   └── resources/
│   │       ├── application.properties # Configuration de l'application
│   │       ├── static/                # Ressources statiques
│   │       └── templates/             # Templates
│   └── test/                          # Tests unitaires et d'intégration
├── pom.xml                            # Configuration Maven
└── README.md                          # Documentation du projet
```

## API REST
L'application expose les endpoints REST suivants:

### Patients
- `GET /api/patients` - Récupérer tous les patients
- `GET /api/patients/{id}` - Récupérer un patient par ID
- `POST /api/patients` - Créer un nouveau patient

### Médecins
- `GET /api/medecins` - Récupérer tous les médecins
- `GET /api/medecins/{id}` - Récupérer un médecin par ID
- `POST /api/medecins` - Créer un nouveau médecin

### Rendez-vous
- `GET /api/rendezvous` - Récupérer tous les rendez-vous
- `GET /api/rendezvous/{id}` - Récupérer un rendez-vous par ID
- `POST /api/rendezvous` - Créer un nouveau rendez-vous

## Documentation de l'API
La documentation complète de l'API est disponible via Swagger UI à l'adresse:
```
http://localhost:8888/swagger-ui.html
```

## Sécurité
L'application utilise Spring Security pour la sécurité. Actuellement, la configuration permet l'accès à tous les endpoints sans authentification pour faciliter le développement.

## Développement
Pour contribuer au projet:
1. Créez une branche pour votre fonctionnalité (`git checkout -b feature/ma-fonctionnalite`)
2. Committez vos changements (`git commit -m 'Ajout de ma fonctionnalité'`)
3. Poussez vers la branche (`git push origin feature/ma-fonctionnalite`)
4. Ouvrez une Pull Request

## Licence
Ce projet est sous licence [insérer type de licence].

## Contact
Pour toute question ou suggestion, veuillez contacter [votre nom/email]. 