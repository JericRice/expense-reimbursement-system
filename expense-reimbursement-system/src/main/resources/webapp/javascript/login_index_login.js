
let loginForm = document.getElementById("login-form");

loginForm.onsubmit = async (event) => {
    event.preventDefault();
    let usernameInput = document.getElementById("username");
    let passwordInput = document.getElementById("password");

    const response = await fetch("http://localhost:9001/api/post/users/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: usernameInput.value,
            password: passwordInput.value
        })
    });

    console.log(response);


    const data = await response.json();
    console.log(data.token);
    console.log(data.role)
    //if user found
    if(data != "invalid credentials"){
        localStorage.setItem('token',data.token);
        if(data.role === 1){
            window.location = "/employee/dashboard";
        }else if(data.role === 2){
            window.location = "/financemanager/dashboard";
        }
    }
    console.log(data);
}
