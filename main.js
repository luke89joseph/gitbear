fetch ("https://api.github.com/repos/bearbots-bhs/gitbear/commits")
    .then (function(response) {
        return response.json();
    })

    .then(function(data) {
        let lastCommit = data[0];
        document.getElementById("linkTO-repo").innerHTML = `(VIEW REPO) Last commit: "${lastCommit.commit.message}"`;
    })
    .catch(function(error) {
        console.error('Error fetching data:', error);
    });