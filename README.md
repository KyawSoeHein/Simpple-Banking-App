# Simpple-Banking-App

How to run the project?
    - Please run the project in intelliJ using java version 21 and gradle version 8.8.

Note from me
    - For this project, I decided to use static methods and variables instead of using dependency injection.
    - The reason is I feel like installing spring is overkill for this project and due to time constraints, I didn't 
        have time to test other dependency injection libraries.
    - I understand that statics are hard to mock during testing but I covered most of the logics.
    - I neglected writing test cases for unnecessary classes like pages from ui.
    - For interest rule, I wasn't clear on how to calculate, so I calculate with my own rules
        - There is a new option to calculate interests for all accounts
        - When user input that option, I check what is the interest rule I should pick up
            - I pick up the rule which was defined before transaction date
            - if no rule to pick, I default to 1% rule
        - For number of days, 
            - I try to calculate days difference between current picked up interest rule and next rule
            - again, I try to calculate days difference between current picked up transaction and next transaction
            - Between these two day differences, I pick up the smaller one to input inside formula
            - if either of day difference is 0, I pick up non-zero day difference
        - After that, I do interest calculation

