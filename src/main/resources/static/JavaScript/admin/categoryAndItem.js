
window.addEventListener("load",()=>{
	alert("hello everyone")
	setCategoryOptions()
})

// fetching category data
const category = [];
let fetchedCategory;

async function fetchCategory() {
	try {
		const res = await fetch('/common/category');
		if (!res.ok) {
			// If the response status is not OK (e.g., 404, 500), throw an error
			throw new Error(`HTTP error! status: ${res.status}`);
		}
		const data = res.json();
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
	
	alert("done")
}

