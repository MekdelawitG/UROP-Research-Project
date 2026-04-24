# Project Timeline

This timeline outlines the completed phases of the research project, from initial planning to final presentation on April 24.

---

## January 12 – January 21  
**Project Kickoff & Literature Review** *(Completed)*  
- Defined research focus on evaluating LLM-generated tests  
- Identified key evaluation metrics: code coverage and mutation testing  
- Reviewed relevant research papers  

**Results**
- Established research direction  
- Selected core references  

---

## January 23 – January 28  
**Project Organization & Planning** *(Completed)*  
- Created GitHub project website  
- Organized documentation (papers, tools, benchmarks, schedule)  
- Refined research questions  

**Results**
- Project website  
- Finalized research questions  

---

## January 29 – February 11  
**Benchmark Selection & Classification** *(Completed)*  
- Selected Java benchmarks from Defects4J  
- Classified benchmarks as easy, medium, and hard based on complexity (LOC)  
- Finalized benchmark list  

**Results**
- Selected benchmark classes (CharUtils, Duration, Fraction, BooleanUtils, Period, ArrayRealVector)  

---

## February 12 – February 18  
**Toolchain Setup & Experiment Design** *(Completed)*  
- Configured JaCoCo for coverage analysis  
- Configured PIT for mutation testing  
- Defined experimental workflow and comparison approach  

**Results**
- Fully working evaluation pipeline  

---

## February 19 – March 4  
**Easy Benchmark Experiments** *(Completed)*  
- Generated LLM-based tests for simple classes  
- Ran baseline (human-written) tests  
- Collected coverage and mutation results  

**Results**
- Initial comparison data  
- Observed similar coverage but weaker mutation scores  

---

## March 5 – March 18  
**Medium Benchmark Experiments** *(Completed)*  
- Conducted experiments on moderately complex classes  
- Compared LLM-generated tests with baseline tests  

**Results**
- Notable drop in mutation performance for LLM tests  
- Increased gap between LLM and human-written tests  

---

## March 19 – April 1  
**Hard Benchmark Experiments** *(Completed)*  
- Evaluated complex classes with higher LOC  
- Analyzed scalability of LLM-generated tests  

**Results**
- Significant performance gap in fault detection  
- LLM tests struggled with complex logic and edge cases  

---

## April 2 – April 15  
**Results Analysis & Synthesis** *(Completed)*  
- Compared coverage and mutation results across all benchmarks  
- Identified trends based on complexity  
- Interpreted strengths and limitations  

**Results**
- Key insight: coverage does not guarantee fault detection  
- Clear trend of decreasing effectiveness with complexity  

---

## April 16 – April 23  
**Finalization & Presentation Preparation** *(Completed)*  
- Designed and finalized research poster  
- Summarized results and conclusions  
- Prepared for presentation  

**Results**
- Completed poster  
- Final research summary  

---

## April 24  
**Final Presentation**

---

