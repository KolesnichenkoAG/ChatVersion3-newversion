package HomeWorklesson6;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TestArrayLastFour {

    @Test
    void testArray () {
        ArrayLastFour arrayLastFour = new ArrayLastFour();
        int [] array1 = {10, 9, 4, 5, 7, 2};
        int [] arrayTest1 = arrayLastFour.cutArray(array1);
        int [] arrayRes1 = {5, 7, 2};
        assertArrayEquals(arrayRes1, arrayTest1, "Неверное преобразование массива");

        int [] array2 = {4, 2, 9, 9, 6, 2};
        int [] arrayTest2 = arrayLastFour.cutArray(array2);
        int [] arrayRes2 = {2, 9, 9, 6, 2};
        assertArrayEquals(arrayRes2, arrayTest2, "Неверное преобразование массива");

        int [] array3 = {4, 2, 8, 8, 4, 2};
        int [] arrayTest3 = arrayLastFour.cutArray(array3);
        int [] arrayRes3 = {2};
        assertArrayEquals(arrayRes3, arrayTest3, "Неверное преобразование массива");

        int [] array4 = {4, 2, 4, 2, 4, 4};
        int [] arrayTest4 = arrayLastFour.cutArray(array4);
        int [] arrayRes4 = {};
        assertArrayEquals(arrayRes4, arrayTest4, "Неверное преобразование массива");

        int [] array5 = {4, 2, 4, 2, 4, 3};
        int [] arrayTest5 = arrayLastFour.cutArray(array5);
        int [] arrayRes5 = {3};
        assertArrayEquals(arrayRes5, arrayTest5, "Неверное преобразование массива");
    }

    @Test
    void testArrayExc() throws IOException {
        ArrayLastFour arrayLastFour = new ArrayLastFour();
        int [] array = {10, 3, 2, 2, 6, 2};

        assertThrows(ArrayExceptionNotFour.class, () -> arrayLastFour.cutArray(array));
    }

}
