/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */
const alleCryptomunten = fetch("/cryptoLijst", {

})

function myFunction() {
    document.getElementById("cryptoDropdown").classList.toggle("show");
}
document.getElementById("cryptoZoekveld")

function filterFunction() {
 const input = document.getElementById("cryptoZoekveld");
 const filter = input.value.toUpperCase();
 const div = document.getElementById("cryptoDropdown");
 const dropDownDivs = div.getElementsByTagName("div");
    for (let i = 0; i < dropDownDivs.length; i++) {
   const txtValue = dropDownDivs[i].textContent || dropDownDivs[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            dropDownDivs[i].style.display = "";
        } else {
            dropDownDivs[i].style.display = "none";
        }
    }
}

