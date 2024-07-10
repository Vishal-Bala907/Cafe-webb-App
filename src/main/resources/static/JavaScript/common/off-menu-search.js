
/*const addToOfflineBill = require("./off-menu");*/

function searchForTheItem() {
	const search = document.getElementById("off-bill-search").value;
	fetch(`/empAndCus/off-search/${search}`)
		.then(res => {
			if (!res.ok) {
				throw new Error("Unable to find")
			}
			return res.json();
		})
		.then(data => {
			setSearchedMenuLayout(data);
		})
		.catch(err => {
			console.error(err)
		})
}

function setSearchedMenuLayout(data) {
	let productsTemplate = ""
	data.forEach((prod) => {
		let SameCateProds = `<div class="search-item-card">
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
					<span id='search-incre-${prod.pro_Id}' class="incre-decre">+</span>
					<input id='search-count-${prod.pro_Id}' type="number" class="off-inp" />
					<span id='search-decre-${prod.pro_Id}' class="incre-decre">-</span>
				</div>
				
				<button id="search-add-button-${prod.pro_Id}" class="item-buy-buttons" >Add</button>
				</div>

		</div>`

		productsTemplate = productsTemplate + SameCateProds;
	})
	document.getElementById("search-result").style.display = "flex";
	document.getElementById("search-close").style.display = "flex";
	document.getElementById("search-result").innerHTML = "";
	document.getElementById("search-result").innerHTML = productsTemplate;

	data.forEach((prod) => {
		document.getElementById(`search-add-button-${prod.pro_Id}`).addEventListener('click', () => {
			addToOfflineBill(prod,"diff");
		})

		// increment
		document.getElementById(`search-incre-${prod.pro_Id}`).addEventListener('click', () => {
			searchIncrementCount(prod);
		})
		// decrement
		document.getElementById(`search-decre-${prod.pro_Id}`).addEventListener('click', () => {
			searchDecrementCount(prod);
		})
	})
}

function searchIncrementCount(prod) {
	let value = document.getElementById(`search-count-${prod.pro_Id}`).value;
	document.getElementById(`search-count-${prod.pro_Id}`).value = Number(value) + 1;
}
function searchDecrementCount(prod) {
	let value = document.getElementById(`search-count-${prod.pro_Id}`).value;
	if (value > 0) {
		document.getElementById(`search-count-${prod.pro_Id}`).value = Number(value) - 1;
	}
}

function CloseSearchedItemPage(){
	document.getElementById("search-result").style.display = "none";
	document.getElementById("search-close").style.display = "none";
}