public class arraytest {
    public static void main(String[] args){
    int[] arr = {1, 2, 3, 4, 5};
    
    int arrayLength = arr.length;

    for(int i = 0; i < arrayLength; i++)
        System.out.println("Element at index " + i + " : " + arr[i]);

  }       
}