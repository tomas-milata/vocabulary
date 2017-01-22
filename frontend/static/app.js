var get = function (url) {
    return new Promise(function (resolve, reject) {
        var xhr = new XMLHttpRequest()
        xhr.open("GET", url)
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                resolve(xhr.response)
            }
        }
        xhr.send()
    })
}

var translation = null

document.querySelector('button[id="translate"]').addEventListener('click',
    function (e) {
        var phrase = document.getElementById("phrase").value
        var uri = "http://localhost:8080/translation?phrase=" + phrase
        get(uri).then(function (result) {
            var translationList = document.getElementById("translation")
            translationList.innerHTML = "";
            translation =  JSON.parse(result)
            for(var i = 0; i < translation.phrases.length; i++) {
                var phrase = translation.phrases[i]
                translationList.innerHTML += '<li>' + phrase + '</li>'
            }
        })
    })