import navDiv from "./nav.js";

// console.log("div");

const navbar = document.querySelector("nav");
navbar.innerHTML = navDiv;

const baseUrl = "http://localhost:8080/api/buses";
const busesList = document.getElementById("buses-list");
const addButton = document.getElementById("add-button");
const busNameInput = document.getElementById("bus-name");

let arr = [];

async function fetchBuses() {
    try {
        console.log("inside fetch buses");
        const response = await fetch(baseUrl);
        const buses = await response.json();
        console.log(buses);
        return buses;
    } catch (error) {
        console.error("Error fetching buses:", error);
        throw error;
    }
}


fetchBuses();

// async function createBus(busData) {
//     const response = await fetch(baseUrl, {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify(busData),
//     });
//     const createdBus = await response.json();
//     return createdBus;
// }

// async function updateBus(id, updatedData) {
//     const response = await fetch(`${baseUrl}/${id}`, {
//         method: "PUT",
//         headers: {
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify(updatedData),
//     });
//     const updatedBus = await response.json();
//     return updatedBus;
// }

// async function deleteBus(id) {
//     const response = await fetch(`${baseUrl}/${id}`, {
//         method: "DELETE",
//     });
//     return response.status === 204;
// }

// async function renderBuses() {
//     const buses = await fetchBuses();
//     busesList.innerHTML = "";
    
//     buses.forEach(bus => {
//         const li = document.createElement("li");
//         li.textContent = bus.busName;
        
//         const updateButton = document.createElement("button");
//         updateButton.textContent = "Update";
//         updateButton.addEventListener("click", async () => {
//             const updatedName = prompt("Enter the updated name for the bus:");
//             if (updatedName) {
//                 await updateBus(bus.busId, { busName: updatedName });
//                 renderBuses();
//             }
//         });
        
//         const deleteButton = document.createElement("button");
//         deleteButton.textContent = "Delete";
//         deleteButton.addEventListener("click", async () => {
//             const deleted = await deleteBus(bus.busId);
//             if (deleted) {
//                 renderBuses();
//             }
//         });
        
//         li.appendChild(updateButton);
//         li.appendChild(deleteButton);
//         busesList.appendChild(li);
//     });
// }

// addButton.addEventListener("click", async () => {
//     const name = busNameInput.value.trim();
//     if (name) {
//         await createBus({ busName: name });
//         busNameInput.value = "";
//         renderBuses();
//     }
// });

// renderBuses();
