
let modal = document.getElementById("initialBudgetCostModal");
let btn = document.getElementById("initialBudgetCostModalBtn");
let closeBtn = document.getElementsByClassName("closeBtn");
let saveBtn = document.getElementsByClassName("saveBtn");

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

saveBtn.onclick = function(){

}