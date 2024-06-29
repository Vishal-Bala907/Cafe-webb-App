
let headerCurrentHeight = 3;
let header = document.getElementById("header");
let navItemGroup = document.getElementsByClassName("nav-item-group");
let openCloseButton = document.getElementById("openCloseButton");
header.style.height = headerCurrentHeight + "em";
let clickCount = 0;

/*document.getElementById("openCloseButton").addEventListener("click",()=>{
	navbarfunction();
})*/

function navbarfunction() {
	clickCount++;
	if (clickCount % 2 != 0) {
		navbarMobileView();
	} else {
		navbarDesktopView();
	}
}

window.addEventListener(("resize"), () => {
	header.style.height = "3em";
	header.style.paddingTop = "0px";
	header.style.paddingBottom = "0px"
	header.style.paddingLeft = "0px";

	if (window.innerWidth > 716) {
		navItemGroup[0].style.visibility = "visible";
		navItemGroup[1].style.visibility = "visible";
	} else {
		navItemGroup[0].style.visibility = "hidden";
		navItemGroup[1].style.visibility = "hidden";
	}
})

function navbarMobileView() {
	const intervalId = setInterval(function() {
		if (headerCurrentHeight < 21) {
			headerCurrentHeight++;
			header.style.height = headerCurrentHeight + "em";
		} else if (headerCurrentHeight == 21) {
			header.style.paddingTop = "20px";
			header.style.paddingBottom = "20px"
			header.style.paddingLeft = "20px";
			navItemGroup[0].style.visibility = "visible";
			navItemGroup[1].style.visibility = "visible";
			openCloseButton.innerText = "close";
			clearInterval(intervalId)
		}
	}, 20)
}
function navbarDesktopView() {
	const intervalId = setInterval(function() {

		navItemGroup[0].style.visibility = "hidden";
		navItemGroup[1].style.visibility = "hidden";
		if (headerCurrentHeight > 3) {
			headerCurrentHeight--;
			header.style.height = headerCurrentHeight + "em";
		} else if (headerCurrentHeight == 3) {
			header.style.paddingTop = "0px";
			header.style.paddingBottom = "0px"
			header.style.paddingLeft = "0px";
			openCloseButton.innerText = "menu";
			clearInterval(intervalId)
		}
	}, 20)
}