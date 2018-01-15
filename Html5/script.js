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

