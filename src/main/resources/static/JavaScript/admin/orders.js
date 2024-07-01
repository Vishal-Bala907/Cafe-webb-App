/*
	primarily manages undispatched orders
 */

window.addEventListener("load", () => {
	fetchAllUndispatchedOrders();
})

function fetchAllUndispatchedOrders() {
	fetch("/empAndCus/undispathced-orders")
		.then(res => {
			if (!res.ok) {
				throw new Error("unable to fetch")
			}
			return res.json();
		}).then(data => {
			console.log(data)
			loadAllUndispathedOrders(data);
		}).catch(err => {
			console.error(err)
		})
}

// loding data to ui
function loadAllUndispathedOrders(data) {
	ordersTemplate = "";
	data.forEach(order => {
		item = `<div class="single-order" id="order-item=${order.o_id}">
				<div class="o-info">
					<b>Pro. name :</b>
					<p id="pro_name">${order.o_p_name}</p>
				</div>
				<hr />
				<div class="o-info">
					<b>Pro. Price :</b>
					<p id="pro_price">${order.discountedPrice}</p>
				</div>
				<hr />
				<div class="o-info">
					<b>Cust. name :</b>
					<p id="customer_name">${order.user_details.username}</p>
				</div>
				<hr />
				<div class="o-info-buttons">
					<button class="dispatch-order" id="order-${order.o_id}">Dispatch</button>
					<button class="cancel-order" id="cancel-${order.o_id}">Cancel</button>
				</div>
			</div>`

		ordersTemplate += item;
	})

	document.getElementById("orders-container").innerHTML = ordersTemplate


	data.forEach(order => {
		document.getElementById(`order-${order.o_id}`).addEventListener('click', () => {
			dispatchTheOrder(order);
		})

		document.getElementById(`cancel-${order.o_id}`).addEventListener('click', () => {
			cancelTheOrder(order);
		})
	})
}

function dispatchTheOrder(order) {
	fetch("/empAndCus/dispatch-order", {
		method: "PUT",
		headers: {
			"Content-type": "application/json"
		},
		body: JSON.stringify(order)

	})
		.then(res => {
			if (!res.ok) {
				throw new Error("unable to craete order")
			}
			return res.text();
		}).then(data => {
			console.log(data)
			document.getElementById(`order-item=${order.o_id}`).remove();
		}).catch(err => {
			console.error(err)
		})
}

function cancelTheOrder(order) {
	fetch("/empAndCus/cancel-order", {
		method: "PUT",
		headers: {
			"Content-type": "application/json"
		},
		body: JSON.stringify(order)

	})
		.then(res => {
			if (!res.ok) {
				throw new Error("unable to craete order")
			}
			return res.text();
		}).then(data => {
			console.log(data)
			document.getElementById(`order-item=${order.o_id}`).remove();
		}).catch(err => {
			console.error(err)
		})

}