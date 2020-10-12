/*
 Copyright [2020] [Matthew Mutiso]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

using Microsoft.EntityFrameworkCore;

namespace HelloRoachEF
{
    public class RoachDbContext : DbContext
    {
        string _connectionString;
        public RoachDbContext(string connectionString)
        {
            _connectionString = connectionString;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder) =>
             optionsBuilder.UseNpgsql(_connectionString);

        public DbSet<Account> Accounts { get; set; }

        protected override void OnModelCreating(ModelBuilder builder)
        {
            builder.Entity<Account>(options =>
            {
                options.ToTable("accounts");
                options.HasIndex(x => x.Id);

                options.Property(p => p.Id).HasColumnName("id");
                options.Property(p => p.Balance).HasColumnName("balance");
            });
        }
    }
}
