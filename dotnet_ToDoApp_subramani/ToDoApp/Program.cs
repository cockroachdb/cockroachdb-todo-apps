using Npgsql;
using System;
using System.Collections.Generic;

namespace ToDoApp
{
    class Program
    {
        private ToDoService _service;

        static void Main(string[] args)
        {
            NpgsqlConnectionStringBuilder connStringBuilder = new NpgsqlConnectionStringBuilder();
            connStringBuilder.Host = "localhost";
            connStringBuilder.Port = 26257;
            connStringBuilder.SslMode = SslMode.Disable;
            connStringBuilder.Username = "subramani";
            connStringBuilder.Database = "tododb";
            InitialiseDatabase(connStringBuilder.ConnectionString);
            Program prgm = new Program();
            prgm.ToDo(connStringBuilder.ConnectionString);
        }

        static void InitialiseDatabase(string connString)
        {
            using (NpgsqlConnection conn = new NpgsqlConnection(connString))
            {
                conn.Open();

                new NpgsqlCommand("CREATE TABLE IF NOT EXISTS todo (id int PRIMARY KEY, task VARCHAR(50))", conn).ExecuteNonQuery();

            }
        }

        public void ToDo(string connString)
        {
            this._service = new ToDoService(connString);
            bool isCorrectInteger = true;
            int optionNumber = -1;
            Console.WriteLine("Welcome to your to do list!!");
            Console.WriteLine("This to do application supports the following operations: \n 1. Create a new to do item " +
                "\n 2. Read all your to do items. \n 3. Delete an item \n 4. Exit");

            while (true)
            {
                Console.WriteLine("Please enter the number of the operation you want to perform.");
                do
                {
                    if (!isCorrectInteger)
                        Console.WriteLine("Entered option is invalid. Please enter again...");

                    string option = Console.ReadLine();
                    isCorrectInteger = int.TryParse(option, out optionNumber);

                } while (!isCorrectInteger);

                this.PerformRequestedOperation(optionNumber);
            }
        }

        public void PerformRequestedOperation(int optionNumber)
        {
            switch (optionNumber)
            {
                case 1:
                    Console.WriteLine("Enter the task you want to add to your to do list");
                    string task = Console.ReadLine();
                    long? latestId = this._service.GetLatestId();
                    int count = this._service.Create(task, (latestId ?? 0) + 1);
                    if(count > 0)
                        Console.WriteLine("CREATED SUCCESSFULLY");
                    else
                        Console.WriteLine("OPERATION FAILED.");
                    break;
                case 2:
                    List<Models.ToDoModel> list = this._service.ReadAll();
                    if (list.Count > 0)
                    {
                        Console.WriteLine("Printing all to do items...");
                        list.ForEach(x => Console.WriteLine($"{x.Id}. {x.Task}"));
                    }
                    else
                    {
                        Console.WriteLine("There are no items in your to do list");
                    }
                    break;
                case 3:
                    int idToDelete;
                    Console.WriteLine("Enter the id of the item you want to delete");
                    if(int.TryParse(Console.ReadLine(), out idToDelete))
                    {
                        if (this._service.Delete(idToDelete))
                            Console.WriteLine("DELETED SUCCESSFULLY");
                        else
                            Console.WriteLine("OPERATION FAILED.");
                    }
                    break;
                case 4:
                    Console.WriteLine("Exiting...");
                    Environment.Exit(0);
                    break;
                default:
                    Console.WriteLine("Entered option is invalid. Exiting...");
                    Environment.Exit(0);
                    break;
            }
        }
    }
}
