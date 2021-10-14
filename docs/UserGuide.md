# cOOPer User Guide

## Introducing cOOPer

cOOPer is a **desktop application**, optimized for use via a **Command Line Interface (CLI)**.
cOOPer was developed to simplify administrative processes such as **communication**
and **finance management** of **tech startups**. If you are running a tech startup, cOOPer can help you with **accounting**
and **communicative** tasks like:
- **Generating** your company's **balance sheet**
- **Forecasting** your company's **cash flow**
- **Managing** and **generating** your company's **invoices**
- **Scheduling meetings** with your employees
- Having **discussions** with your employees

## What's in This User Guide

- [1. Quick Start](#1-quick-start)
- [2. Setup](#2-setup)
  - [2.1 Roles](#21-roles) 
  - [2.2 Signing in](#22-user-registration)
- [3. Log in](#3-login)
- [4. Admin Features](#4-admin-features)
  - [4.1 Adding expenses: `add`](#41-adding-expenses-add)
  - [4.2 Generating balance sheet: `list`](#42-generating-balance-sheet-list)
  - [4.3 Declaring available timings for meetings: `available`](#43-declaring-available-timings-for-meetings-available)
  - [4.4 Viewing meetings: `meetings`](#44-viewing-meetings-meetings)
- [5. Employee Features](#5-employee-features)
  - [5.1 Declaring available timings for meetings: `available`](#51-declaring-available-timings-for-meetings-available)
  - [5.2 Viewing meetings: `meetings`](#52-viewing-meetings-meetings)
- [6. Exiting the program](#6-exiting-the-program)
- [7. FAQ](#7-faq)
- [8. Command Summary](#8-command-summary)
  

## 1. Quick Start

1. Ensure that you have Java 11 or above installed on your desktop.
2. Download the latest version of cOOPer [here](https://github.com/AY2122S1-CS2113T-W13-4/tp/releases).
3. Copy the file to an empty folder. This folder will be used as the *home folder* for your cOOPer application.
4. [Open a terminal window](https://www.lifewire.com/open-command-prompt-in-a-folder-5185505)
   in the home folder.
5. Run cOOPer by entering `java -jar cooper.jar`.
6. A greeting from cOOPer should appear as such:

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
Login or register to gain access to my features!
To login, enter "login  [yourUsername] as [yourRole]"
To register, enter "register [yourUsername] as [yourRole]"
=========================================================================
>> 
```

> #### 📝 Note:
> cOOPer's command prompt is displayed as a `>>` in the output. You should not include it when you enter
> subsequent commands.

7. Refer to the [Setup](#2-setup) section for information on how to set up the app upon first-time use.

## 2. Setup

### 2.1 Roles
- There are two main roles you can hold as a user of cOOPer, namely the **admin** role or the **employee** role.

- You are eligible to hold the **admin** role if you hold a high position in the startup. *e.g.* Chief Executive Officer (CEO),
Chief Financial Officer (CFO), Human Resources Manager (HR).

- On the other hand, you hold the **employee** role if you are a basic employee at the startup.

- cOOPer offers tailor-made functions and features specific to your role to ensure the correct level of
administrative access within the company throughout cOOPer's usage.

### 2.2 User Registration
- Upon first-time use of cOOPer, an individual holding the admin role in the startup is in charge of 
registering all the members of the startup with the correct role. 

- Once a member has been registered, they will be able to log in to cOOPer to access its features.

- How to register a user:
  1. When you see the greeting message as shown in the [Quick Start](#1-quick-start) section, enter `register [username]
  as [role]`. 
  2. Upon successful registration, you should see a message informing you of your successful registration.
  3. You can now log in to access cOOPer's features specific to your role. Refer to the [Login](#3-login) section to
  find out more about logging in.

> #### 📝Note:
> `[username]` refers to the user's name, while `[role]` refers to the user's role as determined [here](#21-roles).

- Example input:

```
>> register Sebastian as admin
```

- Expected output:

```
=========================================================================
Sebastian is now successfully registered as an admin!
=========================================================================
```

## 3. Login
- Once you are successfully [registered](#22-user-registration), you can now log in to access cOOPer's features.
- How to log in:
  1. When you see the greeting message as shown in the [Quick Start](#1-quick-start) section, 
  enter `login [username] as [role]`.
  2. You now have access to cOOPer's features specific to your role.

> #### 📝Note:
> - `[username]` and `[role]` refer to the user's name and role as registered in cOOPer's system.
> - The username you are logging in with is case-sensitive. *e.g.* logging in with the username `sebastian`
> is not the same as logging in with `Sebastian`.

- Example input:

```
>> login Sebastian as admin
```

- Expected output:

```
=========================================================================
You are now logged in successfully as Sebastian!
=========================================================================
```

## 4. Admin Features 

### 4.1 Adding expenses: `add`

### 4.2 Generating balance sheet: `list`

### 4.3 Declaring available timings for meetings: `available`

### 4.4 Viewing meetings: `meetings`
{Give detailed description of each feature}

## 5. Employee Features

### 5.1 Declaring available timings for meetings: `available`
- For easier scheduling of meetings, cOOPer has a function to gather availabilities of everybody to find a common time for a meeting.
- How to input your availability:
  1. After [logging in](#3-login) to the system, enter `available [username] at [time]`.
  2. You will now have your name stored under the specified time in the system.

> #### 📝Note:
> - `[username]` is the name you wish to enter the availability for. It is preferably your own username.
> - `[time]` has a format of **HH:mm**, in *24-hour clock*. Any other format will **not** be accepted and your availability will not be stored.

- Expected input:

```
>> available Sebastian at 14:00
```

- Expected output:

```
=========================================================================
Success!
Sebastian's availability has been added to 14:00
=========================================================================
```

### 5.2 Viewing meetings: `meetings`
- To view the table of availabilities after inputting [availabilities](#51-declaring-available-timings-for-meetings-available), cOOPer allows you to visualise the availabilities in a table format.
- How to view available timings:
  1. After [logging in](#3-login) to the system, enter `meetings`.
  2. You will now obtain a table with the availabilities entered.

- Expected input:

```
>> meetings
```

- Expected output:

```
=========================================================================
These are the availabilities:
┌────────────────────────────────────────────────────────────────────┐
│ 10:00 │ Eugene
│ 14:00 │ Sebastian
└────────────────────────────────────────────────────────────────────┘
=========================================================================
```

## 6. Exiting the program
Exits the program.
- Example input:

```
>> exit
```

- Expected output:

```
=========================================================================
Bye, see you next time! :D
=========================================================================
```

## 7. FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## 8. Command Summary

**Command** | **Format** | **Example**
------------|------------|------------

