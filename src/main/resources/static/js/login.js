document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('login-form');
    const errorDiv = document.createElement('div');
    errorDiv.className = 'alert alert-danger d-none';
    loginForm.insertBefore(errorDiv, loginForm.firstChild);

    loginForm.addEventListener('submit', async function(event) {
        event.preventDefault();

        const submitButton = this.querySelector('button[type="submit"]');
        const originalButtonText = submitButton.innerHTML;
        submitButton.disabled = true;
        submitButton.innerHTML = 'Loading...';

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const loginData = {
            name: username,
            password: password
        };

        try {
            console.log('Login isteği gönderiliyor:', loginData);
            const response = await fetch('http://localhost:8080/api/moviedb/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json'
                },
                body: JSON.stringify(loginData),
                credentials: 'include'
            });

            console.log('Login response status:', response.status);
            
            if (response.ok) {
                const data = await response.json();
                console.log('Login response:', data);
                
                if (data.success) {
                    console.log('Yönlendiriliyor:', data.redirectUrl);
                    window.location.href = 'http://localhost:8080' + data.redirectUrl;
                } else {
                    showError(data.message || 'Giriş başarısız.');
                }
            } else if (response.status === 401) {
                const data = await response.json();
                console.log('Login başarısız:', data);
                showError(data.message || 'Kullanıcı adı veya şifre hatalı!');
            } else {
                const data = await response.json();
                console.log('Login hatası:', data);
                showError(data.message || 'Bir hata oluştu. Lütfen tekrar deneyin.');
            }
        } catch (error) {
            console.error('Login error:', error);
            showError('Sunucuya bağlanırken bir hata oluştu. Lütfen internet bağlantınızı kontrol edin.');
        } finally {
            submitButton.disabled = false;
            submitButton.innerHTML = originalButtonText;
        }
    });

    function showError(message) {
        errorDiv.textContent = message;
        errorDiv.classList.remove('d-none');
        
        setTimeout(() => {
            errorDiv.classList.add('d-none');
        }, 5000);
    }

    document.querySelectorAll('input').forEach(input => {
        input.addEventListener('input', () => {
            errorDiv.classList.add('d-none');
        });
    });
});
