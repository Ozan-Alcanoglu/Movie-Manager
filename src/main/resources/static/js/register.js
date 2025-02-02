document.getElementById('register-form').addEventListener('submit', function(event) {
    event.preventDefault();  // Normal form submit işlemi engellenir

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    const registerData = {
        name: username,
        password: password
    };

    fetch('http://localhost:8080/api/moviedb/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(registerData)
    })
    .then(response => {
        if (response.status === 200) {  // Başarılı girişte
            window.location.replace('/moviedb/home');  // Yönlendir
        } else {
            window.location.replace('/moviedb/register?error=true');  // Hatalı girişte
        }
    })
    .catch(error => {
        console.error('Bir hata oluştu:', error);
        alert('Bir hata oluştu. Lütfen tekrar deneyin.');
    });
});
