
console.log("start");
fetch("https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/config.json")
    .then(function(response) {
        console.log(response);
        return response;
        //return response.json();  // Convert the response to JSON
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
                let image = data[0].img;
            }
            else {
                let image = "placeholder.png";
            }

            //console.log("k");
            document.getElementById("mainbody").innerHTML += `
                <div class="robot" id="${dir}">
                    <img src="images/${image}">
                    <h2>${data[0].name}</h2>
                </div>
            `;
            //console.log("o");
            document.getElementById(dir).onclick = function () {
                localStorage.setItem("botDir", dir);
                window.location.href = "bot.html";
            };
        })
        .catch(function(error) {
            console.error("Error fetching JSON", error);
        });
}
