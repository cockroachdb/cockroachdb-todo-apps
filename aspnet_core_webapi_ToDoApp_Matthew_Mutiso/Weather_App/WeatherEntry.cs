using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace Weather_App
{
    public class WeatherEntry
    {
        public Guid Id { get; set; }
        public DateTime Date { get; set; }

        public int TemperatureC { get; set; }

        [NotMapped]
        public int TemperatureF => 32 + (int)(TemperatureC / 0.5556);

        public string Summary { get; set; }
    }
}
