import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class with two different methods to count inversions in an array of integers.
 * @author Alan S. Kim
 * @version 1.0.0 November 17, 2022
 */
public class InversionCounter {

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses nested loops to run in Theta(n^2) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsSlow(int[] array) {
        long inversionCount = 0L;
        for(int i = 0; i<array.length-1; i++){
            for(int j = i + 1; j<array.length; j++){
                if(array[i]>array[j]){
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    /**
     * Returns the number of inversions in an array of integers.
     * This method uses mergesort to run in Theta(n lg n) time.
     * @param array the array to process
     * @return the number of inversions in an array of integers
     */
    public static long countInversionsFast(int[] array) {
        // Make a copy of the array so you don't actually sort the original
        // array.

        int[] arrayCopy = new int[array.length],
              scratch =  new int[array.length];
        System.arraycopy(array, 0, arrayCopy, 0, array.length);
        long inversionCount = mergesort(arrayCopy);
        return inversionCount;
    }

    private static long mergesortHelper(int[] array, int[] scratch, int low, int high) {
        long count = 0;
        if (low < high) {
            int mid = low + (high - low) / 2;
            count += mergesortHelper(array, scratch, low, mid);
            count += mergesortHelper(array, scratch, mid + 1, high);
            int i = low, j = mid + 1;
            for (int k = low; k <= high; k++) {
                if (i <= mid && (j > high || array[i] <= array[j])) {
                    scratch[k] = array[i++];
                } else {
                    scratch[k] = array[j++];
                    count += mid - i + 1;
                }
            }
            for (int k = low; k <= high; k++) {
                array[k] = scratch[k];
            }
        }
        return count;
    }
    public static long mergesort(int[] array) {
        long count = 0;
        int[] scratch = new int[array.length];
        count = mergesortHelper(array, scratch, 0, array.length - 1);
        return count;
    }

    /**
     * Reads an array of integers from stdin.
     * @return an array of integers
     * @throws IOException if data cannot be read from stdin
     * @throws NumberFormatException if there is an invalid character in the
     * input stream
     */
    public static int[] readArrayFromStdin() throws IOException,
                                                     NumberFormatException {
        List<Integer> intList = new LinkedList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        int value = 0, index = 0, ch;
        boolean valueFound = false;
        while ((ch = reader.read()) != -1) {
            if (ch >= '0' && ch <= '9') {
                valueFound = true;
                value = value * 10 + (ch - 48);
            } else if (ch == ' ' || ch == '\n' || ch == '\r') {
                if (valueFound) {
                    intList.add(value);
                    value = 0;
                }
                valueFound = false;
                if (ch != ' ') {
                    break;
                }
            } else {
                throw new NumberFormatException(
                        "Invalid character '" + (char)ch +
                        "' found at index " + index + " in input stream.");
            }
            index++;
        }

        int[] array = new int[intList.size()];
        Iterator<Integer> iterator = intList.iterator();
        index = 0;
        while (iterator.hasNext()) {
            array[index++] = iterator.next();
        }
        return array;
    }

    public static void main(String[] args) {
        long inversionCount=0L;
        if(args.length == 0){
            try{
                System.out.print("Enter sequence of integers, " +
                        "each followed by a space: ");
                int[] array = readArrayFromStdin();
                inversionCount = countInversionsFast(array);
                if(array.length == 0){
                    System.err.println("Error: Sequence of integers not received.");
                    System.exit(1);
                }
                else {
                    System.out.println("Number of inversions: " + inversionCount);
                }
            } catch (IOException error1) {
                System.err.println("There is an IOException");
                System.exit(1);
            } catch (NumberFormatException error2) {
                System.err.println("Error: " + error2.getMessage());
                System.exit(1);
            }
        }

        else if(args.length == 1){
            String mode = args[0];
            if (mode.equals("slow")) {
                try {
                    System.out.print("Enter sequence of integers, " +
                            "each followed by a space: ");
                    int[] array = readArrayFromStdin();
                    inversionCount = countInversionsSlow(array);
                    if(array.length == 0){
                        System.err.println("Error: Sequence of integers not received.");
                        System.exit(1);
                    }
                    else {
                        System.out.println("Number of inversions: " + inversionCount);
                    }
                } catch (IOException error1) {
                    System.err.println("There is an IOException");
                    System.exit(1);
                } catch (NumberFormatException error2) {
                    System.err.println("Error: " + error2.getMessage());
                    System.exit(1);
                }
            }
            else{
                System.err.println("Error: Unrecognized option " + "'" + args[0] + "'.");
                System.exit(1);
            }
        }
        else{
            System.err.println("Usage: java InversionCounter [slow]");
            System.exit(1);
        }
    }
}
