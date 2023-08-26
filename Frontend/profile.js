let data = JSON.parse(localStorage.getItem("data"))
let name1 = document.querySelector("#title > h4")
let btn = document.querySelector(".btn-primary")
let title = document.getElementById("name")
let lastname = document.getElementById("lastname")
let email = document.getElementById("email")
let phone = document.getElementById("phone")
let company = document.getElementById("company")
let location1 = document.getElementById("location1")

console.log(data[0])


name1.innerHTML = data[0].name+" "+data[0].lastname



title.value = data[0].name
email.value = data[0].email
lastname.value = data[0].lastname

btn.addEventListener("click",()=>{
    let userdata = [{
        name : title.value,
        lastname : lastname.value,
        email : email.value,
        phone : phone.value,
        company : company.value,
        location1 : location1.value
    }]
    localStorage.setItem("data",JSON.stringify(userdata))
})

