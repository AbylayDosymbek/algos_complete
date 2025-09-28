# Algorithms assignment — README

This repository implements:
- MergeSort (reusable buffer, cutoff)
- QuickSort (random pivot, recurse smaller side)
- Deterministic Select (median-of-medians)
- Closest Pair 2D (divide-and-conquer)
- Instrumentation: Counters, DepthTracker, CSV bench CLI
- Tests (JUnit 5) and plotting script

Run tests:
```
mvn test
```

Build jar:
```
mvn -DskipTests package
```

Run benchmark (example):
```
java -cp target/algos-1.0-SNAPSHOT.jar org.example.cli.Main --algo mergesort --minN 128 --maxN 65536 --steps 8 --repeats 3 --out data/mergesort.csv
```
