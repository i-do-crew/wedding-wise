
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

$(".budgetAmountModalBtn").click(function (e) {
    const vendorId = e.target.getAttribute('data-id');
    const vendorName = e.target.getAttribute('name');
    const amount = $("button[name='" + vendorName + "']").closest('span').text();
    console.log("Amount Is: " + amount);
    $("#budget-entry-model").attr('action', '/budget/edit/' + vendorId);
    $("#expense-update-title").text(vendorName);
    $("#amount").val(amount);
})