window.onload = async (e) =>{
    e.preventDefault();

    let visibility = false;

    const response = await fetch(`http://localhost:9001/api/get/users/feedback`);

    const feedback = await response.json();
    console.log(feedback);

    if(feedback !== "null"){
        let tag = document.getElementById("feedback");
        tag.innerHTML = `
        <div class="alert alert-danger" role="alert">
            ${feedback}
        </div>`
    }

}
