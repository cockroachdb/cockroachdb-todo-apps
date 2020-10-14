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
                props.Property(x => x.Id).HasColumnName("temperature_c");
                props.Property(x => x.Id).HasColumnName("summary");
            });
        }
    }
}
