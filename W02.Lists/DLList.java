public class DLList {
    /**
     * https://www.youtube.com/watch?v=BspFdzVvYe8&t=279s
     *
     * What if we want to do better in both addLast() and removeLast()?
     * Set another pointer go backwards, not a singly linked list,
     * but a doubly linked list. However, the sentinel becomes useless.
     * We need more improvements (the sanity implementation is in proj 1):
     *
     * 1. Set two sentinels, at the beginning and in the end, going
     * opposite during updating.
     * https://docs.google.com/presentation/d/1suIeJ1SIGxoNDT8enLwsSrMxcw4JTvJBsMcdARpqQCk/pub?start=false&loop=false&delayms=3000&slide=id.g829fe3f43_0_291
     *
     * 2. Set only one sentinel, but make the list circular, with the
     * front and back pointers sharing the same sentinel node. (Might be
     * more aesthetically beautiful)
     *
     * @NOTICE:
     * 1. Both prev and next can't be null when adding new node.
     * 2. Doesn't need to null out the node manually. With no reference,
     * it's automatically deleted!
     * 3. When doing empty:
     * sentinel.prev = sentinel; sentinel.next = sentinel;
     * https://docs.google.com/presentation/d/1suIeJ1SIGxoNDT8enLwsSrMxcw4JTvJBsMcdARpqQCk/pub?start=false&loop=false&delayms=3000&slide=id.g829fe3f43_0_376
     *
     * @NOTICE: Never say node.item = ..., remember the relationship
     * is not belonging but managing, so ues 'new'.
     *
     * E.g.: addLast in LinkedListDeque, proj 1, it is
     * not sentinel.prev.item = ..., but  sentinel.prev = new Node (...)
     */
}
