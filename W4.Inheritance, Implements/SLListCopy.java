public class SLListCopy<Item> implements List61B<Item> {
    private class StuffNode {
        public Item item;
        public StuffNode next;
        public StuffNode(Item i, StuffNode n) {
            item = i;
            next = n;
        }
    }
    private int size;
    private final StuffNode sentinel;


    public SLListCopy() {
        sentinel = new StuffNode(null, null);
        size = 0;
    }
    public SLListCopy(Item x) {
        sentinel = new StuffNode(null, null);
        sentinel.next = new StuffNode(x, null);
        size = 1;
    }


    @Override
    public void print() {
        for (StuffNode p = sentinel.next; p != null; p = p.next) {
            System.out.print(p.item + " ");
        }
    }

    public void addFirst(Item x) {
        sentinel.next = new StuffNode(x, sentinel.next);
        size++;
    }

    @Override
    public void addLast(Item x) {
        StuffNode p = sentinel;
        while (p.next != null) {
            p = p.next;
        }
        p.next = new StuffNode(x, null);
        size++;
    }

    @Override
    public Item getFirst() {
        return sentinel.next.item;
    }

    @Override
    public Item getLast() {
        StuffNode p = sentinel;

        /* Advance p to the end of the list. */
        while (p.next != null) {
            p = p.next;
        }

        return p.item;
    }

    @Override
    public Item get(int i) {
        if  (i >= size) {
            return null;
        }
        StuffNode p = sentinel;
        for (int j = 0; j < i; j++) {
            p = p.next;
        }
        return p.next.item;
    }

    @Override
    public int size() {
        return size;
    }

}
