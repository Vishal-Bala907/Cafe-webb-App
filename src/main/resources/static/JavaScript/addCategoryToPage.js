let c = "";
let selectedCategory;

//
window.addEventListener("load", () => {
	let data = JSON.parse(sessionStorage.getItem('cate-data'));
	createCateCard(data);
	//	console.log(data)
	console.log("Fetch categories button clicked"); // Debugging statement
	fullCategoryData()
		.then(data => {
			console.log("Categories fetched successfully:", data); // Debugging statement
			setCategories(data);
		})
		.catch(error => {
			console.error("Error handling categories:", error); // Log error for debugging
		});
})

function createCateCard(data) {
	console.log("vishal bala",data)
	data.forEach(cate => {
		let template = `<div id='cate-${cate.c_Id}' class="category-card">

			<img class="cate-card-img" src ='../${cate.cover}'
				alt="Image 3" />


			<p class="cate-name">${cate.catagoryName}</p>



		</div>`

		c = c + template;
	})

	document.getElementById('category-cards').innerHTML = c;

	// Attach event listeners to remove items from bag
	data.forEach(cate => {
		document.getElementById(`cate-${cate.c_Id}`).addEventListener('click', () => {
			getMyData(cate)
		});
	})
}


async function fetchDataOfTheCategory(id) {
	try {
		const res = await fetch(`/common/getCommonCate/${id}`);
		if (!res.ok) {
			console.error("Some error occured !!!");
			throw new Error("Unable to fetch");
		}
		const data = await res.json();
		return data;
	} catch (error) {
		console.error(error);
	}
}

function getMyData(cate) {
	fetchDataOfTheCategory(cate.c_Id)
		.then(data => {
			sessionStorage.setItem("selected-cate", JSON.stringify(data));
			window.location.href = "/admin/selectedItemPage";
		})
		.catch(error => {
			console.log(error)
		})
}