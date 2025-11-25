Exercice 1 — Tester un GET simple (status code)

➡️ Endpoint : /posts/1
Objectif : vérifier que la réponse retourne 200.

❗ Pas d’extractions, juste .statusCode(200).

Exercice 2 — Vérifier un champ JSON

➡️ /posts/1
Objectif : vérifier que :

userId = 1

id = 1

Exercice 3 — Lister tous les posts

➡️ /posts
Objectif : vérifier que tu reçois 100 éléments.

🟡 🔰 Niveau 2 — Extractions & paramètres
Exercice 4 — Extraire un champ JSON

➡️ /posts/1

Objectif :

extraire title

l’imprimer

vérifier qu'il n'est pas vide

Exercice 5 — Tester un query param

➡️ /posts?userId=1

Objectif :

faire la requête avec .queryParam("userId", 1)

vérifier que tous les objets retournés ont userId = 1

Exercice 6 — Tester un path param

➡️ /posts/{id}

Objectif :

remplacer {id} dynamiquement avec .pathParam("id", 5)

vérifier que id = 5

🟠 🔰 Niveau 3 — POST, PUT, DELETE

⚠️ L’API jsonplaceholder ne modifie rien pour de vrai, mais elle accepte les requêtes.

Exercice 7 — Envoyer un POST

➡️ /posts

Objectif :

envoyer un body JSON : { title, body, userId }

vérifier :

status = 201

id est retourné

le title de la réponse = celui que tu as envoyé

Exercice 8 — PUT (update complet)

➡️ /posts/1

Objectif :

modifier un post complet via PUT

vérifier que la réponse contient tes nouvelles valeurs

Exercice 9 — PATCH (update partiel)

➡️ /posts/1

Objectif :

mettre à jour seulement title

vérifier que seule cette propriété change

Exercice 10 — DELETE

➡️ /posts/1

Objectif :

envoyer une requête DELETE

vérifier que le status = 200 ou 204

🔵 🔰 Niveau 4 — Extractions complexes & Data classes Kotlin
Exercice 11 — Extraire un objet complet

➡️ /posts/1

Objectif :

créer une data class Post

convertir la réponse en objet Kotlin via :

.extract().body().`as`(Post::class.java)


vérifier les propriétés.

Exercice 12 — Extraire une liste

➡️ /posts

Objectif :

extraire tous les posts en List<Post>

vérifier que la liste contient 100 éléments

🔴 🔰 Niveau 5 — Tests plus avancés
Exercice 13 — Tester la performance

➡️ /posts/1

Objectif :

vérifier que le temps de réponse est < 700 ms

.time(lessThan(700L))

Exercice 14 — Créer un RequestSpecification global

Objectif :

créer une spec :

baseUri

default headers

logs

l’utiliser dans plusieurs tests

Exercice 15 — Chaînage : POST → GET

Objectif :

Faire un POST

Extraire id

Faire un GET /posts/{id}

Vérifier que le GET contient bien les valeurs envoyées dans le POST