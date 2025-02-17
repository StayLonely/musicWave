const audio = document.getElementById("audio");
const playPauseButton = document.getElementById("play-pause-button");
const volumeControl = document.getElementById("volume-control");
const progressBar = document.getElementById("progress-bar");
const currentTimeDisplay = document.getElementById("current-time");
const totalTimeDisplay = document.getElementById("total-time");
const filePathFull = document.getElementById('data-file-path');
const audioPlayer = document.getElementById("audio-player");
let isPlaying = false;

document.querySelectorAll('.play-btn').forEach(button => {
    button.addEventListener('click', function() {

        const filePath = filePathFull.textContent; // Получаем путь из скрытого p

        if (filePath) {
            audio.src = "/uploads/" + filePath; // Устанавливаем путь в элемент audio
            audio.play().then(() => {
                audioPlayer.display
                playPauseButton.textContent = "Pause"; // Начинаем воспроизведение
                isPlaying = true;
            }).catch(error => {
                console.error("Не удалось воспроизвести аудио:", error);
            });
        } else {
            console.error("Путь к файлу не определён");
        }
    });
});

playPauseButton.addEventListener("click", () => {
    if (isPlaying) {
        audio.pause();
        playPauseButton.textContent = "Play";
    } else {
        audio.play();
        playPauseButton.textContent = "Pause";
    }
    isPlaying = !isPlaying;
});

volumeControl.addEventListener("input", () => {
    audio.volume = volumeControl.value;
});

audio.addEventListener("timeupdate", () => {
    const currentTime = audio.currentTime;
    trackDuration = audio.duration;

    const currentMinutes = Math.floor(currentTime / 60);
    const currentSeconds = Math.floor(currentTime % 60);
    const totalMinutes = Math.floor(trackDuration / 60);
    const totalSeconds = Math.floor(trackDuration % 60);

    currentTimeDisplay.textContent = `${currentMinutes}:${currentSeconds < 10 ? '0' : ''}${currentSeconds}`;
    totalTimeDisplay.textContent = `${totalMinutes}:${totalSeconds < 10 ? '0' : ''}${totalSeconds}`;

    const progress = (currentTime / trackDuration) * 100;
    progressBar.style.width = `${progress}%`;
});

// Интерактивный прогресс-бар
progressBar.parentNode.addEventListener('click', function(event) {
    const progressBarWidth = this.clientWidth; // Полная ширина прогресс-бара
    const offsetX = event.offsetX; // Позиция клика внутри прогресс-бара
    const newTime = (offsetX / progressBarWidth) * trackDuration; // Новое время в секундах
    audio.currentTime = newTime; // Устанавливаем текущее время аудио
});

// Обновление длительности после первой загрузки (это важно для корректности перематывания)
audio.addEventListener('loadedmetadata', () => {
    trackDuration = audio.duration;
});