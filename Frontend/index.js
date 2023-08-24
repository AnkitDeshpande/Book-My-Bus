import navDiv from "./nav.js";

console.log("div");

const navbar = document.querySelector("nav");
navbar.innerHTML = navDiv;

console.log("navbar",navbar);

const divtoshow = 'nav .details .div2 ul'
const divpopup = document.querySelector(divtoshow);
const divTrigger = document.querySelector('.account')
divTrigger.addEventListener('click',()=>{
  console.log(!divpopup.classList.contains('show'))
  
  setTimeout(()=>{
    if(!divpopup.classList.contains('show')){
      divpopup.classList.add('show')
    }
  },250)
})
document.addEventListener('click',(e)=>{
  const isClosest = e.target.closest(divtoshow)
  if(!isClosest && divpopup.classList.contains('show')){
    divpopup.classList.remove('show')
  }
})



