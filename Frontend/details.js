
let arr = JSON.parse(localStorage.getItem('details'))
let a = document.getElementById('arrival')
let d = document.getElementById('departure')

a.innerHTML = arr[0].arrival
d.innerHTML = arr[0].departure