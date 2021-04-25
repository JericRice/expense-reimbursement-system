const denyReimb = async (e) => {
    e.preventDefault();
    let reimbId = e.target.id.slice(7);
    console.log(reimbId)

    let token = localStorage.getItem('token');

    const res = await fetch('http://localhost:9001/api/get/users/check-session',{
        headers:{
            'token': token
        }
    });

    const user = await res.json();

    var url = `http://localhost:9001/api/put/reimbursements/deny/${user.id}/${reimbId}`;
    const data = await makeAjaxRequisition( reimbId, url );
    console.log(data);
    if(data === true){
        let status = document.getElementById(`status${reimbId}`);
        let approveBtn = document.getElementById(`approveBtn${reimbId}`);
        let denyBtn = document.getElementById(`denyBtn${reimbId}`);

        status.innerHTML = `<span id="status${reimbId}" class = "denied-status">DENIED</span>`;
        approveBtn.style.visibility = "hidden";
        denyBtn.style.visibility = "hidden";
    }
}
