using System;
using System.Collections.Generic;
using System.Diagnostics.CodeAnalysis;
using System.Linq;
using System.Threading.Tasks;

namespace Weather_App
{
    public class WeatherEntryApiModel
    {
        public int TemperatureC { get; set; }
        public string Summary { get; set; }

        public WeatherEntry WeatherEntry => new WeatherEntry
        {
            Id = Guid.NewGuid(),
            Date = DateTime.UtcNow,
            TemperatureC = TemperatureC,
            Summary = Summary
        };
    }
}
