let uuid = JSON.parse(localStorage.getItem("uuid")) || "";
console.log(uuid);
let baseURL = `http://localhost:8888`;
document.addEventListener("DOMContentLoaded", function () {
    // Add event listener to the "Logout" link
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
                // Handle the logout action here (replace with your actual logout logic)
                Swal.fire({
                    title: "Logged Out!",
                    text: "You have been successfully logged out.",
                    icon: "success",
                    confirmButtonColor: "#3085d6",
                }).then(() => {
                    // Redirect to the login page or any other designated page for logging out
                    let url = `${baseURL}/admin/logout?key=${uuid}`;
                    fetch(url, {
                        method: "POST",
                    })
                        .then((response) => response)
                        .then((data) => {
                            localStorage.setItem(
                                "username",
                                JSON.stringify("")
                            );
                            localStorage.setItem("uuid", JSON.stringify(""));
                            console.log(data);
                            window.location.href =
                                "/Book%20My%20Bus(Frontend)/User-Side/login.html";
                        })
                        .catch((error) => {
                            console.error("Error posting data:", error);
                        });
                });
            }
        });
    });
});
