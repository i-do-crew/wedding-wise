let modal = document.getElementById("rateVendorModalBtn");
let btn = document.getElementById("individualVendorRateBtn");
let closeBtn = document.getElementById("closeBtn");

btn.onclick = function() {
    modal.style.display = "block";
}
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

closeBtn.onclick = function()  {
    modal.hide();
}