if (
    !(
        window.File &&
        window.FileReader &&
        window.FileList &&
        window.Blob
    )
) {
    document.querySelector("#FileDrop #Text").textContent =
        "Reading files not supported by this browser";
} else {
    const fileDrop = document.querySelector("#FileDrop");

    fileDrop.addEventListener("dragenter", () =>
        fileDrop.classList.add("Hover")
    );

    fileDrop.addEventListener("dragleave", () =>
        fileDrop.classList.remove("Hover")
    );

    fileDrop.addEventListener("drop", () =>
        fileDrop.classList.remove("Hover")
    );

    document
        .querySelector("#FileDrop input")
        .addEventListener("change", (e) => {
            //get the files
            const files = e.target.files;
            if (files.length > 0) {
                const file = files[0];
                document.querySelector(
                    "#FileDrop #Text"
                ).textContent = file.name;
                parseFile(file);
            }
        });
}

let currentMidi = null;

function parseFile(file) {
    //read the file
    const reader = new FileReader();
    reader.onload = function (e) {
        const midi = new Midi(e.target.result);
        document.querySelector(
            "#ResultsText"
        ).value = JSON.stringify(midi, undefined, 2);
        document
            .querySelector("tone-play-toggle")
            .removeAttribute("disabled");
        currentMidi = midi;
    };
    reader.readAsArrayBuffer(file);
}

const synths = [];
document
    .querySelector("tone-play-toggle")
    .addEventListener("play", (e) => {
        const playing = e.detail;
        if (playing && currentMidi) {
            const now = Tone.now() + 0.5;
            currentMidi.tracks.forEach((track) => {
                //create a synth for each track
                const synth = new Tone.PolySynth(Tone.Synth, {
                    envelope: {
                        attack: 0.02,
                        decay: 0.1,
                        sustain: 0.3,
                        release: 1,
                    },
                }).toDestination();
                synths.push(synth);
                //schedule all of the events
                track.notes.forEach((note) => {
                    synth.triggerAttackRelease(
                        note.name,
                        note.duration,
                        note.time + now,
                        note.velocity
                    );
                });
            });
        } else {
            //dispose the synth and make a new one
            while (synths.length) {
                const synth = synths.shift();
                synth.disconnect();
            }
        }
    });

