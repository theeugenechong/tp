# Fan Shixi - Project Portfolio Page

## Overview
cOOPer is a **Command Line Interface (CLI) desktop application** developed to simplify administrative processes of tech startups such as communication and finance management.

cOOPer was developed in Java following an **Object-Oriented Programming (OOP)** paradigm, hence the letters OOP in its name.

### Summary of Contributions

#### Code Contributed
All the code I contributed for this project can be found at this [RepoSense Link](https://nus-cs2113-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-25&tabOpen=true&tabType=authorship&tabAuthor=fansxx&tabRepo=AY2122S1-CS2113T-W13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false).

#### Enhancements Implemented
I was in charge of the availability and meetings component of cOOPer, which I coded from scratch (from the parsing logic to the actual features).

- **Feature 1:** Implemented the `available` and `availability` features
    - This feature allows users to key in their available date and time, as well as view everyone's availability.
    - The `available` feature comes with error-checking, to make sure the time and date entered are of the correct format.
    - Viewing of `availability` generates a neatly formatted table, enhancing the user experience of cOOPer.
    - Date and time are filtered chronologically when the table of availability is generated.

<div style="page-break-after: always;"></div>

- **Feature 2:** Implemented the `schedule` and `meetings` features
    - The `schedule` feature allows admins to schedule meetings with an in-built algorithm, or with a timing manually inputted by the user.
    - The `schedule` feature also comes with error-checking, to make sure the time and date entered are of the correct format.
    - I made the algorithm that finds a common time for the meeting fast and efficient, such that it saves users' time.
    - The `meetings` feature generates a table of meetings customized for the user.

- **Feature 3:** Implemented the storage function for availability and meetings storage
  - I built on the 'template' @theeugenechong came up with.
  - This feature stores `availability` and `meetings` in their own custom format, and reads the storage files when cOOPer starts.
  - This storage feature also does error checking of the `availability.txt` and `meetings.txt` files to make sure the content and times are correctly formatted, before loading into cOOPer.

### Contributions to the User Guide
- **Contribution 1:** Documented the `available` and `availability` commands
  - Provided detailed explanation to how to use these commands
  - Provided example input and output to these commands

- **Contribution 2:** Documented the `schedule` and `meetings` commands
  - Provided detailed explanation to how to use these commands
  - Provided example input and output to these commands

- **Contribution 3:** Added some commands and their examples to the 'Command Summary' section

### Contributions to the Developer Guide
- **Contribution 1:** Documented the `Meetings` component under the 'Design' section 
  - Came up with the class diagrams and explanations about the design of the component.

- **Contribution 2:** Documented the 'Declaring an availability' and 'Scheduling a meeting' features under the 'Implementation' section
  - Came up with explanations and sequence diagrams for the implementation of these features.

- **Contribution 3:** Documented the `available`, `availability`, `schedule`, and `meetings` commands in the 'Instructions for Manual Testing' section
  - Came up with step by step testing and test examples for the commands.

<div style="page-break-after: always;"></div>

### Contribution to Team-based Tasks
- Created tags in the issue tracker
- Helped with the maintenance of the issue tracker (added issues with their respective tags and milestones)
- Resolved merge conflicts when merging multiple pull requests.
- Contributed in team meetings to plan out the tasks to do.

### Review/mentoring contributions
- Pull requests I reviewed:
  1. Pull request [#48](https://github.com/AY2122S1-CS2113T-W13-4/tp/pull/48)
  2. Pull request [#200](https://github.com/AY2122S1-CS2113T-W13-4/tp/pull/200)
- Reviewed the content and formatting of both the User Guide and Developer Guide.

### Contributions beyond the project team
- [Reported bugs for the Practical Exam Dry Run](https://github.com/fansxx/ped/issues).

<div style="page-break-after: always;"></div>

## [Optional] Contribution to the User Guide

Here is an extract of one of my contributions to the User Guide:

> ### Declaring available timing for meetings: `available`
> - Enters your available timing for easier scheduling of meetings.
> - How to declare your available timing:
>  1. Enter `available [date] [time]`.
>  2. You will now have your name stored under the specified time in the system.
>
>> ℹ️`[time]` refers to the **start of the hour** that you are available at. For example, `available 14:00` means that you are available from **14:00** to **14:59**.<br>
>> ⚠️`[date]` has a format of **dd-MM-yyyy**. Any other format will **not** be accepted and your availability will not be stored. <br>
>> ⚠️`[time]` has a format of **HH:mm**, in *24-hour clock*. Any other format will **not** be accepted and your availability will not be stored.<br>
>> ⚠️Duplicate `[username]` in one timeslot will **not** be accepted.
>
> - Example input:
> 
>```
>>> available 08-11-2021 14:00
>```
>
> - Expected output:
>
>```
>=========================================================================
>Success!
>Sebastian's availability has been added to 08-11-2021 14:00
>=========================================================================
>```
>
>[⬆️ Back to top](#whats-in-this-user-guide)

<div style="page-break-after: always;"></div>

## [Optional] Contribution to the Developer Guide

Here is an extract of one of my contributions to the Developer Guide:

> #### Meetings
>
>**API**: [`cooper.meetings`](https://github.com/AY2122S1-CS2113T-W13-4/tp/tree/master/src/main/java/cooper/meetings)
>
><p align="center">
>    <img src="developerGuideDiagrams/meetingsComponent.png" alt="meetingsComponent"><br>
></p>
>
>The `Meetings` component contains the `MeetingManager` and `Meeting` classes.
>
>`MeetingManager` stores **2** attributes:
>1. the **timings** along with the **usernames** of the available users, which is a `TreeMap<LocalTime, ArrayList<String>>` object,
>2. the **list of meetings** scheduled, which is an `ArrayList<Meeting>` object.
>
>The `MeetingManager` constructs the instances of `Meeting`, and stores it as an `ArrayList<Meeting>` in itself.
>
>The `Meetings` component:
>
>+ Handles the **declaration of availability**
>+ Assists in  the **scheduling** of meetings
>+ Lists the current availability and meetings