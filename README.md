# GraphMinPath

Experimentos para comparar resultados y costos en terminos de tiempo para tres implementaciones del algoritmo de caminos minimos de Dijkstra:
* Dijkstra normal con arreglos
* Dijkstra con colas clasicas
* Dijkstra con colas de fibonacci

## Modo de ejecución

En el paquete experiments existe una clase llamada *Main Experiment* la cual corre las pruebas dado un numero de iteraciones y escribe los resultados en un archivo .csv

## Notas

La clase ClassicHeap no tiene implementada por completo la operación decreaseKey por lo cual sus resultados son excluidos por el momento.