var HttpClient = function() {
    this.get = function(aUrl, aCallback) {
        var anHttpRequest = new XMLHttpRequest();

        anHttpRequest.onreadystatechange = function() {
            if (anHttpRequest.readyState == 4 && anHttpRequest.status == 200)
                aCallback(anHttpRequest.responseText);
        }

        anHttpRequest.open( "GET", aUrl, true );
        anHttpRequest.send( null );
    }
}

var client = new HttpClient();
var response1;
client.get('http://localhost:8084/MonitorWeb/webresources/access/memory', function(response) {
        console.log(response);
        response1 = JSON.parse(response);
        var table = document.getElementById('memorytab');
    	var x = table.rows.length;

        var n = response1.memory.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);

          cell1.innerHTML = response1.memory[i-2].timestamp;
          cell2.innerHTML = response1.memory[i-2].sga;
          cell3.innerHTML = response1.memory[i-2].pga;
        }

});
console.log(response1);

var response2;
client.get('http://localhost:8084/MonitorWeb/webresources/access/cpu' , function(response) {
        response2 = JSON.parse(response);

        var table = document.getElementById('cputab');
    	var x = table.rows.length;

        var n = response2.cpu.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);
          var cell5 = row.insertCell(4);
          var cell6 = row.insertCell(5);
          var cell7 = row.insertCell(6);
          var cell8 = row.insertCell(7);

          cell1.innerHTML = response2.cpu[i-2].timestamp;
          cell2.innerHTML = response2.cpu[i-2].num_cpu_cores;
          cell3.innerHTML = response2.cpu[i-2].iowait_time;
          cell4.innerHTML = response2.cpu[i-2].nice_time;
          cell5.innerHTML = response2.cpu[i-2].busy_time;
          cell6.innerHTML = response2.cpu[i-2].user_time;
          cell7.innerHTML = response2.cpu[i-2].num_cpus;
          cell8.innerHTML = response2.cpu[i-2].iddle_time;
        }
});
console.log(response2);

var response3;
client.get('http://localhost:8084/MonitorWeb/webresources/access/datafiles' , function(response) {
        response3 = JSON.parse(response);

        var table = document.getElementById('datafilestab');
    	var x = table.rows.length;

        var n = response3.datafiles.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);

          cell1.innerHTML = response3.datafiles[i-2].filename;
          cell2.innerHTML = response3.datafiles[i-2].size_file;
          cell3.innerHTML = response3.datafiles[i-2].used;
          cell4.innerHTML = response3.datafiles[i-2].pct_used;
        }
});
console.log(response3);

var response4;
client.get('http://localhost:8084/MonitorWeb/webresources/access/sessions' , function(response) {
        response4 = JSON.parse(response);

        var table = document.getElementById('sessionstab');
    	var x = table.rows.length;

        var n = response4.sessions.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);
          var cell5 = row.insertCell(4);

          cell1.innerHTML = response4.sessions[i-2].sid;
          cell2.innerHTML = response4.sessions[i-2].timestamp;
          cell3.innerHTML = response4.sessions[i-2].box;
          cell4.innerHTML = response4.sessions[i-2].os_user;
          cell5.innerHTML = response4.sessions[i-2].program;
        }
});
console.log(response4);

var response5;
client.get('http://localhost:8084/MonitorWeb/webresources/access/grants' , function(response) {
        response5 = JSON.parse(response);

        var table = document.getElementById('grantstab');
    	var x = table.rows.length;

        var n = response5.grants.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);
          var cell5 = row.insertCell(4);
          var cell6 = row.insertCell(5);

          cell1.innerHTML = response5.grants[i-2].grantee;
          cell2.innerHTML = response5.grants[i-2].privilege;
          cell3.innerHTML = response5.grants[i-2].admin_option;
          cell4.innerHTML = response5.grants[i-2].common;
          cell5.innerHTML = response5.grants[i-2].inherited;
          cell6.innerHTML = response5.grants[i-2].timestamp;
        }
});
console.log(response5);

var response6;
client.get('http://localhost:8084/MonitorWeb/webresources/access/users' , function(response) {
        response6 = JSON.parse(response);

        var table = document.getElementById('userstab');
    	var x = table.rows.length;

        var n = response6.users.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);

          cell1.innerHTML = response6.users[i-2].username;
          cell2.innerHTML = response6.users[i-2].default_tablespace;
          cell3.innerHTML = response6.users[i-2].temporary_tablespace;
          cell4.innerHTML = response6.users[i-2].last_login;
        }
});
console.log(response6);

var response7;
client.get('http://localhost:8084/MonitorWeb/webresources/access/tablespaces' , function(response) {
        response7 = JSON.parse(response);

        var table = document.getElementById('tablespacetab');
    	var x = table.rows.length;

        var n = response7.tablespaces.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);
          var cell5 = row.insertCell(4);
          var cell6 = row.insertCell(5);
          var cell7 = row.insertCell(6);
          var cell8 = row.insertCell(7);

          cell1.innerHTML = response7.tablespaces[i-2].tablespace;
          cell2.innerHTML = response7.tablespaces[i-2].pct_used;
          cell3.innerHTML = response7.tablespaces[i-2].total_mb;
          cell4.innerHTML = response7.tablespaces[i-2].used_mb;
          cell5.innerHTML = response7.tablespaces[i-2].free_mb;
          cell6.innerHTML = response7.tablespaces[i-2].datafiles;
          cell7.innerHTML = response7.tablespaces[i-2].username;
          cell8.innerHTML = response7.tablespaces[i-2].datafile_name;
        }
});
console.log(response7);


var response8;
client.get('http://localhost:8084/MonitorWeb/webresources/access/tables' , function(response) {
        response8 = JSON.parse(response);

        var table = document.getElementById('tablestab');
    	var x = table.rows.length;

        var n = response8.tables.length;


        for(let i = 2; i < n + 2; i++) {
          var row = table.insertRow(i);

          var cell1 = row.insertCell(0);
          var cell2 = row.insertCell(1);
          var cell3 = row.insertCell(2);
          var cell4 = row.insertCell(3);
          var cell5 = row.insertCell(4);


          cell1.innerHTML = response8.tables[i-2].timestamp;
          cell2.innerHTML = response8.tables[i-2].schema;
          cell3.innerHTML = response8.tables[i-2].object;
          cell4.innerHTML = response8.tables[i-2].object_size;
          cell5.innerHTML = response8.tables[i-2].tablespace;
        }
});
console.log(response8);