public class VengefulSLList<Item> extends SLListCopy<Item> {
    /** Can do everything SLList can do, but can also print all the items that have been banished by removeLast(). */

    /**
     * @NOTICE: How Inheritance Breaks Encapsulation:
     *      public void bark() {
     *          barkMany(1);
     *      }
     *      public void barkMany(int N) {
     *          for (int i = 0; i < N; i += 1) {
     *              System.out.println("bark");
     *          }
     *      }
     *
     *      @Override
     *      public void barkMany(int N) {
     *          System.out.println("As a dog, I say: ");
     *          for (int i = 0; i < N; i += 1) {
     *              bark();
     *          }
     *      }
     * The program above gets caught in an infinite loop.
     */

    /**
     * @NOTICE: The compiler determines whether or not something is valid based on the static type of the object, e.g.:
     *
     *      VengefulSLList<Integer> vsl = new VengefulSLList<Integer>(9);
     *      SLList<Integer> sl = vsl;
     *
     *      sl.addLast(50); // compile
     *      sl.removeLast(); // compile: Since there is override, so follow the dynamic method selection.
     *      sl.printLostItems();
     * Does not compile! The static type is SLList, and it doesn't have an explicit dynamic type, since it's specified at instantiation.
     * @NOTICE: So don't follow the dynamic method selection.
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
     *      Poodle largerPoodle = (Poodle) maxDog(frank, frankJr);
     * However, if we do this:
     *      Poodle frank = new Poodle("Frank", 5);
     *      Malamute frankSr = new Malamute("Frank Sr.", 100);
     *      Poodle largerPoodle = (Poodle) maxDog(frank, frankSr); // runtime exception - `ClassCastException`!
     *
     * You can always cast up (to a more generic version of a class) without fear of ruining anything because we know
     * the more specific version is a version of the generic class. For example you can always cast a Poodle to a Dog
     * because all Poodles are Dog’s.
     *
     * You can also cast down (to a more specific version of a class) with caution as you need to make sure that, during
     * runtime, nothing is passed in that violates your cast. For example, sometimes Dogs are Poodles but not always.
     *
     * Finally, you cannot ever cast to a class that is neither above nor below the class being cast. For an example,
     * you cannot cast a Dog to a Monkey because a Monkey is not in the direct lineage of a Dog - it is a child of
     * Animal so a bit more distant. You can think of this as “side casting” and it will result in a compile time
     * error since the compiler knows this cast cannot possibly work.
     */

    /**
     * @Terminology: **Higher Order Functions - HoF**
     * Basically it's the implementation of g(f(x)), a method takes in entities of function types as parameters. Check:
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
        Item x = super.removeLast(); // This can automatically do removeLast for this class.
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
