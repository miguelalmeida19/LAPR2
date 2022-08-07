package app.algorithms.sorting;

public class QuickSort implements SortAlgorithm{

    public QuickSort(){

    }

    @Override
    public String[] sort(String[] array) {
        QuicksortString(array);
        return array;
    }

    public static int partition(String[] stringArray, int index1, int index2) {
        String pivotValue = stringArray[index1];
        while (index1 < index2) {
            String value1;
            String value2;
            while ((value1 = stringArray[index1]).compareTo(pivotValue) < 0) {
                index1 = index1 + 1;
            }
            while ((value2 = stringArray[index2]).compareTo(pivotValue) > 0) {
                index2 = index2 - 1;
            }
            stringArray[index1] = value2;
            stringArray[index2] = value1;
        }
        return index1;
    }

    public static void QuicksortString(String[] stringArray, int index1, int index2) {
        if (index1 >= index2) {
            // we are done
            return;
        }
        int pivotIndex = partition(stringArray, index1, index2);
        QuicksortString(stringArray, index1, pivotIndex);
        QuicksortString(stringArray, pivotIndex + 1, index2);
    }

    public static void QuicksortString(String[] stringArray) {
        QuicksortString(stringArray, 0, stringArray.length - 1);
    }
}