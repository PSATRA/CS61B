public class intLists {
    /**
     * can also start backwards, build a helper method with args: first, rest;
     * start backwards, like: l = fun(2, null); l = fun(1, l); ...
     * this is a better and more concise way, but far from perfect
     */

    public int first;
    public intLists rest;

    /**
     * This will cause the linked list to create a circular reference to itself.
     * You should properly create a new node.？
     * Also, directly modifying this within an instance method is not allowed in Java.
     * like: this = ...; (x)
     * public void addFirst(int num) {
     *    intLists p = this;
     *    this.first = num; // you simultaneously changed p.first !
     *    this.rest = p; }
     */

    /* recursion, this can change, but cannot be reassigned */
    public int size() {
        // because recursion, the return type is int, or we print, no return
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /* no recursion version */
    public void iterativeSize() {
        // 'this' cannot be reassigned, but we need 'this = this.rest'?
        intLists p = this;
        int out = 0;
        while (p != null) {
            p = p.rest;
            out++;
        }
        System.out.println(out);
    }

    /* get the element from a certain position, start from 0. */
    public int get(int pos) {
        if (pos == 0) {
            return first;
        }
        return rest.get(pos - 1);
    }

    /* no recursion version */
    public int iterativeGet(int pos) {
        intLists p = this;
        for (int i = 0; i < pos; i++) {
            p = p.rest;
        }
        return p.first;
    }

    public static void main(String[] args) {
        intLists l = new intLists();
        l.first = 0;

        l.rest = new intLists();
        l.rest.first = 1;

        l.rest.rest = new intLists();
        l.rest.rest.first = 2;

        System.out.println(l.first + " " + "5");
        System.out.println(l.rest.first);
        System.out.println(l.rest.rest.first);
        System.out.println(l.rest.rest.rest);              // automatically assigned with null!!!
        System.out.println();

        System.out.println(l.size());
        l.iterativeSize();
        System.out.println();

        System.out.println(l.get(0));
        System.out.println(l.iterativeGet(1));
        System.out.println(l.iterativeGet(2));
        System.out.println();
    }
}

