
let modal = document.getElementById("editGuestModal");
let btn = document.getElementsByClassName("editModalBtn");
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



// $(document).ready(function(){
//     $("#modal").click(function(){
//         $("#myModal").modal();
//     });
// });
