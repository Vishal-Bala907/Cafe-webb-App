window.addEventListener('load', () => {
	console.log("Menu button clicked"); // Debugging statement
	fullCategoryData()
		.then(data => {
			console.log("Categories fetched successfully:", data); // Debugging statement
			let categoryData = JSON.stringify(data);
			sessionStorage.setItem("cate-data", categoryData);
			populateMenuNavBar();
		})
		.catch(error => {
			console.error("Error handling categories:", error); // Log error for debugging
		});

})
window.addEventListener('resize', () => {
	try {
		setBillResponsiveness();
	} catch (err) {
		console.error(err)
	}
})

let populateMenuNavBar = () => {
	let cate = JSON.parse(sessionStorage.getItem("cate-data"));
	let menuUl = document.getElementById("menu-cate");
	cate.forEach(item => {
		const li = document.createElement("li");
		li.id = `cate-${item.c_Id}`;
		li.textContent = item.catagoryName
		menuUl.appendChild(li);
	})
	cate.forEach(item => {
		document.getElementById(`cate-${item.c_Id}`).addEventListener('click', () => {
			menuCategoryClicked(item);
		})
	})
	menuCategoryClicked(cate[0])
}

function menuCategoryClicked(item) {
	fetchDataOfTheCategory(item.c_Id)
		.then(data => {
			sessionStorage.setItem("selected-menu-cate", JSON.stringify(data));
			setMenuLayout();
		})
		.catch(error => {
			console.log(error)
		})
}

/*<input id="selected-${prod.pro_Id}" class="qt" type="number" />*/

function setMenuLayout() {
	let menuItems = JSON.parse(sessionStorage.getItem("selected-menu-cate"))
	let productsTemplate = ""
	console.log("jhh sduo hf \n", menuItems)
	menuItems.forEach((prod) => {
		let SameCateProds = `<div class="item-card">
			<div class="imagecover">
				<img class="cover-card-img" src="../${prod.productImage}"
					alt="Image 3" /> <span id="discount">${prod.discount}% off</span>
			</div>
			<div class="name">
				<p>
					<b>${prod.productName}</b>
				</p>
			</div>
			<div class="price">
				<p class="mrp" style="text-decoration: line-through;">Rs: ${prod.productPrice}</p>
				<p class="actual">Rs: ${prod.discountedPrice} only</p>
			</div>

			<div class="off-button">
				
				<div >
					<span id='incre-${prod.pro_Id}' class="incre-decre">+</span>
					<input id='count-${prod.pro_Id}' type="number" class="off-inp" />
					<span id='decre-${prod.pro_Id}' class="incre-decre">-</span>
				</div>
				
				<button id="add-button-${prod.pro_Id}" class="item-buy-buttons" >Add</button>
				</div>

		</div>`

		productsTemplate = productsTemplate + SameCateProds;
	})

	document.getElementById("menu-section").innerHTML = "";
	document.getElementById("menu-section").innerHTML = productsTemplate;

	menuItems.forEach((prod) => {
		document.getElementById(`add-button-${prod.pro_Id}`).addEventListener('click', () => {
			addToOfflineBill(prod,"same");
		})

		// increment
		document.getElementById(`incre-${prod.pro_Id}`).addEventListener('click', () => {
			incrementCount(prod);
		})
		// decrement
		document.getElementById(`decre-${prod.pro_Id}`).addEventListener('click', () => {
			decrementCount(prod);
		})
	})
}

function incrementCount(prod) {
	let value = document.getElementById(`count-${prod.pro_Id}`).value;
	document.getElementById(`count-${prod.pro_Id}`).value = Number(value) + 1;
}
function decrementCount(prod) {
	let value = document.getElementById(`count-${prod.pro_Id}`).value;
	if (value > 0) {
		document.getElementById(`count-${prod.pro_Id}`).value = Number(value) - 1;
	}
}

let offItems = [];
function addToOfflineBill(prod,source) {
	console.log(prod.sold)
	let count ;
	if(source === "same"){
		count = document.getElementById(`count-${prod.pro_Id}`).value;
	}else{
		count = document.getElementById(`search-count-${prod.pro_Id}`).value;
	}

	if (count > 0) {

		// Create a new row element
		const newRow = document.createElement('tr');
		newRow.className = 'tr';

		// Create and append cells to the new row
		const cell1 = document.createElement('td');
		cell1.className = 'td';
		cell1.textContent = offItems.length + 1;
		newRow.appendChild(cell1);

		const cell2 = document.createElement('td');
		cell2.className = 'td';
		cell2.textContent = prod.productName;
		newRow.appendChild(cell2);

		const cell3 = document.createElement('td');
		cell3.className = 'td';
		cell3.textContent = prod.discountedPrice;
		newRow.appendChild(cell3);

		const cell4 = document.createElement('td');
		cell4.className = 'td';
		cell4.textContent = count;
		newRow.appendChild(cell4);

		// Append the new row to the table body
		document.getElementById("tbody").appendChild(newRow);

		// updating values
		prod.sold = Number(prod.sold) + Number(count);
		const item = {
			item: prod,
			count: count
		}
		offItems.push(item);
		console.log(offItems)
		sessionStorage.setItem("off-bill", JSON.stringify(offItems));

	}
}
function createOfflineBill() {
	// sending the product data
	const data = JSON.parse(sessionStorage.getItem("off-bill"));
	let billData = [];
	data.forEach(obj => {
		billData.push(obj.item);
	})
	console.log(billData)
	fetch('/empAndCus/create-off-bill', {
		method: "PUT",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(billData)
	})
		.then(res => {
			if (!res.ok) {
				throw new Error("Error creating order");
			}
			return res.json();
		})
		.then(data => {
			alert("Order placed !!!")
			location.reload();
		})
		.catch(err => {
			console.error(err);
		})
}
let billCounter = 0;
function openCloseBill() {
	let bill = document.getElementsByClassName("bill")[0];
	let items_selection = document.getElementsByClassName("items-selection")[0];
	let offline_close = document.getElementsByClassName('offline-close')[0];

	if (billCounter % 2 != 0) {
		// close
		bill.style.display = "none"
		offline_close.style.right = "0px";
		items_selection.style.width = "100%";
		offline_close.innerText = "open_in_full";
	} else {
		// open
		bill.style.display = "flex"
		offline_close.style.right = "0px";
		/*items_selection.style.width = "60%";*/
		offline_close.innerText = "close";
	}
	billCounter++;

}
function setBillResponsiveness() {
	let bill = document.getElementsByClassName("bill")[0];
	let items_selection = document.getElementsByClassName("items-selection")[0];
	if (window.innerWidth <= 860) {
		bill.style.width = '60%';
		bill.style.display = "flex";
		bill.style.flexDirection = "column";
		bill.style.position = "absolute";
		bill.style.right = "0";
		bill.style.height = "100%";
		items_selection.style.width = "100%";
	} else {

		bill.style.display = "flex";
		bill.style.width = '40%';
		bill.style.flexDirection = "column";
		items_selection.style.width = "60%";

	}
}

