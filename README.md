Impresións ao escribir a App. ¿tiveches nalgún momento a sensación que algunha parte do código a estabas escribindo de forma non apropiada por falta de non aplicar un patrón non visto ou por calquera outra cuestión?.
El momento que me generó más dudas fue a la hora de vincular el menú y la clase que llevaba la estructura general del juego, que se encuentra en la clase que contiene el main y me quedé con la impresión de que la clase del main debería estar usando el menú, y no 
a la inversa. La gestión de alguna de las listas no me pareció del todo correcta. Cada impostor almacena los estudiantes que asesina en una lista de su clase, pero tal como lo planteé yo el asesinato también se almacena en una lista en la clase del main para simplificar
las impresiones de resultados. Me hubiera gustado darle mejor uso. 

Indica si precisañes empregar iteradores donde e porqué.
Fue necesario usar iteradores, especialmente, en la gestión de listas que implicaban recorridos y eliminaciones, para evitar resultados inesperados. Por ejemplo, en mi método comprobar asesinato fue necesario e incluso usando iteradores podría producirse una excepción
si en primer lugar iteras sobre los estudiantes y luego sobre los impostores para comprobar si estaban en la misma habitación, dado que podría darse el caso de que dos impostores coincidieran con estudiantes en la misma sala e intentasen matar al mismo antes de que se 
actualizase bien el listado.

Indica si precisaches sobreescribir hashcode/equals donde e porqué.
Fue necesario en la clase Tarea, que tiene dos atributos que la identifican por completo y era necesario para que las comparaciones y las búsquedas se realizasen por el contenido de las mismas. 

Indica donde usaches comparable/comparator e porqué.
En las clases de Personaje y Tarea, puesto que era necesario para poder realizar las ordenaciones de forma correcta dado que la clase en sí no tenía implementada Comparable.

Unha lista breve das meioras achegadas por ti, e dicir, de funcións e restriccións que non se piden no enunciado.
No me ha dado tiempo a aplicar "mejoras" en sí, el comportamiento es similar a lo que se pedía y a lo explicado en la demostración en vídeo. 
Adapté la impresión final a mi gusto para diferenciar entre los vivos, expulsados y asesinados. 
También opté por crear una lista de asesinados en la clase del main para simplificar. 

Outras observacións.
No estoy contento con el resultado, a pesar de que creo que se cumple con lo que se pedía, por diversas razones:
-no se cumple con la encapsulación ni principio de ocultación
-no se cumple con la responsabilidad única
-no se cumple con algunos pricipios, como que la clase tipo interaface debe usar a las otras, y no al revés.
-el código de la clase App es demasiado largo, debería haberse planteado de otra manera
