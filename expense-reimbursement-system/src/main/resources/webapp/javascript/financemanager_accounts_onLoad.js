window.onload = async (e) => {
    e.preventDefault();

    let response = await fetch('http://localhost:9001/api/get/users/check-session');

    const user = await response.json();


    response = await fetch("http://localhost:9001/api/get/users", {
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


        col1.innerHTML = "<b>" + element.id + "</b>";
        col2.innerText = element.username
        col3.innerText = element.firstname;
        col4.innerHTML = element.lastname;
        col5.innerHTML = element.email;
        col6.innerHTML = element.role.value;


        //tableBody.insertRow(row);
    });

}

