
package java.lang;


/**
 * @author gust
 */
public class StackTraceElement {

    private String declaringClass;
    private String methodName;
    private String fileName;
    private int lineNumber;

    StackTraceElement parent;
    Class declaringClazz;

    /**
     * @return the declaringClass
     */
    Class getDeclaringClass() {
        return declaringClazz;
    }

    public String getClassName() {
        return declaringClass;
    }

    /**
     * @return the methodName
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber() {
        return lineNumber;
    }

    public String toString() {

        StringBuilder stack = new StringBuilder();
        stack.append(getDeclaringClass());
        stack.append(".").append(getMethodName());
        stack.append("(").append(getFileName());
        stack.append(":").append(getLineNumber()).append(")");
        return stack.toString();

    }
}
