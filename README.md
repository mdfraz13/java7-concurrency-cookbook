java7-concurrency-cookbook
==========================

This repository contains code from the book "Java 7 Concurrency Cookbook".

# Packages: #

## ro.tatacalu.java7concurrency.ch02 - CHAPTER 2 ##
- ro.tatacalu.java7concurrency.ch02.recipe01: Synchronizing a method
- ro.tatacalu.java7concurrency.ch02.recipe02: Arranging independent attributes in synchronized classes
- ro.tatacalu.java7concurrency.ch02.recipe03: Using conditions in synchronized code
- ro.tatacalu.java7concurrency.ch02.recipe04: Synchronizing a block of code with a Lock
- ro.tatacalu.java7concurrency.ch02.recipe05: Synchronizing data access with read/write locks
- ro.tatacalu.java7concurrency.ch02.recipe06: Modifying Lock fairness
- ro.tatacalu.java7concurrency.ch02.recipe07: Using multiple conditions in a Lock

## ro.tatacalu.java7concurrency.ch03 - CHAPTER 3 ##
- ro.tatacalu.java7concurrency.ch03.recipe01: Controlling concurrent access to a resource - binary semaphore
- ro.tatacalu.java7concurrency.ch03.recipe02: Controlling concurrent access to multiple copies of a resource - counting semaphore
- ro.tatacalu.java7concurrency.ch03.recipe03: Waiting for multiple concurrent events - CountDownLatch
- ro.tatacalu.java7concurrency.ch03.recipe04: Synchronizing tasks in a common point - CyclicBarrier; Divide and Conquer
- ro.tatacalu.java7concurrency.ch03.recipe05: Running concurrent phased tasks - Phaser
- ro.tatacalu.java7concurrency.ch03.recipe06: Controlling phase change in concurrent phased tasks - extending Phaser
- ro.tatacalu.java7concurrency.ch03.recipe07: Changing data between concurrent tasks - Exchanger

## ro.tatacalu.java7concurrency.ch04 - CHAPTER 4 - Thread Executors ##
- ro.tatacalu.java7concurrency.ch04.recipe01: Creating a thread executor - ThreadPoolExecutor
- ro.tatacalu.java7concurrency.ch04.recipe02: Creating a fixed-size thread executor
- ro.tatacalu.java7concurrency.ch04.recipe03: Executing tasks in an executor that returns a result - Callable, Future
- ro.tatacalu.java7concurrency.ch04.recipe04: Running multiple tasks and processing the first result
- ro.tatacalu.java7concurrency.ch04.recipe05: Running multiple tasks and processing all the results
- ro.tatacalu.java7concurrency.ch04.recipe06: Running a task in an executor after a delay
- ro.tatacalu.java7concurrency.ch04.recipe07: Running a task in an executor periodically
- ro.tatacalu.java7concurrency.ch04.recipe08: Canceling a task in an executor
- ro.tatacalu.java7concurrency.ch04.recipe09: Controlling a task finishing in an executor
- ro.tatacalu.java7concurrency.ch04.recipe10: Separating the launching of tasks and the processing of their results in an executor - CompletionService

### Abandoned this book due to the vast amount of fail and inconsistencies it contains ###



-------------------------------------------------------------------------------------------------------



# Rule of Thumb
Try to use objects in java.util.concurrent instead of figuring out concurrency for yourself 

Try to use synchronized over using actual Lock/Condition objects

Brian Goetz (author of Java Concurrency in Practice) coined the following “synchronization motto”:
“If you write a variable which may next be read by another thread, or you read a variable which may have last been written by another thread, you must use synchronization.”

# Definitions are hazy

Synchronization --- generally means sharing data between multiple processors or threads

Concurrency refers to a measure of (or the art of improving) how effectively an application allows multiple jobs required by that application (e.g. serving web page requests from a web server) to run simultaneously.

Java is generally a good choice of language for multiprocessor applications, both because of in-built concepts fundamental to the language and because of a relatively rich concurrency library provided from Java 5 onwards.



# Thread Objects

Threads share the process's resources, including memory and open files

Every application has at least one thread called the main thread

Each thread can be scheduled on a different CPU core

Each thread is associated with an instance of the class Thread

# Threads are always in 1 of 6 states

New --- instantiated, but you haven't called start() yet

Runnable --- up to OS whether it is actually running

Inactive, i.e.

Blocked --- waiting to acquire an intrinsic object lock

Waiting --- waiting for a java.util.concurrent Condition or Lock object

Timed waiting --- waiting for Thread.sleep(time) or similar

Terminated --- either run() exited normally, or an uncaught exception terminated it

The stop() and suspend() methods, which directly make a thread stop, have been deprecated because they could be stopped while the data structure they're operating upon is in an inconsistent state.

# Two ways to start a thread

Provide your own Runnable to a Thread

This is the preferred method. "The general contract of the method run is that it may take any action whatsoever."

    public class HelloRunnable implements Runnable {
        public void run() {
            System.out.println("Hello from thread!");
      }
      public static void main(String[] args) {
        Runnable hello = new HelloRunnable();
        Thread thread = new Thread(hello);
        thread.start();
    }
    }

Subclass Thread

The other way is preferred.

     public class HelloThread extends Thread {
     public void run() { System.out.println("Hello from thread!"); }
     public static void main(String[] args) { new HelloThread().start(); }
  }
  
# Thread basics

Wait for the completion of another thread with myThreadInstance.join(timeout)

To synchronize threads, Java uses monitors, a mechanism allowing only one thread at a time to execute a region of code protected by the monitor

You can give threads priority levels

void setPriority(int priority)

Thread.MIN_PRIORITY = 1, MAX_PRIORITY = 10 (reverse order from Unix)

High priority threads

User interaction

Short tasks

Low priority

Long tasks

The VM generally runs only the highest-priority task, but not always

This makes it easy to accidentally starve low-priority tasks

You can designate a thread to be a daemon meaning the JVM will exit if only daemon threads are still executing

# Interrupting Threads

Say you have a "runnable" Thread instance:

    Thread thr = new Thread(new MyRunnable());
    thread.start();
    
Now it's off doing its thang, but you'd like to kindly ask it to stop when it gets a chance. So you call interrupt() on the instance:

    thread.interrupt();

Now in MyRunnable implements Runnable you defined run(). Some of the methods your Runnable can call (e.g. Thread.sleep(millisec)) can throw InterruptedException which will happen after you called interrupt() above. At this point before you call sleep() or any other blocking call, you can check if you have been interrupted with

     boolean Thread.currentThread().isInterrupted()

and perform actions before you actually throw the exception. But then you throw it, and you can catch it too, which is where you should probably interrupt yourself in whatever way is apropos.

# Implementing Callbacks

This is based on Chapter 3 of Harold, Elliotte Rusty (2013-10-04). Java Network Programming. O'Reilly Media. Kindle Edition.

The reason we create an "instanceOfMe" is to show what it looks like when the callbackHandler() is not static, which it would have to be if we didn't create an instance of something to call the callbackHandler on.
The reason we don't spawn and start the other thread in the constructor is becase the thread could try to call back before the constructor has finished initializing the object!

If many objects have the same callbacks, make an interface for them.

Code

    public class Callbacker implements Runnable {
    private Thing forDoing;
    private ToCallback toCall;
    public Callbacker(Thing forDoing, ToCallback toCall) {
        this.forDoing = forDoing;
        this.toCall = toCall;
    }
    @Override public void run() {
        Info forYou = myRaisonDEtre(forDoing);
        toCall.callbackHandler(forYou);
    }
    }

    public class ToCallback {
    Thing something;
    public ToCallback(Thing something) {
        this.something = something;
    }
    public static void main(String[] args) {
        ToCallback instanceOfMe = new ToCallback(someThing);
        instanceOfMe.startThreadToDoThings();
    }
    public void startThreadToDoThings() {
        Callbacker cb = new Callbacker(something, this);
        new Thread(cb).start();
    }
    public void callbackHandler(Into receivedStuff) {
        doThingsWith(receivedStuff);
    }
    }
    
    
# Locks

See package

    java.util.concurrent.locks

# Interface lock

Use this when synchronized methods and blocks aren't going to cut it, e.g. if you want to use their associated Condition objects, or if you want something more sophisticated than "I wait for the lock, become sole owner, and release it when I'm done," e.g. the ReadWriteLock (see below), and the non- blocking tryLock() method (see interface methods).

Using a synchronized block with a Lock as a parameter just uses the Lock object's associated monitor just as usual. It is suggested not to do that because it's confusing.

We have (none are "optional"):

      interface Lock {
    void lock()   // can't be interrupted while waiting, can cause deadlock
    void lockInterruptibly()   // will wake up if interrupted while waiting
    Condition newCondition()   // bound to this Lock instance
    boolean tryLock()          // only acquire if free

    /* e.g. myLock.tryLock(100, TimeUnit.MILLISECONDS);
     * this method can be interrupted while waiting  */
    boolean tryLock(long time, TimeUnit unit)  // give up after time

    void unlock()
 }

"With this increased flexibility comes additional responsibility." In the docs, we are instructed to use the following idiom

     Lock l = new ReentrantLock(); // could be *any* type of lock
    l.lock();
    try {
    // access protected resource
    }
    finally {       // IMPORTANT:
    l.unlock(); // still unlock even after Exception is thrown
    }
    
# Interface ReadWriteLock

A ReadWriteLock maintains a pair of associated locks, one for read-only operations and one for writing. The read lock may be held simultaneously by multiple reader threads, so long as there are no writers. The write lock is exclusive. [-- Oracle]
Protect a concurrent object with this thing when there are many readers but few updates to it.

This is implemented in class ReentrantReadWriteLock

This interface does not extend anything.

     interface ReadWriteLock {
    Lock readLock()     // return read-lock
    Lock writeLock()    // return write-lock
    }
    
Class ReentrantLock

A ReentrantLock has the same behavior & semantics as the implicit monitor lock invoked with the synchronized keyword, but has extended capabilities.

What makes it "reentrant" is that a single thread can repeatedly acquire a lock it already owns (which increases its getHoldCount()). If you do that, you must correspondingly unlock() over and over until your hold count == 0. This allows a single thread to acquire a lock, then call another method that also acquires that lock, without any issues.

If you construct a fair ReentrantLock, it will favor granting access to the longest-waiting thread. Otherwise, no particular access order is guaranteed. Don't make your lock fair unless you have a reason, because it makes your code much slower.

Note that the Serializable interface has no methods to bring to the table. Upon deserialization, a Lock will be unlocked, regardless of the state it was in when it was serialized.

     class ReentrantLock implements Lock, Serializable {
    ReentrantLock(boolean fair)

    boolean isLocked()

    int     getHoldCount() // number of holds by current thread
    Thread  getOwner() // null if not owned

    Collection<Thread> getWaitingThreads(Condition) // waiting on Condition
    int getWaitQueueLength(Condition) // est. #threads waiting on Condition
    boolean hasWaiters(Condition)

    boolean hasQueuedThread(Thread) // is thread waiting to acquire this?
    boolean hasQueuedThreads()      // check if *anyone* is waiting

    boolean isHeldByCurrentThread()

    boolean isFair() // is fairness == true?
      }


#  Interface Condition

Use a Condition to make one thread wait for another thread's signal. The Condition variable facilitates this being done atomically. A Condition instance is bound to a Lock. You create the condition via

     Condition c = myLock.newCondition()
     
     
You might do this when you have acquired a lock, but still must wait before you can do useful work.

You can only call await(), signal(), or signalAll() if you have the Lock that the Condition is attached to.

When you call await() you relinquish the lock, and your Thread enters the wait state (see above). It is not made runnable again when the lock becomes available. It is only made runnable when someone calls myCondition.signal() and you are next-in-line on the Condition, or someone calls myCondition.signalAll(). At this point you can take the lock back and continue where you left off (returning from your call to await()). In general the code for this really ought to take the form

      while(!(ok to proceed))
      condition.await();


This is because just because you got the lock back doesn't mean that whatever reason you stopped in the first place is not true anymore so you'll want to check again and keep blocking if the condition is still not met. This code does exactly that.
If no one ever signals you, you can deadlock.

# The interface looks like:

    interface Condition {

    /* wait for another thread to call signal() on this Condition */
    void await()       // wait for Condition.signal() or Thread.interrupt()
    void awaitUninterruptibly()  // wait for signal() but not interrupt()

    /* wait until timeout */
    boolean await(long, TimeUnit)
    long    awaitNanos(long)
    void    awaitUntil(Date)

    void signal()               // wakeup one waiting thread
    void signalAll()            // wakeup *all* waiting threads
    }
    
    
# Synchronized

Synchronization prevents race conditions (threads stepping on each other's toes, accessing corrupt shared data).
Unless told otherwise (using a synchronized block or volatile), threads may work on locally cached copies of variables (e.g. count), updating the "main" copy when it suits them. For the reasons having to do with processor architecture, they may also re-order reads and writes, so a variable may not actually get updated when otherwise expected. However, on entry to and exit from blocks synchronized on a particular object, the entering/exiting thread also effectively synchronizes copies of all variables with "main memory" (aka. the Java heap, as seen by the JVM)
Block vs Method

A synchronized method

     public synchronized void blah() {
     // do stuff
     }
is semantically equivalent to a synchronized (this) block

    public void blah() {
     synchronized (this) {
    // do stuff
     }
    }

is semantically equivalent to using the intrinsic lock itself

    public void blah() {
    this.intrinsicLock.lock();
    try {
        // do stuff
    }
    finally {
        this.intrinsicLock.unlock();
    }
    }
    
    
We can wait on the intrinsicLock's intrinsicCondition using this.wait() i.e. simply wait(), and notifyAll().

    synchronized(objInstance) block

Unlike synchronized methods, synchronized statements must specify the object that provides the intrinsic lock
synchronized method

# Two Effects:

Only one thread can execute it at a time
Exiting the method establishes a happens-before relationship with subsequent invocations for the same object
Refs

Synchronized overview

# Multi-Process Programming

Based on: ProcessBuilder's Java Docs

Use class ProcessBuilder to create a new process in the OS
This is a child-process (in OS terms)
By default it inherits the current process's environment (accessible for itself via System.getEnv())
The working dir defaults to same as current process, but is modifiable (see e.g. below)
By default, STD|IN|OUT|ERR are redirected to the current process, and can be accessed via streams obtained using methods get(Output|Input|Error)Stream()
The Process object does not get killed when there are no references to it left, it just keeps executing
You can wait until the process terminates with proc.waitFor()
To kill the Process via its reference: proc.destroy()
Basic usage pattern

/* create the ProcessBuilder as you'd expect */
ProcessBuilder procBldr = new ProcessBuilder("myCmd", "arg1", "arg2");

/* modify environment variables */
Map<String, String> env = procBldr.environment();
env.put("VAR1", "value");

/* change working dir */
procBldr.directory(new File("a_Location/inner"));

/* redirect IO streams (makes them inaccessible to current proccess */
procBldr.redirectOutput(...);
procBldr.redirectErrorStream(...);

// make all IO locations the same as the current process
procBldr.inheritIO();

/* to capture the output you can EITHER do */

// this
procBldr.redirectOutput(ProcessBuilder.Redirect.INHERIT);

// or this for finer control and piping etc.
InputStream is = p.getInputStream();
BufferedReader reader = new BufferedReader(new InputStreamReader(is));
String s;
while ((s = reader.readLine()) != null)
    System.out.println(s);
is.close();

/* start the process */
Process proc = procBldr.start();
Example of printing the output of "ls"

Based on Art of Coding (and verified to work)

ProcessBuilder procBldr = new ProcessBuilder("ls");
Process p = procBldr.start();
p.waitFor();
System.out.println("Process exited with code = "+p.exitValue());

// To send STDERR to STDOUT
procBldr.redirectErrorStream(true);

// this can be simplified, see my notes above
InputStream is = p.getInputStream();
BufferedReader reader = new BufferedReader(new InputStreamReader(is));
String s;
while ((s = reader.readLine()) != null)
    System.out.println(s);
is.close();
Other

Constructors cannot be synchronized
Every object has an intrinsic lock associated with it
Java Memory Model

On modern platforms, code is frequently reordered by the compiler, the processor and the memory subsystem to achieve maximum performance
The Java Memory Model (JMM) defines when such reorderings are possible
Execution-time constraints on the relationship between threads and main memory to achieve consistent and reliable applications
Makes it possible to reason about code execution in the face of optimizations
JVMs must observe within-thread as-if-serial semantics
NB: as-if-serial semantics do not prevent different threads from having different views of the data.
It does mean that everything that happens before the release of a lock will be seen to be ordered before and visible to everything that happens after a subsequent acquisition of that same lock.
Cache coherence:

After we exit a synchronized block, we release the monitor
This flushes the cache to main memory, making writes made by this thread visible to other threads.
So now, before we can enter a synchronized block, we acquire the monitor, invalidating the local processor cache, so that variables will be reloaded from main memory, so are now able to see all of the writes made by the previous release.
Volatile Fields

Indicates that a variable's value will be modified by different threads.
Lock-free mechanism for synchronizing access to an instance field
Declaring a volatile Java variable means:

The value of this variable will never be cached thread-locally: all reads and writes will go straight to "main memory"
This shows what it does

volatile int i;
...
i += 5;
Is basically equivalent to:

// Note that we can't literally synchronize on an int primitive!
int temp;
synchronized (i) { temp = i; }
temp += 5;
synchronized (i) { i = temp; }
Note! Although it may look it, the operation i += 5 is *not atomic*. If that's what you want, you should probably use AtomicInteger instead.

The value of a volatile field becomes visible to all *readers*** (other threads in particular, aka consistent) after a *write** operation completes* on it
Without volatile, readers could see some non-updated value
Category	synchronized(thing)	volatile
works on primitive types	no	yes
can block	yes	no (no lock involved)
can be null	no	yes
provides atomicity	no	no
Refs

Volatile Tutorial
Jamex's Common Volatile Bugs
Final variables

Basically a const in C++

Remember the whole double-checked locking thing? Well, apparently, "final can be used to make sure that when you construct an object, another thread accessing that object doesn't see that object in a partially-constructed state, as could otherwise happen"
Refs

Final Tutorial
Atomics

AtomicInteger AtomicLong AtomicIntegerArray AtomicReferenceArray

In package java.util.concurrent.atomic, they have atomic methods in/decrementAndGet(), meaning you can safely use e.g. AtomicInteger as a shared counter without any synchronization.
Concurrent Data Structures

You want to stay away from the above low-level constructs whenever posssible, and use higher-level structures implemented by concurrency experts with a lot of time to sink into it and experience to debug it. Queues are often the right choice for multithreading scenarios; you have producer(s) that insert in and consumer(s) that retrieve out. Blocking queues block when you try to insert into a full queue or remove from an empty one, until that operation because feasible.\


       interface BlockingQueue<E> {

        /* insert */
        void add()      // Exception if it's full
        bool offer()    // block while full, false on timeout
        void put()      // block while full, no timeout

       /* return head element */
       E element()     // Exception if empty
        E peek()        // null if empty

       /* remove and return head element */
        E remove        // Exception if empty
        E take()        // block while empty
       }

# You'll also find the following implementations:

Queues

ArrayBlockingQueue  // fixed max size on creation

LinkedBlockingQueue // no fixed upper bound on size

PrioirityBlockingQueue // removed by priority, unbounded size

LinkedTransferQueue // SE 7, producer's insert blocks till consumer removes
Other

ConcurrentHashMap       // shared cache, ≤ 16 *simult* writers by default

ConcurrentSkipListMap

ConcurrentSkipListSet

ConcurrentLinkedQueue

CopyOnWriteArrayList

CopyOnWriteArraySet

ConcurrentHashMap

Feature	Collections.synchronizedMap(Map)	ConcurrentHashMap

Allows multiple concurrent readers	yes	yes

Must synchronize while iterating through	yes	no


Allows (tunable) multiple concurrent writers	no	yes

In cases in which multiple threads are expected to access a common collection, "Concurrent" versions are normally preferable.
Updates to these things are not *atomic*, but we can provide atomicity in three simple ways

Use the putIfAbsent(K,V) method

synchronize the whole updating method

Use the replace() method like a sort of compare-and-swap like the following real-live piece of "optimistic concurrency via compare-and- swap"

          private void incrementCount(String q) {
            Integer oldVal, newVal;
          do {
           oldVal = queryCounts.get(q);
            newVal = (oldVal == null) ? 1 : (oldVal + 1);
              } while (!queryCounts.replace(q, oldVal, newVal));
           }
  
# How does it work?

Has a field Segment[] whose size is the 3rd constructor parameter ("concurrencyLevel")
Each Segment contains a HashEntry<K,V>[] hashTable whose size is \(\frac{\mathrm{totalSize}{(1^{st}param)}}{\mathrm{numSegments}{(3^{rd}param)}}\)
When you go to write it, you first hash into the correct Segment and only lock that one
Refs

# Java2Blog Tutorial

Oracle's Package Concurrent Summary

ConcurrentHashMaps on Jamex

Collections.synchronized(Collection|List|Map|Set|SortedMap|SortedSet)()

See table nearby for comparison with ConcurrentHashMap

Wrapping a Map with Collections.synchronizedMap(myMap) makes it safe to access the map concurrently: each call to get(), put(), size(), containsKey() etc will synchronize on the map during the call

Note that this doesn't make updates to the Map *atomic, but it does make it *safe. That is, concurrent calls to update will never leave the Map in a corrupted state. But they might 'miss a count' from time to time.
For example, two threads could concurrently read a current value of, say, 2 for a particular query, both independently increment it to 3, and both set it to 3, when in fact two queries have been made.

The same is true of the [above] ConcurrentHashMap, though we can fix that (above)


# Refs

ConcurrentHashMaps Jamex

Callables, Futures, Thread Pools

Runnable --- runs a task asynchronously, no params, no return value

You implmement public void run() which gets called via new Thread(runnable).start()

Callable<V> --- runs a task asynchronously, no params, with return value

You implement public V call()

Future<V> --- holds result of asynchronous computation

no callbacks

blocking V get()

non-blocking boolean isCancelled()

# Thread Pools

Use these if your program use a large number of short-lived threads.

When a thread dies, it gets the next task instead of getting deallocated & reallocated.

# Executors --- contains static factory methods for constructing thread pools

newFixedThreadPool(size)    // fixed size pool

newCachedThreadPool()       // creates new threads when necessary

newScheduledThreadPool(nThreads)    // run periodically / after delay

newSingleThreadScheduledExecutor()  // see above

Future<T> submit(Callable<T>/Runnable[, T result]) --- submit to pool

Callable is the easiest way to get the return value. Just call get() on the Future<T>

shutdown() --- shuts down pool nicely (call when you're done so you program can terminate)

shutdownNow() --- cancels all current tasks and shuts down

Other
-------------------------------------------------------------------------------------------------

## Java Concurrency in Practice



#### Chapter 1: Introduction

* *Safety* means "nothing bad ever happens," while *liveness* means "something good eventually happens."
* Compromising safety often means compromising correctness. An example is a race condition between multiple threads.
* A liveness failure in a single-threaded program can be an infinite loop. In a multithreaded program, it could be deadlock, starvation, or livelock.
* While liveness means that something good *eventually* happens, performance means that it will happen *quickly*.
* Context switches have significant costs, including saving and restoring execution context, less of locality, and spending CPU time on scheduling threads instead of running them.

#### Chapter 2: Thread Safety

* Writing thread-safe code is, at its core, about managing access to shared, mutable state.
* Whenever more than one thread accesses a given state variable, and one of them might write to it, they all must coordinate their access to it using synchronization.
* A broken program shares mutable state without synchronization. Either don't share the state, make it immutable, or use synchronization upon every access.
* It is far easier to design a class to be thread-safe than to retrofit it for safety later.
* The same object-oriented techniques that help you write well-organized, maintainable classes -- such as encapsulation and data hiding -- can also help you create thread-safe classes.
* A program that consists entirely of thread-safe classes may not be thread-safe, and a thread-safe program may contain classes that are not thread-safe.

##### 2.1: What is thread safety?

* A class is thread-safe if it behaves correctly when accessed from multiple threads, regardless of their scheduling or interleaving of execution, and with no additional synchronization or coordination by the calling code.
* Thread-safe classes encapsulate any needed synchronization so that clients need not provide their own.

##### 2.2: Atomicity

* A race condition occurs when the correctness of a computation depends on the relative timing or interleaving of multiple threads.
* The most common type of race condition is *check-then-act*, where an observation could have become invalid between the time you observed it and the time you acted on it, causing a problem.
* Read-modify-write operations require knowing a previous value and ensuring that no one else changes or uses that value while you are in mid-update.
* Check-then-act and read-modify-write sequences are compound actions, or sequences that must be executed atomically in order to remain thread-safe.

##### 2.3: Locking

* Mutual exclusion locks, or mutexes, means that at most one thread may own a lock. If thread A attempts to acquire a lock by thread B, it blocks until B releases it.
* Reentrancy means that locks are acquired on a per-thread rather than per-invocation basis. Each lock is associated with an acquisition count and an owning thread.

##### 2.4: Guarding state with locks

* For each mutable state variable that may be accessed by more than one thread, all accesses to that variable must be performed by the same lock held. The variable is *guarded* by the lock.
* When a class has invariants that involve more than one state variable, we must ensure that each variable participating in the invariant must be guarded by the same lock.

##### 2.5: Liveness and performance

* Acquiring and releasing a lock has some overhead, so do not break down guarded blocks too far, even if this would not compromise atomicity.
* When implementing a synchronization policy, resist the temptation to prematurely sacrifice simplicity (potentially compromising safety) for the sake of performance.
* Avoid holding locks during lengthy computations or operations at risk of not completing quickly, such as when performing disk or network I/O.

#### Chapter 3: Sharing Objects

##### 3.1: Visibility

* To ensure visibility of memory writes across threads, you must use synchronization.
* There is no guarantee that operations in one thread will be performed in the order given by the program, as long as the reordering is not detectable from within *that* thread.
* Because of reordering, attempts to reason about the order in which memory actions "must" happen in insufficiently synchronized multithreaded programs will almost certainly be incorrect.
* Unless synchronization is used every time a variable is accessed, it is possible for a thread to read a stale value for that variable.
* Stale data can cause serious and confusing failures such as unexpected exceptions, corrupted data structures, inaccurate computations, and infinite loops.
* *Out-of-thin-air safety* is when a variable reads a thread without synchronization, it reads a value that was actually written by another thread instead of some random value.
* With the Java Memory Model, the values of variables that were visible to thread A prior to releasing a lock are guaranteed to be visible to thread B upon acquiring the same lock.
* Variables marked with the `volatile` keyword are not reordered with other memory operations, and are not put in caches where they are hidden from other processors.
* A `volatile` variable can be used for a completion, interruption, or status flag, but the semantics are not strong enough to make the increment operation atomic, for example.

##### 3.2: Publication and escape

* *Publishing* an object means making it available to code outside of its current scope. An object that is published when it should not have been is said to have *escaped*.
* Any object that is *reachable* from a published object by following some chain of nonprivate field references and method calls has also been published.
* Once an object escapes, you have to assume that another class or thread may, maliciously or carelessly, misuse it. This is a compelling reason to use encapsulation.
* An object is in a predictable, consistent state only after its constructor returns, so publishing an object (via `this`) from within its constructor can publish an incompletely constructed object.
* You can avoid this improper construction by using a private constructor and a public static factory method.

##### 3.3: Thread confinement

* If data is only accessed by a single thread, then no synchronization is needed. This technique is called *thread confinement*.
* *Ad-hoc thread confinement* is when the responsibility for maintaining thread confinement falls entirely on the implementation.
* Thread confinement is often a consequence of deciding to implement a particular subsystem, such as the GUI, as a single thread. The simplicity benefit of such a system outweighs the fragility of ad-hoc thread confinement.
* *Stack confinement* is a special case of thread confinement in which an object can only be reached through local variables. Local variables are intrinsically confined to the executing thread.

##### 3.4: Immutability

* Immutable objects are simple, because they can only have one state. Immutable objects are also safe, because you can freely share and publish them without the need to make defensive copies.
* An object is immutable if its state cannot be modified after construction, all its fields are `final`, and it is properly constructed, i.e. the `this` reference does not escape during construction.
* Whenever a group of related data items but be acted upon atomically, consider creating an immutable holder class for them.

##### 3.5: Safe publication

* Simply storing a reference to an object into a public field is not enough to publish that object safely. Improper publication allows another thread to observe a partially constructed object.
* However, immutable objects can be used safely by any thread without additional synchronization, even when synchronization is not used to publish them.
* To publish an object safely, both the reference to the object and the object's state must be made visible to other threads at the same time.
* Objects that are not technically immutable, but whose state will not be modified after publication, are called *effectively immutable*. Such objects, when safely published, can be used safely by any thread without additional synchronization.
* While effectively immutable objects must be safely published, mutable objects must be safely published, and mus the either thread-safe or guarded by a lock.
* Many concurrency errors stem from failing to understand the "rules of engagement" for a shared object. When you publish an object, document how it should be accessed.

#### Chapter 4: Composing Objects

##### 4.1: Designing a thread-safe class

* Designing a thread-safe class requires identifying the invariants that constrain the state variables, and establishing a policy for managing concurrent access to them.
* The smaller the *state space* of an object or variable, the easier it is to reason about. Use `final` fields to reduce the state space.
* Operations with state-based preconditions are called *state-dependent*.
* Ownership implies control, but once you publish a reference to a mutable object, you no longer have exclusive control, but at best "shared ownership."

##### 4.2: Instance confinement

* Encapsulating data within an object confines access to the data to the object's methods, making it easier to ensure that the data is always accessed with the appropriate lock held.
* Instance confinement also allows different state variables to be held by different locks.
* Confined objects can also escape by publishing other objects such as iterators or inner class instances that may indirectly publish the confined objects.
* Using a private lock prohibits client code from acquiring it, whereas a publicly accessible lock allows client code to participate in its synchronization policy, perhaps incorrectly.

##### 4.3: Delegating thread safety

* A class with multiple independent thread-safe state variables and no operations that have any invalid state transitions can delegate thread safety to the underlying state variables.
* If a state variable is thread-safe, does not participate in any invariants that constrain its value, and has no prohibited state transitions for any of its operations, then it can be safely published.

##### 4.4: Adding functionality to existing thread-safe classes

* Extending a class to support a thread-safe operation is more fragile than adding code directly to the class, as its synchronization policy is now distributed over multiple source files.
* Just as subclassing violates encapsulation of implementation, client-side locking violates encapsulation of synchronization policy.

##### 4.5: Documenting synchronization policies

* Document a class's thread safety guarantees for its clients; document its synchronization policy for its maintainers.
* If you want clients to be able to create new atomic operations on your class, you must document which locks they should acquire to do so safely.
* If you must guess whether a class is thread-safe, improve the quality of your guess by interpreting the specification by someone who must implement it versus someone who will merely use it.

#### Chapter 10: Avoiding Liveness Hazards

##### 10.1: Deadlock

* When thread A holds lock L and tries to acquire lock M, but at the same time thread B holds lock M and tries to acquire L, this is deadlock, or *deadly embrace*.
* When a database system detects that a set of transactions has deadlocked by searching the is-waiting-for graph for cycles, it picks a victim and aborts that transaction, thereby releasing its held locks.
* A program will be free of lock-ordering deadlocks if all threads acquire the locks they need in a fixed global order. Sometimes we must induce this ordering.
* Invoking an alien method with a lock held is asking for liveness trouble. That method might risk deadlock by acquiring other locks, or block for an unexpectedly long time and stall other threads on the held lock.
* Calling a method with no locks held is called an *open call*, and classes that rely on open calls are more well-behaved and composable than classes that make calls with locks held.
* Resource deadlocks occur when thread A holds resource X and tries to acquire resource Y, but at the same time thread B holds resource Y and tries to acquire resource X.

##### 10.2: Avoiding and diagnosing deadlocks

* Try acquiring locks with a timeout. By using a timeout that is much longer than you expect acquiring the lock to take, you can regain control when something unexpected happens.
* Thread dumps not only include a stack trace for each running thread, but locking information such as which locks are held by a thread, and which lock a thread is waiting to acquire.

##### 10.3: Other liveness hazards

* *Starvation* is when a thread is perpetually denied access to resources it needs in order to make progress; the most commonly starved resource is CPU cycles.
* *Livelock* is a liveness failure in which a thread, while not blocked, still cannot make progress because it keeps retrying an operation that will always fail.
* Livelock can occur when multiple cooperating threads change their state in response to the others in such a way that no thread can ever make progress. The solution is to introduce randomness into the retry mechanism.

### Chapter 11: Performance and Scalability

##### 11.1: Thinking about performance

* Improving performance means doing more work with fewer resources. When performance of an activity is limited by availability of a particular resource, it is *bound* by that resource.
* Using concurrency to achieve better performance means using the processing resources we have more effectively, and enable a program to exploit additional processing resources that become available.
* *Scalability* describes the ability to improve throughput or capacity when additional computing resources (such as CPUs, memory, storage, or I/O bandwidth) are added.
* The "how much" aspects like scalability, throughput, and capacity are concern for server applications. The "how fast" aspects like service time or latency are concern for client applications.
* Most optimizations are premature because they are often undertaken before a clear set of requirements is available.
* Make it right, then make it fast. And if attempting to make it fast, measure. Don't guess.

##### 11.2: Amdahl's law

* Amdahl's law describes how much a program can theoretically be sped up by additional computing resources, based on the proportion of parallelizable to serial components.
* If F is the faction of the calculation that must be executed serially, then on a machine with N processors, we can achieve a speedup of most: 1/(F+(1-F)/N).
* When evaluating an algorithm, thinking "in the limit" about what would happen with hundreds or thousands of processors can offer some insight into where scaling limits might appear.

##### 11.3: Costs introduced by threads

* When a new thread is switched in, the data it needs is unlikely to be in the local processor cache, and so a context switch causes a flurry of cache misses and runs a little slower at first.
* Schedulers give each runnable thread a certain minimum time quantum, thereby amortizing the cost of the context switch and its consequences over more interrupted execution time.
* A program that does more blocking has more of its threads suspended and switched out. The program therefore incurs more context switches, increasing scheduling overhead and reducing throughput.
* Special instructions called *memory barriers* can flush or invalidate caches and flush hardware write buffers. They inhibit compiler optimizations; most operations cannot be reordered with them.
* *Lock elision* optimizes away lock acquisitions. *Lock coarsening* merges together adjacent blocks holding the same lock, reducing synchronization overhead and helping the optimizer.
* When a lock is contended, the losing threads must block. This can be implemented either by *spin-waiting* or by *suspending* the blocked thread through the operating system.

##### 11.4: Reducing lock contention

* Two factors influence the likelihood of contention for a lock: How often that lock is requested, and how long it is held once acquired.
* *Lock splitting* and *lock striping* involve using separate locks to guard multiple independent state variables previously guarded by a single lock.
* Splitting a lock into two offers the greatest possibility for improvement when a lock is experiencing moderate but not heavy contention.
* Lock striping extends lock splitting by partitioning locking on a variable-sized set of independent objects. But locking the collection for exclusive access is more difficult and costly.
* If your class has a small number of hot fields that do not participate in invariants with other variables, then replacing them with atomic variables may improve scalability.
* Tools like `vmstat` or `mpstat` can show whether your application is CPU-bound, while tools like `iostat` or `perfmon` can show whether your application is I/O-bound.
* The tool `vmstat` has a column reporting the number of threads that are runnable but not currently running because a CPU is not available.

There's a lot more advanced stuff in here that I will not take notes on

Controlling groups of tasks with ExecutorCompletionService
The fork-join framework, allowing RecursiveTask<T>,\
Synchronizers --- pre-implemented patterns for common multithread synchronization patterns, e.g. Exchanger, CyclicBarrier (neural nets?), and Semaphore

