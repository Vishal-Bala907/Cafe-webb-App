window.addEventListener('click', () => {

})
document.getElementById('mng-img').addEventListener('change', function(event) {
	const file = event.target.files[0];

	if (file) {
		const reader = new FileReader();
		reader.onload = function(e) {
			const imagePreview = document.getElementById("manage-image-tag");
			imagePreview.src = e.target.result;

		};
		reader.readAsDataURL(file);
	}
})


function populateCategory() {
	const prod = JSON.parse(sessionStorage.getItem('prod-man'))
	document.getElementById("category-image-tag").src = `../${prod.cover}`;
	document.getElementById("name").value = prod.catagoryName;
	document.getElementById("c_Id").value = prod.c_Id;
	populateSubitemsToUpdate()
}

function populateSubitemsToUpdate() {
	const manageCategory = JSON.parse(sessionStorage.getItem('prod-man'));
	const selectedCategoryProds = manageCategory.products;
	let productsTemplateCate = "";

	selectedCategoryProds.forEach((prod) => {

		let SameCateProds = `<div class="item-card" id='manage-prod-card-${prod.pro_Id}'>
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
				<p class="actual">Rs: ${parseInt(prod.productPrice - prod.productPrice * (prod.discount / 100))} only</p>
			</div>

			<div class="button">
				
				<button id="manage-button-${prod.pro_Id}" class="item-buy-buttons" >Update</button>
				<button id="delete-prod-button-${prod.pro_Id}" class="item-buy-buttons" >DELETE</button>
			</div>

		</div>`

		productsTemplateCate = productsTemplateCate + SameCateProds;
	})
	document.getElementById("manage-prod-container").innerHTML = productsTemplateCate;
	selectedCategoryProds.forEach((prod) => {
		document.getElementById(`manage-button-${prod.pro_Id}`).addEventListener('click', () => {
			updateProduct(prod);
		})
		document.getElementById(`delete-prod-button-${prod.pro_Id}`).addEventListener('click', () => {
			deleteProduct(prod);
		})
	})
}

function setProperties(product) {
	document.getElementById("manage-image-tag").src = `../${product.productImage}`;

	document.getElementById("prod-name").value = product.productName

	document.getElementById("price").value = product.productPrice
	document.getElementById("dis").value = product.discount

	document.getElementById("sold").value = product.sold
	document.getElementById("cate_name").value = product.categoryName
	document.getElementById("dis").value = product.discount
	document.getElementById("pro_Id").value = product.pro_Id
}

function updateProduct(product) {
	console.log(product)
	document.getElementById("manage-item-container").style.display = 'flex';
	setProperties(product)


}
function deleteProduct(product) {
	fetch("/common/deleteProduct", {
		method: "DELETE",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify(product)
	}).then(res => {
		if (!res.ok) {
			throw new Error('Network response was not ok');
		}
		return res.json();
	}).then(data => {
		console.log(data)
		document.getElementById(`manage-prod-card-${product.pro_Id}`).innerHTML = ""
		document.getElementById(`manage-prod-card-${product.pro_Id}`).outerHTML = ""
		removeProdFromSession(product)
	}).catch(err => {
		console.log(err)
	})
}

function removeProdFromSession(product){
	const prodman = JSON.parse(sessionStorage.getItem('prod-man'));
	const prods = prodman.products;
	prodman.products = prods.filter(obj=>{return product.pro_Id != obj.pro_Id;})
	sessionStorage.setItem('prod-man',JSON.stringify(prodman));
	//prodman.filter()
}

function openCloseItemUpdateForm() {
	document.getElementById("manage-item-container").style.display = 'none';
}


