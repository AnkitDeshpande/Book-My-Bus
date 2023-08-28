// const baseServerURL = `http://localhost:8888`;
// const userRegisterURL = `${baseServerURL}/customers`;

const signinbtn = document.querySelector(".signinbtn");
const sign_in_btn = document.querySelector("#sign-in-btn");
const sign_up_btn = document.querySelector("#sign-up-btn");
const container = document.querySelector(".container");
const container1 = document.querySelector(".container1");
const container2 = document.querySelector(".container2");
const sign_in_btn2 = document.querySelector("#sign-in-btn2");
const sign_up_btn2 = document.querySelector("#sign-up-btn2");
const sign_up_admin = document.querySelector("#sign-up-admin");
const sign_up_user = document.querySelector("#sign-up-user");
signinbtn.addEventListener("click", loginUser);

sign_up_btn.addEventListener("click", () => {
    container.classList.add("sign-up-mode");
});
sign_in_btn.addEventListener("click", () => {
    container.classList.remove("sign-up-mode");
});
// sign_up_btn2.addEventListener("click", () => {
//     container.classList.add("sign-up-mode2");
// });
// sign_in_btn2.addEventListener("click", () => {
//     container.classList.remove("sign-up-mode2");
// });
// sign_up_admin.addEventListener("click", () => {
//     container1.classList.add("display");
//     container2.classList.remove("display");
// });
// sign_up_user.addEventListener("click", () => {
//     container1.classList.remove("display");
//     container2.classList.add("display");
// });

const firstname = document.querySelector(".firstname");
const lastname = document.querySelector(".lastname");
const mobileno = document.querySelector(".mobileno");
const email = document.querySelector(".email");
const password = document.querySelector(".password");
const signupbtn = document.querySelector(".signupbtn");

signupbtn.addEventListener("click", async (e) => {
    e.preventDefault();

    const userObj = {
        // userLoginId: 2,
        userName: firstname.value,
        name: lastname.value,
        email: email.value,
        password: password.value,
        contact: mobileno.value,
        role: "user",
    };

    fetch("http://localhost:8080/api/users", {
        method: "POST",
        headers: {
            "Content-type": "application/json",
        },
        body: JSON.stringify(userObj),
    })
        .then((res) => res.json())
        .then((data) => console.log(data))
        .catch((error) => {
            console.log(error);
        });
});

function loginUser(e) {
    e.preventDefault();

    var email = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    
    console.log(email, password);
    // Make GET request to fetch JWT token
    

    // Set up the fetch options with the Basic Authentication header
    fetch("http://localhost:8080/api/users", {
        method: "GET",
        headers: {
            "Content-type": "application/json",
        },
    })
        .then((res) => res.json())
        .then((data) => checklogin(data,email,password))
        .catch((error) => {
            console.log(error);
        });
}
function checklogin(data,email,password){
    data.forEach(element => {
        // console.log(element.userName,email,password)
        if(element.userName==email && element.password==password){
            window.location.href = "index.html"
        }
    });
    
    
}