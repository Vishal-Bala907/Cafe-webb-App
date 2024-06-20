
window.addEventListener('load', () => {
	// FETCHING ALL THE CATEGORIES SO THE USER CAN SELECT ONE FOR UPDATE
	fullCategoryData()
		.then(data => {
			console.log("Categories fetched successfully:", data); // Debugging statement
			let categoryData = JSON.stringify(data);
			sessionStorage.setItem("cate-data", categoryData);
			populateCategoryForUpdateSelection(data);
		})
		.catch(error => {
			console.error("Error handling categories:", error); // Log error for debugging
		});
})

function populateCategoryForUpdateSelection(data) {
	let categoryCards = "";
	data.forEach(cate => {
		let template = `<div id='man-cate-${cate.c_Id}' class="man-category-card">

			<img class="man-cate-card-img" src ='../${cate.cover}'
				alt="Image 3" />


			<p class="man-cate-name">${cate.catagoryName}</p>



		</div>`

		categoryCards = categoryCards + template;
	})

	document.getElementById('man-category-cards').innerHTML = categoryCards;

	// Attach event listeners to remove items from bag

	data.forEach(cate => {
		document.getElementById(`man-cate-${cate.c_Id}`).addEventListener('click', () => {
			getProductMangePage(cate);
		});
	})
}

async function getCategoryDataForManage(id) {
	try {
		const res = await fetch(`/common/manage-prod/${id}`);
		if (!res.ok) {
			throw new Error("Cannot get");
		}
		const data = res.json();
		return data;
	} catch (error) {
		alert(error)
		console.error(error);
	}
}

function getProductMangePage(cate) {
	getCategoryDataForManage(cate.c_Id).then(data => {
		console.log(data);
		sessionStorage.setItem("prod-man", JSON.stringify(data))
		window.location.href = "/admin/man-cateAndProd-page";
	}).catch(error => {
		console.error(error)
	})
}

