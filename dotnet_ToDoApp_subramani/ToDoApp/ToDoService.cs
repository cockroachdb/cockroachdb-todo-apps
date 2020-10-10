using Npgsql;
using System;
using System.Collections.Generic;
using System.Text;
using ToDoApp.Models;

namespace ToDoApp
{
    public class ToDoService
    {
        private readonly NpgsqlConnection conn;

        public ToDoService(string connString)
        {
            this.conn = new NpgsqlConnection(connString);
            this.conn.Open();
        }

        public long? GetLatestId()
        {
            long? rowCount = null;
            using (NpgsqlCommand cmd = new NpgsqlCommand("SELECT id FROM todo ORDER BY id DESC LIMIT 1", this.conn))
            using (NpgsqlDataReader reader = cmd.ExecuteReader())
                while (reader.Read())
                    rowCount = (long?)reader.GetValue(0);

            return rowCount;
        }

        public int Create(string task, long id)
        {
            int rowCount = -1;
            using (NpgsqlCommand cmd = new NpgsqlCommand())
            {
                cmd.Connection = this.conn;
                cmd.CommandText = "UPSERT INTO todo(id, task) VALUES(@id1, @val1)";
                cmd.Parameters.AddWithValue("id1", id);
                cmd.Parameters.AddWithValue("val1", task);
                rowCount = cmd.ExecuteNonQuery();
            }

            return rowCount;
        }

        public bool Delete(int id)
        {
            try
            {
                new NpgsqlCommand($"DELETE FROM todo WHERE id = {id}", this.conn).ExecuteNonQuery();

                return true;
            }
            catch
            {
                return false;
            }
        }

        public List<ToDoModel> ReadAll()
        {
            List<ToDoModel> todoList = new List<ToDoModel>();
            using (NpgsqlCommand cmd = new NpgsqlCommand("SELECT id, task FROM todo", this.conn))
            using (NpgsqlDataReader reader = cmd.ExecuteReader())
                while (reader.Read())
                    todoList.Add(new ToDoModel()
                    {
                        Id = (long)reader.GetValue(0),
                        Task = (string)reader.GetValue(1)
                    });

            return todoList;
        }
    }
}
