# Simple Banking App

## How to Run the Project
To run this project, follow these steps:

1. Ensure you have **Java 21** installed.
2. Ensure you have **Gradle 8.8** installed.
3. Open the project in **IntelliJ IDEA**.
4. Run the project using IntelliJ's **Run** configuration.

## Notes from the Developer
### Design Decisions
- For this project, I decided to use **static methods and variables** instead of **dependency injection**.
- Installing **Spring** felt like overkill for this project due to its simplicity and time constraints.
- I did not explore other **dependency injection** libraries due to limited time.
- I understand that **static methods** can be hard to mock in tests, but I have covered most of the logic in test cases.
- I omitted test cases for **unnecessary classes**, such as UI-related pages.

### Interest Rule Calculation
Since the interest calculation method was not clearly specified, I followed my own approach:

1. A new option is available to **calculate interest for all accounts**.
2. When the user selects this option, the system determines the **applicable interest rule**:
    - The rule that was **defined before the transaction date** is selected.
    - If no applicable rule is found, a **default interest rate of 1%** is used.
3. The number of days for interest calculation is determined as follows:
    - The difference in days between the **current interest rule and the next rule** is calculated.
    - The difference in days between the **current transaction and the next transaction** is calculated.
    - The **smaller** of these two values is used in the interest formula.
    - If either of the two values is **0**, the **non-zero value** is used.
4. The final interest is calculated based on these values.

## License
This project is open-source and free to use. Contributions are welcome!

