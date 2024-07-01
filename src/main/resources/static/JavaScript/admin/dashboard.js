
let dashBoardData = []
let income = {}
let qt = {}
let graph = {}
let myChart = null;
let allSales = {}

window.addEventListener("load", () => {
	fetchDashboardData('yesturday');
})

function printContent() {
	printJS({
		printable: 'print-graph',
		type: 'html',
		css: '/CSS/admin/dashboard.css' // Optional: you can specify the path to your CSS file
	});
}

function fetchDashboardData(time) {
	fetch(`/empAndCus/graph-data/${time}`).then(res => {
		if (!res.ok) {
			throw new Error("unable to fetch")
		}
		return res.json();
	}).then(data => {
		dashBoardData = data;
		income = dashBoardData[0];
		qt = dashBoardData[1];
		graph = dashBoardData[2];

		allSales = dashBoardData[3]

		setDashboardCardInfo();
		createGraph();

	}).catch(err => {
		console.error(err)
	})
}

function setDashboardCardInfo() {
	// keys of income 
	let qtKey = Object.keys(qt);
	let qtValue = Object.values(qt)

	let incomeKey = Object.keys(income);
	let incomeValue = Object.values(income);
	// setting sold data
	if (qtKey[0] == '') {
		alert("no data to display")
		document.getElementById('max-sold-item').innerText = ""
		document.getElementById('max-sold-qt').innerText = ""

		document.getElementById('min-sold-item').innerText = ""
		document.getElementById('min-sold-qt').innerText = ""
	} else {
		let i = qtValue[0] > qtValue[1] ? 0 : 1;
		let j = i == 1 ? 0 : 1;

		document.getElementById('max-sold-item').innerText = qtKey[i];
		document.getElementById('max-sold-qt').innerText = qtValue[i];

		document.getElementById('min-sold-item').innerText = qtKey[j];
		document.getElementById('min-sold-qt').innerText = qtValue[j];
	}
	// setting income data
	if (incomeKey[0] == '') {
		alert("no data to display")
		document.getElementById('max-income-item').innerText = ""
		document.getElementById('max-sold-income').innerText = ""

		document.getElementById('min-income-item').innerText = ""
		document.getElementById('min-sold-income').innerText = ""
	} else {
		document.getElementById('max-income-item').innerText = incomeKey[1];
		document.getElementById('max-sold-income').innerText = incomeValue[1];

		document.getElementById('min-income-item').innerText = incomeKey[0];
		document.getElementById('min-sold-income').innerText = incomeValue[0];
	}
}

// Graph related data

function createGraph() {

	const data = {
		labels: Object.keys(allSales),
		datasets: [
			{
				label: 'Income Graph',
				data: Object.values(allSales),
				borderColor: 'blue',
				//backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red),
				fill: false
			}, {
				label: 'Sales Graph',
				data: Object.values(graph),
				borderColor: 'red',
				//backgroundColor: Utils.transparentize(Utils.CHART_COLORS.red),
				fill: false
			}
		]
	};
	const config = {
		type: 'line',
		data: data,
		options: {
			plugins: {
				filler: {
					propagate: false,
				},
				title: {
					display: true,
					text: (ctx) => 'Fill: ' + ctx.chart.data.datasets[0].fill
				}
			},
			interaction: {
				intersect: false,
			}
		},
	};

	/* ***************************** */



	const ctx = document.getElementById('myChart').getContext('2d');

	const createChart = () => {
		if (myChart) {
			myChart.destroy();
		}
		myChart = new Chart(ctx, config);
	};

	createChart(); // Initial chart creation

	const actions = [
		{
			name: 'Fill: false (default)',
			handler: (chart) => {
				chart.data.datasets.forEach(dataset => {
					dataset.fill = false;
				});
				chart.update();
			}
		},
		{
			name: 'Fill: origin',
			handler: (chart) => {
				chart.data.datasets.forEach(dataset => {
					dataset.fill = 'origin';
				});
				chart.update();
			}
		},
		{
			name: 'Fill: start',
			handler: (chart) => {
				chart.data.datasets.forEach(dataset => {
					dataset.fill = 'start';
				});
				chart.update();
			}
		},
		{
			name: 'Fill: end',
			handler: (chart) => {
				chart.data.datasets.forEach(dataset => {
					dataset.fill = 'end';
				});
				chart.update();
			}
		},
		{
			name: 'Randomize',
			handler(chart) {
				chart.data.datasets.forEach(dataset => {
					dataset.data = generateData();
				});
				chart.update();
			}
		},
		{
			name: 'Smooth',
			handler(chart) {
				smooth = !smooth;
				chart.options.elements.line.tension = smooth ? 0.4 : 0;
				chart.update();
			}
		}
	];
}
