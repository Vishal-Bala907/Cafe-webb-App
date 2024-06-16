let sideBarOpenCloseCount = 0;
let sideBarWidth = 5;
let smallScreenSidebar = 10;

let sidebarCover = document.getElementById("sidebar-cover");
let sidebarUl = document.getElementById("sidebar-ul");
let SideBarOpenCloseButton = document.getElementById("SideBarOpenCloseButton");

function SideBarOpenClosefunction() {
	sideBarOpenCloseCount++;
	if (sideBarOpenCloseCount % 2 != 0) {
		// sidebar opend
		if (window.innerWidth > 586) {
			openSideBarForBigScreens();
		} else if (window.innerWidth < 586) {
			openSideBarForSmallScreens()
		}
	} else {
		// sidebar opend
		if (window.innerWidth > 586) {
			closeSideBar();
		} else if (window.innerWidth < 586) {
			closeSideBarForSmallScreens()
		}
	}
}

function closeSideBar() {
	console.log("decreasing")
	const timeId = setInterval(() => {
		if (sideBarWidth > 5) {
			sideBarWidth--;
			sidebarCover.style.width = sideBarWidth + "%"
			sidebarCover.style.position = "relative";
			sidebarUl.style.visibility = "hidden";
			SideBarOpenCloseButton.innerText = "arrow_forward"
		} else if (sideBarWidth == 5) {
			clearInterval(timeId)
		}
	}, 10)
}
function closeSideBarForSmallScreens() {
	console.log("decreasing")
	const timeId = setInterval(() => {
		if (smallScreenSidebar > 10) {
			smallScreenSidebar--;
			sidebarCover.style.width = smallScreenSidebar + "%"
			sidebarCover.style.position = "relative";
			sidebarUl.style.visibility = "hidden";
			SideBarOpenCloseButton.innerText = "arrow_forward"
		} else if (smallScreenSidebar == 10) {
			clearInterval(timeId)
		}
	}, 2)
}

function openSideBarForBigScreens() {
	console.log("opening")
	const timeId = setInterval(() => {
		if (sideBarWidth < 25) {
			sideBarWidth++;
			sidebarCover.style.width = sideBarWidth + "%"
			sidebarUl.style.visibility = "visible";
			SideBarOpenCloseButton.innerText = "arrow_back"
		} else if (sideBarWidth == 25) {
			clearInterval(timeId)
		}
	}, 10)
}

// Small screen size no problem
function openSideBarForSmallScreens() {
	console.log("opening")
	const timeId = setInterval(() => {
		if (smallScreenSidebar < 75) {
			smallScreenSidebar++;
			sidebarCover.style.width = smallScreenSidebar + "%"
			sidebarUl.style.visibility = "visible";
			sidebarCover.style.position = "absolute";
			SideBarOpenCloseButton.innerText = "arrow_back"
			SideBarOpenCloseButton.style.zIndex = 2;
			/*SideBarOpenCloseButton.style.right = 2+ "px"*/
		} else if (smallScreenSidebar == 75) {
			clearInterval(timeId)
		}
	}, 5)
}