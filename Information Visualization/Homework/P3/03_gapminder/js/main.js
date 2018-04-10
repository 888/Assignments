// Creates a bootstrap-slider element
$("#yearSlider").slider({
    tooltip: 'always',
    tooltip_position:'bottom'
});
// Listens to the on "change" event for the slider
$("#yearSlider").on('change', function(event){
    // Update the chart on the new value
    updateChart(event.value.newValue);
});

// Color mapping based on continents
var continentColors = {Asia: '#fc5a74', Europe: '#fee633',
    Africa: '#24d5e8', Americas: '#82e92d', Oceania: '#fc5a74'};

d3.csv('./data/gapminder.csv',
    function(d){
        // This callback formats each row of the data
        return {
            country: d.country,
            year: +d.year,
            population: +d.population,
            continent: d.continent,
            lifeExp: +d.lifeExp,
            gdpPercap: +d.gdpPercap
        }
    },
    function(error, dataset){
        if(error) {
            console.error('Error while loading ./gapminder.csv dataset.');
            console.error(error);
            return;
        }

        // **** Set up your global variables and initialize the chart here ****
        xScale = d3.scaleLog()
            .domain([100, 64000])
            .range([50, 980]);
        yScale = d3.scaleLinear()
            .domain([20, 90])
            .range([650, 40]);
        radScale = d3.scaleSqrt()
            .domain([1000, 1500000000])
            .range([0.5, 45]);

        years = [1952, 1957, 1962, 1967, 1972, 1977, 1982, 1987, 1992, 1997, 2002, 2007];
        fiftyTwo = [];
        fiftySeven = [];
        sixtyTwo = [];
        sixtySeven = [];
        seventyTwo = [];
        seventySeven = [];
        eightyTwo = [];
        eightySeven = [];
        ninetyTwo = [];
        ninetySeven = [];
        ohTwo = [];
        ohSeven = [];
        reference = {
            '1952' : fiftyTwo,
            '1957' : fiftySeven,
            '1962' : sixtyTwo,
            '1967' : sixtySeven,
            '1972' : seventyTwo,
            '1977' : seventySeven,
            '1982' : eightyTwo,
            '1987' : eightySeven,
            '1992' : ninetyTwo,
            '1997' : ninetySeven,
            '2002' : ohTwo,
            '2007' : ohSeven
        };
        dataset.forEach(function(i) { if (i.year === 1952) fiftyTwo.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1957) fiftySeven.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1962) sixtyTwo.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1967) sixtySeven.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1972) seventyTwo.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1977) seventySeven.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1982) eightyTwo.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1987) eightySeven.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1992) ninetyTwo.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 1997) ninetySeven.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 2002) ohTwo.push(i); return i; });
        dataset.forEach(function(i) { if (i.year === 2007) ohSeven.push(i); return i; });

        svg = d3.selectAll('svg');
        svg.append('text')
            .attr('class', 'label')
            .attr('transform', 'translate(10, 20)')
            .text('Life Expectancy (Years)');
        svg.append('text')
            .attr('class', 'label')
            .attr('transform', 'translate(350, 690)')
            .text('GDP per capita ($, adjusted for inflation of that year)');
        svg.append('text')
            .attr('class', 'title')
            .attr('transform', 'translate(700, 30)')
            .style("font-size","30px")
            .text('Gapminder Countries');
        svg.append('g')
            .attr('class', 'y axis')
            .attr('transform', 'translate(55,0)')
            .call(d3.axisLeft(yScale));
        svg.append('g')
            .attr('class', 'x axis')
            .attr('transform', 'translate(0, 650)')
            .call(d3.axisBottom(xScale).tickValues([300, 500, 1000, 2000, 4000, 8000, 16000, 32000, 64000]).tickFormat(d3.format(".0f")));
        updateChart(1952);
    });

function updateChart(year) {
    //**** Update the chart based on the year here ****
    var current = reference[year];
    var now = svg.selectAll('.circle')
        .data(current, function(d) { return d.country });
    now = now.enter()
        .append('circle')
            .attr('class', 'circle')
            .attr('r', function(d) { return radScale(d.population) })
            .attr('cx', function(d) { return xScale(d.gdpPercap) })
            .attr('cy', function(d) { return yScale(d.lifeExp) })
            .style("fill", function(d) { continent = d.continent; return continentColors[continent]; })
        .merge(now)
            .attr('r', function(d) { return radScale(d.population) })
            .attr('cx', function(d) { return xScale(d.gdpPercap) })
            .attr('cy', function(d) { return yScale(d.lifeExp) })
            .style("fill", function(d) { continent = d.continent; return continentColors[continent]; });
}