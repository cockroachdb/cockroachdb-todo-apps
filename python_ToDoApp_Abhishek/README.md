"""
Contributer's Name - Abhishek Palaskar
(C) 2020 Abhishek Palaskar

"""

Step 1. Install the psycopg2 driver

To install the Python psycopg2 driver, run the following command:

pip install psycopg2

Start the built-in SQL shell:

cockroach sql --insecure

In the SQL shell, issue the following statements to create the abhi user and bank database: (you can create your username also)

CREATE USER IF NOT EXISTS abhi;

CREATE DATABASE bank;

GRANT ALL ON DATABASE bank TO abhi;

Exit the SQL shell:

\q

Step 3. Run the Python code

Now that you have a database and a user, you'll run the code shown below to:

Create an accounts table and insert some rows.

Transfer funds between two accounts inside a transaction. To ensure that we handle transaction retry errors, we write an application-level

retry loop that, in case of error, sleeps before trying the funds transfer again. If it encounters another retry error, it sleeps for a longer interval, implementing exponential backoff.

Finally, we delete the accounts from the table before exiting so we can re-run the example code.

Change to the directory where you cloned the repo and run the code:

python example.py

Initial Balances 
['1', '500']
['2', '200']
After Balances 
['1', '400']
['2', '300']



