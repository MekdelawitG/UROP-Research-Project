# Results

This section presents the comparison between LLM-generated tests and human-written benchmark tests using code coverage and mutation testing.

---

## Evaluation Metrics

- **Line Coverage (JaCoCo):** Measures how much of the code is executed  
- **Mutation Coverage (PIT):** Measures how effectively tests detect faults  

---

## Detailed Results (Per Benchmark)

| Benchmark        | Complexity | Human Coverage | LLM Coverage | Human Mutation | LLM Mutation |
|-----------------|-----------|---------------|-------------|---------------|-------------|
| CharUtils       | Easy      | 100%          | 100%        | 97%           | 94%         |
| Duration        | Easy      | 100%          | 98%         | 100%          | 96%         |
| Fraction        | Medium    | 96%           | 89%         | 81%           | 74%         |
| BooleanUtils    | Medium    | 100%          | 79%         | 99%           | 79%         |
| Period          | Hard      | 99%           | 65%         | 89%           | 50%         |
| ArrayRealVector | Hard      | 89%           | 55%         | 77%           | 47%         |

---

## Key Findings

- LLM-generated tests often achieve **similar line coverage** on simpler classes  
- **Mutation scores are consistently lower**, indicating weaker fault detection  
- The performance gap **increases as program complexity grows**  
- Coverage alone does not reflect true test effectiveness  

---

## Interpretation

Although LLM-generated tests can execute a large portion of the code, they often fail to detect deeper logical faults. This is especially noticeable in more complex classes, where human-written tests demonstrate stronger assertions and better handling of edge cases.

---

## Conclusion

LLM-generated tests can assist developers by generating initial test cases. However, they currently do not match the effectiveness of human-written test suites, particularly for complex software systems.

---

## LLM-Generated Test Suites

The following test suites were generated using a consistent prompt across all benchmarks:

- [CharUtilsLLMTest.java](CharUtilsLLMTest.java)
- [DurationLLMTest.java](DurationLLMTest.java)
- [FractionLLMTest.java](FractionLLMTest.java)
- [BooleanUtilsLLMTest.java](Boolean1UtilsLLMTest.java)
- [PeriodLLMTest.java](PeriodLLMTest.java)
- [ArrayRealVectorLLMTest.java](ArrayRealVectorLLMTest.java)

---

## Prompt Used for Test Generation

A consistent prompt was used across all benchmarks.

generate a comprehensive JUnit 4 test class for the following Java code, specifically optimized to achieve 100% line coverage and a maximum mutation survival score by testing all boundary conditions, mathematical edge cases, and error paths:
The full source code of the target class was appended below this prompt during test generation.

All benchmarks used the same prompt structure, with only the target class and source code changed.

### Requirements:
- Use JUnit 5 only (org.junit.jupiter.api.*)
- Do NOT use external libraries (Mockito, EasyMock, etc.)
- Tests must be deterministic (no randomness, time-based logic, or I/O)
- Include meaningful assertions

### Coverage Goals:
- Normal behavior
- Edge cases (null inputs, empty inputs, boundary values)
- Exception handling

### Constraints:
- Do NOT reference existing human-written tests
- Keep tests non-redundant and well-structured

### Output:
1. List behaviors and edge cases to test
2. Output a single JUnit test class

</details>

All benchmarks used the same prompt structure, with only the target class name modified.

---

## How Results Were Collected

- **Coverage Data:** Extracted from JaCoCo reports (`target/site/jacoco/index.html`)
- **Mutation Data:** Extracted from PIT reports (`target/pit-reports/`)
- Each benchmark was evaluated twice:
  1. Using human-written baseline tests  
  2. Using LLM-generated tests  

---

## Notes

Exact percentages are reported per benchmark to provide a clear, quantitative comparison of test effectiveness. Results consistently show that high coverage does not necessarily imply strong fault detection.
