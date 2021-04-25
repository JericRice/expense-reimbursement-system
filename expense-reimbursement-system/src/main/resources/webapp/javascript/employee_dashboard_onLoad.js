var reimbData = null;

window.onload = async (e) => {
    e.preventDefault();

    //get user info
    let response = await fetch('http://localhost:9001/api/get/users/check-session');

    const user = await response.json();

    //get reimbs for user
    response = await fetch(`http://localhost:9001/api/get/reimbursements/${user.username}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    const data = await response.json();
    reimbData = data;

    //fill table
    let table = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];

    popRows(data, table)
    console.log(data);

}

//create bold lettering and color for status
function getStatusForHTML(status, id){
    if(status === "PENDING")
        return `<span id="status${id}" class = "pending-status">${status}</span>`
    else if(status === "APPROVED")
        return `<span id="status${id}" class = "approved-status">${status}</span>`
    else if(status === "DENIED")
        return `<span id="status${id}" class = "denied-status">${status}</span>`
}

async function downloadReceipt(e){
    console.log(e.target.id);
    console.log("clicked");

    const res = await fetch(`http://localhost:9001/api/get/reimbursements/id/${e.target.id.slice(12)}`)

    const reimbursement = await res.json();
    console.log(reimbursement);
    console.log(typeof(reimbursement.receipt));



    //download file from byte array
    //var sampleArr = base64ToArrayBuffer(reimbursement.receipt);
    //saveByteArray("Sample Receipt", sampleArr);


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

function popRows(data, table){
    data.forEach((element,index) => {
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

        col1.innerHTML = "<b>" + element.id + "</b>";
        col2.innerHTML = "$" +element.amount;
        col3.innerHTML = element.description;
        col4.innerHTML = element.type.value;
        col5.innerHTML = getStatusForHTML(element.status.value, element.id);
        col6.innerHTML = element.dateSubmitted;
        col7.innerHTML = element.dateResolved;
        col8.innerHTML = element.resolver.username;
        if(element.receipt)
            col9.innerHTML = `<i class="fas fa-file-pdf" id = "btn-receipt-${element.id}" onclick="downloadReceipt(event)"></i>`;


        //tableBody.insertRow(row);
    });
}
