let modal = document.getElementById("editAboutModal");
let btn = document.getElementById("editAboutModalBtn");
let closeBtn = document.getElementsByClassName("closeBtn");

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