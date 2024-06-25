
let userCartInSession = [];

window.addEventListener('load', () => {
	userCartInSession = JSON.parse(sessionStorage.getItem('cart'))
	populateCart();
	calculateTotal();
	setAddressDetails();
})

function populateCart() {
	let cartInnerHtml = "";
	if (userCartInSession.length == 0) {
		document.getElementById('cart-items-area').innerHTML = `<h1>No Items in the cart!</h1>`
		return;
	}
	userCartInSession.forEach(cart => {
		let oneCart = `<div class="items-area" id='cart-item-${cart.cart[0].cartId}'>
				<div class="item-card">
					<div class="imagecover">
						<img class="cover-card-img" src="../${cart.productImage}"
							alt="Image 3" /> <span id="discount">${cart.discount} %off</span>
					</div>
					<div class="name">
						<p>
							<b>${cart.productName}</b>
						</p>
					</div>
					<div class="price">
						<p class="mrp" style="text-decoration: line-through;">Rs: ${cart.productPrice}</p>
						<p class="actual">Rs: ${cart.discountedPrice} only</p>
					</div>

					<div class="button">
						<button id='remove-from-cart-${cart.cart[0].cartId}' class="item-buy-buttons">Remove</button>
					</div>
				</div>
			</div>`

		cartInnerHtml += oneCart;
	})

	document.getElementById('cart-items-area').innerHTML = cartInnerHtml;
	userCartInSession.forEach(cart => {
		document.getElementById(`remove-from-cart-${cart.cart[0].cartId}`).addEventListener('click', () => {
			removeFromCart(cart);
		})
	})
}

function removeFromCart(cart) {

	fetch(`/empAndCus/remove-from-cart/${cart.cart[0].cartId}`)
		.then(res => {
			if (!res.ok) {
				throw new Error("Some error got occuered")
			}
			return res.json();
		})
		.then(data => {
			sessionStorage.setItem("cart", JSON.stringify(data));
			calculateTotal();
			document.getElementById("cart").innerText = data.length;
		})
		.catch(error => {
			console.log(error)
		})

	document.getElementById(`cart-item-${cart.cart[0].cartId}`).innerHTML = ""
}

function calculateTotal(){
	let total = JSON.parse(sessionStorage.getItem('cart'))
	let actualAmt = 0;
	let amt = 0;
	total.forEach(item=>{
		actualAmt += item.productPrice;
		amt += item.discountedPrice;
	})
	document.getElementById('act-amt').innerText = actualAmt;
	document.getElementById('amt').innerText = amt;
}


// Address work
function setAddressDetails(){
	fetch("/empAndCus/get-address")
	.then(res=>{
		if(!res.ok){
			throw new Error("Unable to fetch address")
		}
		return res.json();
	})
	.then(data=>{
		console.log(data)
		fillAddressDetails(data);
	})
	.catch(error=>{
		console.error(error)
	})
}

function fillAddressDetails(data){
	document.getElementById('country').value = data.country;
	document.getElementById('state').value = data.state;
	document.getElementById('city').value = data.city;
	document.getElementById('street').value = data.street;
	document.getElementById('house_no').value = data.house_no;
	document.getElementById('postal_code').value = data.postal_code;
	document.getElementById('add_id').value = data.add_id;
	
	
}