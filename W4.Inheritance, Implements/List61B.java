public interface List61B<Item> {
    /**
     * @Terminology:
     * 1. Interface: replace class
     * 2. Implement: implements List61B<Item>
     * @NOTICE: "Implement" means making something(superclass) abstract come true, so we have subclass implements superclass.
     * 3. Override: @Override: Only @Override those methods in the
     * interface! It's not the "overload" of a bunch of method.
     *
     * Usage: Somewhere a method should take both SLList and AList as parameters,
     * will you write two methods and overload? No, you pass the interface.
     *
     * Declare: List61B<String> list = new SLList<String>();
     * (NOT:     List61B<String> list = new List<String>();
     * Run: list.addFirst(5);
     * @NOTICE: A SLList is created, and the address of the SLList is stored in
     * the list variable, so do list.addFirst(5) will modify SLList.
     */

    /**
     * @Terminology: two types of inheritance
     * 1. Interface Inheritance(WHAT): Subclasses inherit signatures, not implementation.
     * SLList and AList (subclasses/hyponyms)inherit the interface (i.e. all the methods/behaviors) of List61B (superclasses/hypernyms).
     * @NOTICE: Such relationship can be multi-generational.
     *
     * 2. Implementation Inheritance(HOW): Subclasses can inherit both signatures and implementation.
     * Add the keyword: default.
     *
     */

    /**
     * @Terminology: Dynamic method selection:
     * If the run-time type overrides the method, the run-time type's own method is used instead.
     * This explains why the `list` variable use print() of its own.
     *
     * IMPORTANT: This does not work for overloaded methods, only for override. E.g.
     * Say there are two methods in the same class:
     * public static void peek(List61B<String> list) {
     *     System.out.println(list.getLast());
     * }
     * public static void peek(SLList<String> list) {
     *     System.out.println(list.getFirst());
     * }
     * and you run this code:
     * SLList<String> SP = new SLList<String>();
     * List61B<String> LP = SP;
     * SP.addLast("elk");
     * SP.addLast("are");
     * SP.addLast("cool");
     * peek(SP);
     * peek(LP); // The second call to peek() will use the first peek method which takes in a List61B.
     *
     * This is because the only distinction between two overloaded methods is the types of the parameters.
     * When Java checks to see which method to call, it checks the static type and calls the method with
     * the parameter of the same type.
     *
     *
     * @Term: Compile-time type == static type == specified at declaration == never changes =? superclass
     * @Term: Run-time type == dynamic type == specified at instantiation(e.g. when using `new`) =? subclass
     *
     * E.g.
     * List<Integer> list = new SLList<>();
     * Compile-time type: List<Integer>.
     * Run-time type: SLList<Integer>.
     */

    public void addLast(Item y);
    /**
    * This is just a signature, like a declaration in C, not implementation.
    * So this is interface inheritance.
    */


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

}
