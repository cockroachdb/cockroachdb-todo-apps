using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace Weather_App.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ActualWeatherController : ControllerBase
    {
        

        private readonly ILogger<ActualWeatherController> _logger;
        private readonly CockroachDBContext _context;

        public ActualWeatherController(ILogger<ActualWeatherController> logger, CockroachDBContext context)
        {
            _logger = logger;
            _context = context ?? throw new ArgumentNullException(nameof(context));
        }

        [HttpGet]
        public IEnumerable<WeatherEntry> Get()
        {
            var entries = _context.WeatherEntries.ToList();

            return entries;
        }

        [HttpPost]
        public async Task<ActionResult<WeatherEntry>> Post(WeatherEntryApiModel model)
        {
            var entity = model.WeatherEntry;
            _context.Add(entity);
            await _context.SaveChangesAsync();

            return CreatedAtAction(nameof(Get), new { id = entity.Id },  entity);
        }
    }
}
