$(document).ready(function() {
    $(".test").click(function() {
        fire_ajax_submit();
    });

    $("#gameTable").DataTable({
        columns: [
            { data: 'name' },
            { data: 'average' },
            { data: 'num_ratings'},
            { data: 'weight' }
        ]
    });

    $("#popularity_slider").on("input", function() {
        let val = $(this).val();
        if (val < 33)
            $("#popularity_value").text("More popular");
        else if (val >= 33 && val < 67) {
            $("#popularity_value").text("Balanced");
        } else {
            $("#popularity_value").text("Higher Quality");
        }
        fire_ajax_get_primary_data($(this).val());
    });

    $("#popularity_slider").on("change", function() {
        fire_ajax_get_primary_data($(this).val());
    })
});

var fire_ajax_submit = function() {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "api/test",
        cache: false,
    })
    .done(function(data) {
        $("#test_insert").text(data);
    })
    .fail()
    .always();
}

var fire_ajax_get_primary_data = function(qVar) {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "api/getOverview",
        data: {
            quality: qVar
        }
    })
    .done(function(data) {
        $("#svgContainer").empty();
        let v = createMainGraph(data);
        $("#svgContainer").append(v);
        $("#gameTable").DataTable().clear().rows.add(data).draw();
    })
    .always();
}

var createMainGraph = function(data) {
    margin = ({top: 25, right: 20, bottom: 35, left: 40});
    width = 960;
    height = 800;
    radius = 8;
    data.forEach(d => {
        d.average = +d.average;
        d.num_ratings = +d.num_ratings;
    });
x = d3.scaleLinear()
    .domain([1, 10]).nice()
    .range([margin.left, width - margin.right]);

y = d3.scaleSymlog()
    .domain(d3.extent(data, d => d.num_ratings)).nice()
    .rangeRound([height - margin.bottom, margin.top]);

xAxis = g => g
    .attr("transform", `translate(0,${height - margin.bottom})`)
    .call(d3.axisBottom(x).ticks(width / 80))
    .call(g => g.select(".domain").remove())
    .call(g => g.append("text")
    .attr("x", width)
    .attr("y", margin.bottom - 4)
    .attr("fill", "currentColor")
    .attr("text-anchor", "end")
    .text(data.average));

yAxis = g => g
    .attr("transform", `translate(${margin.left},0)`)
    .call(d3.axisLeft(y))
    .call(g => g.select(".domain").remove())
    .call(g => g.append("text")
        .attr("x", -margin.left)
        .attr("y", 10)
        .attr("fill", "currentColor")
        .attr("text-anchor", "start")
        .text(data.num_ratings));

hexbin = d3.hexbin()
    .x(d => x(d.average))
    .y(d => y(d.num_ratings))
    .radius(radius * width / 954)
    .extent([[margin.left, margin.top], [width - margin.right, height - margin.bottom]]);

bins = hexbin(data);

color = d3.scaleSequential(d3.interpolateBuPu).domain([0, d3.max(bins, d => d.length) / 2]);

    const svg = d3.create("svg")
        .attr("viewBox", [0, 0, width, height]);

    svg.append("g")
        .call(xAxis);
    
    svg.append("g")
        .call(yAxis);
    
    svg.append("g")
      .attr("stroke", "steelblue")
      .attr("stroke-width", 1.5)
      .attr("fill", "none")
    .selectAll("path")
    .data(bins)
    .join("path")
        .attr("d", hexbin.hexagon())
        .attr("transform", d => `translate(${d.x},${d.y})`)
        .attr("fill", d => color(d.length));
    
    svg.selectAll("path")
        .on("click", function(data) {
            $("#gameTable").DataTable().clear().rows.add(data).draw();
        });

    return svg.node();
}