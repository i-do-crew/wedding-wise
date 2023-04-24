
let modal = document.getElementById("modalBtn");
let btn = document.getElementById("budgetBtn");
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
//     $("#budgetModal").click(function(){
//         $("#budgetModalBtn").modal();
//     });
// });