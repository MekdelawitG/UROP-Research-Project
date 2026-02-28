# Easy Benchmarks

This section presents experimental results for benchmarks classified as **Easy** based on the  criteria listed under benchmark section:

---

## BooleanUtils

### Benchmark Metadata

- **Class:** `org.apache.commons.lang3.BooleanUtils`
- **Lines of Code (LOC):** 360  
- **Number of Methods:** 54  
- **Mutation Operators (PIT):** 214 
- **Classification:** Easy  

---

### Prompt Used

<details>
<summary>Click to view prompt</summary>

You are generating JUnit 5 unit tests for the Apache Commons Lang class:
org.apache.commons.lang3.BooleanUtils

Research Context:
This test suite will be evaluated using mutation testing (PIT) and line coverage. 
The goal is to generate a strong, independent unit test suite based only on the source code below.

Constraints:
1. Use JUnit 5 only (org.junit.jupiter.api.*).
2. Do NOT use Mockito, EasyMock, or any external libraries.
3. Tests must be deterministic (no randomness, no time-based logic, no file/network I/O).
4. Every test must include meaningful assertions.
5. Cover:
   - Normal behavior
   - Edge cases (null inputs, empty arrays, single-element arrays)
   - Exception behavior (methods that throw IllegalArgumentException or NullPointerException)
6. Do NOT reference or assume any existing human-written tests.
7. Keep the suite focused and non-redundant (approximately 25–60 well-designed tests).
8. The test class must compile inside a Maven project where BooleanUtils already exists.

Helper Class Note:
BooleanUtils references NumberUtils.INTEGER_ONE and NumberUtils.INTEGER_ZERO.

You may assume:
NumberUtils.INTEGER_ONE == Integer.valueOf(1)
NumberUtils.INTEGER_ZERO == Integer.valueOf(0)

Output Format:
Step 1: Output a checklist of behaviors and edge cases to test, grouped by method.
Step 2: Then output ONLY the Java code for a single JUnit 5 test class:
Package: org.apache.commons.lang3
Class name: BooleanUtilsLLMTest
Do not include explanations after the Java code.
Here is the full source file: (here the full BooleanUtils.java was attached) /*

</details>

---

### LLM-Generated Test Code

- [BooleanUtilsLLMTest.java](link-to-your-generated-java-file)

---

### Results

| Test Suite       | Line Coverage | Mutation Coverage | Test Strength |
|------------------|--------------|------------------|--------------|
| LLM-Generated    | 67%          | 54%              | 95%          |
| Human-Written    | 99%          | 99%              | 100%         |

---

### Observations

- The LLM-generated test suite achieved moderate line coverage (67%) and lower mutation coverage (54%), suggesting that many logical paths and edge cases were not fully exercised.
- The mutation gap (45%) is significantly larger than the coverage gap (32%), indicating that simply executing lines of code does not guarantee strong fault detection.


---

## ObjectUtils

### Benchmark Metadata

- **Class:** `org.apache.commons.lang3.ObjectUtils`
- **Lines of Code (LOC):** 360  
- **Number of Methods:** 54  
- **Mutation Operators (PIT):** 137  
- **Classification:** Easy  

---

### Prompt Used

<details>
<summary>Click to view prompt</summary>

You are generating JUnit 5 unit tests for the Apache Commons Lang class:
org.apache.commons.lang3.ObjectUtils

Research Context:
This test suite will be evaluated using mutation testing (PIT) and line coverage. 
The goal is to generate a strong, independent unit test suite based only on the source code below.

Constraints:
1. Use JUnit 5 only (org.junit.jupiter.api.*).
2. Do NOT use Mockito, EasyMock, or any external libraries.
3. Tests must be deterministic (no randomness, no time-based logic, no file/network I/O).
4. Every test must include meaningful assertions.
5. Cover:
   - Normal behavior
   - Edge cases (null inputs, empty arrays, single-element arrays)
   - Exception behavior (methods that throw IllegalArgumentException or NullPointerException)
6. Do NOT reference or assume any existing human-written tests.
7. Keep the suite focused and non-redundant (approximately 25–60 well-designed tests).
8. The test class must compile inside a Maven project where ObjectUtils already exists.

Output Format:
Step 1: Output a checklist of behaviors and edge cases to test, grouped by method.
Step 2: Then output ONLY the Java code for a single JUnit 5 test class:
        Package: org.apache.commons.lang3
        Class name: ObjectUtilsLLMTest
Do not include explanations after the Java code.
Here is the full source file: (here the full ObjectUtils.java was attached) /*

</details>

---

### LLM-Generated Test Code

- [ObjectUtilsLLMTest.java](link-to-your-generated-java-file)

---

### Results

| Test Suite       | Line Coverage | Mutation Coverage | Test Strength |
|------------------|--------------|------------------|--------------|
| LLM-Generated   | 74%          | 61%              | 82%          |
| Human-Written   | 99%          | 86%              | 87%          |

---

### Observations

- Human-written tests achieved significantly higher mutation coverage (+25%).
- Although LLM tests exercised a substantial portion of the code (74% line coverage), they failed to kill a significant number of mutants.
- The mutation gap is larger than the coverage gap, suggesting weaker strength in LLM-generated tests.
- Human tests more thoroughly exercised exception paths, boundary conditions, and null-handling behavior.

---

## Preliminary Easy-Level Findings

Across Easy benchmarks:

- LLM-generated tests achieve moderate line coverage.
- Mutation coverage consistently lags behind human-written tests.

These findings suggest that while LLMs can generate syntactically correct and partially effective unit tests, they do not yet match human-written suites in fault detection capability.
