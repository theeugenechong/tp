![cOOPer](userGuideImages/cooperLogo.png)

# Developer Guide

## Introducing cOOPer
cOOPer is a **Command Line Interface (CLI) desktop** application developed to simplify administrative processes of **tech startups**
such as **communication** and **finance management**.

This developer guide is for software designers, developers, and software testers of cOOPer.

This developer guide will be your reference manual if you are looking to:
- Know more about cOOPer's internal software design
- Improve cOOPer's internal software design
- Extend features provided by cOOPer
- Enhance cOOPer's existing features
- Perform software testing on cOOPer

## What's in this Developer Guide
- [Acknowledgements](#Acknowledgements)
- [Setting Up and Getting Started](#setting-up-and-getting-started)
  - [Setting up cOOPer on your computer](#setting-up-cooper-on-your-computer)
  - [Before you code](#before-you-code)
- [Design](#design)

## Acknowledgements
{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Setting Up and Getting Started

> #### 💡 Tip:
> Here are the software/tools used in developing cOOPer. You are recommended to use them :
> - **IDE**: IntelliJ IDEA (highly recommended)
> - **JDK**: Java 11 
> - **Version control**: Git 
> - **Git GUI**: Sourcetree
> - **Continuous Integration (CI)**: Gradle

### Setting up cOOPer on your computer
1. Fork [this repo](https://github.com/AY2122S1-CS2113T-W13-4/tp) and clone the fork into your computer.
2. If you are using IntelliJ IDEA, ensure that IntelliJ is configured to use **JDK 11**. You can refer to IntelliJ's
own documentation [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) to correctly configure
the JDK.
3. Import the project as a Gradle project. You can follow [this guide](https://se-education.org/guides/tutorials/intellijImportGradleProject.html)
to find out how to import the project into IntelliJ.
4. Verify the setup by:
   1. Running `seedu.cooper.Cooper` and entering a few commands.
   2. Running tests to ensure they all pass.

### Before you code
- **Configure coding style**
  - If you are using IntelliJ IDEA, follow [this guide](https://se-education.org/guides/tutorials/intellijCodeStyle.html)
  to set up IntelliJ to match our coding style.
- **Set up CI**
  - GitHub automatically detects the GitHub actions config file located in the `.github/workflows` folder. CI for cOOPer
  is automatically run at each push to the `master` branch or whenever a pull request is created.
- **Get to know cOOPer's design**
  - One last thing to know before you start coding is cOOPer's overall software design. You are recommended to get some
  sense of cOOPer's overall design in the [Design](#design) section below.

## Design 

### Architecture

![architectureDiagram](developerGuideImages/architectureDiagram.png)

The **Architecture Diagram** above shows the high-level design of cOOPer.

`Cooper` contains the *main* method of the program. `Cooper`'s responsibilities are as such:
- Upon **launching the app**, `Cooper` initializes the components and loads stored user data into the components. 
- While the **app is running**, `Cooper` reads user input which is then processed by the components to produce 
a result.
- Upon **shutting down the app**, `Cooper` saves any unsaved user data to the hard disk and shuts down the components. 

Apart from `Cooper` the rest of the app consists of these seven components:
- `Ui`: Handles the reading of user input and printing of messages to the terminal.
- `Parser`: Interprets and validates user input.
- `Verification`: Verifies that the user is signing in to the system with the correct credentials.
- `Command`: Executes commands which are parsed from user input.
- `Finance`: Stores finance-related data of the app.
- `Meetings`: Stores data of the app needed for scheduling meetings among users.
- `Storage`: Loads data from, and saves data to storage files in the computer hard disk.

#### Interaction of the architecture components to process user input
1. The *sequence diagram* below shows how cOOPer's components interact with each other when a user enters their **sign in 
details** for verification.

> #### 📝 Note:
> `userInput` represents the sign in details input by the user for verification. For example, `register 
> John pw 12345 as admin`.

![signInSequenceDiagram](developerGuideImages/signInSequenceDiagram.png)

2. The next sequence diagram below shows how cOOPer's components interact with each other when a user enters a **command** after
successfully logging in.

> #### 📝 Note:
> `userInput` represents a command input by the user. For example, `meetings`.

![commandSequenceDiagram](developerGuideImages/commandSequenceDiagram.png)

## Implementation

### Parser

#### Dopsun chatbot-cli

cOOPer's parser uses the `dopsun chatbot-cli` library that allows developer to 
define any arbitrary input schema under `src/main/resources/parser/command-data.properties` 
such as `login = login ${username-hint} pw ${password-hint} as ${role-hint}`. 
Parser library will then automatically parse the place-holders defined in the form
`${hint}` to strings. For example `login Yvonne pw 12345 as admin` will be parsed 
into the following fields:

```
{ "username-hint" : Yvonne,
  "password-hint" : 12345,
  "role-hint" : admin }
```

This gives us great flexibility and extensibility to the parser module as developers
do not need to worry about writing new parsing routine for every instruction and adding 
new instructions to frontend become trivial.

#### Parser software descriptions

cOOPer has two main parsers. `CommandParser` and `SignInDetailsParser` which are 
both inherited from `ParserBase` abstract class and are required to override abstract
base function `parseInput(String input)`. 

##### CommandParser Class

`CommandParser` class will return a `Command` polymorphic base object which 
implements the `execute()` function. The details about the `Command` family classes
are described in the next section.

##### SignInDetails Class

`SignInDetails` class will return a `SignInProtocol` polymorphic base object. `Login` 
and `Registration` classes are inherited from the `SignInProtocol` base class.

### Command

#### Command software descriptions

As mentioned above, `CommandParser` class with return a `Command` polymorphic 
base object. Any specialisation of the `Command` base object must implement the
`execute()` abstract base method. For example, Developer can add a new command 
like `HelloCommand` by inheriting from the `Command` base class and implements 
the `execute()` function to print out `hello world`. This allows developers to 
inherit any arbitrary number of different command specialisation with different 
behaviours using a unified driver.

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
