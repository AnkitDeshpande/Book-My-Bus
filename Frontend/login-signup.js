// const baseServerURL = `http://localhost:8888`;
// const userRegisterURL = `${baseServerURL}/customers`;



const signinbtn = document.querySelector('.signinbtn')
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
sign_up_btn2.addEventListener("click", () => {
    container.classList.add("sign-up-mode2");
});
sign_in_btn2.addEventListener("click", () => {
    container.classList.remove("sign-up-mode2");
});
sign_up_admin.addEventListener("click", () => {
    container1.classList.add("display");
    container2.classList.remove("display");
});
sign_up_user.addEventListener("click", () => {
    container1.classList.remove("display");
    container2.classList.add("display");
});




const firstname = document.querySelector(".firstname")
const lastname = document.querySelector(".lastname")
const mobileno = document.querySelector(".mobileno")
const email = document.querySelector(".email")
const password = document.querySelector(".password")
const signupbtn = document.querySelector('.signupbtn')


signupbtn.addEventListener('click', (e) => {
    e.preventDefault()
    let userObj={
        firstName: firstname.value,
        lastName: lastname.value,
        email: email.value,
        password: password.value,
        mobileNumber: mobileno.value,
      }
      console.log(userObj)
      fetch('http://localhost:8888/customers',{
        method:"POST",
        headers:{
          "Content-type":"application/json"
        },
        body:JSON.stringify(userObj)
      })
      .then(res => res.json())
      .then(data => console.log(data))
      .catch(error =>{
      console.log(error)
      })
})


  
function loginUser(e){

      e.preventDefault()

    
      var email = document.getElementById("username").value
      var password = document.getElementById("password").value
      
    console.log(email,password)
      // Make GET request to fetch JWT token
      var authHeader = "Basic " + btoa(email + ":" + password);

      var myHeaders = new Headers();
      myHeaders.append("Content-Type", "application/json");
      myHeaders.append("Authorization", authHeader);
    
      // Set up the fetch options with the Basic Authentication header
      var requestOptions = {
          method: "GET",
          headers: myHeaders,
          redirect: "follow",
      };
    
      const varification = "";
      // Send the GET request to the /signIn endpoint
      fetch('http://localhost:8888/signIn', requestOptions)
      .then((response) => {
        if (response.ok) {
          var token = response.headers.get("Authorization");
          localStorage.setItem("jwtToken", JSON.stringify(token));
          console.log("Token stored:", token);
        } else {
          console.log("Error:", response.status);
        }
        return response.json();
      })
      .then((result) => {
        var customerId = result.customerId;
        var customername = result.name;
        var cart= result.cart;
        localStorage.setItem("cart", JSON.stringify(cart));
        localStorage.setItem("customername", customername);
        console.log(result);
        console.log(result.role);
        if(result.role == 'ROLE_USER'){
          alert("user login successfull")
          window.location.href = "index.html";
        }else{
          alert("admin login successfull")
          window.location.href = "admin.html";
        }
        localStorage.setItem("customerId", customerId);
      })
      .catch((error) => console.log("error", error));
  
}  
  