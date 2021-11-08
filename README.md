![cOOPer](docs/userGuideImages/cooperLogo.png)

# What is cOOPer?

cOOPer is a **desktop application** developed to simplify administrative processes of **tech startups** such as **communication** and **finance management**.

If you are running a tech startup, cOOPer can help you with **accounting** and **communicative** tasks like:

- **Generating** your company's **financial statements**
- **Forecasting** your company's **cash flow**
- **Scheduling meetings** with your employees
- Having **discussions on forum** with your colleagues

## Setting up cOOPer on your computer

> 💡 Here are the **software / tools** used in developing cOOPer. You are recommended to use them :
>
> + _**IDE**_: IntelliJ IDEA (highly recommended)
> + _**JDK**_: Java 11 
> + **Version control**: Git 
> + **Git GUI**: Sourcetree
> + **Build system**: Gradle

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
To log in, enter "login [yourUsername] /pw [password] /as [yourRole]".
To register, enter "register [yourUsername] /pw [password] /as [yourRole]".

To exit, enter "exit".
=========================================================================
>> [Logged out]
```

5. Build automation using Gradle

* This project uses Gradle for build automation and dependency management. It includes a basic build script as well (i.e. the `build.gradle` file).
* If you are new to Gradle, refer to the [Gradle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/gradle.html).

## Testing

### JUnit tests

* Run JUnit tests:
  1. Navigate to `src/test`.
  2. Right click on `test` and select 'Run 'All tests' '.
  3. All the tests should pass, and you should see the following:

<p align="center">
    <img width=250 src="docs/developerGuideDiagrams/junitPassed.png" alt="junitPassed"><br>
</p> 



## Checkstyle

* A sample CheckStyle rule configuration is provided in this project.
* If you are new to Checkstyle, refer to the [Checkstyle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/checkstyle.html).

## CI using GitHub Actions

The project uses [GitHub actions](https://github.com/features/actions) for CI. When you push a commit to this repo or PR against it, GitHub actions will run automatically to build and verify the code as updated by the commit/PR.

## Documentation

* For users, refer to cOOPer's [User Guide](UserGuide.md) 
* For developers, refer to our [Developer Guide](DeveloperGuide.md)
* To know more about the team of developers, click [here](AboutUs.md)