public class AListCopy<Item> implements List61B<Item> {
    private Item[] items;
    private int size;


    public AListCopy() {
        items = (Item[]) new Object[100];
        size = 0;
    }


    public void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    @Override
    public void addLast(Item x) {
        if (size == items.length) {
            resize(size * 4);
        }

        items[size] = x;
        size = size + 1;
    }

    @Override
    public Item getFirst() { return items[0]; }

    @Override
    public Item getLast() {
        return items[size - 1];
    }

    @Override
    public Item get(int i) {
        return items[i];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Item removeLast() {
        Item x = getLast();
        items[size - 1] = null;
        size = size - 1;
        return x;
    }
}
