

import footerDiv from "./footer.js";
import navDiv from "./nav.js";




const navbar = document.querySelector("nav");
const footer = document.querySelector("#footer");

navbar.innerHTML = navDiv;
footer.innerHTML = footerDiv;



let arr = JSON.parse(localStorage.getItem('details'))
let a = document.getElementById('arrival')
let d = document.getElementById('departure')

a.innerHTML = arr[0].arrival
d.innerHTML = arr[0].departure