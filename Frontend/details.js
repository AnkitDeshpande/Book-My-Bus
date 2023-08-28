

import footerDiv from "./footer.js";
import navDiv from "./nav.js";




let navbar = document.querySelector("nav");
let footer = document.querySelector("#footer");

navbar.innerHTML = navDiv;
footer.innerHTML = footerDiv;
let productContainer = document.querySelector('#body');


let arr = JSON.parse(localStorage.getItem('details'))
let a = document.getElementById('arrival')
let d = document.getElementById('departure')

a.innerHTML = arr[0].arrival
d.innerHTML = arr[0].departure



// buses display
document.addEventListener('DOMContentLoaded', function () {
    // Fetch the selected category from local storage
    // let selectedCategory = localStorage.getItem('selectedCategory');
    let selectedCategory = null;
    if (selectedCategory) {

        // fetch(`http://localhost:8888/products/filter?category=${selectedCategory}`)
        //     .then(response => response.json())
        //     .then(products => {
        //         products.forEach(product => displayProduct(product));
        //     })
        //     .catch(error => console.error('Error fetching products:', error));
        // // Clear the saved category from local storage if you have used it for your purpose
        // localStorage.removeItem('selectedCategory');
    } else {
        fetch('http://localhost:8080/api/buses')
            .then(response => response.json())
            .then(products => products.forEach(element => {
                console.log(element)
                console.log(arr)
                if(arr[0].arrival.toLowerCase()==element.routeFrom.toLowerCase() && arr[0].departure.toLowerCase()==element.routeTo.toLowerCase()){
                    displayProduct(element)
                }
                
            }))
            .catch(error => console.error('Error fetching products:', error));
    }
});




function displayProduct(product) {
    
    let Div = document.createElement('div');
    Div.classList.add("details")

    let DivImage = document.createElement('div');
    DivImage.classList.add("image")
    let img = document.createElement('img')
    img.src = product.image;

    DivImage.append(img)

    let DivName = document.createElement('div');
    DivName.classList.add("name")
    let nameh2 = document.createElement('h2')
    nameh2.innerHTML = product.busName
    let namebr1 = document.createElement('br')
    let namep = document.createElement('p')
    namep.innerHTML = product.busType
    let namebr2 = document.createElement('br')
    let live = document.createElement('p');
    live.classList.add("live")
    live.innerHTML = "Live Tracking"


    DivName.append(nameh2,namebr1,namep,namebr2,live)



    let DivTime = document.createElement('div');
    DivTime.classList.add("arrivaltime")
    let timeh3 = document.createElement('h3')
    timeh3.classList.add("atime")
    timeh3.innerHTML = product.arrivalTime
    let timebr1 = document.createElement('br')
    let timep = document.createElement('p')
    timep.innerHTML = "200 Ft Bypass"

    DivTime.append(timeh3,timebr1,timep)


    let DivTimed = document.createElement('div');
    DivTimed.classList.add("departuretime")
    let timeh3d = document.createElement('h3')
    timeh3d.classList.add("dtime")
    timeh3d.innerHTML = product.departureTime
    let timebr1d = document.createElement('br')
    let timepd = document.createElement('p')
    timepd.innerHTML = "800 Ft Puliya"

    DivTimed.append(timeh3d,timebr1d,timepd)




    let Divratting = document.createElement('div');
    Divratting.classList.add("ratting")
    let rath3d = document.createElement('h3')
    rath3d.classList.add("ratting1")
    rath3d.innerHTML = `${'<i class="fa-regular fa-star"></i> 4.5'}`
    let ratbr1d = document.createElement('br')
    let ratpd = document.createElement('p')
    ratpd.classList.add("ratting1")
    ratpd.innerHTML = `${'<i class="fa-regular fa-star"></i>280'}`

    Divratting.append(rath3d,ratbr1d,ratpd)





    let Divprice = document.createElement('div');
    Divprice.classList.add("Priceing")
    let priceh3d = document.createElement('h3')
    priceh3d.classList.add("price")
    priceh3d.innerHTML = 'Starts from'
    let pricebr1d = document.createElement('br')
    let pricepd = document.createElement('p')
    pricepd.innerHTML = `${'\u20B9'}500`//${product.price}`

    Divprice.append(priceh3d,pricebr1d,pricepd)





    let Divseat = document.createElement('div');
    Divseat.classList.add("seates")
    let seath3d = document.createElement('h3')
    seath3d.classList.add("seates")
    seath3d.innerHTML = `${'Total seates:- '}${product.seats}`
    let seatbr1d = document.createElement('br')
    let seatpd = document.createElement('p')
    seatpd.innerHTML = `${'Available seates:- '}${product.availableSeats}`//${product.price}`

    Divseat.append(seath3d,seatbr1d,seatpd)




    let btn = document.createElement('button');
    btn.innerHTML = 'Book Seat'
    btn.addEventListener('click',()=>{
        localStorage.setItem("id",JSON.stringify(product.busId))
        window.location.href = "form.html"
    })

    
    Div.appendChild(DivImage)
    Div.appendChild(DivName)
    Div.appendChild(DivTime)
    Div.appendChild(DivTimed)
    Div.appendChild(Divratting)
    Div.appendChild(Divprice)
    Div.appendChild(Divseat)
    Div.appendChild(btn)
    productContainer.appendChild(Div);
    
}
