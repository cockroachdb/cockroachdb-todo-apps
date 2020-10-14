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
        private readonly Func<CockroachDBContext> _contextFactory;

        public ActualWeatherController(ILogger<ActualWeatherController> logger, Func<CockroachDBContext> contextFactory)
        {
            _logger = logger;
            _contextFactory = contextFactory ?? throw new ArgumentNullException(nameof(contextFactory));
        }

        [HttpGet]
        public IEnumerable<WeatherEntry> Get()
        {
            using (var _context = _contextFactory())
            {
                var entries = _context.WeatherEntries.ToList();

                return entries;
            }               
        }

        [HttpPost]
        public async Task<ActionResult<WeatherEntry>> Post(WeatherEntryApiModel model)
        {
            var entity = model.WeatherEntry;
            using (var _context = _contextFactory())
            {
                _context.Add(entity);
                await _context.SaveChangesAsync();
            }                

            return CreatedAtAction(nameof(Get), new { id = entity.Id },  entity);
        }
    }
}
