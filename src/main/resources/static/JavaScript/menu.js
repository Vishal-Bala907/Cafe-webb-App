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
	console.log("jhh sduo hf \n",menuItems)
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

			<div class="button">
				
				<button id="add-button-${prod.pro_Id}" class="item-buy-buttons" >Add</button>
				
			</div>

		</div>`

		productsTemplate = productsTemplate + SameCateProds;
	})
	/*<button class="item-buy-buttons" >Buy</button>*/

	document.getElementById("menu-section").innerHTML = "";
	document.getElementById("menu-section").innerHTML = productsTemplate;

	menuItems.forEach((prod) => {
		document.getElementById(`add-button-${prod.pro_Id}`).addEventListener('click', () => {
			addToBagClicked(prod);
		})
	})
}
