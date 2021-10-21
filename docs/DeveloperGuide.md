# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

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
