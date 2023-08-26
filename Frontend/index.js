import footerDiv from "./footer.js";
import navDiv from "./nav.js";




const navbar = document.querySelector("nav");
const footer = document.querySelector("#footer");

navbar.innerHTML = navDiv;
footer.innerHTML = footerDiv;

// console.log("navbar",navbar);

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
const message = document.querySelector('.meggage')

rotate_btn.addEventListener('click',()=>{
  let Avalue = arrivalInput.value
  arrivalInput.value = departureInput.value
  departureInput.value = Avalue
})

Search_btn.addEventListener('click',(e)=>{
  e.preventDefault()
  let d = new Date();
  let obj = {
    arrival : arrivalInput.value,
    departure : departureInput.value,
    date : date.value,
    time : TimeInput.value
  }
  let arr = []
  // let arr = obj.date.split("-").map(Number)
  // let arr1 = obj.time.split(":").map(Number)
  if(obj.arrival==''||obj.departure==''||obj.date==''||obj.time==''){
    message.innerHTML = "Please provide all details"
  // }else if(d.getDate()>arr[2] || d.getMonth()+1>arr[1] || d.getFullYear()>arr[0]){
  //   message.innerHTML = "Please provide valid Date details"
  //   console.log(d.getDate()<=arr[2], d.getMonth()+1<=arr[1] , d.getFullYear()<=arr[0])
  // }else if(d.getDate()==arr[2]){
  //   console.log("date same")
  //   if(d.getHours()>arr1[0]){
  //     console.log("not correct time")
  //     message.innerHTML = "Please provide valid Date details"
  //   }else if(d.getHours()==arr1[0]){
  //     if(d.getMinutes()>arr1[1]){
  //       message.innerHTML = "Please provide valid Date details"
  //     }
  //   }
  //   console.log(d.getHours(),arr1[0])
   }else{
    message.innerHTML = ''
    arr.push(obj)
    localStorage.setItem("details",JSON.stringify(arr))
    window.open('Details.html')
  }
  
})




//FAQ's

let faqs = document.querySelectorAll('.faq')
faqs.forEach(faq => {
    faq.addEventListener('click',() =>{
        faq.querySelector('.faq-ans').classList.toggle('faq-ans-active')
        faq.querySelector('i').classList.toggle('faq-icon-active')
    })
})

