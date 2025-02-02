async function logout() {
    try {
        const response = await fetch('/api/moviedb/logout', {
            method: 'POST',
            credentials: 'include'
        });

        if (response.ok) {
            // Başarılı logout sonrası login sayfasına yönlendir
            window.location.href = '/moviedb/login';
        } else {
            console.error('Logout işlemi başarısız oldu');
        }
    } catch (error) {
        console.error('Logout hatası:', error);
    }
} 