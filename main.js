



/*
console.log("start");
let image = "placeholder.png";
fetch("https://raw.githubusercontent.com/bearbots-bhs/gitbear/refs/heads/main/imports/config.json")
    .then(function(response) {
        console.log(response);
        //return response;
        return response.json();  // Convert the response to JSON
    })
    .then(function(data) {
        for (let i = 0; i < data.length; i++) {
            renderBot(data[i].dir);
            console.log(data[i].dir);
        }
        console.log("done");
    })
    .catch(function(error) {
        console.error("Error fetching JSON", error);
    });
    

function renderBot(dir) {
    
    let url = `https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/${dir}/config.json`;

    fetch(url)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            if (data[0].img != null) {
                image = data[0].img;
            }
            else {
                image = "placeholder.png";
            }

            //console.log("k");
            document.getElementById("mainbody").innerHTML += `
                <a href="bot.html">
                <div class="robot" id="${dir}">
                    <img src="images/${image}">
                    <h2>${data[0].name}</h2>
                </div>
                </a>
                <br>
            `;
            //console.log("o");
            
            
        })
        .catch(function(error) {
            console.error("Error fetching JSON", error);
        });
}
*/