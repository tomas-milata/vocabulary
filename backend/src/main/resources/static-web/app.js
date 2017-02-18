var baseUri = 'http://localhost:8080'

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

var post = function (url, data) {
    return new Promise(function (resolve, reject) {
        var xhr = new XMLHttpRequest()
        xhr.open("POST", url, true)
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status <= 299) {
                resolve(xhr.response)
            }
        }
        var json = JSON.stringify(data);
        xhr.send(json)
    })
}

var translation = null
var requestedPhrase = null

document.querySelector('button[id="translate"]').addEventListener('click',
    function (e) {
        var phrase = document.getElementById("phrase").value
        requestedPhrase = phrase
        var uri = baseUri + "/translation?phrase=" + phrase
        get(uri).then(function (result) {
            var translationList = document.getElementById("translation")
            translationList.innerHTML = "";
            translation = JSON.parse(result)
            for(var i = 0; i < translation.phrases.length; i++) {
                var phrase = translation.phrases[i]
                translationList.innerHTML += '<li>' + phrase + '</li>'
            }
            document.getElementById('reminder-block').classList.remove('hidden')
        })
    })

document.getElementById('remind').addEventListener('click',
    function (e) {
        var phrase = document.getElementById("phrase").value
        var uri = baseUri + "/vocabulary"
        post(uri, {
            'phrase': {
               'phrase': requestedPhrase,
               'language' : 'EN'
            },
            'translation': translation
        }).then(function (result) {
            alert("Vocabulary saved.")
        })
    })