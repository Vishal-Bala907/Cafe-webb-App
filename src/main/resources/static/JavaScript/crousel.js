let currentIndex = 0;

function showSlide(index) {
	const slides = document.querySelectorAll(".carousel-item");
	if (index >= slides.length) currentIndex = 0;
	if (index < 0) currentIndex = slides.length - 1;
	slides.forEach((slide, i) => {
		slide.style.transform = `translateX(${-currentIndex * 100}%)`;
	});
}

function nextSlide() {
	currentIndex++;
	showSlide(currentIndex);
}

function prevSlide() {
	currentIndex--;
	showSlide(currentIndex);
}

// Auto-play the carousel every 3 seconds
setInterval(() => {
	nextSlide();
}, 3000);

