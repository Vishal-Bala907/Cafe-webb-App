window.addEventListener('load', () => {
	getProfileDetailsForUpdate();
	setAddressDetails();
})

function getProfileDetailsForUpdate() {
	fetch("/empAndCus/getprofile")
		.then(res => {
			if (!res.ok) {
				throw new Error("unable to fetch profile")
			}
			return res.json();
		})
		.then(data => {
			setProfileDataForUpdate(data);
		}).catch(err => {
			console.error(err)
		})
}

function setProfileDataForUpdate(data) {
	console.log(data)
	document.getElementById("name").value = data.username;
	document.getElementById("email").value = data.email;
	document.getElementById("u_id").value = data.u_id;


	document.getElementById("selected-image-tag").src = `/${data.user_image}`;



}