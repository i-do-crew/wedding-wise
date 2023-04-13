
var modal = document.getElementById("modalBtn");
var btn = document.getElementById("budgetBtn");

btn.onclick = function() {
    modal.style.display = "block";
}
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

