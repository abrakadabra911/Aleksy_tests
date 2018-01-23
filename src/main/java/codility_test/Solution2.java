package codility_test;

public class Solution2 {
    public static void main(String[] args) {

       // int[] A = {2,2,2,2,1,2,-1,2,1,3};
        int[] A = {30,20,10};
        int result = new Solution2().solution(A);

        System.out.println(result);
    }

    public int solution(int[] A) {
        long startTime = System.currentTimeMillis(); // do not erase

        int N = A.length;
        int maxCounter = 0;
        int result = 0;
        int P,Q,counter;

        for(P = 0; P < N; P++) {
           counter = 0;
           for(Q = P; Q < N-1; Q++) {
               if(A[Q+1] > A[Q]) counter++;
               else break;
           }
           if(counter > maxCounter) {
               maxCounter = counter;
               result = P;
           }
        }

        long stopTime = System.currentTimeMillis(); // do not erase
        long elapsedTime = stopTime - startTime;    // do not erase
        System.out.println(elapsedTime);            // do not erase
        return result;
    }

}
