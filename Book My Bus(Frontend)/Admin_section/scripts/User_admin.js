let uuid = JSON.parse(localStorage.getItem("uuid")) || "";

let baseURL = `http://localhost:8888`;

async function getData() {
    let res = await fetch(`${baseURL}/user/viewall?key=${uuid}`);
    console.log(res.status);
    let data = await res.json();

    if (res.status != 302) {
        Swal.fire({
            title: "No user signed",
            text: "No user are found in database",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Home page",
            cancelButtonText: "Cancel",
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Home page",
                    text: "Redirecting to home page.",
                    icon: "success",
                    confirmButtonColor: "#3085d6",
                }).then(() => {
                    window.location.href = "./Admin_Home.html";
                });
            }
        });
    }
    display(data);
}

getData();
let show = document.getElementById("tbody");
function display(data) {
    console.log(data);
    data.map((elem, index) => {
        let row = document.createElement("tr");
        let c1 = document.createElement("td");
        c1.innerText = index;

        let c2 = document.createElement("td");
        c2.append(elem.userLoginId);

        let c3 = document.createElement("td");
        c3.innerText = elem.firstName + " " + elem.lastName;

        let c4 = document.createElement("td");
        c4.innerText = elem.email;

        let c6 = document.createElement("td");
        c6.innerText = elem.contact;

        let c7 = document.createElement("td");
        c7.innerText = elem.userName;

        let c8 = document.createElement("td");
        c8.innerHTML = `<button class="d-button">DELETE</button>`;

        row.append(c1, c2, c7, c3, c4, c6, c8);

        show.append(row);

        c8.addEventListener("click", () => {
            deleteData(elem.userLoginId);
            window.location.reload();
        });
    });
}

async function deleteData(id) {
    let res = await fetch(`${baseURL}/user/delete/${id}?key=${uuid}`, {
        method: "DELETE",
        headers: {
            "Content-type": "application/json",
        },
    });
    let data = await res.json();
    console.log(data);
}

document.addEventListener("DOMContentLoaded", function () {
    var logoutLink = document.getElementById("logout");
    logoutLink.addEventListener("click", function (event) {
        event.preventDefault(); // Prevent the default link behavior
        Swal.fire({
            title: "Logout Confirmation",
            text: "Are you sure you want to logout?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Logout",
            cancelButtonText: "Cancel",
            confirmButtonColor: "#d33",
            cancelButtonColor: "#3085d6",
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Logged Out!",
                    text: "You have been successfully logged out.",
                    icon: "success",
                    confirmButtonColor: "#3085d6",
                }).then(() => {
                    fetch(`${baseURL}/admin/logout?key=${uuid}`, {
                        method: "POST",
                        headers: {
                            "Content-type": "application/json",
                        },
                    })
                        .then((res) => res.json())
                        .then((data) => {
                            console.log(data);
                            alert("Check");
                            window.location.href =
                                "/Book%20My%20Bus(Frontend)/User-Side/login.html"; // Replace "login.html" with the actual logout page URL
                        });
                });
            }
        });
    });
});

// let res=await fetch(`${baseURL}/user/delete/${id}?key=${uuid}`,{
//   method: 'DELETE',
//   headers: {
//     'Content-type': 'application/json'
//   }
// });
