window.addEventListener('load', () => {
	const sort = document.getElementById("role").value;

	getEmployeesOrCustomers(sort)
		.then(data => {
			console.log(data)
			populateUsers(data)
		})
		.catch(error => {
			console.log(error)
		})
})
function sortUser() {
	const sort = document.getElementById("role").value;
	getEmployeesOrCustomers(sort)
		.then(data => {
			console.log(data)
			populateUsers(data)
		})
		.catch(error => {
			console.log(error)
		})
}


async function getEmployeesOrCustomers(sort) {
	try {
		const res = await fetch(`/empAndCus/getEmpOrCus/${sort}`)

		if (!res.ok) {
			throw new Error("Something got wrong")
		}
		const data = await res.json();
		return data;
	} catch (error) {
		console.log("error", error);
	}
}

function populateUsers(data) {
	let users = "";
	if (data.length == 0) {
		document.getElementById("cards-container").innerHTML =
			`<div >
				<p>Empty List !!! </p>
			</div>`

		return;
	}
	data.forEach(user => {
		let role;
		if (user.role === "ROLE_EMPLOYEE") {
			role = "EMPLOYEE";
		} else if (user.role === "ROLE_CUSTOMER") {
			role = "CUSTOMER";
		} else if (user.role === "ROLE_ADMIN") {
			role = "ADMIN";
		}

		let userCard = `<div class="card" id='user-${user.u_id}'>
				<img src="../${user.user_image}" alt="user image" />
				<div class="contents">
					<h2 id="name" class="name">${user.username}</h2>
					<p id="role" class="role">${role}</p>
					<p class="position" id="position">${user.post}</p>
				</div>
			</div>`

		users += userCard;

	})
	document.getElementById("cards-container").innerHTML = users;
	data.forEach(user => {
		document.getElementById(`user-${user.u_id}`).addEventListener('click', () => {
			cardClicked(user);
		})
	})
}


function printDiv() {
	window.print();
}

function cardClicked(user) {
	console.log(user.address);
	document.getElementsByClassName("details")[0].style.display = "flex";
	let role;
	if (user.role === "ROLE_EMPLOYEE") {
		role = "EMPLOYEE";
	} else if (user.role === "ROLE_CUSTOMER") {
		role = "CUSTOMER";
	} else if (user.role === "ROLE_ADMIN") {
		role = "ADMIN";
	}
	document.getElementById('userimage').src = `../${user.user_image}`;
	document.getElementById('username').innerText = user.username;
	document.getElementById('post').innerText = user.post;
	document.getElementById('userRole').innerText = role;
	document.getElementById('salary').innerText = user.salary;
	document.getElementById('doj').innerText = user.doj;
	document.getElementById('dol').innerText = user.dol;

	document.getElementById('city').innerText = user.address.city;
	document.getElementById('country').innerText = user.address.country;
	document.getElementById('house_no').innerText = user.address.house_no;
	document.getElementById('postal_code').innerText = user.address.postal_code;
	document.getElementById('state').innerText = user.address.state;
	document.getElementById('street').innerText = user.address.street;
}

function closeDetailsSection() {
	document.getElementsByClassName("details")[0].style.display = "none";
}