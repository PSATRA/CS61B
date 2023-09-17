public class DLList {
    /**
     * https://www.youtube.com/watch?v=BspFdzVvYe8&t=279s
     *
     * What if we want to do better in both addLast() and removeLast()?
     * Set another pointer go backwards, not a singly linked list, but a doubly linked list.
     * However, the sentinel becomes useless. We need more improvements (more detailed implementation is in project 1):
     *
     * 1. Set two sentinels, at the beginning and in the end, going opposite during updating.
     * https://docs.google.com/presentation/d/1suIeJ1SIGxoNDT8enLwsSrMxcw4JTvJBsMcdARpqQCk/pub?start=false&loop=false&delayms=3000&slide=id.g829fe3f43_0_291
     *
     * 2. Set only one sentinel, but make the list circular, with the front and back pointers sharing the
     * same sentinel node. (Might be more aesthetically beautiful)
     * @NOTICE:
     * 1. Both prev and next can't be null when adding new node.
     * 2. Doesn't need to null out the node manually, with no reference, its automatically deleted !!!Remember that
     * the Java garbage collector will “delete” things for us if and only if there are no pointers to that object.
     * java will automatically collect the garbage.
     * 3. When empty,  _sentinel._prev = _sentinel; _sentinel._next = _sentinel;
     * https://docs.google.com/presentation/d/1suIeJ1SIGxoNDT8enLwsSrMxcw4JTvJBsMcdARpqQCk/pub?start=false&loop=false&delayms=3000&slide=id.g829fe3f43_0_376
     *
     * @NOTICE: Never say node.item = ..., remember the relationship is not belonging but managing, so ues new.
     * E.g.: addLast in LinkedListDeque, proj 1,  is not _sentinel._prev.item = ..., but  _sentinel._prev = new Node (...)
     */
}
