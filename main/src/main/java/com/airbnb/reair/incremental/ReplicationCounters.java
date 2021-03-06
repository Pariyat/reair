package com.airbnb.reair.incremental;

import java.util.HashMap;
import java.util.Map;

/**
 * Counters used to track the progress of replication.
 */
public class ReplicationCounters {
  public enum Type {
    // Tasks that have completed successfully
    SUCCESSFUL_TASKS,
    // Tasks that aren't completable (e.g. missing source table), but are
    // otherwise finished.
    NOT_COMPLETABLE_TASKS,
    // Tasks that were submitted to run.
    EXECUTION_SUBMITTED_TASKS,
    // Tasks that failed to execute. This shouldn't happen in normal
    // operation.
    FAILED_TASKS
  }

  private Map<Type, Long> counters;

  public ReplicationCounters() {
    counters = new HashMap<>();
  }

  /**
   * Increment the count for the given counter type.
   *
   * @param type the type of counter
   */
  public synchronized void incrementCounter(Type type) {
    long currentCount = 0;
    if (counters.get(type) != null) {
      currentCount = counters.get(type);
    }
    counters.put(type, currentCount + 1);
  }

  /**
   * Get the value of the given counter.
   *
   * @param type the type of counter
   * @return the value of the counter
   */
  public synchronized long getCounter(Type type) {
    long currentCount = 0;
    if (counters.get(type) != null) {
      currentCount = counters.get(type);
    }
    return currentCount;
  }

  /**
   * Reset all counters to 0.
   */
  public synchronized void clear() {
    counters.clear();
  }
}
