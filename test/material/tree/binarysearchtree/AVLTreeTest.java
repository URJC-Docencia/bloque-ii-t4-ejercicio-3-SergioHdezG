
package material.tree.binarysearchtree;

import java.util.Iterator;
import java.util.TreeSet;
import material.Position;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mayte
 */
public class AVLTreeTest {
    
    public AVLTreeTest() {
    }

    /**
     * Test of find method, of class AVLTree.
     */
    @Test
    public void testFind() {
        System.out.println("find");
        AVLTree<Integer> b = new AVLTree<>();

        for (int cont = 0; cont < 25; cont += 2) {
            b.insert(cont);
        }

        b.insert(17);

        Position<Integer> p = b.find(17);
        assertEquals(p.getElement().intValue(), 17);
        p = b.find(2);
        assertEquals(p.getElement().intValue(), 2);
        p = b.find(13);
        assertEquals(p, null);
    }

    

    /**
     * Test of insert method, of class AVLTree.
     */
    @Test
    public void testInsert() {
        System.out.println("insert");
         AVLTree<Integer> b = new AVLTree<>();
        TreeSet<Integer> expected = new TreeSet<>();
        b.insert(5);
        expected.add(5);
        b.insert(3);
        expected.add(3);
        b.insert(1);
        expected.add(1);
        b.insert(7);
        expected.add(7);
        b.insert(6);
        expected.add(6);

        Iterator<Integer> it = expected.iterator();
        for (Position<Integer> e : b) {
            assertEquals(it.next(), e.getElement());
        }
    }
     @Test
public void testInsert3() {
        AVLTree<Integer> b = new AVLTree<>();
        TreeSet<Integer> expected = new TreeSet<>();
        b.insert(4);
        expected.add(4);
        b.insert(7);
        expected.add(7);
        b.insert(12);
        expected.add(12);
        b.insert(15);
        expected.add(15);
        b.insert(3);
        expected.add(3);
        b.insert(5);
        expected.add(5);
        b.insert(14);
        expected.add(14);
        b.insert(18);
        expected.add(18);
        b.insert(16);
        expected.add(16);
        b.insert(17);
        expected.add(17);

        Iterator<Integer> it = expected.iterator();
        for (Position<Integer> e : b) {
            assertEquals(it.next(), e.getElement());
        }
}
         @Test
        public void testInsert2() {
        AVLTree<Integer> b = new AVLTree<>();
        TreeSet<Integer> expected = new TreeSet<>();
        b.insert(-1);
        expected.add(-1);
        b.insert(1);
        expected.add(1);
        b.insert(3);
        expected.add(3);
        b.insert(5);
        expected.add(5);
        b.insert(-5);
        expected.add(-5);

        Iterator<Integer> it = expected.iterator();
        for (Position<Integer> e : b) {
            assertEquals(it.next(), e.getElement());
        }
    }

        
    /**
     * Test of isEmpty method, of class AVLTree.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        AVLTree<Integer> instance = new AVLTree<>();
        
        boolean result = instance.isEmpty();
        assertTrue(result);
        instance.insert(3);
        assertFalse(instance.isEmpty());
    }

    /**
     * Test of remove method, of class AVLTree.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        AVLTree<Integer> b = new AVLTree<>();
        TreeSet<Integer> expected = new TreeSet<>();

        b.insert(5);
        expected.add(5);
        b.insert(3);
        expected.add(3);
        Position<Integer> p = b.insert(1);
        b.insert(7);
        expected.add(7);
        b.insert(6);
        expected.add(6);

        b.remove(p);

        Iterator<Integer> it = expected.iterator();
        for (Position<Integer> e : b) {
            assertEquals(it.next(), e.getElement());
        }
    }
@Test
public void testRemove2() {
        AVLTree<Integer> b = new AVLTree<>();
        TreeSet<Integer> expected = new TreeSet<>();
        Position<Integer> p1, p2, p3, p4, p5, p6;

        b.insert(4); 
        expected.add(4);
        b.insert(7); 
        expected.add(7);
        b.insert(12);
        b.insert(15);
        b.insert(3);
        b.insert(5); expected.add(5);
        b.insert(14); expected.add(14);
        b.insert(18);
        b.insert(16);
        b.insert(17);
         
        p1 = b.find(18);
        b.remove(p1);
        p2 = b.find(3);
        b.remove(p2);
        p3 = b.find(12);
        b.remove(p3);
        p4 = b.find(17);
        b.remove(p4);
        p5 = b.find(15);
        b.remove(p5);
        p6 = b.find(16);
        b.remove(p6);

        assertEquals(expected.size(),b.size());
        Iterator<Integer> it = expected.iterator();
        for (Position<Integer> e : b) {
            assertEquals(it.next(), e.getElement());
        }
    }
@Test
    public void testRemove3() {
        AVLTree<Integer> b = new AVLTree<>();
        final int LIMIT = 100;
        final Position [] array = new Position[LIMIT];

        for (int cont = 0; cont < LIMIT; cont++) {            
            Position p = b.insert(cont);
            array[cont] = p;
       }
        
        for (int cont = 0; cont < LIMIT; cont+=2) {
            b.remove(array[cont]);
        }

        int cont = 1;
        for (Position<Integer> e : b) {
            assertEquals(cont,e.getElement().intValue());
            cont+=2;
        }
    }
    /**
     * Test of size method, of class AVLTree.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        AVLTree<Integer> b = new AVLTree<>();
        assertEquals(0,b.size());
        b.insert(4);
        assertEquals(1,b.size());
        b.insert(7);
        assertEquals(2,b.size());
        b.insert(12);
        assertEquals(3,b.size());
        b.insert(15);
        assertEquals(4,b.size());
        b.insert(3);
        assertEquals(5,b.size());
        b.insert(14);
        assertEquals(6,b.size());
        b.insert(18);
        assertEquals(7,b.size());
        b.insert(16);
        assertEquals(8,b.size());
        b.insert(17);
        assertEquals(9,b.size());
    }

    /**
     * Test of rangeIterator method, of class AVLTree.
     */
    @Test
    public void testRangeIterator() {
        System.out.println("rangeIterator");
        AVLTree<Integer> b = new AVLTree<>();
        b.insert(4);
        b.insert(7);
        b.insert(12);
        b.insert(15);
        b.insert(3);
        b.insert(14);
        b.insert(18);
        b.insert(16);
        b.insert(17);

        Iterator<? extends Position<Integer>> it = b.rangeIterator(5, 15).iterator();
        assertEquals(7, it.next().getElement().intValue());
        assertEquals(12, it.next().getElement().intValue());
        assertEquals(14, it.next().getElement().intValue());
        assertEquals(15, it.next().getElement().intValue());
    }

    
}
