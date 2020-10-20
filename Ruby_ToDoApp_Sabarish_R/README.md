## Ruby todo-app based on Rails 5.2 using Active Record

Steps to see the app running :
1. Install Ruby and Rails versions given in the Gemfile.
2. Install Cockroach Db and follow the steps given [here](https://www.cockroachlabs.com/docs/v20.1/build-a-ruby-app-with-cockroachdb.html).
3. Create a new user and a database(if needed) and replace the database field in database.yml file.
4. Start the cockroachdb cluster - secure or insecure.
5. Start the rails server `rails s`