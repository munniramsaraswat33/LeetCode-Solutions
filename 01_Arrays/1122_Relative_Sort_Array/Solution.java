class Solution {
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int count[] = new int[1001];
        for(int arr : arr1){
            count[arr]++;
        }

        HashMap<Integer, Integer> map = new HashMap<>();
        int i=0;
        for(int arr : arr2){
            map.put(i, arr);
            i++;
        }
        int j = 0;
        i = 0;
        while(i != map.size()){
            int val = map.get(i);
            while(count[val] != 0){
                arr1[j] = val;
                count[val]--;
                j++;
            }
            i++;
        }
        for(int k = 0; k<count.length; k++){
            while(count[k]!=0){
                arr1[j] = k;
                j++;
                count[k]--;
            }
        }
        return arr1;
    }
}