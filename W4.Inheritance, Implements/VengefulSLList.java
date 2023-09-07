public class VengefulSLList<Item> extends SLListCopy<Item> {
    /** Can do everything SLList can do, but can also print all the items that have been banished by removeLast(). */

    /**
     * @NOTICE: The compiler determines whether or not something is valid based on the static type of the object, e.g.:
     *
     *      VengefulSLList<Integer> vsl = new VengefulSLList<Integer>(9);
     *      SLList<Integer> sl = vsl;
     *
     *      sl.addLast(50); // compile
     *      sl.removeLast(); // compile: Since there is override, so follow the dynamic method selection.
     *      sl.printLostItems();
     * Does not compile! The static type is SLList.
     *      VengefulSLList<Integer> vsl2 = sl;
     * Does not compile! Since the compiler only sees that the static type of sl is SLList, it will not allow a
     * VengefulSLList "container" to hold it.
     *
     * Further, method calls have compile-time types equal to their declared(return) type.
     * Suppose we have this method:
     *
     *      public static Dog maxDog(Dog d1, Dog d2) { ... }
     * Since the return type of maxDog is Dog, any call to maxDog will have compile-time type Dog.
     *
     *      Poodle frank = new Poodle("Frank", 5); // ok
     *      Poodle frankJr = new Poodle("Frank Jr.", 15); // ok
     *      Dog largerDog = maxDog(frank, frankJr); // ok
     *      Poodle(is-a dog) largerPoodle = maxDog(frank, frankJr);
     * Does not compile! RHS has compile-time type Dog, but you can't say dog "is-a" poodle.
     *
     * @Term: Casting
     * You can tell the compiler that a specific expression has a specific compile-time type, so that the compiler
     * will view an expression as a different compile-time type.
     * Looking back at the code that failed above, since we know that frank and frankJr are both Poodles, we can cast:
     *      Poodle largerPoodle = (Poodle) maxDog(frank, frankJr); // a = (a)A
     * However it's also dangerous:
     *      Poodle frank = new Poodle("Frank", 5);
     *      Malamute frankSr = new Malamute("Frank Sr.", 100);
     *      Poodle largerPoodle = (Poodle) maxDog(frank, frankSr); // runtime exception - `ClassCastException`!
     */

    /**
     * @Terminology: **Higher Order Functions**
     * Basically it's the implementation of f(f(x)), check:
     * https://www.youtube.com/watch?v=OcfTN1PZ7oA
     * https://joshhug.gitbooks.io/hug61b/content/chap4/chap42.html
     */

    SLList<Item> deletedItems;

    public VengefulSLList() {
        /** @NOTICE: If you have variables, definitely remember to add a constructor. */

        super(); // Must come first!
        /**
         * @Term: super(...): Put it at the top. Can be omitted, but if there are several new constructors, Java's
         * implicit call may not be what we intend to call. And, what if you need super(x) or just super(5)?
         * So just don't omit, call it explicitly!
         */

        deletedItems = new SLList<>();
    }
    public VengefulSLList(Item x) {  // Super(...) cannot be omitted!!!
        super(x);
        deletedItems = new SLList<>();
    }


    @Override
    public Item removeLast() {
        Item x = super.removeLast();
        deletedItems.addLast(x);
        return x;
    }
    /**
     * Overrides the removeLast() of the superclass. If you need the original implementation and
     * just want to do something on the basis of it, use `super.XXX()`.
     * @Term: super
     *
     * If you just copy the original code from superclass without using `super`, it normally won't
     * work, since you didn't inherit the private members in the original implementation.
     */

    public void printLostItems() {
        deletedItems.print();
    }
}
