

// We are setting a few options for our chart and override the defaults
var options = {
    // Don't draw the line chart points
    showPoint: false,
    // Disable line smoothing
    lineSmooth: false,
    // X-Axis specific configuration
    axisX: {
        // We can disable the grid for this axis
        showGrid: false,
        // and also don't show the label
        showLabel: true
    },
    // Y-Axis specific configuration
    axisY: {
        showGrid: false,
        // Lets offset the chart a bit from the labels
        offset: 60,
        // The label interpolation function enables you to modify the values
        // used for the labels on each axis. Here we are converting the
        // values into million pound.
        labelInterpolationFnc: function(value) {
            return value + 'mV';
        }
    }
};

// All you need to do is pass your configuration as third parameter to the chart function
new Chartist.Line('.ct-chart', data, options);