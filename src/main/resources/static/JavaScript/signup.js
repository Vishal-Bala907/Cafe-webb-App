
let showHideCounter = 0;
let conShowHideCounter = 0;

document.getElementById('img').addEventListener('change', function(event) {
	const file = event.target.files[0];

	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const imagePreview = document.getElementById("selected-image");
			imagePreview.src = e.target.result;
		};
		reader.readAsDataURL(file);
	}
})


let filteredList = []
function mathBothPassword(event) {
	let pass = document.getElementById("pass").value;
	let con_pass = document.getElementById("con-pass").value;
	let con_pass_span = document.getElementById("con-pass-span");

	

	if (pass === con_pass) {
		con_pass_span.innerText = "Password matched..."
		con_pass_span.className = "matched-password"		

	} else {
		con_pass_span.innerText = "Password did not matched..."
		con_pass_span.className = "matched-not-password"	

	}

}


function hideAndShowPassword(event){
	showHideCounter++;
	if(showHideCounter %2 == 0){
		// show
		document.getElementById("eye").innerText = "visibility_off";
		document.getElementById("pass").type = "text";
	}else{
		//hide
		document.getElementById("eye").innerText = "visibility";
		document.getElementById("pass").type = "password";
	}
}
function conHideAndShowPassword(event){
	conShowHideCounter++;
	if(conShowHideCounter %2 == 0){
		// show
		document.getElementById("con-eye").innerText = "visibility_off";
		document.getElementById("con-pass").type = "text";
	}else{
		//hide
		document.getElementById("con-eye").innerText = "visibility";
		document.getElementById("con-pass").type = "password";
	}
}