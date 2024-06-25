var audio_id = document.getElementById("cs_audio");
var audio_vol = audio_id.volume;
audio_id.volume = 0.5;

// Thử catch nếu không thể lấy được metadata của audio
try {
    var measuredTime = new Date(null);
    measuredTime.setSeconds(audio_id.duration); // specify value of SECONDS
    var MHSTime = measuredTime.toISOString().substr(11, 8);
    var a = MHSTime.split(':'); // split it at the colons
    var minutes = (+a[0]) * 60 + (+a[1]);
    document.getElementById("cs_audio_duration").innerHTML = minutes + ":" + ((+a[2]) % 60);
} catch (err) {
    audio_id.addEventListener('loadedmetadata', function() {
        var measuredTime = new Date(null);
        measuredTime.setSeconds(audio_id.duration); // specify value of SECONDS
        var MHSTime = measuredTime.toISOString().substr(11, 8);
        var a = MHSTime.split(':'); // split it at the colons
        var minutes = (+a[0]) * 60 + (+a[1]);
        document.getElementById("cs_audio_duration").innerHTML = minutes + ":" + ((+a[2]) % 60);
    });
}

audio_id.ontimeupdate = function() {
    var audio_currentTime = audio_id.currentTime;
    var playingPercentage = (audio_currentTime / audio_id.duration) * 100;
    document.getElementsByClassName("cs_audio_bar_now")[0].style.width = playingPercentage + "%";

    var measuredTime = new Date(null);
    measuredTime.setSeconds(audio_currentTime); // specify value of SECONDS
    var MHSTime = measuredTime.toISOString().substr(11, 8);
    var spilited_time = MHSTime.split(':'); // split it at the colons
    var minutes = (+spilited_time[0]) * 60 + (+spilited_time[1]);
    document.getElementById("cs_audio_current_time").innerHTML = minutes + ":" + ((+spilited_time[2]) % 60);

    if (audio_id.paused) {
        document.getElementById("cs_audio_play").style.display = "inline-block";
        document.getElementById("cs_audio_pause").style.display = "none";
    } else {
        document.getElementById("cs_audio_pause").style.display = "inline-block";
        document.getElementById("cs_audio_play").style.display = "none";
    }

    if (audio_id.volume == 0) {
        document.getElementById("cs_audio_sound").style.color = "#ff8935";
    } else {
        document.getElementById("cs_audio_sound").style.color = "#fff";
    }
};

function playPause() {
    if (audio_id.paused) {
        audio_id.play();
    } else {
        audio_id.pause();
    }
}

document.getElementById("cs_audio_play").addEventListener("click", function() {
    audio_id.play();
});

document.getElementById("cs_audio_pause").addEventListener("click", function() {
    audio_id.pause();
});

document.onkeydown = function(event) {
    switch (event.keyCode) {
        case 37:
            event.preventDefault();
            var audio_currentTime = audio_id.currentTime;
            audio_id.currentTime = audio_currentTime - 5;
            break;
        case 38:
            event.preventDefault();
            audio_vol = audio_id.volume;
            if (audio_vol != 1) {
                try {
                    audio_id.volume = audio_vol + 0.02;
                } catch (err) {
                    audio_id.volume = 1;
                }
            }
            if (audio_id.volume == 0) {
                document.getElementById("cs_audio_sound").style.color = "#ff8935";
            } else {
                document.getElementById("cs_audio_sound").style.color = "#fff";
            }
            break;
        case 39:
            event.preventDefault();
            var audio_currentTime = audio_id.currentTime;
            audio_id.currentTime = audio_currentTime + 5;
            break;
        case 40:
            event.preventDefault();
            audio_vol = audio_id.volume;
            if (audio_vol != 0) {
                try {
                    audio_id.volume = audio_vol - 0.02;
                } catch (err) {
                    audio_id.volume = 0;
                }
            }
            if (audio_id.volume == 0) {
                document.getElementById("cs_audio_sound").style.color = "#ff8935";
            } else {
                document.getElementById("cs_audio_sound").style.color = "#fff";
            }
            break;
        case 32:
            event.preventDefault();
            playPause();
            break;
    }
};

audio_id.addEventListener('progress', function() {
    var totaltime = audio_id.duration;
    var ranges = [];
    for (var i = 0; i < audio_id.buffered.length; i++) {
        var buffTimeend = audio_id.buffered.end(i);
        var buffpercentage = (buffTimeend / totaltime) * 100;
        document.getElementsByClassName("cs_audio_bar_loaded")[0].style.width = buffpercentage + "%";
    }
}, false);

document.getElementsByClassName("cs_audio_bar")[0].addEventListener("click", function(event) {
    var vCurrentBarWidth = event.clientX - document.getElementsByClassName("cs_audio_bar")[0].offsetLeft;
    document.getElementsByClassName("cs_audio_bar_now")[0].style.width = vCurrentBarWidth + "px";
    audio_id.currentTime = ((vCurrentBarWidth / document.getElementsByClassName("cs_audio_bar")[0].offsetWidth) * 100 / 100) * audio_id.duration;
});

if (audio_id.paused) {
    document.getElementById("cs_audio_pause").style.display = "none";
} else {
    document.getElementById("cs_audio_play").style.display = "none";
}

audio_id.onvolumechange = function(event) {
    var audio_vol = audio_id.volume;
    var vol_width = audio_vol * 65;
    document.getElementsByClassName("cs_volume")[0].style.width = vol_width + "px";
};

document.getElementsByClassName("cs_volBar")[0].addEventListener("click", function(event) {
    var current_vol_width = event.clientX - document.getElementsByClassName("cs_volBar")[0].getBoundingClientRect().left;
    document.getElementsByClassName("cs_volume")[0].style.width = current_vol_width + "px";
    var calculated_vol = current_vol_width / 65;
    audio_id.volume = calculated_vol;
});

function cspd_change_music(music) {
    audio_id.pause();
        audio_id.setAttribute('src', music);
        audio_id.load();
        audio_id.play();
        try {
            var measuredTime = new Date(null);
            measuredTime.setSeconds(audio_id.duration); // specify value of SECONDS
            var MHSTime = measuredTime.toISOString().substr(11, 8);
            var a = MHSTime.split(':'); // split it at the colons
            var minutes = (+a[0]) * 60 + (+a[1]);
            document.getElementById("cs_audio_duration").innerHTML = minutes + ":" + ((+a[2]) % 60);
        } catch (err) {
            audio_id.addEventListener('loadedmetadata', function() {
                var measuredTime = new Date(null);
                measuredTime.setSeconds(audio_id.duration); // specify value of SECONDS
                var MHSTime = measuredTime.toISOString().substr(11, 8);
                var a = MHSTime.split(':'); // split it at the colons
                var minutes = (+a[0]) * 60 + (+a[1]);
                document.getElementById("cs_audio_duration").innerHTML = minutes + ":" + ((+a[2]) % 60);
            });
        }
}
document.addEventListener("DOMContentLoaded", function () {
    var audio_id = document.getElementById("cs_audio");
    var handle = document.getElementById("cs_audio_duration_handle");
    var bar = document.getElementsByClassName("cs_audio_bar")[0];
    var isDragging = false;

    handle.addEventListener("mousedown", function (e) {
        isDragging = true;
        document.addEventListener("mousemove", onDrag);
        document.addEventListener("mouseup", stopDrag);
    });

    function onDrag(e) {
        if (!isDragging) return;
        var barRect = bar.getBoundingClientRect();
        var offsetX = e.clientX - barRect.left;
        var percentage = Math.max(0, Math.min(1, offsetX / barRect.width));
        var newTime = percentage * audio_id.duration;

        audio_id.currentTime = newTime;
        updateProgressBar();
    }

    function stopDrag() {
        isDragging = false;
        document.removeEventListener("mousemove", onDrag);
        document.removeEventListener("mouseup", stopDrag);
    }

    function updateProgressBar() {
        var audio_currentTime = audio_id.currentTime;
        var totalDuration = audio_id.duration;
        var percentage = (audio_currentTime / totalDuration) * 100;

        document.getElementsByClassName("cs_audio_bar_now")[0].style.width = percentage + "%";
        handle.style.left = `calc(${percentage}% - 4px)`;
    }

    audio_id.addEventListener("timeupdate", updateProgressBar);
    audio_id.addEventListener("loadedmetadata", updateProgressBar);
    audio_id.addEventListener("progress", function () {
        var ranges = [];
        var totaltime = audio_id.duration;
        for (var i = 0; i < audio_id.buffered.length; i++) {
            var buffTimeend = audio_id.buffered.end(i);
            var buffpercentage = (buffTimeend / totaltime) * 100;
            document.getElementsByClassName("cs_audio_bar_loaded")[0].style.width = buffpercentage + "%";
        }
    });

    document.getElementsByClassName("cs_audio_bar")[0].addEventListener("click", function (e) {
        var barRect = bar.getBoundingClientRect();
        var offsetX = e.clientX - barRect.left;
        var percentage = offsetX / barRect.width;
        var newTime = percentage * audio_id.duration;

        audio_id.currentTime = newTime;
        updateProgressBar();
    });

    if (audio_id.paused) {
        document.getElementById("cs_audio_pause").style.display = "none";
    } else {
        document.getElementById("cs_audio_play").style.display = "none";
    }

    function playPause() {
        if (audio_id.paused) {
            audio_id.play();
        } else {
            audio_id.pause();
        }
    }

    document.getElementById("cs_audio_play").addEventListener("click", function () {
        audio_id.play();
    });
    document.getElementById("cs_audio_pause").addEventListener("click", function () {
        audio_id.pause();
    });

    document.onkeydown = function (event) {
        switch (event.keyCode) {
            case 37:
                event.preventDefault();
                audio_id.currentTime = Math.max(0, audio_id.currentTime - 5);
                break;
            case 38:
                event.preventDefault();
                audio_id.volume = Math.min(1, audio_id.volume + 0.02);
                break;
            case 39:
                event.preventDefault();
                audio_id.currentTime = Math.min(audio_id.duration, audio_id.currentTime + 5);
                break;
            case 40:
                event.preventDefault();
                audio_id.volume = Math.max(0, audio_id.volume - 0.02);
                break;
            case 32:
                event.preventDefault();
                playPause();
                break;
        }
    };

    document.getElementsByClassName("cs_volBar")[0].addEventListener("click", function (event) {
        var current_vol_width = event.clientX - this.getBoundingClientRect().left;
        var calculated_vol = current_vol_width / this.offsetWidth;
        audio_id.volume = calculated_vol;
    });
});
function updateSongTitle(link) {
    var url = link.getAttribute('onclick').match(/'([^']+)'/)[1]; // Lấy URL từ thuộc tính onclick
    var songName = url.substring(7, url.length - 4); // Lấy đoạn từ ký tự thứ 8 đến 4 ký tự cuối
    document.getElementById('cs_song_title').textContent = songName; // Cập nhật nội dung tên bài hát
}
var audio_id = document.getElementById("cs_audio");
var volume_handle = document.getElementById("cs_volume_handle");
var volume_bar = document.getElementsByClassName("cs_volBar")[0];
var isDraggingVolume = false;

volume_handle.addEventListener("mousedown", function(e) {
    isDraggingVolume = true;
    document.addEventListener("mousemove", onVolumeDrag);
    document.addEventListener("mouseup", stopVolumeDrag);
});

function onVolumeDrag(e) {
    if (!isDraggingVolume) return;
    var barRect = volume_bar.getBoundingClientRect();
    var offsetX = e.clientX - barRect.left;
    var percentage = Math.max(0, Math.min(1, offsetX / barRect.width));
    audio_id.volume = percentage;
    updateVolumeBar();
}

function stopVolumeDrag() {
    isDraggingVolume = false;
    document.removeEventListener("mousemove", onVolumeDrag);
    document.removeEventListener("mouseup", stopVolumeDrag);
}

function updateVolumeBar() {
    var audio_vol = audio_id.volume;
    var vol_width = audio_vol * 100;
    document.getElementsByClassName("cs_volume")[0].style.width = vol_width + "%";
    volume_handle.style.left = `calc(${vol_width}% - 4px)`;
}

audio_id.onvolumechange = function(event) {
    updateVolumeBar();
};

volume_bar.addEventListener("click", function(event) {
    var barRect = volume_bar.getBoundingClientRect();
    var offsetX = event.clientX - barRect.left;
    var percentage = offsetX / barRect.width;
    audio_id.volume = percentage;
    updateVolumeBar();
});