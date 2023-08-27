

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



// buses display
document.addEventListener('DOMContentLoaded', function () {
    // Fetch the selected category from local storage
    // const selectedCategory = localStorage.getItem('selectedCategory');
    const selectedCategory = null;
    if (selectedCategory) {

        fetch(`http://localhost:8888/products/filter?category=${selectedCategory}`)
            .then(response => response.json())
            .then(products => {
                products.forEach(product => displayProduct(product));
            })
            .catch(error => console.error('Error fetching products:', error));
        // Clear the saved category from local storage if you have used it for your purpose
        localStorage.removeItem('selectedCategory');
    } else {
        fetch('http://localhost:8080/api/buses')
            .then(response => response.json())
            .then(products => {
                products.forEach(product => displayProduct(product));
            })
            .catch(error => console.error('Error fetching products:', error));
    }
});

const productContainer = document.getElementById('body');


function displayProduct(product) {

    const Div = document.createElement('div');
    Div.classList.add("details")

    const DivImage = document.createElement('div');
    DivImage.classList.add("image")
    const img = document.createElement('img')
    img.src = product.image;

    DivImage.appendChild(img)

    const DivName = document.createElement('div');
    DivName.classList.add("name")
    const nameh2 = document.createElement('h2')
    nameh2.innerHTML = product.busName
    const namebr1 = document.createElement('br')
    const namep = document.createElement('p')
    namep.innerHTML = product.busType
    const namebr2 = document.createElement('br')
    const live = document.createElement('p');
    live.classList.add("live")
    live.innerHTML = "Live Tracking"


    DivName.appendChild(nameh2,namebr1,namep,namebr2,live)



    const DivTime = document.createElement('div');
    DivTime.classList.add("arrivaltime")
    const timeh3 = document.createElement('h3')
    timeh3.classList.add("atime")
    timeh3.innerHTML = product.arrivalTime
    const timebr1 = document.createElement('br')
    const timep = document.createElement('p')
    timep.innerHTML = "200 Ft Bypass"

    DivTime.appendChild(timeh3,timebr1,timep)


    const DivTimed = document.createElement('div');
    DivTimed.classList.add("departuretime")
    const timeh3d = document.createElement('h3')
    timeh3.classList.add("dtime")
    timeh3.innerHTML = product.departureTime
    const timebr1d = document.createElement('br')
    const timepd = document.createElement('p')
    timep.innerHTML = "800 Ft Puliya"

    DivTimed.appendChild(timeh3d,timebr1d,timepd)




    const Divratting = document.createElement('div');
    Divratting.classList.add("ratting")
    const rath3d = document.createElement('h3')
    rath3d.classList.add("ratting1")
    rath3d.innerHTML = `${'<i class="fa-regular fa-star"></i> 4.5'}`
    const ratbr1d = document.createElement('br')
    const ratpd = document.createElement('p')
    ratpd.classList.add("ratting1")
    ratpd.innerHTML = `${'<i class="fa-regular fa-star"></i>280'}`

    Divratting.appendChild(rath3d,ratbr1d,ratpd)





    const Divprice = document.createElement('div');
    Divprice.classList.add("Priceing")
    const priceh3d = document.createElement('h3')
    priceh3d.classList.add("price")
    priceh3d.innerHTML = 'Starts from'
    const pricebr1d = document.createElement('br')
    const pricepd = document.createElement('p')
    pricepd.innerHTML = `${'\u20B9'}500`//${product.price}`

    Divprice.appendChild(priceh3d,pricebr1d,pricepd)





    const Divseat = document.createElement('div');
    Divseat.classList.add("seates")
    const seath3d = document.createElement('h3')
    seath3d.classList.add("seates")
    seath3d.innerHTML = `${'Total seates:- '}${product.seats}`
    const seatbr1d = document.createElement('br')
    const seatpd = document.createElement('p')
    seatpd.innerHTML = `${'Available seates:- '}${product.availableSeats}`//${product.price}`

    Divseat.appendChild(priceh3d,pricebr1d,pricepd)




    const btn = document.createElement('button');
    btn.innerHTML = 'Book Seat'

    
    Div.appendChild(DivImage,DivName,DivTime,DivTimed,Divratting,Divprice,Divseat,btn)
    productContainer.appendChild(Div);
}
