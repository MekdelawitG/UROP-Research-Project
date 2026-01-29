# Research Papers

This section summarizes key research papers that inform the design and evaluation of this project. 

---

## [TestEval: Benchmarking Large Language Models for Test Case Generation](https://arxiv.org/abs/2406.04531)

**Main idea:**  
TestEval proposes a structured benchmark for evaluating LLM-based test case generation. Rather than relying on arbitrary projects, it emphasizes carefully designed benchmarks aligned with evaluation goals.

**Key takeaways:**
- The effectiveness of LLM-generated tests varies significantly with program complexity.
- Benchmark design strongly influences evaluation outcomes.
- Code coverage alone can be misleading, as tests may execute code without detecting faults.
- Standardized benchmarks are necessary for fair comparison between different LLMs and approaches.

**Relevance to this project:**  
This paper motivates the use of multiple benchmarks of varying complexity (easy, medium, hard) and highlights the limitations of relying solely on coverage metrics when evaluating LLM-generated tests.

---

## [A Review of Large Language Models for Automated Test Case Generation](https://www.mdpi.com/2504-4990/7/3/97)

**Main idea:**  
This survey reviews existing techniques for using LLMs to generate software tests and categorizes approaches based on prompting strategies, feedback loops, fine-tuning, and hybrid methods.

**Key takeaways:**
- LLMs can generate readable and human-like tests, but results are inconsistent.
- Test quality depends heavily on prompt design, context provided, and program complexity.
- Hybrid approaches that combine LLMs with traditional testing techniques often perform better.
- Mutation testing is frequently used as a stronger evaluation metric than code coverage.

**Relevance to this project:**  
This paper supports evaluating LLM-generated tests using mutation testing to assess fault detection capability and provides context for comparing mutation scores with coverage metrics.

---

## Summary
Together, these papers highlight the need for careful evaluation of LLM-generated tests. They motivate the central research question of this project: whether high code coverage achieved by LLM-generated tests corresponds to effective fault detection, particularly across benchmarks of varying complexity.
