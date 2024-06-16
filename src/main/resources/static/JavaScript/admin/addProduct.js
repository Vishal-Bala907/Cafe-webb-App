document.getElementById('cover-img').addEventListener('change', function(event) {
	const file = event.target.files[0];

	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const imagePreview = document.getElementById("category-image-tag");
			imagePreview.src = e.target.result;
				
		};
		reader.readAsDataURL(file);
	}
})

document.getElementById('itm-img').addEventListener('change', function(event) {
	const file = event.target.files[0];

	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const imagePreview = document.getElementById("item-image-tag");
			imagePreview.src = e.target.result;
				
		};
		reader.readAsDataURL(file);
	}
})