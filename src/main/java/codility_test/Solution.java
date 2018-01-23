package codility_test;

public class Solution {
    public static void main(String[] args) {

        int[] A = {1, 4, -1, 3, 2};
        int result = new Solution().solution(A);

        System.out.println(result);
    }


    public int solution(int[] A) {

        int length = 1; // if array starts with -1, so it's lenth == 1;
        int i = 0;

        while(A[i] != -1){
            length++;
            i = A[i];
        }

        return length;
    }


}
