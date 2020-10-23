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
using Microsoft.AspNet.OData;
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
        [EnableQuery]
        public ActionResult<IQueryable<WeatherEntry>> Get()
        {
            var _context = _contextFactory();
            var entries = _context.WeatherEntries;

            return entries;
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
