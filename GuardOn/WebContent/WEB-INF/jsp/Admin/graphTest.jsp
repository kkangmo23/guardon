<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String cp=request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="<%=cp%>/js/jqplot.1.0.8r1250/jquery.jqplot.min.css" type="text/css" media="print, projection, screen" />

<script src="<%=cp%>/js/jqplot.1.0.8r1250/jquery.js"></script>
<script src="<%=cp%>/js/jqplot.1.0.8r1250/jquery.jqplot.js"></script>
<script src="<%=cp%>/js/jqplot.1.0.8r1250/plugins/jqplot.highlighter.min.js"></script>
<script src="<%=cp%>/js/jqplot.1.0.8r1250/plugins/jqplot.cursor.min.js"></script>
<script src="<%=cp%>/js/jqplot.1.0.8r1250/plugins/jqplot.dateAxisRenderer.min.js"></script>

<script src="<%=cp%>/js/jqplot.1.0.8r1250/plugins/jqplot.barRenderer.min.js"></script>
<script src="<%=cp%>/js/jqplot.1.0.8r1250/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script src="<%=cp%>/js/jqplot.1.0.8r1250/plugins/jqplot.pointLabels.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
  var line1=[['23-May-08', 578.55], ['20-Jun-08', 566.5], ['25-Jul-08', 480.88], ['22-Aug-08', 509.84],
      ['26-Sep-08', 454.13], ['24-Oct-08', 379.75], ['21-Nov-08', 303], ['26-Dec-08', 308.56],
      ['23-Jan-09', 299.14], ['20-Feb-09', 346.51], ['20-Mar-09', 325.99], ['24-Apr-09', 386.15]];
  var line2=[['23-May-08', 463.55], ['20-Jun-08', 566.5], ['25-Jul-08', 180.88], ['22-Aug-08', 519.84],
             ['26-Sep-08', 657.13], ['24-Oct-08', 534.75], ['21-Nov-08', 113], ['26-Dec-08', 365.56],
             ['23-Jan-09', 266.14], ['20-Feb-09', 534.51], ['20-Mar-09', 367.99], ['24-Apr-09', 286.15]];
  var plot1 = $.jqplot('chart1', [line1, line2], {
      title:'Data Point Highlighting',
      axes:{
        xaxis:{
          renderer:$.jqplot.DateAxisRenderer,
          tickOptions:{
            formatString:'%b&nbsp;%#d'
          }
        },
        yaxis:{
          tickOptions:{
            formatString:'$%.2f'
            }
        }
      },
      highlighter: {
        show: true,
        sizeAdjust: 7.5
      },
      cursor: {
        show: false
      }  
  });
});
</script>

<script type="text/javascript">
$(document).ready(function(){
	  var s1 = [2, 6, 7, 10];
	  var s2 = [7, 5, 3, 4];	  
	  plot3 = $.jqplot('chart3', [s1, s2], {
		 title:'Bar Chart',
	    // Tell the plot to stack the bars.
	    stackSeries: true,
	    captureRightClick: true,
	    seriesDefaults:{
	      renderer:$.jqplot.BarRenderer,
	      rendererOptions: {
	          // Put a 30 pixel margin between bars.
	          barMargin: 30,
	          // Highlight bars when mouse button pressed.
	          // Disables default highlighting on mouse over.
	          highlightMouseDown: true   
	      },
	      pointLabels: {show: true}
	    },
	    axes: {
	      xaxis: {
	          renderer: $.jqplot.CategoryAxisRenderer
	      },
	      yaxis: {
	        // Don't pad out the bottom of the data range.  By default,
	        // axes scaled as if data extended 10% above and below the
	        // actual range to prevent data points right on grid boundaries.
	        // Don't want to do that here.
	        padMin: 0
	      }
	    },
	    legend: {
	      show: true,
	      location: 'e',
	      placement: 'outside'
	    }     
	  });
	  // Bind a listener to the "jqplotDataClick" event.  Here, simply change
	  // the text of the info3 element to show what series and ponit were
	  // clicked along with the data for that point.
	  $('#chart3').bind('jqplotDataClick',
	    function (ev, seriesIndex, pointIndex, data) {
	      $('#info3').html('series: '+seriesIndex+', point: '+pointIndex+', data: '+data);
	    }
	  );
	});
</script>
</head>
<body>
<div id=chart1 style="width: 600px"></div>
<br/><br/><br/>
<div id=chart3 style="width: 600px"></div>

</body>
</html>