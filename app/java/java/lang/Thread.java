package java.lang;

public class Thread implements Runnable {

    /* Thread priority */
    private int priority = NORM_PRIORITY;

    /* Internal thread queue */
    private Thread threadQ;

    /* What will be run. */
    private Runnable target;

    /* Thread name */
    private char name[];

    /*  save top runtime   */
    private long stackFrame;
    /**
     * The minimum priority that a thread can have.
     */
    public final static int MIN_PRIORITY = 1;

    /**
     * The default priority that is assigned to a thread.
     */
    public final static int NORM_PRIORITY = 5;

    /**
     * The maximum priority that a thread can have.
     */
    public final static int MAX_PRIORITY = 10;


    /* Whether or not the thread is a daemon thread. */
    private boolean daemon = false;

    /**
     * Returns a reference to the currently executing <code>Thread</code>
     * object.
     *
     * @return the currently executing thread.
     */
    public static native Thread currentThread();

    /* For autonumbering anonymous threads. */
    private static int threadInitNumber;

    private static synchronized int nextThreadNum() {
        return ++threadInitNumber;
    }

    /**
     * Causes the currently executing thread object to temporarily pause and
     * allow other threads to execute.
     */
    public static native void yield();

    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds. The thread does not
     * lose ownership of any monitors.
     *
     * @param millis the length of time to sleep in milliseconds.
     * @throws InterruptedException if another thread has interrupted the
     *                              current thread. The <i>interrupted status</i> of the current thread is
     *                              cleared when this exception is thrown.
     * @see java.lang.Object#notify()
     */
    public static native void sleep(long millis) throws InterruptedException;

    /**
     * Initialize a Thread.
     *
     * @param target the object whose run() method gets called
     * @param name   the name of the new thread
     */
    private void init(Runnable target, String name) {
        Thread parent = currentThread();
        this.target = target;
        this.name = name.toCharArray();
        this.priority = parent.getPriority();
        setPriority0(priority);
    }

    /**
     * Allocates a new <code>Thread</code> object.
     * <p>
     * Threads created this way must have overridden their <code>run()</code>
     * method to actually do anything.
     *
     * @see java.lang.Runnable
     */
    public Thread() {
        init(null, "Thread-" + nextThreadNum());
    }

    /**
     * Allocates a new <code>Thread</code> object with the given name.
     * <p>
     * Threads created this way must have overridden their <code>run()</code>
     * method to actually do anything.
     *
     * @param name the name of the new thread.
     */
    public Thread(String name) {
        init(null, name);
    }

    /**
     * Allocates a new <code>Thread</code> object with a specific target object
     * whose <code>run</code> method is called.
     *
     * @param target the object whose <code>run</code> method is called.
     */
    public Thread(Runnable target) {
        init(target, "Thread-" + nextThreadNum());
    }

    /**
     * Allocates a new <code>Thread</code> object with the given target and
     * name.
     *
     * @param target the object whose <code>run</code> method is called.
     * @param name   the name of the new thread.
     */
    public Thread(Runnable target, String name) {
        init(target, name);
    }

    /**
     * Causes this thread to begin execution; the Java Virtual Machine calls the
     * <code>run</code> method of this thread.
     * <p>
     * The result is that two threads are running concurrently: the current
     * thread (which returns from the call to the <code>start</code> method) and
     * the other thread (which executes its <code>run</code> method).
     *
     * @throws IllegalThreadStateException if the thread was already started.
     * @see java.lang.Thread#run()
     */
    public synchronized native void start();

    /**
     * If this thread was constructed using a separate <code>Runnable</code> run
     * object, then that <code>Runnable</code> object's <code>run</code> method
     * is called; otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see java.lang.Thread#start()
     * @see java.lang.Runnable#run()
     */
    public void run() {
        if (target != null) {
            target.run();
        }
    }

    /**
     * Interrupts this thread. In an implementation conforming to the CLDC
     * Specification, this operation is not required to cancel or clean up any
     * pending I/O operations that the thread may be waiting for.
     *
     * @since JDK 1.0, CLDC 1.1
     */
    public void interrupt() {
        interrupt0();
    }

    /**
     * Tests if this thread is alive. A thread is alive if it has been started
     * and has not yet died.
     *
     * @return <code>true</code> if this thread is alive; <code>false</code>
     * otherwise.
     */
    public final native boolean isAlive();

    /**
     * Changes the priority of this thread.
     *
     * @param newPriority priority to set this thread to
     * @throws IllegalArgumentException If the priority is not in the range
     *                                  <code>MIN_PRIORITY</code> to <code>MAX_PRIORITY</code>.
     * @see java.lang.Thread#getPriority()
     * @see java.lang.Thread#MAX_PRIORITY
     * @see java.lang.Thread#MIN_PRIORITY
     */
    public final void setPriority(int newPriority) {
        if (newPriority > MAX_PRIORITY || newPriority < MIN_PRIORITY) {
            throw new IllegalArgumentException();
        }
        setPriority0(priority = newPriority);
    }

    /**
     * Returns this thread's priority.
     *
     * @return this thread's priority.
     * @see java.lang.Thread#setPriority(int)
     */
    public final int getPriority() {
        return priority;
    }

    /**
     * Returns this thread's name. Note that in CLDC the name of the thread can
     * only be set when creating the thread.
     *
     * @return this thread's name.
     */
    public final String getName() {
        return String.valueOf(name);
    }

    /**
     * setup thread name
     *
     * @param tname
     */
    public final void setName(String tname) {
        if (tname != null) {
            name = tname.toCharArray();
        }
    }

    /**
     * Returns the current number of active threads in the virtual machine.
     *
     * @return the current number of active threads.
     */
    public static native int activeCount();

    /**
     * Waits for this thread to die.
     *
     * @throws InterruptedException if another thread has interrupted the
     *                              current thread. The <i>interrupted status</i> of the current thread is
     *                              cleared when this exception is thrown.
     */
    public synchronized final void join() throws InterruptedException {
        while (isAlive()) {
            wait(1000);
        }
    }

    /**
     * Marks this thread as either a {@linkplain #isDaemon daemon} thread
     * or a user thread. The Java Virtual Machine exits when the only
     * threads running are all daemon threads.
     *
     * <p> This method must be invoked before the thread is started.
     *
     * @param on if {@code true}, marks this thread as a daemon thread
     * @throws IllegalThreadStateException if this thread is {@linkplain #isAlive alive}
     */
    public final void setDaemon(boolean on) {
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }

    /**
     * Tests if this thread is a daemon thread.
     *
     * @return <code>true</code> if this thread is a daemon thread;
     * <code>false</code> otherwise.
     * @see #setDaemon(boolean)
     */
    public final boolean isDaemon() {
        return daemon;
    }

    /**
     * Returns a string representation of this thread, including the thread's
     * name and priority.
     *
     * @return a string representation of this thread.
     */
    public String toString() {
        return "Thread[" + getName() + "," + getPriority() + "]";
    }

    /* Some private helper methods */
    private native void setPriority0(int newPriority);

    private native void interrupt0();

}
