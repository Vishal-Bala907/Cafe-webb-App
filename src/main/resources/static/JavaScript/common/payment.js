document.getElementById('rzp-button1').onclick = function(e) {
	let amount = Number(document.getElementById('amt').innerText)
	//alert(amount)
	fetch(`/api/payment/create-order/${amount}`, {
		mode: 'no-cors',
		method: 'GET',
	})
		.then(response => response.json())
		.then(data => {
			var options = {
				"key": "rzp_test_9f6vNAtcFzQaLC", // Enter the Key ID generated from the Dashboard
				"amount": amount, // Amount is in currency subunits
				"currency": "INR",
				"name": "Hello cafe",
				"description": "Test Transaction",
				"image": "https://example.com/your_logo",
				"order_id": data.id, // Use the order ID returned from your backend
				"handler": function(response) {
					alert("Payment ID: " + response.razorpay_payment_id);
					alert("Order ID: " + response.razorpay_order_id);
					alert("Signature: " + response.razorpay_signature);

					paymentSuccessFull();

				},
				"prefill": {
					"name": "Gaurav Kumar",
					"email": "gaurav.kumar@example.com",
					"contact": "9000090000"
				},
				"notes": {
					"address": "Razorpay Corporate Office"
				},
				"theme": {
					"color": "#3399cc"
				}
			};
			var rzp1 = new Razorpay(options);
			rzp1.on('payment.failed', function(response) {
				alert("Error Code: " + response.error.code);
				alert("Error Description: " + response.error.description);
				alert("Error Source: " + response.error.source);
				alert("Error Step: " + response.error.step);
				alert("Error Reason: " + response.error.reason);
				alert("Order ID: " + response.error.metadata.order_id);
				alert("Payment ID: " + response.error.metadata.payment_id);
			});
			rzp1.open();
		})
		.catch(error => {
			console.error('Error creating order:', error);
		});
	e.preventDefault();
}

function paymentSuccessFull() {
	createOrder()
	.then(data=>{
		sessionStorage.setItem('cart',JSON.stringify(data));
		location.reload();
		//location.href = "/admin/menu"
	})
	.catch(error=>{
		console.log(error)
	})
}

async function createOrder() {
	const orders = JSON.parse(sessionStorage.getItem('cart'));
	try {
		const res = await fetch("/empAndCus/create-order", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(orders)
		});

		if (!res.ok) {
			throw new Error("Unable to create order")
		}
		const data = await res.json();
		return data;
	} catch (error) {
		console.error(error)
	}
}