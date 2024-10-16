
//
let productsTemplateCate = "";
let shoppingBag = [];


window.addEventListener("load", () => {
	selectedCategoryProds = JSON.parse(sessionStorage.getItem("selected-cate"));
	console.log(selectedCategoryProds);
	setProducts(selectedCategoryProds);
	
})

/*SETTINUP PRODUCTS*/

/*/*<input id="selected-${prod.pro_Id}" class="qt" type="number" />*/
function setProducts(selectedCategoryProds) {
	selectedCategoryProds.forEach((prod) => {

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

		productsTemplateCate = productsTemplateCate + SameCateProds;
	})
	/*<button class="item-buy-buttons" >Buy</button> */
	document.getElementById("items-cards").innerHTML = productsTemplateCate;
	selectedCategoryProds.forEach((prod) => {
		document.getElementById(`add-button-${prod.pro_Id}`).addEventListener('click', () => {
			addToBagClicked(prod);
		})
	})
}

async function sendToStoreInCartToBackend(prod) {
	try {
		const res = await fetch(`/common/saveToCart/${prod.pro_Id}`, {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(prod)
		})
		if (!res.ok) {
			console.error("some error happedn")
			throw new Error("network errorvfor v")
		}
		const data = await res.json();
		return data;
	} catch (error) {
		console.log("error caught")
	}
}

function addToBagClicked(prod) {
	shoppingBag.push(prod)
	sendToStoreInCartToBackend(prod)
		.then(data => {
			console.log(data);
			// saving cart to session
			sessionStorage.setItem("cart",JSON.stringify(data));
			document.getElementById("cart").innerText = data.length;
		}).catch(error => {
			console.log(error)
		})

	//document.getElementById("cart").innerText = shoppingBag.length;
}

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