function showLoadingGif( id ){
    var identifier = "#loader"+id;
    $(identifier).show();
}

function hideLoadingGif( id ){
    var identifier = "#loader"+id;
    $(identifier).hide();
}

async function makeAjaxRequisition( reimbursementId, urlToRequest ){
    const response = await $.ajax(
    {
        url: urlToRequest,
        type: 'put',
        headers: {
            'Content-Type': 'application/json'
        },
        beforeSend: function(){
            // Show image container
            showLoadingGif( reimbursementId );
        },
        complete: function(data){
            // Hide image container
            hideLoadingGif( reimbursementId );
        }
    });
    return response;
}

const approveReimb = async (e) => {
    e.preventDefault();
    let reimbId = e.target.id.slice(10);
    console.log(reimbId)

    let token = localStorage.getItem('token');

    const res = await fetch('http://localhost:9001/api/get/users/check-session',{
        headers:{
            'token': token
        }
    });

    const user = await res.json();

    var url = `http://localhost:9001/api/put/reimbursements/approve/${user.id}/${reimbId}`;
    const data = await makeAjaxRequisition( reimbId, url );
    console.log(data);
    if(data === true){
        let status = document.getElementById(`status${reimbId}`);
        let approveBtn = document.getElementById(`approveBtn${reimbId}`);
        let denyBtn = document.getElementById(`denyBtn${reimbId}`);

        status.innerHTML = `<span id="status${reimbId}" class = "approved-status">APPROVED</span>`;
        approveBtn.style.visibility = "hidden";
        denyBtn.style.visibility = "hidden";
    }
}
