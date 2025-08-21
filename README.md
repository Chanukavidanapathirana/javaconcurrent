# Java Concurrency Examples

This repository is a collection of small Java programs that illustrate different techniques for handling concurrent operations. Each example lives in its own package under `src/` and provides a `*Example.java` class with a `main` method to run the demo.

## Packages

### `Bank`
Simulates bank account transfers using `ReentrantReadWriteLock` for safe concurrent access. Threads perform deposits, withdrawals, and transfers while avoiding deadlocks.

### `CoffeShop`
(Spelling intentional in the source.) Models customers placing orders and baristas fulfilling them. Coordination is done with intrinsic locks (`synchronized`) and `wait`/`notifyAll` on a bounded queue.

### `SharedBathroomStall`
Demonstrates semaphores and resource pooling by controlling access to a limited number of bathroom stalls. A `Semaphore` enforces fairness and a queue assigns stall numbers.

## Running the examples

Compile all sources:

```bash
javac -d out $(find src -name "*.java")
```

Run a specific example, e.g. the bank demo:

```bash
java -cp out bank.BankExample
```

Replace `bank.BankExample` with the desired package and class such as `coffeshop.CoffeeShopExample` or `sharedbathroomstall.BathroomExample`.
