window.onload = async (e) => {
    e.preventDefault();

    let response = await fetch('http://localhost:9001/api/get/users/check-session');

    const user = await response.json();


    response = await fetch("http://localhost:9001/api/get/reimbursements", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const data = await response.json();

    const sortedById = data.sort((a,b) => {
        if(a.id < b.id)
            return -1;
        if(a.id > b.id)
            return 1;

        return 0;
    })
    let table = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];

    console.log(data)
    sortedById.forEach((element,index) => {
        let row = table.insertRow();
        row.classList.add("table-light");
        let col1 = row.insertCell(0);
        let col2 = row.insertCell(1);
        let col3 = row.insertCell(2);
        let col4 = row.insertCell(3);
        let col5 = row.insertCell(4);
        let col6 = row.insertCell(5);
        let col7 = row.insertCell(6);
        let col8 = row.insertCell(7);
        let col9 = row.insertCell(8);
        let col10 = row.insertCell(9);
        if(element.status.value === "PENDING"){
            col9.innerHTML = `
            <span style="color: green;">
                <i class="fas fa-thumbs-up" id = "approveBtn${element.id}" onclick = "approveReimb(event)"></i>
            </span>`
            col10.innerHTML = `
            <span style="color: tomato;">
                <i class="fas fa-thumbs-down" color="red" id = "denyBtn${element.id}" onclick = "denyReimb(event)"></i>
            </span>`

                    }
        if(element.receipt)
            col2.innerHTML = `<i class="fas fa-file-download" id = "btn-receipt-${element.id}" onclick="downloadReceipt(event)"></i>`;

        col1.innerHTML = "<b>" + element.id + "</b>";
        col3.innerHTML = "$" +element.amount;
        col4.innerHTML = element.description;
        col5.innerHTML = element.author.username;
        col6.innerHTML = element.type.value;
        col7.innerHTML = getStatusForHTML(element.status.value, element.id);
        col8.innerHTML = element.dateSubmitted;


    });

}

function getStatusForHTML(status, id){
    var htmlToReturn = "";
    if(status === "PENDING")
        htmlToReturn += `<span id="status${id}" class = "pending-status">${status}</span>`
    else if(status === "APPROVED")
        htmlToReturn += `<span id="status${id}" class = "approved-status">${status}</span>`
    else if(status === "DENIED")
        htmlToReturn += `<span id="status${id}" class = "denied-status">${status}</span>`
    htmlToReturn += `<div id='loader${id}' style='display: none;'>
                     <img src='../../images/loading2.gif' width='32px' height='32px' />
             </div>`;
    return htmlToReturn;
}

async function downloadReceipt(e){
    console.log(e.target.id);
    console.log("clicked");

    const res = await fetch(`http://localhost:9001/api/get/reimbursements/id/${e.target.id.slice(12)}`)

    const reimbursement = await res.json();
    console.log(reimbursement);
    console.log(typeof(reimbursement.receipt));




    //convert string to binary string
    var binaryString = window.atob(reimbursement.receipt);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    //create pdf blob and download
    var blob = new Blob([bytes], {type: "application/pdf"});
    console.log(blob)
    var link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    var fileName = `ReceiptID${reimbursement.id}`;
    link.download = fileName;
    link.click();
}
