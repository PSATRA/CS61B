public class QuickFindDS implements DisjointSets {

    private final int[] _id;

    /* Θ(N) */
    public QuickFindDS(int N){
        _id = new int[N];
        for (int i = 0; i < N; i++){
            _id[i] = i;
        }
    }

    /* need to iterate through the array => Θ(N) */
    public void connect(int p, int q){
        int pid = _id[p];
        int qid = _id[q];
        for (int i = 0; i < _id.length; i++){
            if (_id[i] == pid){
                _id[i] = qid;
            }
        }
    }

    /* Θ(1) */
    public boolean isConnected(int p, int q){
        return (_id[p] == _id[q]);
    }
}