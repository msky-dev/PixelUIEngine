package net.mslivo.pixelui.utils.concurrency;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class ParallelExecutor implements Disposable {
    private final int workers;
    private final ExecutorService executor;
    private final IntArrayTask[] intArrayTasks;
    private final ArrayTask[] arrayTasks;

    public ParallelExecutor() {
        this(null);
    }

    public ParallelExecutor(ExecutorService executorService) {
        this.workers = Runtime.getRuntime().availableProcessors();
        this.executor = executorService != null ? executorService : Executors.newFixedThreadPool(this.workers);
        this.intArrayTasks = new IntArrayTask[this.workers];
        this.arrayTasks = new ArrayTask[this.workers];
        for (int i = 0; i < this.workers; i++) {
            intArrayTasks[i] = new IntArrayTask();
            arrayTasks[i] = new ArrayTask<>();
        }
    }

    public void runParallel(final int[] array, final IntConsumer consumer) {
        runParallel(array, consumer, array.length);
    }

    public void runParallel(int[] array, IntConsumer consumer, int size) {
        if (size == 0) return;

        int taskCount = Math.min(this.workers, size);
        int chunkSize = (size + taskCount - 1) / taskCount;

        for (int i = 0; i < taskCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, size);
            IntArrayTask task = intArrayTasks[i];
            task.setup(array, start, end, consumer);
            this.executor.execute(task);
        }

        boolean allDone;
        do {
            allDone = true;
            for (int i = 0; i < taskCount; i++) {
                if (!intArrayTasks[i].done) {
                    allDone = false;
                    break;
                }
            }
            Thread.onSpinWait();
        } while (!allDone);

    }

    public <T> void runParallel(final Array<T> array, final Consumer<T> consumer) {
        runParallel(array, consumer, array.size);
    }

    public <T> void runParallel(final Array<T> array, final Consumer<T> consumer, final int size) {
        if (size == 0) return;

        final int taskCount = Math.min(this.workers, size);
        final int chunkSize = (size + taskCount - 1) / taskCount;

        for (int i = 0; i < taskCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, size);
            ArrayTask<T> task = arrayTasks[i];
            task.setup(array, start, end, consumer);
            this.executor.execute(task);
        }

        boolean allDone;
        do {
            allDone = true;
            for (int i = 0; i < taskCount; i++) {
                if (!arrayTasks[i].done) {
                    allDone = false;
                    break;
                }
            }
            Thread.onSpinWait();
        } while (!allDone);
    }


    private static final class IntArrayTask implements Runnable {
        int[] array;
        int start, end;
        IntConsumer consumer;
        volatile boolean done;

        void setup(int[] array, int start, int end, IntConsumer consumer) {
            this.array = array;
            this.start = start;
            this.end = end;
            this.consumer = consumer;
            this.done = false;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                consumer.accept(array[i]);
            }
            this.done = true;
        }
    }


    private static final class ArrayTask<T> implements Runnable {
        Array<T> list;
        int start, end;
        Consumer<T> consumer;
        volatile boolean done;

        void setup(final Array<T> list, final int start, final int end, final Consumer<T> consumer) {
            this.list = list;
            this.start = start;
            this.end = end;
            this.consumer = consumer;
            this.done = false;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                consumer.accept(list.get(i));
            }
            this.done = true;
        }
    }

    @Override
    public void dispose() {
        this.executor.shutdown();
    }
}
