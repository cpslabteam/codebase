package codebase;

public class FingBugsExamples {

    /**
     * Verifies if two objects are equal. It recurse down into arrays
     * 
     * @param leftObject the left hand object
     * @param rightObject the right hand object
     * @return true if the objects are equal and their array elements are equa
     *         as well.
     */
    public static boolean isEqualArrayDeep(final Object leftObject,
            final Object rightObject) {
        if ((leftObject instanceof Object[]) && (rightObject instanceof Object[])) {
            final Object[] leftObjects = (Object[]) leftObject;
            final Object[] rightObjects = (Object[]) rightObject;
            if (leftObjects.length == rightObjects.length) {
                for (int objIndex = 0; objIndex < leftObjects.length; objIndex++) {
                    if (!isEqualArrayDeep(leftObjects[objIndex], rightObjects[objIndex])) {
                        return false;
                    }
                }
                return true;
            }
        }

        if ((leftObject == null) && (rightObject == null)) {
            return true;
        } else {
            return leftObject.equals(rightObject);
        }
    }

}
