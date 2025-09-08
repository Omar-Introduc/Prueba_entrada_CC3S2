# PARTE 2
* Fast-Forward Merge
Es una forma simple de fusionar, ocurre cuando la rama principal no ha cambiado, nos movemos a otra rama incluyendo nuevos commits. No se crea un commit de fusión debido a que el principal no avanzó.

* Cherry-Pick
Es útil cuando necesitamos copiar un commit en específico de una rama y aplicarlo en otra, muy util en cambios urgentes sin la necesidad de ejercer una fusión total.

* Rebase 
Los commits realizados en otra rama los mueve y los aplica al final de otra rama, generando linealidad aunque su uso altera o genera confusión cuadndo se usa en entorno con más desarrolladores.

# PARTE 3

API (Sección 3.2)
El header Content-Type de la respuesta de la API es: 
* content-type: application/json; charset=utf-8.