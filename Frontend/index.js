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





//rotate inputs
const rotate_btn = document.querySelector('.rotate-btn')
const arrivalInput = document.querySelector('.arrival');
const departureInput = document.querySelector('.departure')
const date_btn = document.querySelector('.date')
const TimeInput = document.querySelector('.time');
const Search_btn = document.querySelector('.search')

rotate_btn.addEventListener('click',()=>{
  let Avalue = arrivalInput.value
  arrivalInput.value = departureInput.value
  departureInput.value = Avalue
})

Search_btn.addEventListener('click',(e)=>{
  e.preventDefault()
  let obj = {
    arrival : arrivalInput.value,
    departure : departureInput.value,
    date : date.value,
    time : time.value
  }
  console.log(obj)
})




//FAQ's

let faqs = document.querySelectorAll('.faq')
faqs.forEach(faq => {
    faq.addEventListener('click',() =>{
        faq.querySelector('.faq-ans').classList.toggle('faq-ans-active')
        faq.querySelector('i').classList.toggle('faq-icon-active')
    })
})

