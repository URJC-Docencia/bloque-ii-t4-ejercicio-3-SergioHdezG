
package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jdk.jshell.spi.ExecutionControl;
import material.Position;

/**
 *
 * @author mayte
 */
public class AVLTree<E> implements BinarySearchTree<E> {

    private AVLInfo<E> checkPosition(Position<E> v) {
        if (v != null && v instanceof AVLInfo)
            return (AVLInfo<E>) v;
        throw new RuntimeException("This is not a valid element");
    }

    private class AVLInfo<T> implements Comparable<AVLInfo<T>>, Position<T>{
        
        private T element;
        private int height;
        private Position<AVLInfo<T>> pos;

        public AVLInfo(T element) {
            this.element = element;
            this.height = 1;
            this.pos = null;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public Position<AVLInfo<T>> getTreePos() {
            return pos;
        }

        public void setTreePos(Position<AVLInfo<T>> pos) {
            this.pos = pos;
        }
        
        @Override
        public int compareTo(AVLInfo<T> o) {
            if (element instanceof Comparable && o.element instanceof Comparable){
                Comparable<T> c1 = (Comparable<T>)element;
                
                return c1.compareTo(o.element);
            }
            throw new RuntimeException("This type of information is not comparable");
        }

        @Override
        public T getElement() {
            return this.element;
        }
        
    }

    private class AVLTreeIterator<R> implements Iterator<Position<R>>{
        private Iterator<Position<AVLInfo<R>>> it;
        public AVLTreeIterator(Iterator<Position<AVLInfo<R>>> it){
            this.it = it;
        }
        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public Position<R> next() {
            Position<AVLInfo<R>> p = it.next();
            return p.getElement();
        }
        
    }
    
    private LinkedBinarySearchTree<AVLInfo<E>> t = new LinkedBinarySearchTree<>();

    private Reestructurator r = new Reestructurator();
    
    @Override
    public Position<E> find(E value) {
        AVLInfo<E> nodo = new AVLInfo<>(value);
        Position<AVLInfo<E>> pos = this.t.find(nodo);
        return (pos == null) ? null : pos.getElement();
    }

    @Override
    public Iterable<? extends Position<E>> findAll(E value) throws ExecutionControl.InternalException {
        throw new ExecutionControl.InternalException("An AVL Tree does not allow duplicate values.");
    }

    private void recalcularAlturaDeUnNodo(Position<AVLInfo<E>> p){
        Iterable<? extends Position<AVLInfo<E>>> children = this.t.children(p);
        int max = 0;
        for (Position<AVLInfo<E>> aux : children){
            if (aux.getElement().height > max){
                max = aux.getElement().height;
            }
        }
        p.getElement().setHeight(1+max);
    }
    
    private void recalcularAltura(Position<AVLInfo<E>> p){
        Iterable<? extends Position<AVLInfo<E>>> children = this.t.children(p);
        int max = 0;
        for (Position<AVLInfo<E>> aux : children){
            if (aux.getElement().height > max){
                max = aux.getElement().height;
            }
        }
        p.getElement().setHeight(1+max);

        if (!this.t.isRoot(p))
            recalcularAltura(this.t.parent(p));
    }

    private void balance(Position<AVLInfo<E>> p){
        Position<AVLInfo<E>> aux = p;
        while (!this.t.isRoot(aux)){
            aux = this.t.parent(aux);
            int leftHeight = (this.t.hasLeft(aux)) ? this.t.left(aux).getElement().height : 0;
            int rightHeight = (this.t.hasRight(aux)) ? this.t.right(aux).getElement().height : 0;
            int balanceFactor = rightHeight - leftHeight;

            if (Math.abs(balanceFactor) > 1){
                Position<AVLInfo<E>> son;
                if (balanceFactor < 0){  // Si balanceFactor < 0 me voy a buscra el hijo izquierdo
                    son = this.t.left(p);
                }
                else{
                    son = this.t.right(p);
                }
                leftHeight = (this.t.hasLeft(son)) ? this.t.left(son).getElement().height : 0;
                rightHeight = (this.t.hasRight(son)) ? this.t.right(son).getElement().height : 0;
                int sonBalanceFactor = rightHeight - leftHeight;

                Position<AVLInfo<E>> grandSon;

                if (sonBalanceFactor < 0){  // Si sonBalanceFactor < 0 me voy a buscra el hijo izquierdo
                    grandSon = this.t.left(son);
                }
                else{
                    grandSon = this.t.right(p);
                }
                Position<AVLInfo<E>> subRoot = this.r.restructure(grandSon, this.t);
                if (this.t.hasLeft(subRoot))
                    recalcularAlturaDeUnNodo(this.t.left(subRoot));
                if (this.t.hasRight(subRoot))
                    recalcularAlturaDeUnNodo(this.t.right(subRoot));
                recalcularAltura(subRoot);
            }
        }
    }

    @Override
    public Position<E> insert(E value) {
        AVLInfo<E> nodo = new AVLInfo<>(value);
        Position<AVLInfo<E>> p = this.t.find(nodo);
        if (p == null) {
            p = this.t.insert(nodo);
            nodo.setTreePos(p);
            recalcularAltura(p);
            balance(p);
        }
        return p.getElement();
    }

    @Override
    public boolean isEmpty() {
        return t.isEmpty();
    }

    @Override
    public void remove(Position<E> pos) {
        AVLInfo<E> nodo = this.checkPosition(pos);
        Position<AVLInfo<E>> parent = null;

        if (this.hasLeft(nodo) && this.hasRight(nodo)){
            parent = this.t.succesor(nodo.getTreePos());
            parent = this.t.parent(parent);
        }
        else{
            if (!this.t.isRoot(nodo.getTreePos())){
                parent = this.t.parent(nodo.getTreePos());
            }
        }
        
        this.t.remove(nodo.getTreePos());
        if (parent != null) {
            recalcularAltura(parent);
            balance(parent);
        }
    }

    @Override
    public int size() {
        return t.size();
    }

    @Override
    public Iterable<? extends Position<E>> rangeIterator(E m, E M) {
        AVLInfo<E> ini = new AVLInfo<>(m);
        AVLInfo<E> fin = new AVLInfo<>(M);   
        List<Position<E>> l = new ArrayList<>();
        for(Position<AVLInfo<E>> p: t.rangeIterator(ini, fin)){
            l.add(p.getElement());
        }
        return l;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new AVLTreeIterator<E>(t.iterator());
    }
    
    @Override
    public Position<E> root() {
        return t.root().getElement();
    }

    @Override
    public Position<E> parent(Position<E> v) {
        
        AVLInfo<E> n = checkPosition(v);
        return t.parent(n.getTreePos()).getElement();
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        List<Position<E>> l = new ArrayList<>();
        Iterable<? extends Position<AVLInfo<E>>> children = t.children(n.getTreePos());
        for(Position<AVLInfo<E>> p: children){
            l.add(p.getElement());
        }
        return l;
    }

    @Override
    public boolean isInternal(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.isInternal(n.getTreePos());
    }

    @Override
    public boolean isLeaf(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.isLeaf(n.getTreePos());
    }

    @Override
    public boolean isRoot(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.isRoot(n.getTreePos());
    }
    
    @Override
    public Position<E> left(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.left(n.getTreePos()).getElement();
    }

    @Override
    public Position<E> right(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.right(n.getTreePos()).getElement();
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.hasLeft(n.getTreePos());
    }

    @Override
    public boolean hasRight(Position<E> v) {
        AVLInfo<E> n = checkPosition(v);
        return t.hasRight(n.getTreePos());
    }
    
}
