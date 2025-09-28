# Divide & Conquer Algorithms – Project Report

## 1. Design and Implementation Notes
The code base was organized so that each algorithm could be measured and compared in a controlled way.  
To do this, two small helper components were written:

- DepthTracker – keeps track of the current recursion depth and records the maximum depth reached.  
  This prevents stack overflow in poorly balanced recursions and also serves as a useful metric.  

- Counters – collects statistics such as the number of comparisons and swaps.  
  This shows how algorithms behave not only in asymptotics, but also in constant factors.  

Memory use is minimized: MergeSort allocates a single reusable buffer, QuickSort and Select work entirely in-place, and Closest Pair splits arrays without creating extra copies. For small inputs, MergeSort and QuickSort switch to insertion sort, which is more efficient due to lower overhead and better cache locality.

---

## 2. Running Time Recurrences

### MergeSort
- Recurrence:  
  \[
  T(n) = 2T(n/2) + Θ(n)
  \]
- By Master Theorem (Case 2): work per level = Θ(n), height of tree = log₂(n).  
- Result: Θ(n log n).  
- In practice: stable scaling, with depth following log₂(n). The cutoff to insertion sort gives a big speedup for tiny arrays.

---

### QuickSort
- Expected recurrence:  
  \[
  T(n) = T(k) + T(n-k-1) + Θ(n)
  \]
- Random pivot → balanced splits on average → Θ(n log n) expected time.  
- Worst case depth = Θ(n), but mitigated by always recursing into the smaller part and iterating over the larger → guarantees O(log n) stack usage.  
- Measurements: recursion depth ≈ 1.5–2·log₂(n), runtime consistent with n log n.

---

### Deterministic Select (Median of Medians)
- Recurrence:  
  \[
  T(n) = T(n/5) + T(7n/10) + Θ(n)
  \]
- Solved via Akra–Bazzi: Θ(n).  
- Advantage: fully deterministic, avoids worst-case blow-ups.  
- In practice: linear growth is observed, but the hidden constant is large due to extra partitioning and sorting of groups. For medium n, it often runs slower than randomized QuickSelect or even full sorting.

---

### Closest Pair of Points (2D)
- Algorithm: sort points by x, recursively divide into halves, merge by y, and check a constant number of neighbors in the strip.  
- Recurrence:  
  \[
  T(n) = 2T(n/2) + Θ(n)
  \]
- By Master Theorem, Case 2 → Θ(n log n).  
- Experiments: runtime curve almost identical to MergeSort. The difference is in constant factors: distance calculations make it slower per element.

---

## 3. Experimental Plots

The results are saved to CSV and imported into Excel (`docs/results.xlsx`). Graphs include:

- Time vs n  
  - MergeSort and QuickSort → n log n growth.  
  - Select → nearly linear.  
  - Closest Pair → again n log n.  

- Recursion depth vs n  
  - MergeSort → log₂(n).  
  - QuickSort → ~2·log₂(n).  
  - Select → logarithmic as well.  
  - Closest Pair → log₂(n).  

Constant-factor observations:  
- MergeSort merges are cache-friendly, giving strong practical performance.  
- QuickSort does more swaps but fewer allocations.  
- Select is theoretically linear but practically expensive.  
- Closest Pair spends most time in floating-point distance checks.  
- Java-specific overhead (GC pauses, bounds checks) slightly distorts very small measurements.

---

## 4. Summary

- MergeSort: matches Θ(n log n), highly efficient in practice.  
- QuickSort: also matches Θ(n log n), with well-controlled depth thanks to smaller-first recursion.  
- Select: theoretically Θ(n), but constants dominate → slower for small/medium inputs.  
- Closest Pair: follows Θ(n log n), but higher per-element cost due to geometry.  

Key mismatches:  
- Cutoffs with insertion sort bring practical wins not seen in asymptotics.  
- Deterministic Select is proof of concept for worst-case safety, but slower than randomized approaches.  

Overall: theory and experiment line up very well, with deviations fully explained by constant factors and hardware effects.

---

## 5. Git Workflow

Branch structure used:

- main → stable tagged releases (`v0.1`, `v1.0`).  
- feature/mergesort, feature/quicksort, feature/select, feature/closest, feature/metrics → development branches per component.  
- docs/report → report and plots.  

Example commit storyline:
init: setup maven, junit5, readme
feat(metrics): depth tracker and counters
feat(mergesort): recursive mergesort with buffer reuse and cutoff
feat(quicksort): randomized pivot, smaller-first recursion
feat(select): median-of-medians selection
feat(closest): closest pair divide-and-conquer
bench: add CLI to run algorithms and write CSV
docs(report): recurrence analysis and plots
release: v1.0
