var audio_id = document.getElementById("cs_audio");
var audio_vol = audio_id.volume;
audio_id.volume = 0.5;

function updateDuration() {
    var measuredTime = new Date(null);
    measuredTime.setSeconds(audio_id.duration);
    var MHSTime = measuredTime.toISOString().substr(11, 8);
    var a = MHSTime.split(':');
    var minutes = (+a[0]) * 60 + (+a[1]);
    document.getElementById("cs_audio_duration").innerHTML = minutes + ":" + ((+a[2]) % 60);
}

audio_id.addEventListener('loadedmetadata', updateDuration);

audio_id.ontimeupdate = function() {
    var audio_currentTime = audio_id.currentTime;
    var playingPercentage = (audio_currentTime / audio_id.duration) * 100;
    document.getElementsByClassName("cs_audio_bar_now")[0].style.width = playingPercentage + "%";

    // Cập nhật vị trí của handle
    document.getElementById("cs_audio_duration_handle").style.left = `calc(${playingPercentage}% - 4px)`;

    // Cập nhật thời gian hiện tại
    var minutes = Math.floor(audio_currentTime / 60);
    var seconds = Math.floor(audio_currentTime % 60).toString().padStart(2, '0');
    document.getElementById("cs_audio_current_time").innerHTML = minutes + ":" + seconds;

    // Hiển thị nút phát/tạm dừng
    if (audio_id.paused) {
        document.getElementById("cs_audio_play").style.display = "inline-block";
        document.getElementById("cs_audio_pause").style.display = "none";
    } else {
        document.getElementById("cs_audio_pause").style.display = "inline-block";
        document.getElementById("cs_audio_play").style.display = "none";
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
            break;
        case 32:
            event.preventDefault();
            playPause();
            break;
    }
};

audio_id.addEventListener('progress', function() {
    var totaltime = audio_id.duration;
    for (var i = 0; i < audio_id.buffered.length; i++) {
        var buffTimeend = audio_id.buffered.end(i);
        var buffpercentage = (buffTimeend / totaltime) * 100;
        document.getElementsByClassName("cs_audio_bar_loaded")[0].style.width = buffpercentage + "%";
    }
}, false);

document.getElementsByClassName("cs_audio_bar")[0].addEventListener("click", function(event) {
    var vCurrentBarWidth = event.clientX - document.getElementsByClassName("cs_audio_bar")[0].offsetLeft;
    var percentage = vCurrentBarWidth / document.getElementsByClassName("cs_audio_bar")[0].offsetWidth;
    audio_id.currentTime = percentage * audio_id.duration;
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
    var calculated_vol = current_vol_width / 65;
    audio_id.volume = calculated_vol;
});

document.addEventListener("DOMContentLoaded", function() {
    // Kiểm tra nếu có bài hát được lưu trong local storage thì thiết lập lại
    var selectedSong = JSON.parse(localStorage.getItem('selectedSong'));
    if (selectedSong) {
        cspd_change_music(selectedSong.filePath);
        updateSongTitle(selectedSong.title);
    }
});

function cspd_change_music(music) {
    audio_id.pause();  // Dừng bài hát hiện tại
    audio_id.setAttribute('src', music); // Thiết lập nguồn audio mới
    audio_id.load(); // Load lại nguồn audio mới
    audio_id.addEventListener('loadedmetadata', function() {
        updateDuration(); // Cập nhật thời lượng cho bài hát mới
        audio_id.play(); // Sau khi load xong, chạy bài hát mới
    });
}


document.addEventListener("DOMContentLoaded", function() {
    const showNavbar = (toggleId, navId, bodyId, headerId) => {
        const toggle = document.getElementById(toggleId),
            nav = document.getElementById(navId),
            bodypd = document.getElementById(bodyId),
            headerpd = document.getElementById(headerId);

        if(toggle && nav && bodypd && headerpd) {
            toggle.addEventListener('click', () => {
                nav.classList.toggle('show');
                toggle.classList.toggle('bx-x');
                bodypd.classList.toggle('body-pd');
                headerpd.classList.toggle('body-pd');
            });
        }
    }
    showNavbar('header-toggle', 'nav-bar', 'body-pd', 'header');

    const linkColor = document.querySelectorAll('.nav_link');
    function colorLink() {
        if(linkColor) {
            linkColor.forEach(l => l.classList.remove('active'));
            this.classList.add('active');
        }
    }
    linkColor.forEach(l => l.addEventListener('click', colorLink));

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
        document.getElementById("cs_audio_duration_handle").style.left = `calc(${percentage}% - 4px)`;
    }

    audio_id.addEventListener('timeupdate', function() {
        var currentTime = audio_id.currentTime;
        document.getElementsByClassName('cs_audio_current_time')[0].textContent = formatTime(currentTime);
    });
    audio_id.addEventListener("loadedmetadata", updateProgressBar);
    audio_id.addEventListener("progress", function () {
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
    var url = link.getAttribute('onclick').match(/'([^']+)'/)[1];
    var songName = url.substring(7, url.length - 4);
    document.getElementById('cs_song_title').textContent = songName;
}

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
document.addEventListener("DOMContentLoaded", function(event) {
        const navBar = document.getElementById('nav-bar');
        navBar.addEventListener('mouseenter', () => {
            navBar.classList.add('expanded');
        });
        navBar.addEventListener('mouseleave', () => {
            navBar.classList.remove('expanded');
        });
    });
function reinitializeAudioControls() {
    var audio_id = document.getElementById("cs_audio");
    var playButton = document.getElementById("cs_audio_play");
    var pauseButton = document.getElementById("cs_audio_pause");

    // Play/Pause functionality
    playButton.addEventListener("click", function() {
        audio_id.play();
    });

    pauseButton.addEventListener("click", function() {
        audio_id.pause();
    });

    audio_id.ontimeupdate = function() {
        var playingPercentage = (audio_id.currentTime / audio_id.duration) * 100;
        document.getElementsByClassName("cs_audio_bar_now")[0].style.width = playingPercentage + "%";

        if (audio_id.paused) {
            playButton.style.display = "inline-block";
            pauseButton.style.display = "none";
        } else {
            pauseButton.style.display = "inline-block";
            playButton.style.display = "none";
        }
    };

    // Audio bar click to change current time
    document.getElementsByClassName("cs_audio_bar")[0].addEventListener("click", function(event) {
        var barRect = this.getBoundingClientRect();
        var offsetX = event.clientX - barRect.left;
        var percentage = offsetX / this.offsetWidth;
        audio_id.currentTime = percentage * audio_id.duration;
    });

    // Volume bar click to change volume
    document.getElementsByClassName("cs_volBar")[0].addEventListener("click", function(event) {
        var barRect = this.getBoundingClientRect();
        var offsetX = event.clientX - barRect.left;
        var percentage = offsetX / this.offsetWidth;
        audio_id.volume = percentage;
    });

    // Update current time display
    audio_id.addEventListener('timeupdate', function() {
        var audio_currentTime = audio_id.currentTime;
        var minutes = Math.floor(audio_currentTime / 60);
        var seconds = Math.floor(audio_currentTime % 60).toString().padStart(2, '0');
        document.getElementById("cs_audio_current_time").innerHTML = minutes + ":" + seconds;
    });
}
function loadPage(url, addToHistory = true) {
    $.ajax({
        url: url,
        success: function (data) {
            $('#content').html(data); // Chỉ cập nhật nội dung trong phần tử #content
            if (addToHistory) {
                history.pushState(null, null, url);
            }
            reinitializeAudioControls(); // Reinitialize audio controls nếu cần
        }
    });
}
const audio = document.getElementById('cs_audio'); // Đối tượng âm thanh của bạn
const volumeHandle = document.querySelector('.cs_volume_handle');
const volumeBar = document.querySelector('.cs_volume');

// Lưu trạng thái của âm lượng vào localStorage
function saveVolumeState() {
    localStorage.setItem('cs_audioplayer_volume', audio.volume);
}

// Khôi phục trạng thái của âm lượng từ localStorage
function restoreVolumeState() {
    let savedVolume = localStorage.getItem('cs_audioplayer_volume');
    if (savedVolume !== null) {
        audio.volume = parseFloat(savedVolume);
        updateVolumeUI(audio.volume);
    }
}

// Xử lý sự kiện khi thay đổi âm lượng
audio.addEventListener('volumechange', function() {
    saveVolumeState();
    updateVolumeUI(audio.volume);
});

// Khôi phục trạng thái âm lượng khi trang được tải lại
document.addEventListener('DOMContentLoaded', function() {
    restoreVolumeState();
});
function updateVolumeUI(volume) {
    const volumePercentage = volume * 100;
    volumeBar.style.width = `${volumePercentage}%`;
    volumeHandle.style.left = `${volumePercentage}%`;
}
