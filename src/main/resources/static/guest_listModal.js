$(".editModalBtn").click(function(e) {
$   ("#addGuestModal").css("display", "block");
});

$("#guest-add-btn").click(function(e) {
    $("#guest-modal-title").text("Add Guest");
    $("#save-and-close-btn").removeAttr("formaction");
    $("#save-and-close-btn").text("Save & Close");
});

function edit(guest) {

    $("#guest-modal-title").text("Edit Guest");
    console.log(guest);
    $("#guest-0-id").val(guest.id);
    $("#lname").val(guest.lname);
    $("#plusOne").val(guest.plusOne);
    $("#fname").val(guest.fname);
    $("#email").val(guest.email);
    $("#phNumber").val(guest.phNumber);
    $("#street").val(guest.street);
    $("#aptNo").val(guest.aptNo);
    $("#city").val(guest.city);
    $("#state").val(guest.state);
    $("#zip").val(guest.zip);
    $("#rsvp").val(guest.rsvp);
    $("#save-and-close-btn").attr("formaction","/clients/guests/update/" + guest.id);
    $("#save-and-close-btn").text("Update & Close");


}
