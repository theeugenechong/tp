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
- Documented the creating of the cash flow statement: `cf` → `add` section of the guide - Similar to the balance sheet, these two commands were explained in a pair for better understandability.
- Documented the accessing of the balance sheet `bs` → `list` section of the guide - as I implemented both of these functions, I documented their usage in the guide, explained in a pair for the same reasons.
- Documented the accessing of the cash flow statement `cf` → `list` section of the guide - as I implemented both of these functions, I documented their usage in the guide, explained in a pair for the same reasons.
- Documented the projecting of free cash flow `proj` section in the guide. I implemented this functionality, and hence provided the relevant insights and examples in the guide, to assist the user in using the function effectively.

<div style="page-break-after: always;"></div>

### Contributions to the Developer Guide
- Documented the `Finance` component - created the class diagram and explanations of the design of the finance component.
- Documented the "Interacting wih finance functions" section - Created the class diagram and explanations for the implementation of these features.
- Documented the "Product Scope" section - came up with the target user profile, the value proposition, the user stories, and the non-functional requirements sections of the guide.

### Contribution to Team-based Tasks
- Created the cOOPer logo used in all the team's documents.
- Maintained the issue tracker. (added issues, labels, milestones, proper descriptions etc.)
- Resolved merge conflicts when merging multiple pull requests.


### Contributions beyond the project team
- [Reported bugs for the Practical Exam Dry Run](https://github.com/ChrisLangton/ped/issues).

### Review/mentoring contributions
- Pull requests I reviewed:
  - Pull Request [109](#https://github.com/AY2122S1-CS2113T-W13-4/tp/pull/109)
  - Pull Request [267](#https://github.com/AY2122S1-CS2113T-W13-4/tp/pull/267)
- Reviewed the content and formatting of both the User Guide and Developer Guide.
- Conducted a detailed grammar and English [review](#https://github.com/AY2122S1-CS2113T-W13-4/tp/pull/258) of the User guide

<div style="page-break-after: always;"></div>

## [Optional] Contribution to the User Guide

Here is an extract of one of my contributions to the User Guide:

> ### Creating the balance sheet: `bs` → `add`
>- Fills up the different fields of cOOPer's balance sheet such as 'Cash and Cash Equivalents', 'Inventory', 'Accounts Payable', 'Equity Capital', etc.
>- How to create the balance sheet:
>1. Enter `bs`.
>2. cOOPer initiates the balance sheet function and a `[Balance Sheet]` label will appear beside cOOPer's prompt.
>3. cOOPer will prompt you to enter the first entry, 'Cash and Cash Equivalents'.
>4. Enter `add [amount]`.
>5. `[amount]` will be added as 'Cash and Cash Equivalents' as an **asset**.
>6. cOOPer will continue prompting you for the rest of the balance sheet fields.
>7. When the balance sheet is complete, cOOPer will prompt you to enter [`list`](#viewing-the-balance-sheet-bs--list) to view the complete balance sheet.
>> ℹ️`[amount]` must be an **unsigned integer**.<br>
>> ℹ️cOOPer treats `[amount]` as positive and `[amount]` enclosed in parentheses as negative.<br>
>> ℹ️All **assets** are positive, hence no parentheses are needed, while all **liabilities** are negative, hence parentheses are needed. **Shareholder's equity** can be either positive or negative, hence parentheses are optional. To see which fields belong where, see the example output of the[`list`](#viewing-the-balance-sheet-bs--list) feature.<br>
>> ⚠️Any entries **more than** 300 Million SGD (positive or negative) will **not** be added.<br>
>> 💡   Remember to enter `bs` before entering `add` or cOOPer will not know which financial statement to add to.<br>
>> ⚠️Entering `bs` and `add` after the balance sheet is complete will **overwrite** the current fields of the balance sheet one by one, updating the current draft of the balance sheet. Hence, it is important to add the balance sheet fields to **completion**. <br>
>
>
>- Example input for initiating the balance sheet function:
>
>```
> bs
>```
>
>- Expected output:
>
>```
>=========================================================================
>You are now using the Balance Sheet function.
>You can enter 'list' to view the current Balance Sheet or
>start off by entering Cash & Cash Equivalents:
>=========================================================================
>```
>
>- Example input for adding to 'Cash and Cash Equivalents':
>
>```
> [Balance Sheet] add 1500
>```
>
>- Expected output:
>
>```
>=========================================================================
>Success!
>+1500 has been added as Cash and Cash Equivalents
>
>Next, please enter Accounts Receivable
>=========================================================================
>```
>
>[⬆️ Back to top](#whats-in-this-user-guide)

<div style="page-break-after: always;"></div>

## [Optional] Contribution to the Developer Guide

>#### Finance
>
>**API**: [`cooper.finance`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/finance)
>
><p align="center">
>    <img src="developerGuideDiagrams/financeComponent.png" alt="financeComponent"><br>
></p>
>
>+ The `Finance` component contains the `FinanceManager`, `BalanceSheet`, `CashFlow`, and `Projection` classes, as well as the `FinanceCommand` enumeration.
>+ The `FinanceManager` constructs the instances of the `BalanceSheet`, `CashFlow` and `Projection` for use, and contains attributes and methods that aid the related functions.
>+ The `FinanceCommamnd` enum helps the `Parser` to understand what `Finance` function is being used, with three states: `CF`, `BS`, and `IDLE`.
>+ The `Finance` component also contains the `PdfGenerator` class (not shown in the diagram above) for the `generate` feature. More info on this feature can be found [here](#generating-a-pdf-from-the-financial-statement).
>
>The `Finance` component:
>+ Handles adding / listing / generating of balance sheets, cash flow statements, and free cash flow projections.
>+ Assists the parser in identifying which function is being used at any given time.