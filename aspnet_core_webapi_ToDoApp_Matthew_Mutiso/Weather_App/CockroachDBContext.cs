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
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

using Microsoft.EntityFrameworkCore;

namespace Weather_App
{
    public class CockroachDBContext : DbContext
    {
        public CockroachDBContext(DbContextOptions<CockroachDBContext> options)
            :base(options)
        {

        }

        public DbSet<WeatherEntry> WeatherEntries { get; set; } 

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<WeatherEntry>(props =>
            {
                props.ToTable("weather_entries");
                props.HasKey(x => x.Id);

                props.Property(x => x.Id).HasColumnName("id");
                props.Property(x => x.Date).HasColumnName("date");
                props.Property(x => x.TemperatureC).HasColumnName("temperature_c");
                props.Property(x => x.Summary).HasColumnName("summary");
            });
        }
    }
}
