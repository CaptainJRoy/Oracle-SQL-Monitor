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
client.get('http://localhost:8080/ords/personnel/manage/Memory', function(response) {
        var response1 = JSON.parse(response);

        document.getElementById('timestamp_mem').innerHTML = response1.items[0].timestamp;
        document.getElementById('sga').innerHTML = response1.items[0].sga;
        document.getElementById('pga').innerHTML = response1.items[0].pga;
});

client.get('http://localhost:8080/ords/personnel/manage/CPU' , function(response) {
        var response2 = JSON.parse(response);

        document.getElementById('timestamp_cpu').innerHTML = response2.items[0].timestamp;
        document.getElementById('cpu_cores').innerHTML = response2.items[0].num_cpu_cores;
        document.getElementById('iowait').innerHTML = response2.items[0].iowait_time;
        document.getElementById('nice').innerHTML = response2.items[0].nice_time;
        document.getElementById('busy_time').innerHTML = response2.items[0].busy_time;
        document.getElementById('user_time').innerHTML = response2.items[0].user_time;
        document.getElementById('n_cpu').innerHTML = response2.items[0].num_cpus;
        document.getElementById('iddle_time').innerHTML = response2.items[0].iddle_time;
});

client.get('http://localhost:8080/ords/personnel/manage/Datafiles' , function(response) {
        var response3 = JSON.parse(response);

        document.getElementById('filename').innerHTML = response3.items[0].filename;
        document.getElementById('size_file').innerHTML = response3.items[0].size_file;
        document.getElementById('used').innerHTML = response3.items[0].used;
        document.getElementById('pct_used').innerHTML = response3.items[0].pct_used;
        
});


client.get('http://localhost:8080/ords/personnel/manage/Sessions' , function(response) {
        var response4 = JSON.parse(response);

        document.getElementById('sid').innerHTML = response4.items[0].sid;
        document.getElementById('timestamp').innerHTML = response4.items[0].timestamp;
        document.getElementById('box').innerHTML = response4.items[0].box;
        document.getElementById('os_user').innerHTML = response4.items[0].os_user;
        document.getElementById('program').innerHTML = response4.items[0].program;
});


client.get('http://localhost:8080/ords/personnel/manage/Grants' , function(response) {
        var response5 = JSON.parse(response);

        document.getElementById('grantee').innerHTML = response5.items[0].grantee;
        document.getElementById('privilege').innerHTML = response5.items[0].privilege;
        document.getElementById('admin_option').innerHTML = response5.items[0].admin_option;
        document.getElementById('common').innerHTML = response5.items[0].common;
        document.getElementById('inherited').innerHTML = response5.items[0].inherited;
        document.getElementById('timestamp_grants').innerHTML = response5.items[0].timestamp_grants;
});


client.get('http://localhost:8080/ords/personnel/manage/Users' , function(response) {
        var response6 = JSON.parse(response);

        document.getElementById('username').innerHTML = response6.items[0].username;
        document.getElementById('default_tablespace').innerHTML = response6.items[0].default_tablespace;
        document.getElementById('temporary_tablespace').innerHTML = response6.items[0].temporary_tablespace;
        document.getElementById('last_login').innerHTML = response6.items[0].last_login;
});


client.get('http://localhost:8080/ords/personnel/manage/Tablespaces' , function(response) {
        var response7 = JSON.parse(response);

        document.getElementById('tablespace').innerHTML = response7.items[0].username;
        document.getElementById('pct_used_ts').innerHTML = response7.items[0].pct_used_ts;
        document.getElementById('total_mb').innerHTML = response7.items[0].total_mb;
        document.getElementById('used_mb').innerHTML = response7.items[0].used_mb;
        document.getElementById('free_mb').innerHTML = response7.items[0].free_mb;
        document.getElementById('datafiles').innerHTML = response7.items[0].datafiles;
});

client.get('http://localhost:8080/ords/personnel/manage/Tables' , function(response) {
        var response8 = JSON.parse(response);

        document.getElementById('timestamp_tables').innerHTML = response8.items[0].timestamp_tables;
        document.getElementById('schema').innerHTML = response8.items[0].schema;
        document.getElementById('object').innerHTML = response8.items[0].object;
        document.getElementById('object_size').innerHTML = response8.items[0].object_size;
});