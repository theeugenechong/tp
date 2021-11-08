# Christopher Langton - Project Portfolio Page

## Overview
cOOPer is a **Command Line Interface (CLI) desktop application** developed to simplify administrative processes of tech startups such as communication and finance management.

cOOPer was developed in Java following an **Object-Oriented Programming (OOP)** paradigm, hence the letters OOP in its name.


### Summary of Contributions

### Code Contributed

All the code I contributed for this project can be found at this [RepoSense link](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle%20dsc&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=ChrisLangton&tabRepo=AY2122S1-CS2113T-W13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false).

### Enhancements Implemented

- Implemented the finance-related features of cOOPer:
  - Accessing and creating a financial statement - The user (must be an admin) can "enter" either of the financial statement functions using its respective command, after which the user is "inside" the function, and is allowed to add to the financial statement field by field until it is complete. The user may "exit" the function any time by using any other command.
  - Listing a financial statement - As long as a financial statement exists, the user can print it to the command line any time using the list function. Complete and incomplete fields alike will be shown to the user for full clarity. The user will be prompted to list once the full financial statement is filled.
  - Projecting free cash flow growth year-over-year - This function relies on the cash flow statement being filled up prior to use. The projection function calculates the percentage increase/decrease in free cash flow compared to the previous year, and then allows the user to project their free cash flow at this same rate up to their chosen year, to see what free cash flow they might have to work with at any given year.
  
### Contributions to the User Guide
- Created our cOOPer logo, that is used at the very top of the guide.
- Documented the creating of the balance sheet: `bs` → `add` section of the guide - As I implemented both of these functions, I documented their usage in the guide. After seeking feedback from my teammates, I decided that combining the two commands in the guide would make for a much more intuitive read (and subsequently experience) for the user.
- Documented the creating of the cash flow statemenet: `cf` → `add` section of the guide - Similar to the balance sheet, these two commands were explained in a pair for better understandability.
- Documented the accessing of the balance sheet `bs` → `list` section of the guide - as I implemented both of these functions, I documented their usage in the guide, explained in a pair for the same reasons.
- Documented the accessing of the cash flow statement `cf` → `list` section of the guide - as I implemented both of these functions, I documented their usage in the guide, explained in a pair for the same reasons.
- Documented the projecting of free cash flow `proj` section in the guide. I implemented this functionality, and hence provided the relevant insights and examples in the guide, to assist the user in using the function effectively.

### Contributions to the Developer Guide
- Documented the `Finance` component - created the class diagram and explanations of the design of the finance component.
- Documented the "Interacting wih finance functions" section - Created the class diagram and explanations for the implementation of these features.
- Documented the "Product Scope" section - came up with the target user profile, the value proposition, the user stories, and the non-functional requirements sections of the guide.

### Contribution to Team-based Tasks
- Created the cOOPer logo used in all the team's documents.
- Maintained the issue tracker. (added issues, labels, milestones, proper descriptions etc.)
