import navDiv from "./nav.js";

// console.log("div");

const navbar = document.querySelector("nav");
navbar.innerHTML = navDiv;

const baseUrl = "http://localhost:8080/api/buses";
const busesList = document.getElementById("buses-list");
const addButton = document.getElementById("add-button");
const busNameInput = document.getElementById("bus-name");

const addBusForm = document.getElementById("add-bus-form");


addBusForm.addEventListener("submit", async event => {
    event.preventDefault();

    const busNameInput = document.getElementById("busNameInput");
const busTypeInput = document.getElementById("busTypeInput");
const routeFromInput = document.getElementById("routeFromInput");
const routeToInput = document.getElementById("routeToInput");
const arrivalTimeInput = document.getElementById("arrivalTimeInput");
const departureTimeInput = document.getElementById("departureTimeInput");
const seatsInput = document.getElementById("seatsInput");
const availableSeatsInput = document.getElementById("availableSeatsInput");
const driverNameInput = document.getElementById("driverNameInput");
const imageInput = document.getElementById("imageInput");

    const newBusData = {
        busName: busNameInput.value.trim(),
        busType: busTypeInput.value.trim(),
        routeFrom: routeFromInput.value.trim(),
        routeTo: routeToInput.value.trim(),
        arrivalTime: arrivalTimeInput.value,
        departureTime: departureTimeInput.value,
        seats: parseInt(seatsInput.value),
        availableSeats: parseInt(availableSeatsInput.value),
        driverName: driverNameInput.value.trim(),
        image: imageInput.value.trim(),
        deleted: false, // Set to false by default
    };

    if (newBusData.busName) {
        try {
            await createBus(newBusData);
            addBusForm.reset();
            renderBuses();
        } catch (error) {
            console.error("Error creating bus:", error);
        }
    }
});






async function fetchBuses() {
    try {
        const response = await fetch(baseUrl);
        const buses = await response.json();
        return buses;
    } catch (error) {
        console.error("Error fetching buses:", error);
        throw error;
    }
}

async function updateBus(id, updatedData) {
    const response = await fetch(`${baseUrl}/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedData),
    });
    const updatedBus = await response.json();
    return updatedBus;
}


async function addBus(){

     
}

async function renderBuses() {
    const buses = await fetchBuses();
    busesList.innerHTML = "";

    buses.forEach(bus => {
        const busElement = document.createElement("li");
        busElement.classList.add("busElement");
        busElement.innerHTML = `
            
            <div>${bus.busName}</div>
            // <img src="${bus.image}" alt="${bus.busName} Image">
        
                <div><strong>Bus Type:</strong> ${bus.busType}</div>
                <div><strong>Route From:</strong> ${bus.routeFrom}</div>
                <div><strong>Route To:</strong> ${bus.routeTo}</div>
                <div><strong>Arrival Time:</strong> ${bus.arrivalTime}</div>
                <div><strong>Departure Time:</strong> ${bus.departureTime}</div>
                <div><strong>Seats:</strong> ${bus.seats}</div>
                <div><strong>Available Seats:</strong> ${bus.availableSeats}</div>
                <div><strong>Driver Name:</strong> ${bus.driverName}</div>
                <div><strong>Deleted:</strong> ${bus.deleted}</div>
            
            <button class="update-button" data-id="${bus.busId}">Update</button>
            <button class="delete-button" data-id="${bus.busId}">Delete</button>
        `;

        const updateButton = busElement.querySelector(".update-button");
        updateButton.addEventListener("click", async () => {
            const updatedData = {
                arrivalTime: prompt("Enter Arrival Time:", bus.arrivalTime),
                availableSeats: parseInt(prompt("Enter Available Seats:", bus.availableSeats)),
                busName: prompt("Enter Bus Name:", bus.busName),
                busType: prompt("Enter Bus Type:", bus.busType),
                deleted: prompt("Enter bus status: ",bus.deleted),
                departureTime: prompt("Enter Departure Time:", bus.departureTime),
                driverName: prompt("Enter Driver Name:", bus.driverName),
                image: prompt("Enter Image URL:", bus.image),
                routeFrom: prompt("Enter Route From:", bus.routeFrom),
                routeTo: prompt("Enter Route To:", bus.routeTo),
                seats: parseInt(prompt("Enter Seats:", bus.seats))
            };
            
            const updatedBus = await updateBus(bus.busId, updatedData);
            console.log("Updated Bus:", updatedBus);
            renderBuses();
        });


        const deleteButton = busElement.querySelector(".delete-button");

        deleteButton.addEventListener("click",async()=>{
             
            const updatedData = {deleted:true};
            const updatedBus = await updateBus(bus.busId, updatedData);
            console.log("Updated Bus:", updatedBus);
            renderBuses();

        });

        busesList.appendChild(busElement);
    });
}

renderBuses();
