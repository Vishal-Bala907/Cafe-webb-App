window.addEventListener('load', () => {

})

// searching emp
function searchEmp() {
	let username = document.getElementById('add-emp-search').value;
	if (username === "") {
		alert("Please Enter some name")
		return;
	}
	// fetching user with the name
	fetchUserToMakeEmp(username)
		.then(data => {
			fillUserDetailsToForm(data);
		})
		.catch(error => {
			console.log(error)
			
		})

}

async function fetchUserToMakeEmp(username) {
	try {
		const res = await fetch(`/empAndCus/userByName/${username}`);
		if (!res.ok) {
			throw new Error("Some error occured")
		}
		const data = await res.json();
		return data;

	} catch (error) {
		console.error("Something went wrong", error)
	}
}

function fillUserDetailsToForm(data){
	if(data === undefined){
		document.getElementById("isUserExists").style.padding = "0 10px";
		document.getElementById("isUserExists").innerText = "Username not found";
	}else{
		document.getElementById("u_id").value = data.u_id
		document.getElementById("name").value = data.username
		document.getElementById("doj").value = data.doj
		document.getElementById("dol").value = data.dol
		document.getElementById("salary").value = data.salary
	}
}