/*
		Line no 120 -> responsiveness of the payment form for mobile devices

 */
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

function updateAddressFromCart(event){
	event.preventDefault();
	let add_id = document.getElementById("add_id").value;
	let country = document.getElementById("country").value;
	let state = document.getElementById("state").value;
	let city = document.getElementById("city").value;
	let street = document.getElementById("street").value;
	let house_no = document.getElementById("house_no").value;
	let postal_code = document.getElementById("postal_code").value;
	
	let address = JSON.stringify({
		add_id:add_id,
		country:country,
		state:state,
		city:city,
		street:street,
		house_no:house_no,
		postal_code:postal_code
	});
	
	fetch("/empAndCus/update-address",{
		method:"PUT",
		headers:{
			"Content-Type":"application/json"
		},
		body:address
	})
	.then(res=>{
		if(!res.ok){
			throw new Error("unable to update address");
		}
		return res.json();
	})
	.then(data=>{
		console.log(data);
	})
	.catch(error=>{
		console.error("address update error",error)
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

function calculateTotal() {
	let total = JSON.parse(sessionStorage.getItem('cart'))
	let actualAmt = 0;
	let amt = 0;
	total.forEach(item => {
		actualAmt += item.productPrice;
		amt += item.discountedPrice;
	})
	document.getElementById('act-amt').innerText = actualAmt;
	document.getElementById('amt').innerText = amt;
}


// Address work
function setAddressDetails() {
	fetch("/empAndCus/get-address")
		.then(res => {
			if (!res.ok) {
				throw new Error("Unable to fetch address")
			}
			return res.json();
		})
		.then(data => {
			console.log(data)
			fillAddressDetails(data);
		})
		.catch(error => {
			console.error(error)
		})
}

function fillAddressDetails(data) {
	document.getElementById('country').value = data.country;
	document.getElementById('state').value = data.state;
	document.getElementById('city').value = data.city;
	document.getElementById('street').value = data.street;
	document.getElementById('house_no').value = data.house_no;
	document.getElementById('postal_code').value = data.postal_code;
	document.getElementById('add_id').value = data.add_id;


}

/*******/

let billClickCount_mobD = 0;
document.getElementById("payment-open-close").addEventListener('click', () => {
	if (billClickCount_mobD % 2 == 0) {
		document.getElementById("billing-area").style.display = "flex";
		document.getElementById("payment-open-close").innerText = "close"
		document.getElementById("payment-open-close").style.zIndex = 2;
	} else {
		document.getElementById("billing-area").style.display = "none";
		document.getElementById("payment-open-close").innerText = "bill"
		document.getElementById("payment-open-close").style.zIndex = 1;
	}
	billClickCount_mobD++;
})

//RESIZE EVEN
window.addEventListener("resize", () => {
	if (window.innerWidth <= 687) {
		document.getElementById("billing-area").style.display = "none";
		document.getElementById("payment-open-close").innerText = "bill"
		document.getElementById("payment-open-close").style.zIndex = 1;
	} else {
		document.getElementById("billing-area").style.display = "flex";
		document.getElementById("payment-open-close").innerText = "close"
		document.getElementById("payment-open-close").style.zIndex = 2;
	}
})