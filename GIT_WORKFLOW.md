# Git workflow example

git init
git add .
git commit -m "init: maven, junit5, readme"

git checkout -b feature/metrics
# add util files
git add src/main/java/org/example/util
git commit -m "feat(metrics): counters, depth tracker, CSV writer"

git checkout -b feature/mergesort
git add src/main/java/org/example/alg/MergeSort.java src/test/java/org/example/alg/MergeSortTest.java
git commit -m "feat(mergesort): baseline + reuse buffer + cutoff + tests"

# repeat for other features...
