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
- [How This Developer Guide Works](#how-this-developer-guide-works)
- [Acknowledgements](#acknowledgements)
- [Setting Up and Getting Started](#setting-up-and-getting-started)
  - [Setting up cOOPer on your computer](#setting-up-cooper-on-your-computer)
  - [Before you code](#before-you-code)
- [Design](#design)
  - [Overview](#overview)
  - [Architecture](#architecture)
  - [Ui component](#ui-component)
  - [Parser component](#parser-component)
  - [Verification component](#verification-component)
  - [Command component](#command-component)
  - [Resources component](#resources-component)
  - [Storage component](#storage-component)
  - [Util component](#util-component)
- [Implementation](#Implementation)
  - [Parsing user input](#parsing-user-input)
  - [Verifying user credentials](#verifying-user-credentials)
  - [Generating a PDF from the financial statement](#generating-a-pdf-from-the-financial-statement)
  - [Saving and loading data](#saving-and-loading-data)
- [Appendix: Requirements](#appendix-requirements)
  - [Product Scope](#product-scope)
    - [Target user profile](#target-user-profile)
    - [Value proposition](#value-proposition)
  - [User Stories](#user-stories)
  - [Non-functional requirements](#non-functional-requirements)
  - [Glossary](#glossary)
- [Appendix: Instructions for manual testing](#appendix-instructions-for-manual-testing)
  - [Launch and Shutdown](#launch-and-shutdown)
  - [Sign-in](#sign-in)

## How This Developer Guide Works

Throughout this developer guide, you will see text formatted differently from normal text, as well as symbols appearing before another line of text.
The table below explains the formatting and symbols in this user guide.

**Formatting/Symbol** | **Meaning**              |
------------|------------------------------------|
*italics* |Text in italics represent technical terms used by developers or terminology specific to using cOOPer.
**bold**|Text in bold emphasizes the text's importance and indicates that you should pay more attention to the text.
`code` |Short lines of text highlighted as such indicate a class, method or component of cOOPer.
ℹ️  |The info symbol indicates useful information about diagrams / content.
💡     |The light bulb symbol indicates a useful tip which eases development of cOOPer.

## Acknowledgements
This section includes the sources of code, documentation and third-party libraries reused / adapted in developing cOOPer.
1. [dopsun chatbot-cli](https://github.com/dopsun/chatbot-cli)
2. [Implementation of `Storage` component](https://github.com/theeugenechong/ip/tree/master/src/main/java/duke/storage)
3. [Implementation of PBKDF2 algorithm for storing passwords](https://www.quickprogrammingtips.com/java/how-to-securely-store-passwords-in-java.html)
4. [Converting input stream to file in `Util.java`](https://www.baeldung.com/convert-input-stream-to-a-file)
5. [Making a POST Request for LaTex PDF Generation](https://www.baeldung.com/httpurlconnection-post)

## Setting Up and Getting Started

> 💡 Here are the software / tools used in developing cOOPer. You are recommended to use them :
> - **IDE**: IntelliJ IDEA (highly recommended)
> - **JDK**: Java 11 
> - **Version control**: Git 
> - **Git GUI**: Sourcetree
> - **Build system**: Gradle

### Setting up cOOPer on your computer
1. Fork [this repo](https://github.com/AY2122S1-CS2113T-W13-4/tp) and clone the fork into your computer.
2. If you are using IntelliJ IDEA, ensure that IntelliJ is configured to use **JDK 11**. You can refer to IntelliJ's own documentation [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) to correctly configure the JDK.
3. Import the project as a Gradle project. You can follow [this guide](https://se-education.org/guides/tutorials/intellijImportGradleProject.html) to find out how to import the project into IntelliJ.
4. Verify the setup by running `cooper.Cooper`
   1. Navigate to `src/main/java/cooper/Cooper.java`
   2. Right click on `Cooper.java` and select 'Run Cooper.main()'.
   3. You should see the following output if the setup was done correctly:
```
            /$$$$$$   /$$$$$$  /$$$$$$$
           /$$__  $$ /$$__  $$| $$__  $$
  /$$$$$$$| $$  \ $$| $$  \ $$| $$  \ $$ /$$$$$$   /$$$$$$
 /$$_____/| $$  | $$| $$  | $$| $$$$$$$//$$__  $$ /$$__  $$
| $$      | $$  | $$| $$  | $$| $$____/| $$$$$$$$| $$  \__/
| $$      | $$  | $$| $$  | $$| $$     | $$_____/| $$
|  $$$$$$$|  $$$$$$/|  $$$$$$/| $$     |  $$$$$$$| $$
 \_______/ \______/  \______/ |__/      \_______/|__/
=========================================================================
Hello I'm cOOPer! Nice to meet you!
=========================================================================
Log in or register to gain access to my features!
To log in, enter "login [yourUsername] pw [password] as [yourRole]".
To register, enter "register [yourUsername] pw [password] as [yourRole]".

To exit, enter "exit".
=========================================================================
>> [Logged out]
```

5. Run `JUnit` tests (optional):
      1. Navigate to `src/test`.
      2. Right click on `test` and select 'Run 'All tests' '.
      3. All the tests should pass, and you should see the following:

<p align="center">
    <img width=350 src="developerGuideDiagrams/junitPassed.png" alt="junitPassed"><br>
</p> 

### Before you code
- **Configure coding style**
  - If you are using IntelliJ IDEA, follow [this guide](https://se-education.org/guides/tutorials/intellijCodeStyle.html) to set up IntelliJ to match our coding style.
- **Set up CI**
  - GitHub automatically detects the GitHub Actions config file located in the `.github/workflows` folder. CI for cOOPer is automatically run at each push to the `master` branch or whenever a pull request is created.
- **Get to know cOOPer's design**
  - One last thing to know before you start coding is cOOPer's overall software design. You are recommended to get some sense of cOOPer's overall design in the [Design](#design) section below.

## Design

> 💡 The diagrams in this document were created using **draw.io**. The `.png` templates used to create the diagrams can be found in the [`developerGuideDiagrams`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/docs/developerGuideDiagrams) folder. 
> To create and edit diagrams, access the draw.io [website](https://app.diagrams.net/), select 'Open Existing Diagram' and open the desired `.png` file. Any changes to the diagram will be saved automatically.

### Overview

cOOPer consists of two main layers: **verification** layer and **features** layer as shown in the diagram below.
cOOPer recognizes different sets of inputs at each layer.

<p align="center">
    <img src="developerGuideDiagrams/layerDiagram.png" alt="layerDiagram"><br>
</p> 

Upon launching the app, the user starts at the **verification** layer where they can only [log in](UserGuide.md#login) or [register](UserGuide.md#user-registration). 
Entering valid credentials will then grant the user access to the **features** layer where they can input commands like `cf` and`available` to use cOOPer's features. 
At this layer, entering the `logout` command will bring the user back to the verification layer.

### Architecture

<p align="center">
    <img src="developerGuideDiagrams/architectureDiagram.png" alt="architectureDiagram"><br>
</p> 

The **Architecture Diagram** above shows the high-level design of cOOPer and how cOOPer's components are connected.

`Cooper` contains the *main* method of the program. `Cooper`'s responsibilities are as such:
- Upon **launching the app**, `Cooper` initializes the components and loads stored user data into the components. 
- While the **app is running**, `Cooper` reads user input which is then processed by the components to produce a result.
- Upon **shutting down the app**, `Cooper` shuts down the components. 

Apart from `Cooper`, the rest of the app consists of these seven components:
- [`Ui`](#ui-component): Handles the reading of user input and printing of messages to the terminal.
- [`Parser`](#parser-component): Interprets and validates user input.
- [`Verification`](#verification-component): Verifies that the user is signing in to cOOPer with valid credentials.
- [`Command`](#command-component): Executes commands which are parsed from user input.
- [`Resources`](#resources-component): Manages cOOPer's data for finance, meetings and forum features while the app is running.
- [`Storage`](#storage-component): Loads data from, and saves data to storage files in the computer hard disk.
- [`Util`](#util-component): Provides utility which help with some of cOOPer's features.

#### Interaction of the architecture components to sign in to a user account
- The *sequence diagram* below shows how cOOPer's components interact with each other when a user enters their **sign in details** for verification.

> ℹ️`userInput` represents the sign in details input by the user for verification. For example, `register John /pw 12345 /as admin`.

<p align="center">
    <img src="developerGuideDiagrams/signInSequenceDiagram.png" alt="signInSequenceDiagram"><br>
</p> 
#### Interaction of the architecture components to process user input


- The sequence diagram below shows how cOOPer's components interact with each other when a user enters a **command** after successfully logging in.

> ℹ️ `userInput` represents a command input by the user. For example, `meetings`.
> ℹ️`XYZCommand` is an object representing a command recognised by cOOPer. For example, `AddCommand`.

<p align="center">
    <img src="developerGuideDiagrams/commandSequenceDiagram.png" alt="commandSequenceDiagram"><br>
</p> 


### Ui Component

**API**: [`Ui.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/ui)

<p align="center">
    <img src="developerGuideDiagrams/uiComponent.png" alt="uiComponent"><br>
</p> 


- The `Ui` component consists of a parent `Ui` class and its subclasses as shown by the class diagram above.
- The parent `Ui` class contains general constants and methods used across cOOPer's components which read user input and print recurring messages.
- On the other hand, the subclasses for the different components in cOOPer (`ABCUi`) contain constants and methods specific to the function of that component. 
For example, `FinanceUi` contains a method `printBalanceSheet()` which prints a balance sheet formatted with headers containing the entries input by the user.
- The classes in Ui have *static* methods so there is no need to create a `Ui` object in `Cooper`.

The `Ui` component:
- Reads in user input from the terminal
- Prints status messages, error messages and messages prompting the user for input
- Is used by `Verification` and `Cooper` mainly for reading input, while it is used by `Resources` mainly for printing output messages

### Parser Component

**API**: [`ParserBase.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/parser)

<p align="center">
    <img src="developerGuideDiagrams/parserComponent.png" alt="parserComponent"><br>
</p>


- The `Parser` component consists of an abstract `ParserBase` class with its children classes, `CommandParser` and `SignInDetailsParser`. 
- To emphasize the different [layers](#overview) of cOOPer and to improve *cohesiveness*, different types of objects are constructed from user input at different layers. 
User input at the *verification layer* will be parsed to construct a `SignInProtocol` object while user input at the *features layer* will be parsed to construct a `Command` object. 
- The `SignInProtocol` object executes the signing in of the user with details provided while the `Command` object executes the command input by the user.
- `ParserBase` contains a reference to the `Parser` *interface* from the [dopsun chatbot-cli](https://github.com/dopsun/chatbot-cli) library used by cOOPer. 
More information about cOOPer's implementation of the library can be found [here](#parsing-user-input).

The `Parser`component:
- Constructs and returns a new `SignInProtocol`/`Command` object with the correct parsed attributes
- Determines if user input corresponds to any of the commands recognised by cOOPer
- Validates user input by checking for erroneous command arguments. For example, empty command arguments or entering alphabets where an integer is expected
- Does not perform any printing of error messages, but instead throws `InvalidCommandFormatException`, `UnrecognisedCommandException` etc. to signal erroneous input

### Verification Component

**API**: [`Verifier.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/verification)

<p align="center">
    <img src="developerGuideDiagrams/verificationComponent.png" alt="verificationComponent"><br>
</p>

- The `Verification` component consists of a `Verifier` class which verifies the user's credentials and performs the necessary action in granting access to the user. More on the verification process can be found [here].
- `Cooper` contains a reference to a `Verifier` object.
- The `SignInProtocol` class is an abstract class representing one of the two sign in protocols, `Login` or `Registration`. 
- The `SignInProtocol` class contains a reference to a `SignInDetails` object which as a whole, represents a sign in attempt by the user using one of the two protocols, with the corresponding `SignInDetails`.
For example, a `Login` object containing `SignInDetailsX` represents the user's login attempt with the details `SignInDetailsX`.
- `Login` and `Registration` override `SignInProtocol`'s abstract method, `executeSignIn()` as there are different conditions to check depending on whether the user is trying to log in or register.

The `Verification` component:
- Verifies that the user is signing in to cOOPer with the correct credentials
- Grants the user access to the _features layer_ if the user's credentials are valid. A user with valid credentials means the user is logging in to cOOPer with the same username, password and role they registered with

### Command Component

**API**: [`Command.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/blob/master/src/main/java/cooper/command/Command.java)

<p align="center">
    <img src="developerGuideDiagrams/commandComponent.png" alt="commandComponent"><br>
</p>

- The `Command` component consists of an abstract `Command` class and its subclasses as shown in the diagram above.
- Each subclass (`ABCCommand`) overrides `Command`'s abstract method, `execute()` and has its own unique implementation of the method based on how the command is to be executed.
- This makes the addition of a new command to cOOPer relatively easy. For example, you can define a `HelloCommand` which prints out `Hello world!` by inheriting from `Command` and implementing the `execute()` method as such:

```java
public class HelloCommand extends Command {
	// constructors and attributes for execution of the command are omitted
	public void execute() {
		System.out.println("Hello world!");
	}
}
```

- Some subclasses contain extra attributes which are [parsed](#parser-component) from the arguments in the user input. 
- These attributes aid in the execution of the command.
For example, [`ScheduleCommand`](https://github.com/AY2122S1-CS2113T-W13-4/tp/blob/master/src/main/java/cooper/command/ScheduleCommand.java) contains a `String` representing the meeting name as well as an `ArrayList` representing the users associated with that meeting.
- The `execute()` method takes in a `SignInDetails` object as a parameter. This object represents the sign in details of a user who has successfully signed in to cOOPer. For some of cOOPer's finance features which are only accessible by an _admin_, the `UserRole` attribute of this `SignInDetails` object is checked to grant correct access to the feature.

The `Command` component:
- Executes a command entered by the user.
- May request reference from [`Resources`](#resources-component) component and interface with feature managers depending on the goals of the command.
- Performs the storage of data via the [`Storage`](#storage-component) component if there is any change to the data after the command is executed
- Prints status messages or error messages to the output using the `Ui` component to inform the user of the status of command execution

### Resources Component

**API**: [`Resources.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/resources)

<p align="center">
    <img src="developerGuideDiagrams/resourcesComponent.png" alt="resourcesComponent"><br>
</p>

- The `Storage` component consists of a parent `Storage` class along with its subclasses as shown in the diagram above.
- The `Storage` class contains a `filePath` attribute representing the path of the file where the data is to be stored. It also contains methods common to all its subclasses such as `getScanner()` and `createFileInDirectory()` which aid in the process of writing to and creating the storage file.
- `Cooper` contains a reference to a `StorageManager` object. This `StorageManager` object in turn contains references to each of the subclasses of `Storage`.

The `Storage` component:

- Loads stored user data from the storage file specified by `filePath` into the `Verifier`, `FinanceManager`, `MeetingsManager` and `ForumManager` objects upon launching the app.
- Saves data to the storage file specified by `filePath` from the `Verifier`, `FinanceManager`, `MeetingsManager` and `ForumManager` whenever a change is made to the data in these objects.

ResourcesManager manages access rights to feature managers based on UserRole



#### Finance 

#### Meetings

#### Forum

### Storage Component

**API**: [`Storage.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/storage)

<p align="center">
    <img src="developerGuideDiagrams/storageComponent.png" alt="storageComponent"><br>
</p>

- The `Storage` component consists of a parent `Storage` class along with its subclasses as shown in the diagram above.
- The `Storage` class contains a `filePath` attribute representing the path of the file where the data is to be stored. It also contains methods common to all its subclasses such as `getScanner()` and `createFileInDirectory()` which aid in the process of writing to and creating the storage file.
- `Cooper` contains a reference to a `StorageManager` object. This `StorageManager` object in turn contains references to each of the subclasses of `Storage`.

The `Storage` component:
- Loads stored user data from the storage file specified by `filePath` into the `Verifier`, `FinanceManager`, `MeetingsManager` and `ForumManager` objects upon launching the app.
- Saves data to the storage file specified by `filePath` from the `Verifier`, `FinanceManager`, `MeetingsManager` and `ForumManager` whenever a change is made to the data in these objects.
> We do not put `Storage` class under `Resources` for 2 reasons:
>
> 1. `Storage` class is cOOPer's internal construct for bookkeeping various internal data structures and recover them at startup. This does not categorise under any features user can interact with and hence should not be kept under `ResourcesManager`.
> 2. `Storage` has super priviledges to access internal data structures of all feature components. This contradicts the goal of `ResourcesManager` which is to manage access rights to different features depending on user roles, and hence should be kept separate from it.

### Util Component

**API**: [`Util.java`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/util/Util.java)

- The `Util` component is a component unrelated to cOOPer and serves mainly as a helper component to make some of cOOPer's features possible.
- There are only two methods in the `Util` class, namely `inputStreamToTmpFile()` and `inputStreamToString()`. 
- `inputStreamToTmpFile()` is used to recreate the dopsun chatbot-cli's training files (originally located in `src/main/resources`). 
In the process of packaging cOOPer into a JAR application, these training files are converted to bytes which are unable to be read in by the chatbot API. Hence, there is a need to recreate these files for the chatbot to work.
- `inputStreamToString()` is used for cOOPer's [`generate`](UserGuide.md#generating-a-pdf-from-the-financial-statement--generate) feature which allows the user to generate a PDF file from data in cOOPer's balance sheet or cash flow statement. 
This method is used to convert the `.tex` template files into a `String` object which can then be handled easily in the code. More details of the implementation can be found [here](#generating-a-pdf-from-the-financial-statement).

## Implementation

### Parsing user input

cOOPer uses the [dopsun chatbot-cli](https://github.com/dopsun/chatbot-cli) library as its frontend parser that allows you to define any arbitrary *input schema* under `src/main/resources/parser/command-data.properties` 
such as 

```
login = login ${username-hint} /pw ${password-hint} /as ${role-hint}
```

The chatbot's `Parser` library automatically parses the place-holders defined with `$` leaders to strings. For example, `login Yvonne /pw 12345 /as admin` will be parsed 
into the following fields:

```python
{ "username-hint" : Yvonne,
  "password-hint" : 12345,
  "role-hint" : admin }
```

This gives great flexibility and extensibility to the `Parser` component as you do not need to worry about writing new parsing schemes for every command 
and adding new commands to cOOPer for new features become trivial.

### Meetings
`Meetings` provides features like **declaring** availability, **viewing** availability, **scheduling** meetings, and **viewing** user-specific scheduled meetings.

#### Meeting module descriptions
`MeetingManager` stores **2** attributes:
1. the **timings** along with the **usernames** of the available users, which is a `TreeMap<LocalTime, ArrayList<String>>` object,
2. the **list of meetings** scheduled, which is an `ArrayList<Meeting>` object.

The `ArrayList<Meeting>` object stores 0 or more `Meeting` objects

Meeting object stores 3 attributes:
1. the `meetingName`, which is a `String` object,
2. the `time`, which is a `LocalTime` object,
3. the `listOfAttendees`, which is an `ArrayList<String>` object

When the user wants to enter an availability, `MeetingManager` will check if the time entered is in the **correct format** and if the user has **not already entered their availability to that time**. Addition of availability is successful only if those two requirements are satisfied.

When the user wants to schedule a meeting, `ScheduleCommand` will check if the user has entered a **valid time value**. If so, it will call the `MeetingManager` to run an **auto scheduling** function. If not, it will call the `MeetingManager` to run a **manual scheduling** function.

### Forum 

`Forum` provides features like posting a forum thread, commenting on a post, listing posts. 

#### Forum module descriptions

`ForumManager` stores a list of `ForumPost`objects. Each `ForumPost` object stores a list of `ForumComment` objects. Both `ForumPost` and `ForumComment` need to store two attributes, username and content. When a user wants to delete a post or comment, `ForumManager` will check if the username of the post matches the user performing the action and deletion is successful only if these two are a match.

### Resources

`Resources` manages other manager components like the `FinanceManager`, `MeetingsManager` and `ForumManager`.

#### Resources module descriptions

`ResourcesManager` grants reference to other manager modules for different `Command` objects to perform their execution functions  by checking the `UserRole`. For example, 

```java 
FinanceManager financeManager = resourcesManager.getFinanceManager(userRole);
```

will return a `FinanceManager` object only if `userRole` is an `admin`. Otherwise, `null` will be returned indicating the user does not have the access right to that module.

### Verifying user credentials
The `Verifier` class facilitates the verification of the credentials of a user registering or logging in to cOOPer. 

Different conditions are checked depending on whether a user is trying to log in or register. For example, if a user is trying to register, cOOPer will check if the username is already registered and asks the user to log in if they are not registered yet.
On the other hand, if an unregistered user is trying to log in, cOOPer will ask the user to register first.

For a registered user trying to log in, cOOPer will first check if the entered password is correct. This is done with the help of the `PasswordHasher` class which hashes the entered password with the user's salt stored by cOOPer. The hash obtained will then be compared to the user's stored hash to determine if the entered password is correct. 

If the password is correct, the user's role will then be checked to determine if they are logging in with the role they registered with.

#### Registering a user
The following sequence diagram shows the detailed process of registering a user. `userInput` is `register John /pw 123 /as admin`.

<p align="center">
    <img src="developerGuideDiagrams/registrationSequenceDiagram.png" alt="registrationSequenceDiagram"><br>
</p>

The `SignInDetailsParser` constructs a `SignInDetails` object parsed from the arguments in `userInput`. This `SignInDetails` object is then used to construct a `Registration` object which executes the registration of the user.

<p align="center">
    <img src="developerGuideDiagrams/refFrameRegistration.png" alt="refFrameSequenceDiagram"><br>
</p>

#### Logging in
Assuming that the above registration has taken place successfully, the following sequence diagram shows the login process of the user. `userInput` is `login John /pw 123 /as admin`.

<p align="center">
    <img src="developerGuideDiagrams/loginSequenceDiagram.png" alt="loginSequenceDiagram"><br>
</p>

### Generating a PDF from the financial statement
The [`PdfGenerator`](https://github.com/AY2122S1-CS2113T-W13-4/tp/blob/master/src/main/java/cooper/finance/pdfgenerator/PdfGenerator.java) abstract class is responsible for the generation of the financial statement as a PDF via the `generate` command. It is inherited by the subclasses, `BalanceSheetGenerator` and `CashFlowStatementGenerator`, with each subclass containing different methods to add different sections to the PDF generated.

#### Creating the PDF with LaTeX
The PDF is generated with the help of an online LaTeX compiler. The LaTeX (`.tex`) templates for the PDF can be found under `src/main/resources/pdf`. The `PdfGenerator` class employs the use of the `inputStreamToString()` method of the [`Util`](#util-component) component to convert the contents of these LaTeX templates into a `String` object. The LaTeX template, which is now a `String` is then manipulated by calling Java `String` methods like `replace()` and `append()`. 
Certain identifiers (in the form of LaTeX comments '`%`') in the LaTeX template will be replaced by the actual values of cOOPer's financial statement.

The example below shows the template of an entry in the financial statement:

```
\centering
% {Description}
& \centering
& \centering
& \centering
& % {Amount}
\\[3ex]
```

Calling `replace("% {Description}", "Depreciation and Amortisation")` and `replace("% {Amount}", 1500)` on the template above will result in the following:

```
\centering
Depreciation and Amortisation
& \centering
& \centering
& \centering
& 1500
\\[3ex]
```


When compiled, the LaTeX code above will correspond to an entry 'Depreciation and Amortisation' on the PDF with the amount $1500. This technique can be used on the header and summary templates which will format the header and summary of a particular section in the financial statement.

The methods `createHeader()`, `createEntry()` and `createSummary()` in `PdfGenerator` perform the text replacement as shown above. The diagram below shows how these methods correspond to the different parts of the 'Operating Activities' section in the cash flow statement PDF.

<p align="center">
    <img width="750" src="developerGuideDiagrams/pdfSections.png" alt="pdfSections"><br>
</p>

#### Compiling the LaTeX code online
`createHeader()`, `createEntry()` and `createSummary()` also add the template to an `ArrayList` after performing the text replacement on the template. Iterating through the `ArrayList`, these templates are then appended together using `append()`.
This forms a long `String` which is then sent to the online LaTeX compiler via a [POST request](https://en.wikipedia.org/wiki/POST_(HTTP)). The reply data obtained from the request is used to construct the PDF via the `write()` method of Java's `FileOutputStream` class.

### Saving and loading data
> ℹ️Due to the way the `Storage` component is implemented, the classes and methods used for storage have names which are quite similar. In order to generalize the explanations in this section for how data is saved and loaded, the term `XYZ` will be used as a placeholder where `XYZ` is `signInDetails`, `balanceSheet`, `cashFlowStatement`, `availability`, `meetings` and `forum`.

The `StorageManager` class facilitates the saving and loading of cOOPer's data to and from its storage files. This data includes the sign in details of registered users (from the [`Verification`](#verification-component) component) , entries of the balance sheet and cash flow statement, list of availabilities, scheduled meetings, and forum posts (all from the [`Resources`](#resources-component) component).
cOOPer's data is stored separately in multiple text files named `XYZ.txt` located in the 'cooperData' folder in the home folder.

#### Saving data
Certain commands, when executed successfully, can change the data in the `Verification` and `Resources` components. (e.g. `register`, `add`, `available`, etc.) Whenever the data in these components change, the command that made the change will call the `saveXYZ()` method of the `StorageManager` to update the storage file with the change. 
For example, when a new availability is added successfully, the method `saveAvailability()` is called by `AvailableCommand` and the storage file is updated with the new list of availabilities.

The following sequence diagram shows the general procedure of saving data to the storage file whenever a change is made.

<p align="center">
    <img src="developerGuideDiagrams/saveDataSequenceDiagram.png" alt="saveDataSequenceDiagram"><br>
</p>

#### Loading data
Data is loaded from cOOPer's storage files to the `Verification` and `Resources` component upon launching the app. The `StorageManager` constructor is first called and each subclass `XYZStorage` is initialized with the file paths of their storage files, `XYZ.txt`.
The `loadAllData()` method of `StorageManager` is then called and this method in turn calls the `loadXYZ()` methods of the `XYZStorage` subclasses. If the storage files are not present upon launching cOOPer, the storage files will be created and any error in file creation will be made known to the user. 
Since data in the storage files are of a specific format, any change to the storage format will throw an `InvalidFileDataException` and a message will be printed for the user specifying the file containing invalid data. 

The following sequence diagram shows the general procedure of loading data from the storage file upon launching cOOPer.

<p align="center">
    <img src="developerGuideDiagrams/loadDataSequenceDiagram.png" alt="loadDataSequenceDiagram"><br>
</p>

#### Storage design considerations
**Current choice**: Individual subclasses which store the sign in details of registered users, entries of the balance sheet and cash flow statement, list of availabilities, scheduled meetings, and forum posts in separate storage files.
- Pros:
  - Easy to implement
  - Efficient (only saves the data changed for a specific component, not all)
  - Extensible (each subclass follows a certain template for storage)
- Cons:
  - Some methods are duplicated (e.g. `saveXYZ()`, `loadXYZ()`, etc.)

## Appendix: Requirements

### Product scope

#### Target user profile

The target user profile of cOOPer consists of all levels of administration in a tech startup, namely from the employee level of Secretary up to the management level of CEO.

Example Users:
* has a need to schedule company meetings in timely manner
* has a need to generate and store financial statements
* has a need to raise timely concerns to management
* has a need to perform reliable company growth projections
* comfortable with typing interactions on a CLI

#### Value proposition

**cOOper's value proposition:** Manage company financials **faster** than typical human accounting means
& manage company communication **more reliably** than a typical GUI driven app.

### User Stories
> 💡 Priorities:<br>
> High (must have) - `***`, Medium (nice to have) - `**`, Low (unlikely to have) - `*`

| Priority | As a ... | I want to ...             | So that I can ...                                           |
| ------- | -------- | ------------------------- | ----------------------------------------------------------- |
| `***`    | new user | see usage 'help' instructions    | refer to them when I forget how to use the application      |
| `***`    | new user     | register an account | login and return to my saved work at any point later on |
| `***`    | user     | see a list of roles at login | login to the specific role I need to carry out a task |
| `***`    | user     | have a password encrypted login | have my saved work be protected from any external tampering |
| `***`    | finance admin     | automatically generate the company's financial statements | assess the company's current financial health accurately and quickly |
| `***`    | secretary employee     | see all company personnel's daily availability | schedule meetings between all available members easily |
| `**`    | finance admin     | automatically generate projections on the company's yearly profitability | assess the company's potential future growth|
| `**`    | employee     | make posts on a company forum | discuss difficulties or interesting developments in the company |
| `**`    | secretary employee     | automatically schedule a meeting without having to know other person's availability| save time on finding an appropriate time to meet  |
| `*`    | user in a hurry     | customise shortcut keys in the app| save time on retrieving the data I desire  |

### Non-Functional Requirements

* Should work on any _mainstream OS_ with Java 11 or above installed.
* Should be able to store up to 100 meetings, forum posts, and financial statements without observing any noticeable sluggishness in performance.
* Should log in and enter user-specific roles faster than traditional web-applications.
* A user with average typing speed should be able to accomplish meeting scheduling and forum posting faster and more reliably using commands than using a mouse interaction GUI driven app.
* A user with average typing speed should also be able to accomplish financial statement creation faster than by human means or a mouse interaction GUI driven app. 

### Glossary

* *mainstream OS* - Windows, OS-X, Linux, Unix

## Appendix: Instructions for manual testing

### Launch and Shutdown
1. Launching cOOPer
   1. Download cOOPer's latest JAR file [here](https://github.com/AY2122S1-CS2113T-W13-4/tp/releases) and copy the JAR file into an empty folder.
   2. Launch the Command Prompt / Terminal from the folder.
   3. Check the Java version being used by entering `java -version`. Ensure that you are using Java 11 or above.
   4. Run `java -jar cOOPer.jar`. <br>
   **Expected output:** cOOPer's greeting message is shown.
2. Exiting cOOPer
   1. Enter `exit`.<br>
   **Expected output:** cOOPer's bye message is shown and the program exits successfully.

### Sign-in
To indicate that the user is not signed in to cOOPer yet, a `[Logged out]` label can be seen beside cOOPer's command prompt as such:

```
>> [Logged out]
```

1. Registering
   1. Ensure that the label beside cOOPer's command prompt shows `[Logged out]`.
   2. Enter `register [username] /pw [password] /as [role]` where `[username]` is your username, `[password]` is your password and `[role]` is one of 'admin' or 'employee'.<br>
   **Expected output:** A message informing you that you have successfully registered is shown.
2. Logging in
   1. Ensure that the label beside cOOPer's command prompt shows `[Logged out]`.
   2. Enter `login [username], /pw [password] /as [role]` where `[username]`, `[password]` and `[role]` are the username, password and role you registered with.<br>
   **Expected output:** A message informing you that you are now successfully logged in is shown. The `[Logged out]` label at the command prompt is no longer present.

### Viewing help
1. Viewing help
   1. Ensure that you are logged in to cOOPer.
   2. Enter `help`.<br>
   **Expected output:** A list of commands specific to your role is shown along with their formats.