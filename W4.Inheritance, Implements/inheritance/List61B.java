package inheritance;

public interface List61B<Item> {
    /**
     * @Terminology:
     * 1. Interface: replace class
     * 2. Implement: implements inheritance.List61B<Item>
     * @NOTICE: "Implement" means making something(superclass) abstract come true, so we have subclass implements superclass.
     * 3. @Override: Only @Override those methods in the interface! It's not the "overload" of a bunch of method.
     *
     * Usage: Somewhere a method can take in both SLList and AList as parameters,
     * will you write two methods and overload? No, you just pass the interface.
     *
     *      inheritance.List61B<String> list = new SLList<String>();
     *      inheritance.List61B<String> list = new List<String>();
     *      list.addFirst(5);
     * @NOTICE: A SLList is created, and the address of the SLList is stored in
     * the list variable, so do list.addFirst(5) will modify SLList.
     */

    /**
     * @Terminology: two types of inheritance
     * 1. Interface Inheritance(WHAT): Subclasses inherit signatures, not implementation.
     * SLList and AList (subclasses/hyponyms)inherit the interface (i.e. all the methods/behaviors) of inheritance.List61B (superclasses/hypernyms).
     * Such relationship can be multi-generational.
     *
     * @NOTICE:
     * Class can implement multiple interfaces!
     * Subclasses can only implement one abstract class.
     *
     * 2. Implementation Inheritance(HOW): Subclasses can inherit both signatures and implementation.
     * @Term: Add the keyword: `default`.
     */

    /**
     * @Terminology: Dynamic method selection:
     * If the run-time type overrides the method, the run-time type's own method is used instead.
     * This explains why the `list` variable use its own print().
     *
     * @NOTICE: Two preconditions:
     * 1. Has a dynamic type(has instantiation).
     * 2. The method invoked is override(instead of overload or whatever else).
     *
     * IMPORTANT: This does not work for overloaded methods, only for override. E.g.
     * Say there are two methods in the same class:
     *      public static void peek(inheritance.List61B<String> list) {
     *          System.out.println(list.getLast());
     *      }
     *      public static void peek(SLList<String> list) {
     *          System.out.println(list.getFirst());
     *      }
     * and you run this code:
     *      SLList<String> SP = new SLList<String>();
     *      inheritance.List61B<String> LP = SP;
     *      SP.addLast("elk");
     *      SP.addLast("are");
     *      SP.addLast("cool");
     *      peek(SP);
     *      peek(LP); // The second call to peek() will use the first peek method which takes in a inheritance.List61B.
     * This is because the only distinction between these two overloaded methods is the types of the parameters,
     * not the body, so there is no override. When Java checks to see which method to call, it checks the
     * static type and calls the method with the parameter of the same type.
     *
     * @Term: Compile-time type == static type == specified at declaration == never changes =? superclass
     * @Term: Run-time type == dynamic type == specified at instantiation(e.g. when using `new`) =? subclass
     * E.g.
     *      List<Integer> list = new SLList<>();
     * Compile-time type: List<Integer>.
     * Run-time type: SLList<Integer>.
     */

    /**
     * @Terminology: Abstract class
     *
     * @NOTICE: Abstract class can do anything an interface can do, and more!
     * But when in doubt, try to use interfaces in order to reduce complexity !!!!!
     *
     * For abstract class:
     * 1. Cannot be instantiated.
     * 2. Methods are by default concrete unless specified to be abstract.
     * 3. Can only implement one per subclass.
     * 4. Methods can be public or private.
     * 5. Can have any types of variables.
     *
     * However, for interface:
     * 1. All methods must be public.
     * 2. All variables must be `public static final`.
     * 3. Cannot be instantiated.
     * 4. All methods are by default abstract unless specified to be default.
     * 5. Can implement more than one interface per subclass.
     */

    public void addLast(Item y);
    /**
    * This is just a signature(declaration), not implementation.
    * So this is interface inheritance.
    */

    public void addFirst(Item x);

    /** Prints out the entire list. */
    default public void print() {
        for (int i = 0; i < size(); i += 1) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }
    /**
     * Implementation inheritance.
     * @NOTICE: Use get() since every subclass must have get().
     * @NOTICE: Always actively analyze and optimize your own code: no matter it is a single
     * method or data structure. Here we have get(), but get() in SLList is much slower
     * than that in AList (small ineffectiveness), so do some tweak and optimization.
     *
     * -> Optimization: Just **Really** @Override the print() in SLList.
     */


    public Item getFirst();

    public Item getLast();

    public Item get(int i);

    public int size();

    public Item removeLast();

    /**
     * Inserts item into given position.
     * Code from discussion #3
     */
    public void insert(Item x, int position);

}
