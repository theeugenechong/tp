![cOOPer](userGuideImages/cooperLogo.png)

# Developer Guide

## Introducing cOOPer
cOOPer is a **Command Line Interface (CLI) desktop** application developed as a virtual assistant to simplify administrative processes of **tech startups** such as **communication** and **finance management**.

This developer guide is for software designers, developers, and software testers of cOOPer. It will be your reference manual if you are looking to:
- Know more about cOOPer's internal software design
- Improve cOOPer's internal software design
- Add more features to cOOPer
- Enhance cOOPer's existing features
- Perform software testing on cOOPer

## What's in this Developer Guide
- [Acknowledgements](#acknowledgements)
- [Setting Up and Getting Started](#setting-up-and-getting-started)
  - [Setting up cOOPer on your computer](#setting-up-cooper-on-your-computer)
  - [Before you code](#before-you-code)
- [Design](#design)

## Acknowledgements
1. [dopsun chatbot-cli](https://github.com/dopsun/chatbot-cli)
2. [@theeugenechong's implementation of `Storage` for CS2113T's Individual Project (iP)](https://github.com/theeugenechong/ip/tree/master/src/main/java/duke/storage)
3. 

## Setting Up and Getting Started

> #### 💡 Tip:
> Here are the software/tools used in developing cOOPer. You are recommended to use them :
> - **IDE**: IntelliJ IDEA (highly recommended)
> - **JDK**: Java 11 
> - **Version control**: Git 
> - **Git GUI**: Sourcetree
> - **Build system**: Gradle

### Setting up cOOPer on your computer
1. Fork [this repo](https://github.com/AY2122S1-CS2113T-W13-4/tp) and clone the fork into your computer.
2. If you are using IntelliJ IDEA, ensure that IntelliJ is configured to use **JDK 11**. You can refer to IntelliJ's
own documentation [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) to correctly configure the JDK.
3. Import the project as a Gradle project. You can follow [this guide](https://se-education.org/guides/tutorials/intellijImportGradleProject.html) to find out how to import the project into IntelliJ.
4. Verify the setup by:
   1. Running `cooper.Cooper`, logging in and entering a few commands.   
   2. Running `JUnit` tests in `src/test` to ensure they all pass.

### Before you code
- **Configure coding style**
  - If you are using IntelliJ IDEA, follow [this guide](https://se-education.org/guides/tutorials/intellijCodeStyle.html)
  to set up IntelliJ to match our coding style.
- **Set up CI**
  - GitHub automatically detects the GitHub actions config file located in the `.github/workflows` folder. CI for cOOPer is automatically run at each push to the `master` branch or whenever a pull request is created.
- **Get to know cOOPer's design**
  - One last thing to know before you start coding is cOOPer's overall software design. You are recommended to get some sense of cOOPer's overall design in the [Design](#design) section below.

## Design

> #### 💡 Tip:
> The diagrams in this document were created using **draw.io**. The `.png` templates used to create the diagrams
> can be found in the [`developerGuideDiagrams`](developerGuideDiagrams) folder. To create and edit diagrams, access the draw.io [website](https://app.diagrams.net/),
> select 'Open Existing Diagram' and open the desired `.png` file.

### Overview

cOOPer consists of two main layers: **verification** layer and **features** layer as shown in the diagram below.
cOOPer recognizes different sets of inputs at each layer.

![layerDiagram](developerGuideDiagrams/layerDiagram.png)

Upon launching the app, the user starts at the **verification**
layer where they can only [log in](UserGuide.md#login) or [register](UserGuide.md#user-registration). Entering valid
sign in details will then grant the user access to the **features** layer where they can input commands like `cf` and
`available` to use cOOPer's features. At this layer, entering the `logout` command will bring the user back to the
verification layer.

### Architecture

![architectureDiagram](developerGuideDiagrams/architectureDiagram.png)

The **Architecture Diagram** above shows the high-level design of cOOPer and how cOOPer's components are connected.

`Cooper` contains the *main* method of the program. `Cooper`'s responsibilities are as such:
- Upon **launching the app**, `Cooper` initializes the components and loads stored user data into the components. 
- While the **app is running**, `Cooper` reads user input which is then processed by the components to produce 
a result.
- Upon **shutting down the app**, `Cooper` saves any unsaved user data to the hard disk and shuts down the components. 

Apart from `Cooper`, the rest of the app consists of these seven components:
- [`Ui`](#ui-component): Handles the reading of user input and printing of messages to the terminal.
- [`Parser`](#parser-component): Interprets and validates user input.
- [`Verification`](#verification-component): Verifies that the user is signing in to the system with the correct credentials.
- [`Command`](#command-component): Executes commands which are parsed from user input.
- [`Resources`](#resources-component): Manages cOOPer's data for finance, meetings and forum features while the app is running.
- [`Storage`](#storage-component): Loads data from, and saves data to storage files in the computer hard disk.
- [`Util`](#util-component): Unrelated utility methods which help with some of cOOPer's features.

#### Interaction of the architecture components to process user input
- The *sequence diagram* below shows how cOOPer's components interact with each other when a user enters their **sign in 
details** for verification.

> #### 📝 Note:
> `userInput` represents the sign in details input by the user for verification. For example, `register 
> John pw 12345 as admin`.

![signInSequenceDiagram](developerGuideDiagrams/signInSequenceDiagram.png)

- The next sequence diagram below shows how cOOPer's components interact with each other when a user enters a **command** after
successfully logging in.

> #### 📝 Note:
> - `userInput` represents a command input by the user. For example, `meetings`.
> - `XYZCommand` is an object representing a command recognised by cOOPer. For example, `AddCommand`.

![commandSequenceDiagram](developerGuideDiagrams/commandSequenceDiagram.png)

### Ui Component

**API**: [`Ui.java`](../src/main/java/cooper/ui)

![uiComponent](developerGuideDiagrams/uiComponent.png)

- The `Ui` component consists of a parent `Ui` class and its subclasses. Each subclass is responsible for user interaction in different components of cOOPer.
- The reason for creating subclasses which inherit from `Ui` is solely to make the code neater and easier to understand. All the print methods related to a 
certain component wil be found in that component's `Ui` class. For example, `VerificationUi` contains the methods `showInvalidUserRoleError()` and `showIncorrectPasswordError()` 
which improves readability around the codebase.
- The classes in Ui have *static* methods so there is no need to create a `Ui` object in `Cooper`. The reason for this is
that `Cooper` should only have references to objects related to its features (finance, meetings, etc.). The `Ui` class is seen
as a *helper* class which has the sole responsibility of helping cOOPer interact with its user.

The `Ui` component:
- Reads in user input from the terminal
- Prints status messages, error messages and messages prompting the user for input
- Is used by `Verification` and `Cooper` mainly for reading input, while it is used by `Resources` mainly for printing output messages

### Parser Component

**API**: [`ParserBase.java`](../src/main/java/cooper/parser)

![parserComponent](developerGuideDiagrams/parserComponent.png)

- The `Parser` component consists of an abstract `ParserBase` class with its children classes, `CommandParser` and `SignInDetailsParser`. 
To emphasize the different [layers](#overview) of cOOPer and to improve *cohesiveness*, different objects are parsed from user input at each layer.
- User input at the verification layer will be parsed to construct a `SignInProtocol` object while user input at the features layer 
will be parsed to construct a `Command` object. The `SignInProtocol` object executes the signing in of the user with the details provided in the user input 
while the `Command` object executes the command input by the user.
- `ParserBase` contains a reference to a `Parser` *interface* from the [dopsun chatbot-cli](https://github.com/dopsun/chatbot-cli) library
used by cOOPer. More information about how cOOPer implements the library can be found [here](#parser).

The `Parser`component:
- Constructs and returns a new `SignInProtocol`/`Command` object with the correct parsed attributes
- Determines if user input corresponds to any of the commands recognised by cOOPer
- Validates user input by checking for erroneous command arguments. For example, empty command arguments or entering alphabets where an integer 
is expected
- Does not perform any printing of error messages, but instead throws `InvalidCommandFormatException`, `UnrecognisedCommandException` etc.
to signal erroneous input 

### Verification Component

![verificationClassDiagram](developerGuideDiagrams/verificationClassDiagram.png)

### Command Component

### Resources Component

### Storage Component

### Util Component

**API**: [`Util.java`](../src/main/java/cooper/util/Util.java)

- The `Util` component is a component unrelated to cOOPer and serves mainly as a helper component to make some of cOOPer's
features possible.
- There are only two methods in the `Util` class, namely `inputStreamToTmpFile()` and `inputStreamToString()`. 
- `inputStreamToTmpFile()` is used to create the dopsun chatbot-cli's training files for correct parsing.
- `inputStreamToString()` is used for cOOPer's `generate` feature which allows the user to generate a PDF file from data in cOOPer's Balance Sheet or
Cash Flow Statement. This method is used to convert the `.tex` template files into a `String` object which can then be handled easily 
in the code. More details of implementation can be found [here]().
## Implementation

### Parser

`Parser` interprets and validate user inputs and construct objects for various actions such as `Command` and `SignInProtocol` objects.

#### Dopsun chatbot-cli

cOOPer's uses the [dopsun chatbot-cli](https://github.com/dopsun/chatbot-cli) library as its frontend parser that allows developer to define any arbitrary input schema under `src/main/resources/parser/command-data.properties` 
such as 

```
login = login ${username-hint} pw ${password-hint} as ${role-hint}
```

Parser library will then automatically parse the place-holders defined with `$` leaders to strings. For example `login Yvonne pw 12345 as admin` will be parsed 
into the following fields:

```python
{ "username-hint" : Yvonne,
  "password-hint" : 12345,
  "role-hint" : admin }
```

This gives us great flexibility and extensibility to the parser module as developers do not need to worry about writing new parsing scheme for every instruction and adding new instructions to frontend become trivial.

#### Parser module descriptions

cOOPer has two main parsers. `CommandParser` and `SignInDetailsParser` which are both inherited from `ParserBase` abstract class and are required to override abstract base function `parseInput(String input)`. 

##### CommandParser Class

`CommandParser` class will return a `Command` polymorphic base object which implements the `execute()` function. The details about the `Command` family classes are described in the next section.

##### SignInDetails Class

`SignInDetails` class will return a `SignInProtocol` polymorphic base object. `Login` and `Registration` classes are inherited from the `SignInProtocol` base class.

### Command

`Command` are action objects that implement the `execute()` methods for interacting with different resources such as the `FianceManager`and `MeetingsManager`.

#### Command module descriptions

As mentioned above, `CommandParser` returns a `Command` polymorphic base object. Any specialisation of the `Command` base object must implement the`execute()` abstract base method. For example, Developer can add a new command like `HelloCommand` by inheriting from the `Command` base class and implements the `execute()` function to print out `Hello world` as shown below.

```java
public class HelloCommand extends Command {
	// constructors and arguments to execute function are omitted
	public void execute() {
		System.out.println("Hello world");
	}
}
```

This allows developers to inherit any arbitrary number of different command specialisation with different 
behaviours using a unified driver. Developers do not need to modify the frontend to accommodate for every new commands.

### Forum 

`Forum` provides features like posting a forum thread, commenting on a post, listing posts. 

#### Forum module descriptions

`ForumManager` stores a list of `ForumPost`objects. Each `ForumPost` object stores a list of `ForumComment` objects. Both `ForumPost` and `ForumComment` need to store two attributes, username and content. When a user wants to delete a post or comment, `ForumManager` will check if the username of the post matches the user performing the action and deletion is successful only if these two are a match.

### Resources

`Resources` manages other manager modules like the `FinanceManager`, `MeetingsManager` and `ForumManager`.

#### Forum module descriptions

`ForumManager` grants reference to other manager modules for different `Command` objects to perform their execution functions  by checking the `UserRole`. For example, 

```java 
FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
```

will return a `FinanceManager` object only if `userRole` is an `admin`. Otherwise, `null` will be returned indicating the user does not have the access right to that module.

## Product scope

### Target user profile

The target user profile of cOOPer consists of all levels of administration in a tech startup. Namely - from the employee level of Secretary up to the management level of CEO.

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
| ------- | -------- | ------------------------- | ----------------------------------------------------------- |
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing} 
