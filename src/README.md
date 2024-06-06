# Trabajo practico especial | Programacion 3 - 2024
> #### Integrantes:
> - Villa, Leopoldo. villaleoo@hotmail.com
> - Christensen, Gabriela. gchristensen@alumnos.exa.unicen.edu.ar

## Primera parte
> > Para los ***servicios*** de la primera parte se optó por tener una lista primaria de referencia a las tareas, mas precisamente un ArrayList, contenido en la clase CSVReader.
> > Esta lista de referencia sirve como informacion primaria para las distintas estructuras utilizadas en cada ***servicio*** y tambien para la **segunda parte** del trabajo. CSVReader tambien tiene un ArrayList para los procesadores, que es de utilidad para la **segunda parte** del trabajo.
> ### ***Servicio 1***
> > Para este servicio se utilizo una estructura de Hashing en su instancia de java HashMap.
> > Se optó por esta estructura dado que con el ingreso de un ***id*** de tarea la busqueda en el HashMap tenderia a **O(1)** si hay un solo resultado referenciado en ese balde y O(log n) si en el balde solicitado hay mas tareas referenciadas.
> > A esta estructura, dado que es estatica, le agrega las tareas el CSVReader a medida que va leyendo las tareas del dataset.
> ### ***Servicio 2***
> > Para este servicio se utilizaron 2 ArrayList estaticos. Uno que contiene las tareas que son criticas y otro que contiene las tareas que no son criticas.
> > Las tareas se almacenan en estas listas por el CSVReader a medida que las va obteniendo del dataset. La complejidad de este servicio es **O(1)**, ya que tanto si se buscan las tareas criticas como no criticas la funcion que obtiene una lista de tareas u otra accede directamente a la lista requerida en base al parametro booleano.
> ### ***Servicio 3***
> > Para este servicio se utilizo una estructura de Arbol Binario de Busqueda. La clase que contiene este arbol es **SearchGroupPriority**. El Arbol contiene **Nodos** generados a partir de la lista de tareas. Los Nodos almacenan como ***id*** el grado de prioridad de la tarea y como ***values*** index's de referencia a la lista de tareas ***ordenada descendentemente por grado de prioridad (de la mas prioritarias a la menos prioritarias)***, para crear esta lista se utiliza la lista de tareas del CSVReader.
> > A partir de esta lista ordenada, se crean los Nodos del Arbol y se busca el orden en el cual se van a insertar los Nodos en el Arbol para generar un ***Arbol balanceado***, seguidamente se agregan los Nodos.  
> >Gracias a esta estructura, el servicio 3 presentaria una complejidad **O(log n)**, que es la complejidad de busqueda en los ABB. En el peor de los casos (buscar tareas entre el valor minimo y maximo), se recorre todo el Arbol. Al encontrar los Nodos de referencia cuando se ejecuta una busqueda, por cada Nodo se extrae de la lista de tareas ordenadas, las tareas que esten en las posiciones de los ***values*** de cada Nodo.
> > Se utilizaron clases abstractas para eventualmente crear arboles para otros servicios.

## Segunda parte
> > Se implementaron soluciones a través de ***Backtracking*** y ***Greedy*** para la asignacion de tareas a procesadores. Se tuvo en cuenta las restricciones del parametro **X** ingresado por el usuario que condiciona a los procesadores ***no refrigerados*** y la restriccion de que cada procesador no puede ejecutar mas de **2** tareas **criticas**. Acorde a estas restricciones se hallaron soluciones posibles que, dependiendo del dataset utilizado, puede
> > encontrarse solucion o no.
> > - Un caso en el que no se encontraria solucion es si por cada procesador hay mas de 2 tareas criticas para asignar.
> > - Otro posible caso de fallo es una relacion entre el valor que toma X y la cantidad de tareas criticas. Si se reduce mucho la capacidad de los procesadores no refrigerados y los procesadores refrigerados llegan a su capacidad de 2 tareas criticas. Al querer insertar una tarea critica adicional, podria darse que no se pueda asignar esa tarea.
> > > Por ejemplo:
> > > - 4 procesadores (2 refrigerados, 2 no refrigerados).
> >  >- 8 tareas (5 criticas, todas con tiempo_ejecucion = 50).
> >  >- Valor ingresado de X = 47.
> > - Tambien hay casos en los cuales se llega a una solucion pero hay procesadores vacios, aunque esto puede no ser un error si sigue cumpliendo con el objetivo de minimizar tiempos.
### ***Greedy***
> > La estrategia utilizada fue ordenar las tareas que actuan como candidatos descendientemente por tiempo de ejecucion (de mas costosas a menos costosas en tiempos de ejecucion).  
> > Hecho esto, el algoritmo coloca los procesadores en la estructura solucion (esto seria inicialmente vacia) y luego va recorriendo la cola de tareas buscando el procesador que cumpla con los requerimientos para esa tarea (restricciones) y que al momento no tenga asignada ninguna tarea o que tenga el menor tiempo de ejecucion al momento en la asignacion.  
> > En la clase implementada se agrega mayor detalle de explicacion.
