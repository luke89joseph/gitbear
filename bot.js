
let canvas = document.getElementById("mainbody");

document.getElementById("mainLogo").onclick = function () {
    window.location.href  = "index.html";
};
document.getElementById("linkTO-repo").onclick = function () {
    window.location.href  = "https://github.com/bearbots-bhs/gitbear";
};

let directory = localStorage.getItem("botDir");

fetch (`https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/${directory}/config.json`)
    .then(function(response) {
        return response.json();
    })
    .then (function(data){
        document.getElementById("title").innerHTML = data[0].name;
        //window.location.href = "https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/intoTheDeep24-25/Acompt_FEB2025.java";
        let content = "";
        for (let i = 0; i < data[1].length; i++) {

            let k = data[1][i];

            let id = String(k.name.substring(0, k.name.length-5).trim());
            //console.log(id);
            let link = `https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/${directory}/${k.name}`;
            content += `
            <a href = ${link}>
            <div class="script" id="${id}">
                <h2>${k.name}</h2>
                <h3>${k.task}</h3>
            </div>
            </a>
            <br>
            `;
            /*
            console.log(id);
            document.getElementById(id).onclick = function () {
                window.location.href = `https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/${directory}/${k.name}`;
            };
            */
            //console.log(document.getElementById(id).onclick);
        }
        content += `<h1>CREDITS</h1><h4>${data[0].credits}</h4>`;
        canvas.innerHTML += content;

        /*
        for (let i = 0; i < data[1].length; i++) {
            let k = data[1][i];
            document.getElementById(id).onclick = function () {
                window.location.href = `https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/${directory}/${k.name}`;
            };
        };
        */

    })
    .catch (function(error){
        console.error("Error fetching json", error);
    });

/*
    function renderScript(k, data) {
        let i = data[1][k];

        let id = i.name.substring(0, i.name.length-5)
        canvas.innerHTML += `
        <div class="script" id="${id}">
            <h2>${i.name}</h2>
            <h3>${i.task}</h4>
        </div>
        <br>
        `
        document.getElementById(id).onclick = function() {
            window.location.href = `https://raw.githubusercontent.com/bearbots-bhs/gitbear/main/imports/${directory}/${i.name}`
        }

        //console.log(id);
        
    }
        */
