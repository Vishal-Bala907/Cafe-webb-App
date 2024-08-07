window.addEventListener('load', () => {
	getProfileDetails();
})

function getProfileDetails() {
	fetch("/empAndCus/getprofile")
		.then(res => {
			if (!res.ok) {
				throw new Error("unable to fetch profile")
			}
			return res.json();
		})
		.then(data => {
			console.log(data)
			setProfileData(data);
		}).catch(err => {
			console.error(err)
		})
}

function setProfileData(data) {
	console.log(data)
	document.getElementById("userName").innerText = data.username;
	
	if(data.email == null){
		document.getElementById("email").innerText = "unavailable";
	}else{
		document.getElementById("email").innerText = data.email;
	}
	
	document.getElementById("salary").innerText = data.salary;

	let role = "";
	if (data.role === "ROLE_ADMIN") {
		role = "ADMIN";
	}else if(data.role === "ROLE_CUSTOMER"){
		role = "CUSTOMER";
	}else if(data.role === "ROLE_EMPLOYEE"){
		role = "EMPLOYEE"
	}
	document.getElementById("role").innerText = role;

	document.getElementById("post").innerText = data.post;
	document.getElementById("doj").innerText = data.doj;
	document.getElementById("dol").innerText = data.dol;

	document.getElementById("profile-image").src = `/${data.user_image}`;

	document.getElementById("country").innerText = data.address.country;
	document.getElementById("state").innerText = data.address.state;
	document.getElementById("city").innerText = data.address.city;
	document.getElementById("street").innerText = data.address.street;
	document.getElementById("postal_code").innerText = data.address.postal_code;
	document.getElementById("house_no").innerText = data.address.house_no;

}