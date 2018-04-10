
// **** Your JavaScript code goes here ****
d3.csv("../data/real_estate.csv", function(data) {
    var yearScale = d3.scaleLinear()
        .domain([1850, 2010])
        .range([50, 330]);

    var yScale = d3.scaleLinear()
        .domain([0, 5000])
        .range([300, 10]);

    var place = d3.nest()
        .key(function(d) { return d.location; })
        .entries(data);

    var nyArr = place[0].values;
    var nyX = [];
    var nyY = [];
    var nyB = [];
    nyArr.forEach(function(j) { nyX.push(j.year_built); return j.year_built; });
    nyArr.forEach(function(j) { nyY.push(j.price_per_sqft); return j.price_per_sqft; });
    nyArr.forEach(function(j) { nyB.push(j.beds); return j.beds; });

    var sfArr = place[1].values;
    var sfX = [];
    var sfY = [];
    var sfB = [];
    sfArr.forEach(function(j) { sfX.push(j.year_built); return j.year_built; });
    sfArr.forEach(function(j) { sfY.push(j.price_per_sqft); return j.price_per_sqft; });
    sfArr.forEach(function(j) { sfB.push(j.beds); return j.beds; });

    var bsArr = place[2].values;
    var bsX = [];
    var bsY = [];
    var bsB = [];
    bsArr.forEach(function(j) { bsX.push(j.year_built); return j.year_built; });
    bsArr.forEach(function(j) { bsY.push(j.price_per_sqft); return j.price_per_sqft; });
    bsArr.forEach(function(j) { bsB.push(j.beds); return j.beds; });

    var phArr = place[3].values;
    var phX = [];
    var phY = [];
    var phB = [];
    phArr.forEach(function(j) { phX.push(j.year_built); return j.year_built; });
    phArr.forEach(function(j) { phY.push(j.price_per_sqft); return j.price_per_sqft; });
    phArr.forEach(function(j) { phB.push(j.beds); return j.beds; });

    var svg = d3.select("svg");
    var ny = svg.selectAll('.ny')
        .data(d3.zip(nyX, nyY))
        .enter();
    ny.append('circle')
        .attr('class', 'ny')
        .attr('r', 2)
        .attr('cx', function(d, i) { return yearScale(nyX[i]) })
        .attr('cy', function(d, i) { return yScale(nyY[i]) })
        .style("fill", function(d, i) { return nyB[i] > 2 ? "#2e5d90" : "#499936"; });
    svg.append('text')
        .attr('class', 'title')
        .attr('transform', 'translate(175, 60)')
        .text('New York');
    svg.append('g')
        .attr('class', 'y axis')
        .attr('transform', 'translate(55,0)')
        .call(d3.axisLeft(yScale));
    svg.append('text')
        .attr('class', 'y label')
        .attr('transform', 'translate(15,250)rotate(270)')
        .text('Price per Sqft ($)');
    svg.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(0, 300)')
        .call(d3.axisBottom(yearScale).tickFormat(d3.format(".0f")));
    svg.append('text')
        .attr('class', 'x label')
        .attr('transform', 'translate(200,340)')
        .text('Year Built');
    var sf = svg.selectAll('.sf')
        .data(d3.zip(sfX, sfY))
        .enter()
    sf.append('circle')
        .attr('class', 'sf')
        .attr('r', 2)
        .attr('cx', function(d, i) { return yearScale(sfX[i]) + 350})
        .attr('cy', function(d, i) { return yScale(sfY[i]) })
        .style("fill", function(d, i) { return sfB[i] > 2 ? "#2e5d90" : "#499936"; });
    svg.append('text')
        .attr('class', 'title')
        .attr('transform', 'translate(525, 60)')
        .text('San Francisco');
    svg.append('g')
        .attr('class', 'y axis')
        .attr('transform', 'translate(405,0)')
        .call(d3.axisLeft(yScale));
    svg.append('text')
        .attr('class', 'y label')
        .attr('transform', 'translate(365,250)rotate(270)')
        .text('Price per Sqft ($)');
    svg.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(350, 300)')
        .call(d3.axisBottom(yearScale).tickFormat(d3.format(".0f")));
    svg.append('text')
        .attr('class', 'x label')
        .attr('transform', 'translate(550,340)')
        .text('Year Built');
    var bs = svg.selectAll('.bs')
        .data(d3.zip(bsX, bsY))
        .enter();
    ny.append('circle')
        .attr('class', 'bs')
        .attr('r', 2)
        .attr('cx', function(d, i) { return yearScale(bsX[i]) })
        .attr('cy', function(d, i) { return yScale(bsY[i]) + 350})
        .style("fill", function(d, i) { return bsB[i] > 2 ? "#2e5d90" : "#499936"; });
    svg.append('text')
        .attr('class', 'title')
        .attr('transform', 'translate(175, 410)')
        .text('Boston');
    svg.append('g')
        .attr('class', 'y axis')
        .attr('transform', 'translate(55,350)')
        .call(d3.axisLeft(yScale));
    svg.append('text')
        .attr('class', 'y label')
        .attr('transform', 'translate(15,600)rotate(270)')
        .text('Price per Sqft ($)');
    svg.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(0, 650)')
        .call(d3.axisBottom(yearScale).tickFormat(d3.format(".0f")));
    svg.append('text')
        .attr('class', 'x label')
        .attr('transform', 'translate(200,690)')
        .text('Year Built');
    var ph = svg.selectAll('.ph')
        .data(d3.zip(phX, phY))
        .enter();
    ny.append('circle')
        .attr('class', 'ph')
        .attr('r', 2)
        .attr('cx', function(d, i) { if (i < 223) return yearScale(phX[i]) + 350})
        .attr('cy', function(d, i) { if (i < 223) return yScale(phY[i]) + 350})
        .style("fill", function(d, i) { return phB[i] > 2 ? "#2e5d90" : "#499936"; });
    svg.append('text')
        .attr('class', 'title')
        .attr('transform', 'translate(525, 410)')
        .text('Philadelphia');
    svg.append('g')
        .attr('class', 'y axis')
        .attr('transform', 'translate(405,350)')
        .call(d3.axisLeft(yScale));
    svg.append('text')
        .attr('class', 'y label')
        .attr('transform', 'translate(365,600)rotate(270)')
        .text('Price per Sqft ($)');
    svg.append('g')
        .attr('class', 'x axis')
        .attr('transform', 'translate(350, 650)')
        .call(d3.axisBottom(yearScale).tickFormat(d3.format(".0f")));
    svg.append('text')
        .attr('class', 'x label')
        .attr('transform', 'translate(550,690)')
        .text('Year Built');
});