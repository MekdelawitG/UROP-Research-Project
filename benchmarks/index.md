# Benchmarks

This project evaluates the effectiveness of LLM-generated tests using standardized Java benchmarks from the Defects4J dataset. Each benchmark represents a real-world class with existing human-written test suites, which are used as the baseline for comparison.

Benchmarks are selected across varying levels of complexity to analyze how LLM-generated test performance changes as program complexity increases.

---

## Benchmark Purpose

Benchmarks are used to evaluate:

- Code coverage achieved by LLM-generated tests  
- Fault detection capability using mutation testing  
- The relationship between coverage and mutation score  

Using benchmarks of different complexity allows the study to assess whether LLM-generated tests scale effectively beyond simple programs.

---

## Benchmark Classification

Benchmark difficulty is determined using **Lines of Code (LOC)** as a proxy for complexity.

### Classification Criteria

- **Easy:** ≤ 200 LOC  
- **Medium:** 201 – 400 LOC  
- **Hard:** ≥ 401 LOC  

LOC is used because larger classes typically contain more logic, branches, and edge cases, increasing testing difficulty.

---

## Selected Benchmarks

**Dataset Source:**  
https://github.com/rjust/defects4j

| Class Name        | Project | LOC | Classification | Source |
|------------------|--------|-----|---------------|--------|
| CharUtils        | Lang   | 124 | Easy          | [View](https://github.com/apache/commons-lang) |
| Duration         | Time   | 144 | Easy          | [View](https://github.com/JodaOrg/joda-time) |
| Fraction         | Math   | 310 | Medium        | [View](https://github.com/apache/commons-math) |
| BooleanUtils     | Lang   | 365 | Medium        | [View](https://github.com/apache/commons-lang) |
| Period           | Time   | 478 | Hard          | [View](https://github.com/JodaOrg/joda-time) |
| ArrayRealVector  | Math   | 636 | Hard          | [View](https://github.com/apache/commons-math) |

---

## Benchmark Setup

- All baseline tests are **human-written tests provided by Defects4J**
- These tests are used as the ground truth for comparison
- LLM-generated tests are evaluated against these baselines using the same metrics

---

## Summary

The selected benchmarks provide a balanced distribution of complexity levels, enabling analysis of how LLM-generated test effectiveness changes from simple to more complex codebases. This setup supports a consistent and reproducible evaluation across all experiments.
