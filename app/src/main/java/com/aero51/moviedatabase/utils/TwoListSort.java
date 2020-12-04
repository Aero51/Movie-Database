package com.aero51.moviedatabase.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public  class TwoListSort {

    /**
     * Sorts list objectsToOrder based on the order of orderedObjects.
     * <p>
     * Make sure these objects have good equals() and hashCode() methods or
     * that they reference the same objects.
     */
    public static List<?> sortList(List<?> objectsToOrder, List<?> orderedObjects) {

        HashMap<Object, Integer> indexMap = new HashMap<>();
        int index = 0;
        for (Object object : orderedObjects) {
            indexMap.put(object, index);
            index++;
        }

        Collections.sort(objectsToOrder, new Comparator<Object>() {

            public int compare(Object left, Object right) {

                Integer leftIndex = indexMap.get(left);
                Integer rightIndex = indexMap.get(right);
                if (leftIndex == null) {
                    return -1;
                }
                if (rightIndex == null) {
                    return 1;
                }

                return Integer.compare(leftIndex, rightIndex);
            }
        });
        return objectsToOrder;
    }

}
