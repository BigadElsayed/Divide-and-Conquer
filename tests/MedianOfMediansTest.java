import Algorithms.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedianOfMediansTest {
    private final Median median = new MedianOfMedians();

    @Test
    void singleElement() {
        assertEquals(42, median.getMedian(new int[]{42}));
    }

    @Test
    void oddLength() {
        assertEquals(5, median.getMedian(new int[]{9, 1, 5, 7, 3}));
    }

    @Test
    void evenLengthReturnsUpperMedian() {
        assertEquals(3, median.getMedian(new int[]{4, 1, 3, 2}));
    }

    @Test
    void sortedAndReversed() {
        assertEquals(4, median.getMedian(new int[]{1, 2, 3, 4, 5, 6, 7}));
        assertEquals(4, median.getMedian(new int[]{7, 6, 5, 4, 3, 2, 1}));
    }

    @Test
    void duplicates() {
        assertEquals(2, median.getMedian(new int[]{2, 2, 2, 1, 3, 2, 2}));
    }

    @Test
    void allEqual() {
        assertEquals(7, median.getMedian(new int[]{7, 7, 7, 7, 7}));
    }

    @Test
    void negativeNumbers() {
        assertEquals(-5, median.getMedian(new int[]{-9, -1, -5, -7, -3}));
    }

    @Test
    void emptyArrayThrows() {
        assertThrows(IllegalArgumentException.class, () -> median.getMedian(new int[0]));
    }
}