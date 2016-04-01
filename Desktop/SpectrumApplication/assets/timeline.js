/**
 * Created by Team Spectrum Health on 2/17/2016.
 */
$(document).ready(function() {

  Date.prototype.DateAdd = function(strInterval, Number) {
    var dtTmp = this;
    switch (strInterval) {
      case 's':
        return new Date(Date.parse(dtTmp) + (1000 * Number));
      case 'n':
        return new Date(Date.parse(dtTmp) + (60000 * Number));
      case 'h':
        return new Date(Date.parse(dtTmp) + (3600000 * Number));
      case 'd':
        return new Date(Date.parse(dtTmp) + (86400000 * Number));
      case 'w':
        return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
      case 'q':
        return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number * 3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
      case 'm':
        return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
      case 'y':
        return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());
    }
  }

  function GetDateStr2(AddDayCount) {
    var dd = new Date();
    ddd = dd.DateAdd('d', AddDayCount); //
    var y = ddd.getYear();
    var m = ddd.getMonth() + 1; //
    var d = ddd.getDate();
    return m + "-" + d;
  }

  for (var i = -2; i < 3; i++) {
    var result = GetDateStr2(i);

    var parent = $(".flexTimeline");

    parent.append('<div class="timelineItem" >' + result + '</div>');

  }

  function makeBar(bar, y, m, d, duration) {

    var dur = duration;
    var barLength = (duration * 20);
    var today = new Date();
    var startDate = new Date();
    startDate.setFullYear(y, m - 1, d);
    var dayFromToday = ((startDate.getTime() - today.getTime()) / 1000 / 60 / 60 / 24);
    var startPoint = (20 * dayFromToday + 50);

    document.getElementById(bar).style.width = barLength + '%';
    document.getElementById(bar).style.marginLeft = (startPoint) + '%';
    document.getElementById(bar).style.marginRight = (100 - barLength - startPoint) + '%';

    //boolean (barlength + startpoint < 0){}

  }

  function removeBar(bar){
    makeBar(bar, 2000,1,1,0);
  }


  makeBar('bluebar', 2016, 2, 15, 3);
  makeBar('yellowbar', 2016, 2, 16, 2);
  makeBar('redbar', 2016, 2, 17, 2);
  makeBar('pinkbar', 2016, 2, 18, 29);
  makeBar('greenbar', 2016, 2, 14, 2);


  //removeBar('yellowbar');
});
