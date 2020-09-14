Code newbies and new open source contributors -- help us build our repository of CockroachDB To-Do apps! 

## The Challenge

Build a To-Do app using CockroachDB and a language/ORM of your choice!

Note: Feel free to contribute your own app even if there's already one for the language of your choice. It's okay to have multiple apps using the same language as long as you don't plagiarize. 

## What is CockroachDB?
CockroachDB is the distributed SQL database built to scale fast, survive anything, and thrive everywhere. Whether you are a single-person startup or a global enterprise, CockroachDB was designed to make managing data easy. To learn more about CockroachDB, see our [website](https://www.cockroachlabs.com/) and [docs](https://www.cockroachlabs.com/docs/stable/).

## How to contribute to this repository

### [Optional] Step 0: Open your first Pull Request

If you are new to open source or need a GitHub refresher, open your first pull request in the [First Contributions repository](https://github.com/firstcontributions/first-contributions).

Note for Hacktoberfest participants: The First Contributions repository is the only educational repository considered valid for Hacktoberfest contributions. Opening a Pull Request for this repository counts towards your Hacktoberfest contributions!

### Step 1: Set up a local version of this repository on your laptop

1. Click **Fork** to [fork this repository](https://guides.github.com/activities/forking/#fork).
2. Clone your forked repository to your laptop:
   1. From your GitHub account page, open the forked repository and click **Code**. Then click the copy-to-clipboard button.
   2. Open a terminal and run the following command: `git clone <url-you-just-copied>`
3. Create a branch named `<programming language>_ToDoApp_<your name>`:
   1. On your laptop, navigate to the repository: `cd cockroachdb-todo-apps`
   2. Create a branch: `git checkout -b <programming language>_ToDoApp_<your name>`
4. Copy the `template_ToDoApp` directory and rename it to `<programming language>_ToDoApp_<your name>`. 
   1. `cp template_ToDoApp <programming language>_ToDoApp_<your name>`
   2. `cd <programming language>_ToDoApp_<your name>` 

### Step 2: Build your CockroachDB to-do app

1. [Install CockroachDB](https://www.cockroachlabs.com/docs/v20.1/install-cockroachdb-mac.html).
2. [Start a local CockroachDB cluster](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster).
3. In the directory you created in Step 2, write the code for your to-do app using your CockroachDB cluster. For a sample app, see https://github.com/cockroachdb/examples-python

### Step 3: Open a pull request

1. [Add, commit, and push](https://docs.github.com/en/github/managing-files-in-a-repository/adding-a-file-to-a-repository-using-the-command-line) the folder to your branch.
    1. `cd ..`
    2. `git add <programming language>_ToDoApp_<your name>`
    3. `git commit -m "Added To-Do app for <programming language>"`
    4. `git push -u origin <programming language>_ToDoApp_<your name>`
2. [Open a pull request](https://docs.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request#creating-the-pull-request)!

## Note for newbies

This challenge has three parts: Learn how to contribute to open source projects, learn CockroachDB, and learn how to build apps. Each part of this challenge can be overwhelming if it is new to you. Take your time with the challenge -- pace yourself and be patient with yourself. And don't hesitate to reach out to our community if you need help.

## Join our Community
Join our [Community Slack](https://cockroa.ch/slack) (there's a dedicated #contributors channel!) to ask questions, discuss your ideas, or connect with other contributors.

Please follow the guidelines outlined in our [Code of Conduct](https://docs.google.com/document/d/1_BB3IrsAVglDNPy37Z6KQlii_c3fYETFlWMMBUpbY1M/edit#) to help us make the CockroachDB community a welcoming and helpful place for everyone.
