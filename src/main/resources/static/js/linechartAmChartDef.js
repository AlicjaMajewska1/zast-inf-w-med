chartData = [];
$.ajax({
    url: 'http://localhost:8080/loadChartData',
    dataType: 'json',
    async: false,
    data: {
        filename: "[[${ecgData.filename}]];"
    },
    success: function (data) {
        chartData = data;
        debugger;
    }
});
var chart = AmCharts.makeChart("chartdiv", {
    "type": "serial",
    "theme": "light",
    "marginRight": 80,
    "autoMarginOffset": 20,
    "marginTop": 7,
    "dataProvider": chartData,
    "valueAxes": [{
        "axisAlpha": 0.2,
        "dashLength": 1,
        "position": "left",
        "title": "Napięcie [µV]"
    }],
    "numberFormatter": {
        "precision": -1,
        "decimalSeparator": ".",
        "thousandsSeparator": ""
    },
    "mouseWheelZoomEnabled": true,
    "graphs": [{
        "id": "g1",
        "balloonText": "[[value]]",
        "bulletField": "bullet",
        "title": "red line",
        "valueField": "value",
        "balloon": {
            "drop": true
        }
    }],
    "chartScrollbar": {
        "autoGridCount": true,
        "graph": "g1",
        "scrollbarHeight": 40
    },
    "chartCursor": {
        "limitToGraph": "g1"
    },
    "categoryField": "label",
    "categoryAxis": {
        "parseDates": false,
        "axisColor": "#DADADA",
        "dashLength": 1,
        "minorGridEnabled": true,
        "title": "Czas"
    },
    "export": {
        "enabled": true
    }
});

chart.addListener("rendered", zoomChart);


