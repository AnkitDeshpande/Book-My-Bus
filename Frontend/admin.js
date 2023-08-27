import navDiv from "./nav.js";

console.log("div");

const navbar = document.querySelector("nav");
navbar.innerHTML = navDiv;

let url = https:
async function fetchBuses() {
    console.log("inside fetch buses");
    const response = await fetch(baseUrl);
    console.log(response); // Log the entire response object
    const buses = await response.json();
    console.log(buses);
    return buses;
}







