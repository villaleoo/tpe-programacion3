package tpe.filtros;

import tp2Arboles.TreeNode;
import tpe.Tarea;

import java.util.ArrayList;

public class ArbolBinarioBusqueda {
    private ArbolNodo raiz = null;
    private static ArrayList<Tarea> listaRef;

    ///si la lista de referencia del arbol no es estatica, cada vez que instancio un arbol tengo que andar copiando la lista de tareas, siempre, todas las veces;

    public static void setListaRef(ArrayList<Tarea> lista){
        if(listaRef == null){
            listaRef = lista;
        }
    }

    public int getRoot() {
        return this.raiz != null ? this.raiz.getValor() : -1;
    }

    public boolean hasElement(int value) {
        ArbolNodo tmp = this.raiz;
        boolean hasElem = false;

        while(tmp != null && !hasElem) {
            hasElem = this.isElement(tmp, value);
            if (!hasElem) {
                if (tmp.getValor() < value) {
                    tmp = tmp.getDerecha();
                } else {
                    tmp = tmp.getIzquierda();
                }
            }
        }

        return hasElem;
    }

    private boolean isElement(ArbolNodo node, int value) {
        return node.getValor() == value;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }

    public void insert(int valor) {
        if (this.raiz == null) {
            this.raiz = new ArbolNodo(valor);
        } else {
            this.add(this.raiz, valor);
        }

    }

    private void add(ArbolNodo nodo, int valor) {
        ArbolNodo temp;
        if (nodo.getValor() > valor) {
            if (nodo.getIzquierda() == null) {
                temp = new ArbolNodo(valor);
                nodo.setIzquierda(temp);
            } else {
                this.add(nodo.getIzquierda(), valor);
            }
        } else if (nodo.getValor() < valor) {
            if (nodo.getDerecha() == null) {
                temp = new ArbolNodo(valor);
                nodo.setDerecha(temp);
            } else {
                this.add(nodo.getDerecha(), valor);
            }
        }

    }

    public int getHeight() {
        return this.isEmpty() ? 0 : this.getH(this.raiz);
    }

    private int getH(ArbolNodo nodo) {
        if (nodo == null) {
            return -1;
        } else {
            int hIzq = this.getH(nodo.getIzquierda());
            int hDer = this.getH(nodo.getDerecha());
            return hIzq >= hDer ? hIzq + 1 : hDer + 1;
        }
    }

    public void printPosOrder() {
        this.posOrder(this.raiz);
    }

    private void posOrder(ArbolNodo nodo) {
        if (nodo.getIzquierda() != null) {
            this.posOrder(nodo.getIzquierda());
        } else {
            System.out.println("-");
        }

        if (nodo.getDerecha() != null) {
            this.posOrder(nodo.getDerecha());
        } else {
            System.out.println("-");
        }

        System.out.println(nodo.getValor());
    }

    public void printPreOrder() {
        this.preOrder(this.raiz);
    }

    private void preOrder(ArbolNodo nodo) {
        System.out.println(nodo.getValor());
        if (nodo.getIzquierda() != null) {
            this.preOrder(nodo.getIzquierda());
        } else {
            System.out.println("-");
        }

        if (nodo.getDerecha() != null) {
            this.preOrder(nodo.getDerecha());
        } else {
            System.out.println("-");
        }

    }

    public void printInOrder() {
        this.inOrder(this.raiz);
    }

    private void inOrder(ArbolNodo nodo) {
        if (nodo.getIzquierda() != null) {
            this.inOrder(nodo.getIzquierda());
        } else {
            System.out.println("-");
        }

        System.out.println(nodo.getValor());
        if (nodo.getDerecha() != null) {
            this.inOrder(nodo.getDerecha());
        } else {
            System.out.println("-");
        }

    }

    public ArrayList<ArbolNodo> getRamaMasLarga() {
        return this.isEmpty() ? new ArrayList() : this.getRamaLarga(this.raiz);
    }

    private ArrayList<ArbolNodo> getRamaLarga(ArbolNodo nodo) {
        ArrayList<ArbolNodo> lista = new ArrayList();
        if (nodo == null) {
            return new ArrayList();
        } else {
            lista.add(nodo);
            ArrayList<ArbolNodo> hIzq = this.getRamaLarga(nodo.getIzquierda());
            ArrayList<ArbolNodo> hDer = this.getRamaLarga(nodo.getDerecha());
            if (hIzq.size() >= hDer.size()) {
                lista.addAll(hIzq);
            } else {
                lista.addAll(hDer);
            }

            return lista;
        }
    }

    public ArrayList<ArbolNodo> getFrontera() {
        return this.isEmpty() ? new ArrayList() : this.getHojas(this.raiz);
    }

    private ArrayList<ArbolNodo> getHojas(ArbolNodo nodo) {
        ArrayList<ArbolNodo> lista = new ArrayList();
        if (nodo == null) {
            return new ArrayList();
        } else if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
            lista.add(nodo);
            return lista;
        } else {
            ArrayList<ArbolNodo> hIzq = this.getHojas(nodo.getIzquierda());
            ArrayList<ArbolNodo> hDer = this.getHojas(nodo.getDerecha());
            lista.addAll(hIzq);
            lista.addAll(hDer);
            return lista;
        }
    }

    public int getMaxElement() {
        return this.isEmpty() ? 0 : this.getMaxDerecha(this.raiz);
    }

    private int getMax(ArbolNodo nodo) {
        if (nodo == null) {
            return -1;
        } else {
            int valor = nodo.getValor();
            int izq = this.getMax(nodo.getIzquierda());
            int der = this.getMax(nodo.getDerecha());
            return Math.max(valor, Math.max(izq, der));
        }
    }

    private int getMaxDerecha(ArbolNodo nodo) {
        if (nodo == null) {
            return -1;
        } else {
            int valor = nodo.getValor();
            int der = this.getMaxDerecha(nodo.getDerecha());
            return Math.max(valor, der);
        }
    }

    public ArrayList<ArbolNodo> getElematLevel(int lvl) {
        return this.isEmpty() ? new ArrayList() : this.getListLevel(this.raiz, lvl, 1);
    }

    private ArrayList<ArbolNodo> getListLevel(ArbolNodo node, int lvl, int ac) {
        ArrayList<ArbolNodo> tmp = new ArrayList();
        if (node == null) {
            return new ArrayList();
        } else if (lvl == 1) {
            tmp.add(node);
            return tmp;
        } else if (lvl == ac) {
            tmp.add(node);
            return tmp;
        } else {
            ArrayList<ArbolNodo> izq = this.getListLevel(node.getIzquierda(), lvl, ac + 1);
            ArrayList<ArbolNodo> der = this.getListLevel(node.getDerecha(), lvl, ac + 1);
            tmp.addAll(izq);
            tmp.addAll(der);
            return tmp;
        }
    }

    public int getSumInter() {
        return this.isEmpty() ? 0 : this.getSumatoriaInternos(this.raiz);
    }

    private int getSumatoriaInternos(ArbolNodo nodo) {
        int sumatoria = 0;
        if (nodo.getIzquierda() != null || nodo.getDerecha() != null) {
            sumatoria += nodo.getValor();
            if (nodo.getIzquierda() != null) {
                sumatoria += this.getSumatoriaInternos(nodo.getIzquierda());
            }

            if (nodo.getDerecha() != null) {
                sumatoria += this.getSumatoriaInternos(nodo.getDerecha());
            }
        }

        return sumatoria;
    }

    public ArrayList<Integer> getListHojasMayores(int value) {
        return this.isEmpty() ? new ArrayList() : this.getHojasMayores(this.raiz, value);
    }

    private ArrayList<Integer> getHojasMayores(ArbolNodo nodo, int val) {
        ArrayList<Integer> hojasMayores = new ArrayList();
        if (nodo == null) {
            return new ArrayList();
        } else if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
            if (nodo.getValor() > val) {
                hojasMayores.add(nodo.getValor());
            }

            return hojasMayores;
        } else {
            ArrayList<Integer> izq = this.getHojasMayores(nodo.getIzquierda(), val);
            ArrayList<Integer> der = this.getHojasMayores(nodo.getDerecha(), val);
            hojasMayores.addAll(izq);
            hojasMayores.addAll(der);
            return hojasMayores;
        }
    }

    public void completeTree() {
        this.raiz.setValor(this.treeComplete(this.raiz));
    }

    private int treeComplete(ArbolNodo nodo) {
        if (nodo == null) {
            return 0;
        } else if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
            return nodo.getValor();
        } else {
            int izq = this.treeComplete(nodo.getIzquierda());
            int der = this.treeComplete(nodo.getDerecha());
            nodo.setValor(der - izq);
            return nodo.getValor();
        }
    }

}
