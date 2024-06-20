
window.addEventListener("load", () => {
	try {
		populateCategory();

	}catch(err){
		
	}
	
	try {
		setCategoryOptions()

	}catch(err){
		
	}
	
})


// fetching category data(name array)
const category = [];
let fetchedCategory;

async function fetchCategory() {
	try {
		const res = await fetch('/common/category');
		if (!res.ok) {
			// If the response status is not OK (e.g., 404, 500), throw an error
			throw new Error(`HTTP error! status: ${res.status}`);
		}
		const data = await res.json();
		return data;
	} catch (error) {
		console.error('Error fetching category data:', error);
		throw error; // Re-throwing the error to be handled by the caller
	}

}

document.getElementById("fetch-cate-data").addEventListener("click", () => {
	fetchCategory().then(
		data => {
			console.log('Fetched category data:', data);
			sessionStorage.setItem('category', JSON.stringify(data));
		}
	).catch(
		error => {
			console.error('Failed to fetch category data:', error);
		}
	)
})

// adding options
function setCategoryOptions() {


	const mycate = sessionStorage.getItem('category');
	// Parse the JSON string to get the array
	const retrievedArray = JSON.parse(mycate);
	// Copy the array to another variable
	const fetchedCategory = retrievedArray;

	// Get the select element
	const select = document.getElementById('cate_name');

	// Loop through the array and create options
	fetchedCategory.forEach(item => {
		// Create a new option element
		const option = document.createElement('option');

		// Set the value and text of the option
		option.value = item;
		option.textContent = item;
		option.className = "option"
		// Append the option to the select element
		select.appendChild(option);
	});
}

/******************* CATEGOTRY FULL DATA *******************************/
async function fullCategoryData() {
	try {
		// list of categores with items
		const res = await fetch('/common/category-list');
		if (!res.ok) {
			throw new Error("Failed to fetch categories");
		}
		const data = await res.json();
		return data;
	} catch (error) {
		console.error("Error fetching categories:", error);
		throw error;
	}
}

document.getElementById("fetch-categories").addEventListener("click", () => {

	console.log("Fetch categories button clicked"); // Debugging statement
	fullCategoryData()
		.then(data => {
			console.log("Categories fetched successfully:", data); // Debugging statement
			let categoryData = JSON.stringify(data);
			sessionStorage.setItem("cate-data", categoryData);
		})
		.catch(error => {
			console.error("Error handling categories:", error); // Log error for debugging
		});
});





