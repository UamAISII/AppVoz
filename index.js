/* global webkitSpeechRecognition, webkitSpeechGrammarList, webkitSpeechRecognitionEvent */

var synth = window.speechSynthesis;


var SpeechRecognition = SpeechRecognition || webkitSpeechRecognition;
var SpeechGrammarList = SpeechGrammarList || webkitSpeechGrammarList;
var SpeechRecognitionEvent = SpeechRecognitionEvent || webkitSpeechRecognitionEvent;

var diagnosticPara = document.querySelector('.output');
var testBtn = document.querySelector('button');


function testSpeech() {
    testBtn.disabled = true;
    testBtn.textContent = 'Consultando';
    testBtn.icon='img/microfono.png';
    //testBtn.style.backgroundImage='/img/microfono.jpg';



    diagnosticPara.style.background = 'rgba(0,0,0,0.2)';
    diagnosticPara.textContent = '...escuchando';
    var recognition = new SpeechRecognition();
    var speechRecognitionList = new SpeechGrammarList();
    recognition.grammars = speechRecognitionList;
    recognition.lang = 'es-MX';
    recognition.interimResults = false;
    recognition.maxAlternatives = 1;

    recognition.start();

    recognition.onresult = function (event) {
        var speechResult = event.results[0][0].transcript.toLowerCase();
        diagnosticPara.textContent = 'Consulta recibida: ' + speechResult + '.';
        diagnosticPara.style.background = 'lime';
        $.ajax({
            data: {
                pregunta: speechResult
            },
            type: 'GET',

            url: 'OntologiaServlet'
        })

                .done(function (responseText) {
                    if (synth.speaking) {
                        console.error('speechSynthesis.speaking');
                        return;
                    }

                    var utterThis = new SpeechSynthesisUtterance(responseText);
                    utterThis.onend = function (event) {
                        console.log('SpeechSynthesisUtterance.onend');
                    }
                    utterThis.onerror = function (event) {
                        console.error('SpeechSynthesisUtterance.onerror');
                    }

                    utterThis.lang = "es-MX";
                    utterThis.pitch = 1;
                    utterThis.rate = 1;
                    synth.speak(utterThis);

                    alert(responseText);

                })
                .fail(function (data) {
                    alert(data.responseText);
                });

        console.log('Confidence: ' + event.results[0][0].confidence);
    }

    recognition.onspeechend = function () {
        recognition.stop();
        testBtn.disabled = false;
        testBtn.textContent = 'Iniciar Consulta';
    }

    recognition.onerror = function (event) {
        testBtn.disabled = false;
        testBtn.textContent = 'Iniciar prueba';
        diagnosticPara.textContent = 'Ocurrió un error durante el reconocimiento de voz: ' + event.error;
    }

    recognition.onaudiostart = function (event) {
        //Ocurre cuando el agente inicia la captura del audio.
        console.log('SpeechRecognition.onaudiostart');
    }

    recognition.onaudioend = function (event) {
        //Ocurre cuando el agente termina la captura del audio.
        console.log('SpeechRecognition.onaudioend');
    }

    recognition.onend = function (event) {
        //Ocurre cuando el servicio de reconocimiento de voz está desconectado.
        console.log('SpeechRecognition.onend');
    }

    recognition.onnomatch = function (event) {
        //Ocurre cuando el servicio de reconocimiento de voz regresa un resultado sin reconocimiento. Esto incluye un grado de reconocimiento sin cumplir o exceder el límite de confidencialidad.
        console.log('SpeechRecognition.onnomatch');
    }

    recognition.onsoundstart = function (event) {
        //Ocurre cuando cualquier sonido (se reconozca o no) ha sido detectado.
        console.log('SpeechRecognition.onsoundstart');
    }

    recognition.onsoundend = function (event) {
        //Ocurre cuando cualquier sonido ((se reconozca o no) se detuvo de ser detectado.
        console.log('SpeechRecognition.onsoundend');
    }

    recognition.onspeechstart = function (event) {
        //Ocurre cuando el sonido ha sido reconocido por el servicio de reconocimiento como voz detectada.
        console.log('SpeechRecognition.onspeechstart');
    }
    recognition.onstart = function (event) {
        //Ocurre cuando el servicio de reconocimiento de voz ha iniciado a escuchar el audio de entrada con la intención de reconocer la gramática asociada con el reconocimiento de voz.
        console.log('SpeechRecognition.onstart');
    }
}

testBtn.addEventListener('click', testSpeech);

