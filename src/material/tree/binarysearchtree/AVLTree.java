
package material.tree.binarysearchtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Iterable<? extends Position<E>> findAll(E value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Position<E> insert(E value) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean isEmpty() {
        return t.isEmpty();
    }

    @Override
    public void remove(Position<E> pos) {
        throw new RuntimeException("Not implemented");
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
