![cOOPer](userGuideImages/cooperLogo.png)

# User Guide

## Introducing cOOPer

cOOPer is a **desktop application** developed to simplify administrative processes of **tech startups**
such as **communication** and **finance management**.

If you are running a tech startup, cOOPer can help you with **accounting**
and **communicative** tasks like:

- **Generating** your company's **balance sheet**
- **Forecasting** your company's **cash flow**
- **Managing** and **generating** your company's **invoices**
- **Scheduling meetings** with your employees
- Having **discussions** with your employees

cOOPer  is optimized for use via a **Command Line Interface (CLI)**, so if you are someone who types fast,
cOOPer can help you manage your startup efficiently.

# What's in This User Guide

- [1. Quick Start](#1-quick-start)
- [2. Setup](#2-setup)
  - [2.1 Roles](#21-roles) 
  - [2.2 Signing in](#22-user-registration)
- [3. Log in](#3-login)
- [4. Features for all users](#4-features-for-all-users)
  - [4.1 Declaring available timings for meetings: `available`](#41-declaring-available-timings-for-meetings-available)
  - [4.2 Viewing meetings: `meetings`](#42-viewing-meetings-meetings)
- [5. Admin Features](#5-admin-features)
  - [5.1 Adding expenses: `add`](#51-adding-expenses-add)
  - [5.2 Generating balance sheet: `list`](#52-generating-balance-sheet-list)
- [6. Employee Features](#6-employee-features)
- [7. Exiting the program](#7-exiting-the-program)
- [8. FAQ](#8-faq)
- [9. Command Summary](#9-command-summary)

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
>
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
>
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
>
> - `[username]` and `[role]` refer to the user's name and role as registered in cOOPer's system.
> - The username you are logging in with is case-sensitive. *e.g.* logging in with the username `sebastian`
>   is not the same as logging in with `Sebastian`.

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

## 4. Features for all users

### 4.1 Declaring available timings for meetings: `available`

- For easier scheduling of meetings, cOOPer has a function to gather availabilities of everybody to find a common time for a meeting.
- How to input your availability:
  1. After [logging in](#3-login) to the system, enter `available [username] at [time]`.
  2. You will now have your name stored under the specified time in the system.

> #### 📝Note:
>
> - `[username]` is the name you wish to enter the availability for. It is preferably your own username.
> - `[time]` has a format of **HH:mm**, in *24-hour clock*. Any other format will **not** be accepted and your availability will not be stored.
> - Duplicate `[username]` in one timeslot will **not** be accepted.

- Example input:

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

### 4.2 Viewing meetings: `meetings`

- To view the table of availabilities after inputting [availabilities](#41-declaring-available-timings-for-meetings-available), cOOPer generates a table to help you visualise the availabilities.
- How to view available timings:
  1. After [logging in](#3-login) to the system, enter `meetings`.
  2. You will now obtain a table with the availabilities entered.

- Example input:

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



### 4.3 Posting to forum: `post add`

+ User can post message to forum for everyone to discuss on certain topics.
+ How to post message to forum:
  1. For example, if you want to post "hello world!" to forum, you can do that after [logging in](#3-login) to the system and enter `post add hello world!`.
  2. You will get a confirmation message indicating if a post is successfully posted or not
+ Example input:

```
>> post add hello world!
```

+ Expected output:

```
=========================================================================
me has just posted to forum:
┌────────────────────────────────────────────────────────────────────┐
|  hello world
└────────────────────────────────────────────────────────────────────┘
=========================================================================
```

### 4.3 Commenting on a post: `post comment`

+ When other users see a post is posted on the forum. They can comment on the post using `post comment <comments> on <post id>`
+ How to post message to forum:
  1. For example, if we want to comment on the previous post in 4.2, we can do `post comment welcome on 1`
  2. You will get a confirmation message indicating if a comment is successful posted or not
+ Example input:

```
>> post comment welcome on 1
```

+ Expected output:

```
=========================================================================
me has just commented on a  post from forum:
┌────────────────────────────────────────────────────────────────────┐
|  hello world
|    ∟  welcome
└────────────────────────────────────────────────────────────────────┘
=========================================================================
```



### 4.3 Listing  forum posts: `post list all`

+ Users are able to list all forum posts currently available using `post list all`. Cooper will list all posts and comments in a hierarchical way.
+ Example input:

```
>> post list all
```

+ Expected output:

```
=========================================================================
Here is the list of forum posts:
┌────────────────────────────────────────────────────────────────────┐
|  1. @me: hello world
|    ∟  1. @me: welcome
|    ∟  2. @he: welcome to CS2113T!
|  2. @me: how do you do?
|    ∟  1. @he: how do you do?
|  3. @he: Hey how are you??
└────────────────────────────────────────────────────────────────────┘
=========================================================================
```

## 5. Admin Features 

### 5.1 Adding expenses: `add`

- Adds your company's expenses to a balance sheet.
- How to add an expense:
  1. After [logging in](#3-login) to the system, enter `add [amount]`.
  2. The expense will be added to the balance sheet.

> #### 📝Note:
>
> - By default, cOOPer treats `[amount]` as an inflow. To specify an outflow, a pair of parentheses should
>   be added around `[amount]`. *e.g.* `add 5000` specifies an inflow of $5000 while `add (5000)` specifies an outflow of $5000.
> - `[amount]` should be a **positive integer** representing the amount of inflow/outflow added to the balance sheet.

- Example input for inflow:

```
>> add 5000
```

- Expected output:

```
=========================================================================
Success!
Amount: +5000 has been added to the Balance Sheet.
=========================================================================
```

- Example input for outflow:

```
>> add (5000)
```

- Expected output:

```
=========================================================================
Success!
Amount: -5000 has been added to the Balance Sheet.
=========================================================================
```

### 5.2 Generating balance sheet: `list`

- Prints your company's current balance sheet along with details of each expense and your current balance.

- Example input:

```
>> list
```

- Expected output:

```
=========================================================================
This is the company's current Balance Sheet:
1. inflow of: 5000
2. outflow of: -5000

Current balance: 0
=========================================================================
```

## 6. Employee Features

- As of v1.0, cOOPer does not have features exclusive to employees yet, there will be more to come in future versions!

## 7. Exiting the program

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

## 8. FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Here are the steps:

1. [Download](https://github.com/AY2122S1-CS2113T-W13-4/tp/releases) cOOPer in the other computer. 
2. In the old computer, you should see a folder named `tmp` in cOOPer's home folder. Refer to [Quick Start](#1-quick-start) if you do not 
   know what the *home folder* is.
3. Copy the folder over to cOOPer's home folder in the other computer. 
4. Running cOOPer should load your saved data.

## 9. Command Summary

| **Command** | **Format**                       | **Example**                    |
| ----------- | -------------------------------- | ------------------------------ |
| add         | `add [amount]`                   | `add 5000` or `add (5000)`     |
| list        | `list`                           | `list`                         |
| available   | `available [username] at [time]` | `available Sebastian at 10:00` |
| meetings    | `meetings`                       | `meetings`                     |#
