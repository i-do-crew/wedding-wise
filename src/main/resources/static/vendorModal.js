

var editBtn = document.getElementById("editBtn");
var modalVendor = document.getElementById("modalVendor");
var span = document.getElementsByClassName("close")[0];

//shows the modal
editBtn.onclick = function() {
    modalVendor.style.display = "block";
}

//user clicks the close button/outside the modal, hide the modal
var closeModalButtons = document.querySelectorAll('[data-dismiss="modal"]');
for (var i = 0; i < closeModalButtons.length; i++) {
    closeModalButtons[i].addEventListener("click", function() {
        modalVendor.style.display = "none";
    });
}

window.onclick = function(event) {
    if (event.target == modalVendor) {
        modalVendor.style.display = "none";
    }
}
