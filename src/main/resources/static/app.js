function showPage(page) {
    const pages = document.querySelectorAll('.page');
    pages.forEach(p => p.classList.remove('active-page'));
    document.getElementById(page).classList.add('active-page');
}

function showMessage(message) {
    const messageElement = document.getElementById('message');
    const content = document.getElementById('content');

    content.innerText = message;
    messageElement.classList.add('show');

    setTimeout(() => {
        messageElement.classList.remove('show');
    }, 2000);
}


function loadCategories() {
    fetch('http://localhost:8080/moviedb/genres')
        .then(response => {
            if (!response.ok) {
                throw new Error('Ağ hatası');
            }
            return response.json();
        })
        .then(categories => {
            console.log(categories);  // Kategorileri kontrol et
            const categoryCheckWrapper = document.getElementById('category');
            categoryCheckWrapper.innerHTML = '';  // Eski içerikleri temizle

            categories.forEach(category => {
                const label = document.createElement('label');
                label.className = 'form-check-label';
                label.innerHTML = category.genre;

                const checkbox = document.createElement('input');
                checkbox.className = 'form-check-input';
                checkbox.type = 'checkbox';
                checkbox.value = category.genre;

                const div = document.createElement('div');
                div.className = 'form-check';
                div.appendChild(checkbox);
                div.appendChild(label);

                categoryCheckWrapper.appendChild(div);
            });
        })
        .catch(error => {
            console.error('Kategori yükleme hatası:', error);
        });
}


function searchMovie() {
    const searchMovieId = document.getElementById('searchMovieId').value;
    const searchMovieName = document.getElementById('searchMovieName').value.trim().toLowerCase();

    let moviePromises = [];

    if (searchMovieId) {
        moviePromises.push(
            fetch(`/moviedb/movies/getbyid/${searchMovieId}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Movie not found');
                    }
                    return response.json();
                })
                .then(movie => [movie])
                .catch(error => {
                    console.error('Error fetching movie by ID:', error);
                })
        );
    }

    if (searchMovieName) {
        moviePromises.push(
            fetch(`/moviedb/movies/getname/${searchMovieName}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Movies not found');
                    }
                    return response.json();
                })
                .then(movies => movies)
                .catch(error => {
                    console.error('Error fetching movies by name:', error);
                })
        );
    }

    Promise.all(moviePromises)
        .then(results => {
            const allMovies = results.flat();
            if (allMovies.length === 0) {
                console.log('No movies found.');
                updateMovieCards([]);
            } else {
                updateMovieCards(allMovies);
            }
        })
        .catch(error => {
            console.error('Error fetching movies:', error);
        });
}

function addMovie() {
    const title = document.getElementById('movieTitle').value;
    const year = document.getElementById('movieYear').value;
    const directors = document.getElementById('addmovieDirectors').value.split(',').map(d => d.trim());
    const actors = document.getElementById('addmovieActors').value.split(',').map(a => a.trim());
    const image = document.getElementById('movieImage').value;

    const dateObj = new Date(year);
    const cmonth = dateObj.getUTCMonth() + 1;
    const cday = dateObj.getUTCDate();
    const cyear = dateObj.getUTCFullYear();

    const newDate = `${cyear}-${cmonth.toString().padStart(2, '0')}-${cday.toString().padStart(2, '0')}`;
    const newDatePost = new Date(newDate);

    const movieData = {name: title, date: newDatePost, imageurl: image};

    fetch('http://localhost:8080/moviedb/movies/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(movieData),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Film eklenirken API hatası oluştu');
            }
            return response.json(); // JSON yanıtını döndür
        })
        .then(data => {
            console.log('Film başarıyla eklendi:', data);
        })
        .catch(error => {
            console.error('Film eklenirken hata oluştu:', error);
        });


    // Rol ID'leri sabit
    const directorRoleId = 2;
    const actorRoleId = 1;

    if (directorRoleId && actorRoleId) {
        // Yönetmenleri ekleyin
        directors.forEach(director => {
            const directorData = {
                name: director,
                id: directorRoleId,
            };

            fetch('http://localhost:8080/moviedb/crews/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(directorData)
            })
                .then(response => response.json())
                .then(directorData => {
                    console.log('Yönetmen başarıyla eklendi:', directorData);
                })
                .catch(error => {
                    console.error('Yönetmen eklenirken hata oluştu:', error);
                });
        });

        // Aktörleri ekleyin
        actors.forEach(actor => {
            const actorData = {
                name: actor,
                id: actorRoleId,
            };

            fetch('http://localhost:8080/moviedb/crews/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(actorData)
            })
                .then(response => response.json())
                .then(actorData => {
                    console.log('Aktör başarıyla eklendi:', actorData);
                })
                .catch(error => {
                    console.error('Aktör eklenirken hata oluştu:', error);
                });
        });
        showMessage("film eklendi");

        document.getElementById('addMovieForm').reset();
    } else {
        console.error('Actor ve Director rol id\'leri bulunamadı.');
    }

}




function updateMovie() {
    const movieId = document.getElementById('updateMovieId').value;
    const newTitle = document.getElementById('updateMovieTitle').value;
    const newYear = document.getElementById('updateMovieYear').value;
    const newImageUrl = document.getElementById('updateMovieImage').value;

    const dateObj = new Date(newYear);
    const cmonth = dateObj.getUTCMonth() + 1;
    const cday = dateObj.getUTCDate();
    const cyear = dateObj.getUTCFullYear();

    const newDate = `${cyear}-${cmonth.toString().padStart(2, '0')}-${cday.toString().padStart(2, '0')}`;
    const newDatePost = new Date(newDate);

    const updatedMovieData = {
        name: newTitle,
        date: newDatePost,
        imageurl: newImageUrl
    };

    fetch(`http://localhost:8080/moviedb/movies/update/${movieId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedMovieData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Film bilgileri güncellenirken hata oluştu');
            }
        })
        .then(updatedMovie => {
            console.log('Film başarıyla güncellendi:', updatedMovie);

            document.getElementById('updateMovieForm').reset();
        });
    showMessage("film güncellendi")
}

function updateCrewMembers() {

    const castId = document.getElementById('updateCastId')?.value;
    const directors = document.getElementById('updateDirectors')?.value.split(',').map(d => d.trim());
    const actors = document.getElementById('updateActors')?.value.split(',').map(a => a.trim());

    const directorRoleId = 2;
    const actorRoleId = 1;

    // Eğer yönetmenler mevcutsa, onları güncellea
    if (directors && directors.length > 0) {
        directors.forEach(director => {
            const directorData = {
                name: director
            };

            fetch(`http://localhost:8080/moviedb/crews/update/${castId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(directorData)
            })
                .then(response => response.json())
                .then(directorData => {
                    console.log('Yönetmen başarıyla güncellendi:', directorData);
                    document.getElementById('updateCastForm').reset();
                });
        });
    }

    // Eğer aktörler mevcutsa, onları güncelle
    if (actors && actors.length > 0) {
        actors.forEach(actor => {
            const actorData = {
                name: actor
            };

            fetch(`http://localhost:8080/moviedb/crews/update/${castId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(actorData)
            })
                .then(response => response.json())
                .then(actorData => {
                    console.log('Aktör başarıyla güncellendi:', actorData);
                    document.getElementById('updateCastForm').reset();

                });
        });
    }
    showMessage("cast güncellendi")
    // Kadro başarıyla güncellendiğinde kullanıcıya bildirim gönder
}





document.addEventListener('DOMContentLoaded', function () {
    loadCategories();

    // Formlara event listener'ları ekleyin
    document.getElementById('addMovieForm').addEventListener('submit', function(event) {
        event.preventDefault();
        addMovie();
    });

    document.getElementById('updateCastForm').addEventListener('submit', function(event) {
        event.preventDefault();
        updateCrewMembers();
    });

    document.getElementById('updateMovieForm').addEventListener('submit', function(event) {
        event.preventDefault();
        updateMovie();
    });
});
